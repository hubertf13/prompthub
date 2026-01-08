package pl.filipczuk.prompthub.core.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class FcmTokenStorage(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("prompthub_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit { putString(KEY_FCM_TOKEN, token) }
    }

    fun getToken(): String? =
        prefs.getString(KEY_FCM_TOKEN, null)

    companion object {
        private const val KEY_FCM_TOKEN = "fcm_token"
    }
}