package com.ramanie.rickmorty.presentation.charcter_details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ramanie.rickmorty.domain.models.DataPoint
import com.ramanie.rickmorty.ui.theme.RickAction
import com.ramanie.rickmorty.ui.theme.RickTextPrimary

@Composable
fun DataPointComponent (dataPoint: DataPoint){
    Column {
        Text(text = dataPoint.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = RickAction)

        Text(text = dataPoint.description,
            fontSize = 24.sp,
            color = RickTextPrimary)
    }
}