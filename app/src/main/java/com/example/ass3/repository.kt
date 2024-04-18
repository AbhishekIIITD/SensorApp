package com.example.ass3

sealed interface DataEvents{
    data class addData(val data: OrientationData) : DataEvents
}