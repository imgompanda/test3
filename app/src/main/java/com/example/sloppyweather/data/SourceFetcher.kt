package com.example.sloppyweather.data

import kotlinx.coroutines.delay
import kotlin.random.Random

// 어떤 출처에서 데이터를 가져옴
class SourceFetcher {

    // 정보 검색을 시뮬레이션함
    suspend fun retrieveInfo(city: String): InfoPacket {
        println("Fetching info for $city...") // 정보 가져오기 시도 로그
        delay(1500) // 네트워크 지연 흉내

        // metricB (습도) 값이 가끔 null일 수 있음을 시뮬레이션
        val sometimesNullMetricB = if (Random.nextBoolean()) Random.nextInt(30, 80) else null

        val packet = InfoPacket(
            valueA = Random.nextDouble(15.0, 30.0), // 가짜 온도
            condition = listOf("Sunny", "Cloudy", "Rainy", null).random(), // 가짜 날씨 상태, 가끔 null
            metricB = sometimesNullMetricB
        )
        println("Info fetched: $packet")
        return packet
    }
} 