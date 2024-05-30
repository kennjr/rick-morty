package com.ramanie.rickmorty.presentation.charcter_details

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramanie.rickmorty.presentation.character_episodes.CharacterImageComponent
import com.ramanie.rickmorty.ui.theme.RickAction
import kotlinx.coroutines.delay

@Composable
fun CharacterDetailsScreen (
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    characterId: Int,
    onEpisodeClicked: (Int) -> Unit
){
    // we make the network call below
    LaunchedEffect(key1 = Unit) {
        delay(500)
        viewModel.getCharacters(characterId)
    }

    val state by viewModel.stateFlow.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        when(val viewState = state){
            CharacterDetailsViewState.Loading -> {
                item { LoadingState () }
            }
            is CharacterDetailsViewState.Success -> {
                // name
                item {
                    CharacterDetailsNamePlateComponent(name = viewState.character.name,
                        status = viewState.character.status)
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }

                // the image
                item {
                    CharacterImageComponent(imageUrl = viewState.character.imageUrl)
                }

                // the data about the character
                items(viewState.datapoints){
                    Spacer(modifier = Modifier.height(32.dp))
                    DataPointComponent(dataPoint = it)
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }

                // the button
                item {
                    Text(text = "View all episodes",
                        color = RickAction,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .border(width = 1.dp, color = RickAction, shape = RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                            .padding(vertical = 8.dp)
                            .clickable { onEpisodeClicked(characterId) }
                            .fillMaxWidth())
                }

                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
            is CharacterDetailsViewState.Error -> {

            }
        }
    }
}

@Composable
internal fun LoadingState (){
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 128.dp))
}