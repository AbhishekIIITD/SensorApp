package com.example.ass3

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.ass3.ui.theme.Ass3Theme
import kotlin.math.atan2
import kotlin.math.log
import kotlin.math.sqrt


class MainActivity : ComponentActivity() , SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private val _realTimeSensorData = mutableStateOf<FloatArray?>(null)
    val realTimeSensorData get() = _realTimeSensorData

    private val _saveInterval = mutableStateOf(5L) // Default interval of 5 seconds
    private val _isSavingData = mutableStateOf(false)
    private val intervalOptions = listOf(1L, 5L, 10L, 30L, 60L)



    private val _isRealTimeScreen = mutableStateOf(true)
    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            InventoryDatabase::class.java,
            "WeatherDb"
        ).build()
    }

    private val viewModel by viewModels<OrientationViewModel> (
        factoryProducer={
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return OrientationViewModel(db.Dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!

        setContent {
            Ass3Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val state by viewModel.data.collectAsState()
                    Column(modifier = Modifier ,horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround) {
                        if (_isRealTimeScreen.value) {
//                        Log.d("main",realTimeSensorData.value.toString())
                            RealTimeDataScreen(realTimeSensorData.value)
                        } else {
                            GraphScreen(viewModel)
                        }


                        var isIntervalDropdownExpanded by remember { mutableStateOf(false) }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Save Interval: ${_saveInterval.value} seconds")
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { isIntervalDropdownExpanded = true }) {
                                Text("Select Interval")
                            }

                            DropdownMenu(
                                expanded = isIntervalDropdownExpanded,
                                onDismissRequest = { isIntervalDropdownExpanded = false },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                intervalOptions.forEach { interval ->
                                   DropdownMenuItem(text = {"igigg s" }, onClick = {
                                       _saveInterval.value = interval
                                       isIntervalDropdownExpanded = false
                                   },modifier=Modifier
                                   )
                                }
                            }
                        }

                        Button(
                            onClick = { _isRealTimeScreen.value = !_isRealTimeScreen.value },
                            modifier = Modifier
                        ) {
                            Text(if (_isRealTimeScreen.value) "Show Graph" else "Show Real-time Data")
                        }

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val pitch = atan2(-x, sqrt(y * y + z * z)) * (180 / Math.PI)
            val roll = atan2(y, sqrt(x * x + z * z)) * (180 / Math.PI)
            val yaw = atan2(sqrt(x * x + y * y), z) * (180 / Math.PI)

            val sensorData = SensorData(
                pitch = pitch,
                roll = roll,
                yaw = yaw
            )

            val temp = floatArrayOf(pitch.toFloat(), roll.toFloat(), yaw.toFloat())
            if(_realTimeSensorData.value==null || !temp.contentEquals(_realTimeSensorData.value)){
                _realTimeSensorData.value = temp
                viewModel.onSensorChanged(sensorData)
            }
            }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ass3Theme {
        Greeting("Android")
    }
}