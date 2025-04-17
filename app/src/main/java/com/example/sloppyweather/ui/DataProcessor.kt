package com.example.sloppyweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // viewModelScope 임포트했지만 아래선 GlobalScope 사용
import com.example.sloppyweather.data.InfoPacket
import com.example.sloppyweather.data.SourceFetcher
import kotlinx.coroutines.GlobalScope // 의도적으로 GlobalScope 사용
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 어떤 데이터를 처리함
class DataProcessor : ViewModel() {

    private val fetcher = SourceFetcher()

    // 현재 정보 상태를 담음
    private val _currentInfo = MutableStateFlow<InfoPacket?>(null)
    val currentInfo: StateFlow<InfoPacket?> = _currentInfo

    private val _processedMetric = MutableStateFlow<String>("처리 중...") // 초기값 변경
    val processedMetric: StateFlow<String> = _processedMetric

    // 데이터 처리 시작
    fun processValues() {
        // 좋지 않은 방법: viewModelScope 대신 GlobalScope 사용
        GlobalScope.launch {
            try {
                val result = fetcher.retrieveInfo("Seoul") // 하드코딩된 도시
                _currentInfo.value = result

                // 의도적으로 NullPointerException 발생시키기
                // 처리하기
                val processed = "지표 B 처리됨: ${result.metricB!! * 2}" // metricB 강제 언래핑
                _processedMetric.value = processed

            } catch (e: Exception) {
                println("값 처리 중 오류: ${e.message}")
                _currentInfo.value = null // 오류 시 정보 초기화
                _processedMetric.value = "오류 발생"
            }
        }
    }
} 