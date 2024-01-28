package com.app.weatherapphalyk.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.weatherapphalyk.data.WeatherRepository
import com.app.weatherapphalyk.domain.WeatherEntity
import com.app.weatherapphalyk.ui.theme.WeatherAppHalykTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherScreen(viewModel: MainViewModel) {
    val weatherList by viewModel.weatherData.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Список погоды
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        ) {
            weatherList?.let { list ->
                if (list.isEmpty()) {
                    item {
                        Text(
                            "Нет данных. Попробуйте изменить запрос.",
                            Modifier.padding(16.dp)
                        )
                    }
                } else {
                    items(list) { weather ->
                        CityWeatherItem(weather)
                    }
                }
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                viewModel.onSearchQueryChanged(query)
            },
            label = { Text("Введите название города") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
    }
}


@Composable
fun CityWeatherItem(cityWeather: WeatherEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = cityWeather.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "${cityWeather.temperature}°C", fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun CityWeatherItemPreview() {
    WeatherAppHalykTheme {
        CityWeatherItem(
            cityWeather = WeatherEntity(
                name = "Almaty",
                temperature = 15.0
            )
        )
    }
}