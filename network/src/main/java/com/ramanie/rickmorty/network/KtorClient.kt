package com.ramanie.rickmorty.network

import com.ramanie.rickmorty.network.data.dtos.RemoteCharacter
import com.ramanie.rickmorty.network.data.dtos.RemoteCharactersPage
import com.ramanie.rickmorty.network.data.dtos.RemoteEpisode
import com.ramanie.rickmorty.network.data.dtos.toDomainCharacter
import com.ramanie.rickmorty.network.data.dtos.toDomainCharactersPage
import com.ramanie.rickmorty.network.data.dtos.toDomainEpisode
import com.ramanie.rickmorty.network.domain.models.Character
import com.ramanie.rickmorty.network.domain.models.CharactersPage
import com.ramanie.rickmorty.network.domain.models.Episode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 *  @author kennjr
 */

class KtorClient {

    companion object{
        const val BASE_URL = "https://rickandmortyapi.com/api"
        const val CHARACTER_URL = "${BASE_URL}/character"
        const val LOCATION_URL = "${BASE_URL}/location"
        const val EPISODE_URL = "${BASE_URL}/episode"
    }

    private val client = HttpClient(OkHttp){
        // the line below will setup the base of the request, so we're just passing the base url
        defaultRequest { url(BASE_URL) }

        // with the ktor client we can install just what we need
        // below, we're installing and configuring the logging client
        install(Logging){
            logger = Logger.SIMPLE
        }

        // the most important part of setting up the client is below,
        // we tell the client how we want to receive the response from the server. in our case we want it in json
        install(ContentNegotiation){
            /* the ignoreUnknownKeys will prevent the client from throwing an error if not all of an obj.'s properties were passed */
            json(Json{ ignoreUnknownKeys = true })
        }
    }

    // the map below allows us to cache a character and save a user's data as we improve ux
    private var characterCache = mutableMapOf<Int, Character>()

    suspend fun getCharacter (id: Int): ApiOperation<Character> {
        // whenever a user make a request for a character, we're first gonna check whether the
        // character had been cached, if so we'll return the cached version else we're gonna make a
        // request for the character
        characterCache[id]?.let { return ApiOperation.Success(it) }
        /* the .body() will do the json serialisation and parsing for us based on the set ContentNegotiation */
        return safeApiCall {
            client.get("${CHARACTER_URL}/$id")
                .body<RemoteCharacter>().toDomainCharacter()
                .also {
                    // we're caching the data
                    characterCache[id] = it
                }
        }
    }

    // this fun will work for the edge-case when there's just 1 episode available eg: for Ethan of id - 114
    private suspend fun getSingleEpisode (episodeId: Int): ApiOperation<Episode>{
        return safeApiCall {
            client.get("${EPISODE_URL}/$episodeId")
                .body<RemoteEpisode>().toDomainEpisode()
        }
    }

    suspend fun getCharactersByPage (pageNum: Int): ApiOperation<CharactersPage>{
        return safeApiCall {
            client.get("${CHARACTER_URL}?page=$pageNum")
                .body<RemoteCharactersPage>().toDomainCharactersPage()
        }
    }

    suspend fun getEpisodes (episodeIds: List<Int>): ApiOperation<List<Episode>> {
        /**
         * The api allows us to make a request for multiple episodes, by passing each episode's id separated by commas
         * Below we're joining the ids in a string, separated by commas
         */
        return if (episodeIds.size == 1){
            // we've just got one episode
            getSingleEpisode(episodeIds.last()).mapSuccess { listOf(it) }
        }else if (episodeIds.size > 1){
            // we've got multiple episodes
            val idsSeparated = episodeIds.joinToString(separator = ",")
            /* the .body() will do the json serialisation and parsing for us based on the set ContentNegotiation */
            safeApiCall {
                client.get("${EPISODE_URL}/$idsSeparated")
                    .body<List<RemoteEpisode>>().map { it.toDomainEpisode() }
            }
        }else {
            // we don't have any episodes
            ApiOperation.Success(emptyList())
        }
    }

    // the inline fun below is what we use to make safe requests to the API,
    // 'safe' requests are ones that won't crash the system if an error occurs,
    // we use the try/catch to ensure that no error crashes the app
    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T>{
        return try {
            ApiOperation.Success(data = apiCall())
        }catch (e: Exception){
            ApiOperation.Failure(exception = e)
        }
    }
}

/**
 * The interface below will describe the status of an operation made
 */
sealed interface ApiOperation<T>{
    data class Success<T>(val data: T): ApiOperation<T>
    data class Failure<T>(val exception: Exception): ApiOperation<T>

    /**
     * This fun will transform our data to whatever type we want,
     * we need it to return a list from the single episode returned by the getSingleEpisode
     */
    fun <R> mapSuccess (transform: (T) -> R): ApiOperation<R>{
        return when(this){
            // in the success case we're converting the data of type T to R
            is Success -> Success(transform(data))
            is Failure -> Failure(exception)
        }
    }

    fun onSuccess(block: (T) -> Unit): ApiOperation<T>{
        /*
            We're returning the obj. itself bc we wanna have a builder pattern at the call site i.e
            we'll be able to call the fun as part of the api request e.g .onSuccess { ... }
         */
        // we're able to pass the data bc of the if check that ensures that the current API operation is of type Success
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): ApiOperation<T>{
        /*
            We're returning the obj. itself bc we wanna have a builder pattern at the call site i.e
            we'll be able to call the fun as part of the api request e.g .onFailure { ... }
         */
        if (this is Failure) block(exception)
        return this
    }
}