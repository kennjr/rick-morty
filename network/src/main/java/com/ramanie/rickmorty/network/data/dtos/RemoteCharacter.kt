package com.ramanie.rickmorty.network.data.dtos

import com.ramanie.rickmorty.network.domain.models.Character
import kotlinx.serialization.Serializable

/**
 * We can use the class below to make requests to the api, bc of how we've configured the client in the KtorClient file
 */

@Serializable
data class RemoteCharacter(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
){
    @Serializable
    data class Origin(
        val name: String,
        val url: String
    )

    @Serializable
    data class Location(
        val name: String,
        val url: String
    )
}

fun RemoteCharacter.toDomainCharacter(): Character {
    val characterGender = when(gender.lowercase()){
        "male" -> CharacterGender.Male
        "female" -> CharacterGender.FeMale
        "genderless" -> CharacterGender.Genderless
        else -> CharacterGender.Unknown
    }

    val characterStatus = when(status.lowercase()){
        "alive" -> CharacterStatus.Alive
        "dead" -> CharacterStatus.Dead
        else -> CharacterStatus.Unknown
    }

    return Character(created = created, episodeIds = episode.map { it.substring(it.lastIndexOf("/") + 1).toInt() },
        gender = characterGender, id = id, imageUrl = image,
        location = Character.Location(name = location.name, url = location.url),
        name = name, origin = Character.Origin(name = origin.name, url = origin.url),
        species = species, status = characterStatus, type = type)
}
