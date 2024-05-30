package com.ramanie.rickmorty.presentation.home

import com.ramanie.rickmorty.network.domain.models.Character

sealed interface HomeScreenViewState {
    object Loading: HomeScreenViewState
    data class GridDisplay(
        val characters: List<Character> = emptyList()
    ): HomeScreenViewState
}