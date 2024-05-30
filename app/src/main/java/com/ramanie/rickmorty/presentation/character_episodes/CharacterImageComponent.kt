package com.ramanie.rickmorty.presentation.character_episodes

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.ramanie.rickmorty.presentation.charcter_details.LoadingState

private val defaultModifier = Modifier
    .fillMaxWidth()
    .aspectRatio(1f)
    .clip(RoundedCornerShape(12.dp))

@Composable
fun CharacterImageComponent (imageUrl: String, modifier: Modifier = defaultModifier){
    SubcomposeAsyncImage(model = imageUrl,
        contentDescription = "An image of the character",
        modifier = modifier,
        loading = { LoadingState() }
    )
}