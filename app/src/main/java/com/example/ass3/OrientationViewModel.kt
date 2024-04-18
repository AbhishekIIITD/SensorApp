package com.example.ass3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn

class OrientationViewModel(private val dao: OrientationDao) : ViewModel() {

    val data = dao.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//    val delete=dao.deleteAll()
    fun onSensorChanged(sensorData: SensorData) {
        val orientationData = OrientationData(
            pitch = sensorData.pitch,
            roll = sensorData.roll,
            yaw = sensorData.yaw
        )

        viewModelScope.launch {
            //dao.deleteAll()
            dao.insert(orientationData)
        }
    }

    fun onEvent(event: DataEvents) {
        when (event) {
            is DataEvents.addData -> {
                viewModelScope.launch { dao.insert(event.data) }
            }
            // Handle other events as needed
        }
    }
}
