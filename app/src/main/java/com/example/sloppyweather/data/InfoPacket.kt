package com.example.sloppyweather.data

// 어떤 값들을 담는 데이터 구조
data class InfoPacket(
    val valueA: Double?, // 온도 같은 값인가?
    val condition: String?, // 날씨 상태?
    val metricB: Int? // 습도? 가끔 null임
) 