package com.ramanie.rickmorty.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramanie.rickmorty.presentation.charcter_details.LoadingState
import com.ramanie.rickmorty.presentation.components.CharacterGridItem

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onCharacterSelected: (Int) -> Unit,
    modifier: Modifier = Modifier) {

    val viewState by viewModel.viewState.collectAsState()

    val scrollState = rememberLazyGridState()

    val getNextPage: Boolean by remember {
        derivedStateOf {
            val currentCharacterCount = (viewState as? HomeScreenViewState.GridDisplay)?.characters?.size ?: return@derivedStateOf false
            val lastDisplayedIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@derivedStateOf false

            return@derivedStateOf lastDisplayedIndex >= currentCharacterCount.minus(10)
        }
    }

    LaunchedEffect(key1 = getNextPage) {
        if (getNextPage) viewModel.getNextCharactersPage()
    }

    when(val state = viewState){
        is HomeScreenViewState.GridDisplay -> {
            LazyVerticalGrid(state = scrollState, columns = GridCells.Fixed(2), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp), contentPadding = PaddingValues(all = 16.dp), content = {
                items(items = state.characters, key = { it.id }){ character ->
                    CharacterGridItem(character = character, modifier = Modifier) {
                        onCharacterSelected(character.id)
                    }
                }
            })
        }
        HomeScreenViewState.Loading -> {
            LoadingState()
        }
    }

}