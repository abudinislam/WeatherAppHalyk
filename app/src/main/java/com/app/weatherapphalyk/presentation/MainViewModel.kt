package com.app.weatherapphalyk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weatherapphalyk.data.WeatherRepository
import com.app.weatherapphalyk.domain.WeatherEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MainViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherData = MutableStateFlow<List<WeatherEntity>?>(null)
    val weatherData: StateFlow<List<WeatherEntity>?> = _weatherData

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .filter { it.length >= 2 }
                .collect { query ->
                    fetchWeatherData(query)
                }
            val lastCachedData = repository.getLastCachedData()
            lastCachedData?.let {
                fetchWeatherData(it.cityName)
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    private fun fetchWeatherData(query: String) {
        viewModelScope.launch {
            try {
                val weatherEntity = repository.getWeatherForCity(query)
                _weatherData.value = weatherEntity?.let { listOf(it) } ?: emptyList()
            } catch (_: Exception) {
                _weatherData.value = null
            }
        }
    }

    init {
        fetchWeatherData("initialCityName")
    }
}