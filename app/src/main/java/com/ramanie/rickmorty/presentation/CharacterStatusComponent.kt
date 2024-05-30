package com.ramanie.rickmorty.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramanie.rickmorty.network.data.dtos.CharacterStatus
import com.ramanie.rickmorty.ui.theme.RickTextPrimary

@Composable
fun CharacterStatusComponent (status: CharacterStatus){
    Row(
        modifier = Modifier
            // .width(IntrinsicSize.Min)
            // .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = status.color, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(text = "Status: ${status.displayName}", fontSize = 20.sp, color = RickTextPrimary)
        // Text(text = status.displayName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun CharacterStatusComponentPreviewAlive (){
    CharacterStatusComponent(status = CharacterStatus.Alive)
}