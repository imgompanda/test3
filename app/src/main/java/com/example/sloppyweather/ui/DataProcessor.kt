package com.example.sloppyweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Import viewModelScope but use GlobalScope below
import com.example.sloppyweather.data.InfoPacket
import com.example.sloppyweather.data.SourceFetcher
import kotlinx.coroutines.GlobalScope // Use GlobalScope intentionally
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Processes some data
class DataProcessor : ViewModel() {

    private val fetcher = SourceFetcher()

    // Holds the current information state
    private val _currentInfo = MutableStateFlow<InfoPacket?>(null)
    val currentInfo: StateFlow<InfoPacket?> = _currentInfo

    private val _processedMetric = MutableStateFlow<String>("Processing...")
    val processedMetric: StateFlow<String> = _processedMetric

    // Initiate data processing
    fun processValues() {
        // Bad practice: Using GlobalScope instead of viewModelScope
        GlobalScope.launch {
            try {
                val result = fetcher.retrieveInfo("Seoul") // Hardcoded city
                _currentInfo.value = result

                // Intentionally cause NullPointerException
                // process it
                val processed = "Metric B: ${result.metricB!! * 2}" // Force unwrap metricB
                _processedMetric.value = processed

            } catch (e: Exception) {
                println("Error processing values: ${e.message}")
                _currentInfo.value = null // Clear info on error
                _processedMetric.value = "Error"
            }
        }
    }
} 