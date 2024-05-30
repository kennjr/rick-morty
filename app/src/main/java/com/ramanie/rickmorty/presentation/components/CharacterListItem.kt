package com.ramanie.rickmorty.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramanie.rickmorty.domain.models.DataPoint
import com.ramanie.rickmorty.network.domain.models.Character
import com.ramanie.rickmorty.presentation.character_episodes.CharacterImageComponent
import com.ramanie.rickmorty.presentation.charcter_details.DataPointComponent
import com.ramanie.rickmorty.ui.theme.RickAction

@Composable
fun CharacterListItem(modifier: Modifier = Modifier,
                      character: Character,
                      characterDataPoints: List<DataPoint>,
                      onClick: () -> Unit) {
    Row(
        // using the param modifier will allow parent styling to influence this composable
        modifier = modifier
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(Color.Transparent, RickAction)),
                shape = RoundedCornerShape(12.dp)
            )
            // we're adding this clip so that the ripple caused by the onclick is of the same shape as the element
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }) {
        Box {
            CharacterImageComponent(imageUrl = character.imageUrl, modifier = Modifier.aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)))
            CharacterStatusCircle(characterStatus = character.status, modifier = Modifier.padding(start = 6.dp, top = 6.dp))
        }
        LazyHorizontalGrid(rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            content = {
            items(items = listOf(DataPoint(title = "Name", description = character.name)) + characterDataPoints,
                key = { it.hashCode() }){
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(end = 16.dp)) {
                    DataPointComponent(dataPoint = it)
                }
            }
        })
    }
}

private fun sanitizeDataPointForDisplay (dataPoint: DataPoint): DataPoint{
    val newDescription = if(dataPoint.description.length > 14){
        dataPoint.description.take(12) + "..."
    }else{
        dataPoint.description
    }
    return dataPoint.copy(description = newDescription)
}
