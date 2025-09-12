package com.cpen321.usermanagement.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpen321.usermanagement.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

data class WeatherUiState(
    // Loading states
    val isWeatherLoading: Boolean = false,
    val isWeatherLoaded: Boolean = false,

    //
    val isCheckingWeatherState: Boolean = false,

    // Message states
    val errorMessage: String? = null,
    val successMessage: String? = null,
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()


    companion object {
        private const val TAG = "WeatherViewModel"
    }

    suspend fun getWeather(): Result<String> {
        try {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isWeatherLoading = true)

                val weather = weatherRepository.getWeather()
//                _uiState.value = _uiState.value.copy(
//                    isWeatherLoading = false,
//                    isWeatherLoaded = true
//                )
            }
//        } catch (e: java.net.SocketTimeoutException) {
//            handleAuthError("Network timeout. Please check your connection.", e)
//        } catch (e: java.net.UnknownHostException) {
//            handleAuthError("No internet connection. Please check your network.", e)
//        }
        } catch (e: Exception) {
            // TODO: handle exception
        }
        return Result.success("")
    }

    private fun handleAuthError(errorMessage: String, exception: Exception) {
        Log.e(WeatherViewModel.Companion.TAG, "Authentication check failed: $errorMessage", exception)
        _uiState.value = _uiState.value.copy(
            isWeatherLoading = false,
            isWeatherLoaded = false,
            errorMessage = errorMessage
        )
    }

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

//
//    private fun handleAuthError(errorMessage: String, exception: Exception) {
//        Log.e(AuthViewModel.Companion.TAG, "Authentication check failed: $errorMessage", exception)
//        _uiState.value = _uiState.value.copy(
//            isCheckingAuth = false,
//            isAuthenticated = false,
//            errorMessage = errorMessage
//        )
//        updateNavigationState()
//    }

}
