package com.ramanie.rickmorty.network.data.dtos

import androidx.compose.ui.graphics.Color

/*
    similar to an enum, a sealed class is useful when we know what exactly we'd like to store in a class
    we use it to constrain the class to the contents that we are accounting for
 */

sealed class CharacterStatus(val displayName: String, val color: Color) {
    object Alive: CharacterStatus("Alive", Color.Green)
    object Dead: CharacterStatus("Dead", Color.Red)
    object Unknown: CharacterStatus("Unknown", Color.Yellow)
}