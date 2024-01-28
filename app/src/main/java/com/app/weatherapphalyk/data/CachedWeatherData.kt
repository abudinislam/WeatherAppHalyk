package com.app.weatherapphalyk.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class CachedWeatherData(
    @PrimaryKey val cityName: String,
    val temperature: Double,
    val lastUpdateTime: Long
)