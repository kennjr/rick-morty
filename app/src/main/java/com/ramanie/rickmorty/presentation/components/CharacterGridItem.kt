package com.ramanie.rickmorty.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramanie.rickmorty.network.domain.models.Character
import com.ramanie.rickmorty.presentation.character_episodes.CharacterImageComponent
import com.ramanie.rickmorty.ui.theme.RickAction

@Composable
fun CharacterGridItem(modifier: Modifier = Modifier,
                      character: Character,
                      onClick: () -> Unit) {
    Column(
        // using the param modifier will allow parent styling to influence this composable
        modifier = modifier
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(listOf(Color.Transparent, RickAction)),
                shape = RoundedCornerShape(12.dp)
            )
            // we're adding this clip so that the ripple caused by the onclick is of the same shape as the element
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }) {
        Box {
            CharacterImageComponent(imageUrl = character.imageUrl)
            CharacterStatusCircle(characterStatus = character.status, modifier = Modifier.padding(start = 6.dp, top = 6.dp))
        }
        Text(
            text = character.name,
            color = RickAction,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            lineHeight = 26.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        )
    }
}
