package com.app.weatherapphalyk.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CachedWeatherData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}