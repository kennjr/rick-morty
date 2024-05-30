package com.ramanie.rickmorty.data.repo

import com.ramanie.rickmorty.network.ApiOperation
import com.ramanie.rickmorty.network.KtorClient
import com.ramanie.rickmorty.network.domain.models.Character
import com.ramanie.rickmorty.network.domain.models.CharactersPage
import com.ramanie.rickmorty.network.domain.models.Episode
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val ktorClient: KtorClient) {

    suspend fun getEpisodes (episodeIds: List<Int>): ApiOperation<List<Episode>>{
        return ktorClient.getEpisodes(episodeIds)
    }

    suspend fun getCharacter (characterId: Int): ApiOperation<Character>{
        return ktorClient.getCharacter(characterId)
    }

    suspend fun getCharactersPage (pageNum: Int): ApiOperation<CharactersPage>{
        return ktorClient.getCharactersByPage(pageNum)
    }

}