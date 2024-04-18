package com.example.ass3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun RealTimeDataScreen(sensorData: FloatArray?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        if(sensorData==null){
            Text("error in fetching data")
        }else{
            SensorAngle("Pitch", sensorData?.get(1) ?: 0f)
            SensorAngle("Roll", sensorData?.get(0) ?: 0f)
            SensorAngle("Yaw", sensorData?.get(2) ?: 0f)
        }

    }
}


@Composable
fun SensorAngle(name: String, value: Float) {
    Text(
        text = "$name: ${value.roundToInt()}°",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}