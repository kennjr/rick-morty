package com.ramanie.rickmorty.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramanie.rickmorty.data.repo.CharacterRepository
import com.ramanie.rickmorty.network.domain.models.CharactersPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor( val characterRepository: CharacterRepository ): ViewModel() {

    private val _viewState = MutableStateFlow<HomeScreenViewState>(HomeScreenViewState.Loading)
    val viewState: StateFlow<HomeScreenViewState> = _viewState.asStateFlow()

    private val getCharacterPages = mutableListOf<CharactersPage>()

    init {
        getCharactersPage(1, true)
    }

    private fun getCharactersPage (pageNum: Int, isInitial: Boolean = false) = viewModelScope.launch {
        characterRepository.getCharactersPage(pageNum)
            .onSuccess { charactersPage ->
                if (!isInitial) getCharacterPages.clear()
                getCharacterPages.add(charactersPage)
                _viewState.update { currentState ->
                    val currentCharacters = (currentState as? HomeScreenViewState.GridDisplay)?.characters ?: emptyList()
                    return@update HomeScreenViewState.GridDisplay(characters = currentCharacters + charactersPage.characters) }
            }
            .onFailure {
                // todo
            }
    }

    fun getNextCharactersPage (){
        val pageNum = getCharacterPages.size.plus(1)
        getCharactersPage(pageNum)
    }

}