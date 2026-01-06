package pl.filipczuk.prompthub.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.filipczuk.prompthub.repository.FcmTokenRepository

class AuthViewModel(
    context: Context
) : ViewModel() {

    private val authRepository = AuthRepository(context)
    private val fcmTokenRepository = FcmTokenRepository(context)
    private val sharedPrefs = context.getSharedPreferences("prompthub_prefs", Context.MODE_PRIVATE)

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
        val fcmToken = sharedPrefs.getString("fcm_token", null)

        if (fcmToken != null) {
            viewModelScope.launch {
                try {
                    fcmTokenRepository.updateUserToken(fcmToken)

                    with(sharedPrefs.edit()) {
                        remove("fcm_token")
                        apply()
                    }
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