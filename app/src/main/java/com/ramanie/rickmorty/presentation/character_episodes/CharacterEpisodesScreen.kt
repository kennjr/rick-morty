package com.ramanie.rickmorty.presentation.character_episodes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramanie.rickmorty.domain.models.DataPoint
import com.ramanie.rickmorty.network.KtorClient
import com.ramanie.rickmorty.network.domain.models.Character
import com.ramanie.rickmorty.network.domain.models.Episode
import com.ramanie.rickmorty.presentation.charcter_details.CharacterNameComponent
import com.ramanie.rickmorty.presentation.charcter_details.DataPointComponent
import com.ramanie.rickmorty.presentation.charcter_details.LoadingState
import com.ramanie.rickmorty.ui.theme.RickPrimary
import com.ramanie.rickmorty.ui.theme.RickTextPrimary
import kotlinx.coroutines.launch

@Composable
fun CharacterEpisodesScreen( ktorClient: KtorClient, characterId: Int ){
    var characterState by remember {
        mutableStateOf<Character?>(null)
    }

    var episodesState by remember {
        mutableStateOf<List<Episode>>(emptyList())
    }

    LaunchedEffect(key1 = Unit) {
        ktorClient.getCharacter(characterId)
            .onSuccess {
                characterState = it
                launch {
                    ktorClient.getEpisodes(it.episodeIds)
                        .onSuccess { episodes ->
                            episodesState = episodes
                        }
                        .onFailure {
                            // TODO: handle this error
                        }
                }
            }
            .onFailure {
                // TODO: handle this error
            }
    }

    characterState?.let {
        MainCharacterEpisodeScreen(character = it, episodes = episodesState)
    } ?: LoadingState()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainCharacterEpisodeScreen (character: Character, episodes: List<Episode>){
    val episodeGroups = episodes.groupBy { it.seasonNumber }

    LazyColumn(contentPadding = PaddingValues(all = 16.dp)) {
        item { CharacterNameComponent(name = character.name) }
        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
                episodeGroups.forEach{
                    val title = "Season ${it.key}"
                    val description = "${it.value.size} ep"
                    item { DataPointComponent(dataPoint = DataPoint(title, description)) }
                    item { Spacer(modifier = Modifier.width(32.dp)) }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { CharacterImageComponent (imageUrl = character.imageUrl) }

        item { Spacer(modifier = Modifier.height(32.dp)) }

        episodeGroups.forEach{ episodesInSeason ->
            stickyHeader { SeasonHeader(seasonNumber = episodesInSeason.key) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(episodesInSeason.value){ episode ->
                EpisodeRowComponent(episode = episode)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SeasonHeader (seasonNumber: Int){
    Row (modifier = Modifier
        .fillMaxWidth()
        // .background(Color.Transparent)
        .padding(top = 8.dp, bottom = 16.dp)){
        Text(
            text = "Season $seasonNumber",
            color = RickTextPrimary,
            fontSize = 32.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = RickPrimary)
                .border(width = 1.dp, color = RickTextPrimary, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 4.dp)
        )
    }
}