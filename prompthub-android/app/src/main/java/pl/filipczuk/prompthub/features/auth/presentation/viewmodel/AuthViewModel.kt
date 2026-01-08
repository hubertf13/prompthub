package pl.filipczuk.prompthub.features.auth.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.core.data.Result
import pl.filipczuk.prompthub.core.storage.FcmTokenStorage
import pl.filipczuk.prompthub.features.auth.data.repository.AuthRepository
import pl.filipczuk.prompthub.features.notifications.data.repository.FcmTokenRepository

class AuthViewModel(
    context: Context
) : ViewModel() {

    private val authRepository = AuthRepository(context)
    private val fcmTokenRepository = FcmTokenRepository(context)
    private val fcmTokenStorage = FcmTokenStorage(context)

    var error by mutableStateOf<String?>(null)
        private set

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            error = null
            when (val result = authRepository.login(email, password)) {
                is Result.Success -> {
                    syncFcmToken()
                    onSuccess()
                }
                is Result.Error -> {
                    error = result.error
                }
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            error = null
            when (val result = authRepository.register(username, email, password)) {
                is Result.Success -> {
                    syncFcmToken()
                    onSuccess()
                }
                is Result.Error -> {
                    error = result.error
                }
            }
        }
    }

    private fun syncFcmToken() {
        val fcmToken = fcmTokenStorage.getToken()

        if (fcmToken != null) {
            viewModelScope.launch {
                fcmTokenRepository.updateUserToken(fcmToken)
            }
        }
    }

    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onDone()
        }
    }

    fun errorShown() {
        error = null
    }
}