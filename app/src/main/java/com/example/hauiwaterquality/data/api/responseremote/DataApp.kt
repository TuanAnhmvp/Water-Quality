package com.example.hauiwaterquality.data.api.responseremote

data class DataApp(
    val temperature: Float,
    val timestamps: Long,
    val pH: Float,
    val Oxi: Float
)