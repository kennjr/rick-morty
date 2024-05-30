package com.ramanie.rickmorty.network.data.dtos

/*
    similar to an enum, a sealed class is useful when we know what exactly we'd like to store in a class
    we use it to constrain the class to the contents that we are accounting for
*/

sealed class CharacterGender(val displayName: String) {
    object Male: CharacterGender("Male")
    object FeMale: CharacterGender("FeMale")
    object Genderless: CharacterGender("No gender")
    object Unknown: CharacterGender("Not specified")
}