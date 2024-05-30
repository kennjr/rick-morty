package com.ramanie.rickmorty.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramanie.rickmorty.network.data.dtos.CharacterStatus


@Composable
fun CharacterStatusCircle(modifier: Modifier = Modifier,
                          characterStatus: CharacterStatus
) {
    Box(modifier = modifier
        .background(
            brush = Brush.radialGradient(listOf(Color.Black, Color.Transparent)),
            shape = CircleShape
        )
        .size(16.dp),
        contentAlignment = Alignment.Center){
        Box (modifier = Modifier
            .padding(all = 4.dp)
            .size(10.dp)
            .background(characterStatus.color, shape = CircleShape))
    }
}

@Preview
@Composable
private fun CharacterStatusCirclePreview(){
    Column {
        CharacterStatusCircle(characterStatus = CharacterStatus.Dead)
        Spacer(modifier = Modifier.height(12.dp))
        CharacterStatusCircle(characterStatus = CharacterStatus.Alive)
        Spacer(modifier = Modifier.height(12.dp))
        CharacterStatusCircle(characterStatus = CharacterStatus.Unknown)
        Spacer(modifier = Modifier.height(12.dp))
    }
}