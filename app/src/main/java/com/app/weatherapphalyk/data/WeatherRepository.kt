package com.app.weatherapphalyk.data

import com.app.weatherapphalyk.domain.WeatherEntity

class WeatherRepository(private val weatherApi: WeatherApi, private val weatherDao: WeatherDao) {
    companion object {
        const val apiKey = "63433dd7e9d0b1ce34323f1550c265f1"
        const val CACHE_LIFETIME = 3600 * 1000
    }

    suspend fun getWeatherForCity(cityName: String): WeatherEntity? {
        val currentTime = System.currentTimeMillis()
        val cachedData = weatherDao.getWeatherData(cityName)

        if (cachedData != null && currentTime - cachedData.lastUpdateTime < CACHE_LIFETIME) {
            return WeatherEntity(cachedData.cityName, cachedData.temperature)
        }

        return try {
            val response = weatherApi.getWeather(cityName, apiKey)
            val weatherEntity = response.toEntity()

            weatherDao.insertWeatherData(CachedWeatherData(cityName, weatherEntity.temperature, currentTime))
            weatherEntity
        } catch (e: Exception) {
            null
        }
    }
}

