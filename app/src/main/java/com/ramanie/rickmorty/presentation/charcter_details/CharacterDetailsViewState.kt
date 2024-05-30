package com.ramanie.rickmorty.presentation.charcter_details

import com.ramanie.rickmorty.domain.models.DataPoint
import com.ramanie.rickmorty.network.domain.models.Character

/**
 * We'll use this interface to describe the different states that the details screen can be in
 * This is a way to build a data-driven UI, bc we can use a when clause to handle each case in the frontend, easily
 */

sealed interface CharacterDetailsViewState {
    object Loading: CharacterDetailsViewState
    data class Error(val msg: String): CharacterDetailsViewState
    data class Success(val character: Character, val datapoints: List<DataPoint>): CharacterDetailsViewState
}