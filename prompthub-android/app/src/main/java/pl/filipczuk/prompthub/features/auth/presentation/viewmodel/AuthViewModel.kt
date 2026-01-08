package pl.filipczuk.prompthub.features.auth.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.core.storage.FcmTokenStorage
import pl.filipczuk.prompthub.features.auth.data.repository.AuthRepository
import pl.filipczuk.prompthub.features.notifications.data.repository.FcmTokenRepository

class AuthViewModel(
    context: Context
) : ViewModel() {

    private val authRepository = AuthRepository(context)
    private val fcmTokenRepository = FcmTokenRepository(context)
    private val fcmTokenStorage = FcmTokenStorage(context)

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                authRepository.login(email, password)
                syncFcmToken()
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
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
            try {
                authRepository.register(username, email, password)
                syncFcmToken()
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun syncFcmToken() {
        val fcmToken = fcmTokenStorage.getToken()

        if (fcmToken != null) {
            viewModelScope.launch {
                try {
                    fcmTokenRepository.updateUserToken(fcmToken)
                } catch (e: Exception) {

                }
            }
        }
    }

    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onDone()
        }
    }
}