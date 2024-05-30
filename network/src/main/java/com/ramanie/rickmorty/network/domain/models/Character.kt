package com.ramanie.rickmorty.network.domain.models

import com.ramanie.rickmorty.network.data.dtos.CharacterGender
import com.ramanie.rickmorty.network.data.dtos.CharacterStatus

data class Character(
    val created: String,
    val episodeIds: List<Int>,
    val gender: CharacterGender,
    val id: Int,
    val imageUrl: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: CharacterStatus,
    val type: String
){
    data class Origin(
        val name: String,
        val url: String
    )

    data class Location(
        val name: String,
        val url: String
    )

    override fun toString(): String {
        return "id => $id,\ncreated => $created,\nepisodeUrls => $episodeIds,\ngender => ${gender.displayName},\nimageUrl => $imageUrl," +
                "\nlocation => ${location.name},\nname => $name,\norigin => ${origin.name},\nspecies => $species,\nstatus => ${status.displayName}," +
                "\ntype => $type"
    }
}
