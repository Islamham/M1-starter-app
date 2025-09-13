package com.cpen321.usermanagement.ui.screens

import Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.cpen321.usermanagement.R
import com.cpen321.usermanagement.data.remote.dto.WeatherCurrent
import com.cpen321.usermanagement.ui.components.MessageSnackbar
import com.cpen321.usermanagement.ui.components.MessageSnackbarState
import com.cpen321.usermanagement.ui.theme.LocalSpacing
import com.cpen321.usermanagement.ui.theme.Spacing
import com.cpen321.usermanagement.ui.viewmodels.ProfileViewModel
import com.cpen321.usermanagement.ui.viewmodels.WeatherUiState
import com.cpen321.usermanagement.ui.viewmodels.WeatherViewModel

private data class ViewWeatherScreenData(
    val uiState: WeatherUiState,
    val snackBarHostState: SnackbarHostState,
    val onSuccessMessageShown: () -> Unit,
    val onErrorMessageShown: () -> Unit
)

private data class ViewWeatherScreenActions(
    val onBackClick: () -> Unit,
    val onGoClick: () -> Unit,
    val onLocationChange: (String) -> Unit
)

@Composable
fun ViewWeatherScreen(
    profileViewModel: ProfileViewModel,
    weatherViewModel: WeatherViewModel,
    onBackClick: () -> Unit
) {
    val uiState by weatherViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    val actions = ViewWeatherScreenActions(
        onBackClick = onBackClick,
        onGoClick = {
            weatherViewModel.getWeather()
        },
        onLocationChange = {
            weatherViewModel.updateLocation(it)
        }
    )

    ViewWeatherContent(
        data = ViewWeatherScreenData(
            uiState = uiState,
            snackBarHostState = snackBarHostState,
            onSuccessMessageShown = profileViewModel::clearSuccessMessage,
            onErrorMessageShown = profileViewModel::clearError
        ),
        onBackClick = actions.onBackClick,
        onGoClick = actions.onGoClick,
        onLocationChange = actions.onLocationChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ViewWeatherContent(
    data: ViewWeatherScreenData,
    onBackClick: () -> Unit,
    onGoClick: () -> Unit,
    onLocationChange: (String) -> Unit,
    modifier: Modifier = Modifier

) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ViewWeatherTopBar(onBackClick = onBackClick)
        },
        snackbarHost = {
            MessageSnackbar(
                hostState = data.snackBarHostState,
                messageState = MessageSnackbarState(
                    successMessage = data.uiState.successMessage,
                    errorMessage = data.uiState.errorMessage,
                    onSuccessMessageShown = data.onSuccessMessageShown,
                    onErrorMessageShown = data.onErrorMessageShown
                )
            )
        }
    ) { paddingValues ->
        ViewWeatherBody(
            paddingValues = paddingValues,
            uiState = data.uiState,
            onGoClick = onGoClick,
            modifier = modifier,
            onLocationChange = onLocationChange,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ViewWeatherTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.view_weather),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(name = R.drawable.ic_arrow_back)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
private fun ViewWeatherBody(
    paddingValues: PaddingValues,
    uiState: WeatherUiState,
    modifier: Modifier = Modifier,
    onGoClick: () -> Unit,
    onLocationChange: (String) -> Unit,
) {
    val spacing = LocalSpacing.current
    var location by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LocationInputField(
            location = location,
            onLocationChange = {
                location = it
                onLocationChange(it)
            },
            spacing = spacing,
            onGoClick = onGoClick
        )
        WeatherContentBox(
            uiState = uiState
        )
    }
}

@Composable
private fun LocationInputField(
    location: String,
    onLocationChange: (String) -> Unit,
    spacing: Spacing,
    onGoClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = spacing.medium, vertical = spacing.small)
    ) {
        OutlinedTextField(
            value = location,
            onValueChange = onLocationChange,
            label = { Text(text = stringResource(R.string.enter_location)) },
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onGoClick,
            modifier = Modifier
                .padding(start = spacing.small)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = stringResource(R.string.go))
        }
    }
}

@Composable
private fun WeatherContentBox(
    uiState: WeatherUiState
) {
    Box(
        modifier = Modifier
    ) {
        when {
            uiState.isWeatherLoading -> {
                LoadingIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                WeatherCard(
                    weather = uiState.weather
                )
            }
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherCurrent?) {
    val spacing = LocalSpacing.current
    Card(
        modifier = Modifier
            .padding(spacing.large),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        if (weather != null) {
            Text(
                text = "Temperature: ${weather.temperature}°C",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(spacing.medium)
            )
            Text(
                text = "Humidity: ${weather.humidity}%",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(spacing.medium)
            )
            Text(
                text = "Condition: ${weather.weather_descriptions[0]}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(spacing.medium)
            )
        } else {
            Text(
                text = stringResource(R.string.no_weather_data),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(spacing.medium)
            )
        }
    }
}

@Composable
private fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(modifier = modifier)
}





