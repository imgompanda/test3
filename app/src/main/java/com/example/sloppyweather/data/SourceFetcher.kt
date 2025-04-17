package com.example.sloppyweather.data

import kotlinx.coroutines.delay
import kotlin.random.Random

// Fetches data from a source
class SourceFetcher {

    // Simulates retrieving information
    suspend fun retrieveInfo(city: String): InfoPacket {
        println("Fetching info for $city...") // Log fetch attempt
        delay(1500) // Simulate network delay

        // Simulate potential null value for metricB (humidity)
        val sometimesNullMetricB = if (Random.nextBoolean()) Random.nextInt(30, 80) else null

        val packet = InfoPacket(
            valueA = Random.nextDouble(15.0, 30.0), // Fake temperature
            condition = listOf("Sunny", "Cloudy", "Rainy", null).random(), // Fake weather status, sometimes null
            metricB = sometimesNullMetricB
        )
        println("Info fetched: $packet")
        return packet
    }
} 