package com.ramanie.rickmorty.network.domain.models

data class CharactersPage(
    val info: Info,
    val characters: List<Character>
){
    data class Info(
        val count: Int,
        val pages: Int,
        val next: String?,
        val prev: String?
    )
}
