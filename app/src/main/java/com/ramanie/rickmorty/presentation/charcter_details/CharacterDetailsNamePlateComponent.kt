package com.ramanie.rickmorty.presentation.charcter_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ramanie.rickmorty.network.data.dtos.CharacterStatus
import com.ramanie.rickmorty.presentation.CharacterStatusComponent
import com.ramanie.rickmorty.ui.theme.RickAction

@Composable
fun CharacterDetailsNamePlateComponent (name: String, status: CharacterStatus){
    Column (modifier = Modifier.fillMaxWidth()){
        CharacterStatusComponent(status = status)
        CharacterNameComponent(name = name)
    }
}

@Composable
fun CharacterNameComponent (name: String){
    Text(text = name, fontSize = 42.sp,
        fontWeight = FontWeight.Bold, color = RickAction)
}