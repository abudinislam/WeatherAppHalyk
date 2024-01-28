package com.app.weatherapphalyk.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_data WHERE cityName = :cityName")
    suspend fun getWeatherData(cityName: String): CachedWeatherData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherData(weatherData: CachedWeatherData)

}