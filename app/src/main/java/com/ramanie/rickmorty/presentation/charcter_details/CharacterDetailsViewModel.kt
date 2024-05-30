package com.ramanie.rickmorty.presentation.charcter_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramanie.rickmorty.data.repo.CharacterRepository
import com.ramanie.rickmorty.domain.models.DataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor( val repository: CharacterRepository ): ViewModel() {

    // the var. below is what will inform the state of the ui and the views shown to the user
    private val _internalStateFlow = MutableStateFlow<CharacterDetailsViewState>(value = CharacterDetailsViewState.Loading)
    val stateFlow = _internalStateFlow.asStateFlow()

    fun getCharacters (charId: Int) = viewModelScope.launch{
        _internalStateFlow.update { return@update CharacterDetailsViewState.Loading }
        repository.getCharacter(charId)
            .onSuccess { char ->
                val dataPoints = buildList {
                    add(DataPoint("Last known location", char.location.name))
                    add(DataPoint("Species", char.species))
                    add(DataPoint("Gender", char.gender.displayName))
                    char.type.takeIf { it.isNotEmpty() }?.let { type ->
                        add(DataPoint("Type", type))
                    }
                    add(DataPoint("Origin", char.origin.name))
                    add(DataPoint("Episode count", char.episodeIds.count().toString()))
                }

                _internalStateFlow.update {
                    return@update CharacterDetailsViewState.Success(character = char, datapoints = dataPoints)
                }
            }
            .onFailure { exception ->
                _internalStateFlow.update {
                    return@update CharacterDetailsViewState.Error(exception.localizedMessage ?: exception.message ?: "An unexpected error occurred")
                }
            }
    }

}