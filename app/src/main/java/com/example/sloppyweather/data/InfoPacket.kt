package com.example.sloppyweather.data

// Data structure holding some values
data class InfoPacket(
    val valueA: Double?, // Represents temperature maybe?
    val condition: String?, // Weather status?
    val metricB: Int? // Humidity? Sometimes null
) 