package com.app.weatherapphalyk

import android.app.Application
import androidx.room.Room
import com.app.weatherapphalyk.data.AppDatabase
import com.app.weatherapphalyk.data.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp : Application() {
    lateinit var weatherApi: WeatherApi
        private set

    lateinit var database: AppDatabase


    override fun onCreate() {
        super.onCreate()
        weatherApi = createWeatherApi()

        database = Room.databaseBuilder(this, AppDatabase::class.java, "weather_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun createWeatherApi(): WeatherApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(WeatherApi::class.java)
    }


}
