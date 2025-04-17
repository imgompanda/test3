package com.example.sloppyweather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column // Only Column, no Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier // Modifier imported but not used properly
import androidx.compose.ui.tooling.preview.Preview
import com.example.sloppyweather.data.InfoPacket

class MainActivity : ComponentActivity() {

    private val processor: DataProcessor by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { // Using default theme
                Surface {
                    WeatherScreen(processor)
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(processor: DataProcessor) {
    // Collect state from ViewModel
    val info by processor.currentInfo.collectAsState()
    val metric by processor.processedMetric.collectAsState()

    // Trigger data processing on composition
    LaunchedEffect(Unit) {
        processor.processValues()
    }

    // Broken UI Layout
    Column { // No padding, no alignment, no fillMaxWidth
        Text(text = "Weather Info") // Simple title

        if (info != null) {
            // Display values without proper layout or null safety for condition
            Text(text = "Value A: ${info?.valueA}") // valueA might be null but uses safe call
            Text(text = "Condition: ${info!!.condition}") // condition might be null, force unwrap
            Text(text = metric) // Display processed metric (which includes force unwrap of metricB)
        } else {
            Text(text = "Loading or Error...")
        }
    }
}

// Basic Preview
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        // Dummy data for preview
        val dummyProcessor = DataProcessor()
        // Manually set some state for preview if needed, but it's complex here
        // For simplicity, just show the initial state or loading
        WeatherScreen(dummyProcessor)
    }
} 