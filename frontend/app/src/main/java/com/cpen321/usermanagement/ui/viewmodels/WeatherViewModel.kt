package com.cpen321.usermanagement.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpen321.usermanagement.data.remote.dto.User
import com.cpen321.usermanagement.data.remote.dto.WeatherCurrent
import com.cpen321.usermanagement.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



data class WeatherUiState(
    // Loading states
    val isWeatherLoading: Boolean = false,
    val isWeatherLoaded: Boolean = false,
    val weather: WeatherCurrent? = null,

    // Data states
    val user: User? = null,

    // location
    val location: String = "",

//    //
//    val isCheckingWeatherState: Boolean = false,

    // Message states
    val errorMessage: String? = null,
    val successMessage: String? = null,
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    companion object {
        private const val TAG = "WeatherViewModel"
    }

    fun getWeather() {
        try {
            _uiState.value = _uiState.value.copy(
                isWeatherLoading = true,
                isWeatherLoaded = false,
                location = uiState.value.location
            )
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isWeatherLoading = true)

                val weather = weatherRepository.getWeather(uiState.value.location)
                _uiState.value = _uiState.value.copy(
                    isWeatherLoading = false,
                    isWeatherLoaded = true,
                    weather = weather.getOrNull()
                )
                Log.d(TAG, "Weather: $weather")
            }
//        } catch (e: java.net.SocketTimeoutException) {
//            handleAuthError("Network timeout. Please check your connection.", e)
//        } catch (e: java.net.UnknownHostException) {
//            handleAuthError("No internet connection. Please check your network.", e)
//        }
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }

    fun updateLocation(it: String) {
        _uiState.value = _uiState.value.copy(
            location = it
        )
    }

//    private fun handleAuthError(errorMessage: String, exception: Exception) {
//        Log.e(WeatherViewModel.Companion.TAG, "Authentication check failed: $errorMessage", exception)
//        _uiState.value = _uiState.value.copy(
//            isWeatherLoading = false,
//            isWeatherLoaded = false,
//            weather = null,
//            errorMessage = errorMessage
//        )
//    }

//    private fun isWeatherLoaded() {
//        viewModelScope.launch {
//            try {
//
//            } catch (e: java.net.SocketTimeoutException) {
//                handleAuthError("Network timeout. Please check your connection.", e)
//            } catch (e: java.net.UnknownHostException) {
//                handleAuthError("No internet connection. Please check your network.", e)
//            } catch (e: java.io.IOException) {
//                handleAuthError("Connection error. Please try again.", e)
//            }
//        }
//    }

}
