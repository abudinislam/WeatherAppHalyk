package com.app.weatherapphalyk.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.app.weatherapphalyk.MyApp
import com.app.weatherapphalyk.data.WeatherRepository
import com.app.weatherapphalyk.ui.theme.WeatherAppHalykTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val weatherApi = (application as MyApp).weatherApi
            val app = application as MyApp
            val weatherDao = app.database.weatherDao()
            val repository = WeatherRepository(weatherApi, weatherDao)
            viewModel = MainViewModel(repository)
            WeatherAppHalykTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen(viewModel = viewModel)
                }
            }
        }
    }
}
