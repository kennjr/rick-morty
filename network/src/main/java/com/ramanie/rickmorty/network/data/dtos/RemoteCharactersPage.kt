package com.ramanie.rickmorty.network.data.dtos

import com.ramanie.rickmorty.network.domain.models.CharactersPage
import kotlinx.serialization.Serializable

@Serializable
data class RemoteCharactersPage(
    val info: Info,
    val results: List<RemoteCharacter>
){
    @Serializable
    data class Info(
        val count: Int,
        val pages: Int,
        val next: String?,
        val prev: String?
    )
}

fun RemoteCharactersPage.toDomainCharactersPage(): CharactersPage {
    return CharactersPage(
        info = CharactersPage.Info(
            count = info.count,
            pages = info.pages,
            next = info.next,
            prev = info.prev,
        ),
        characters = results.map { it.toDomainCharacter() }
    )
}
