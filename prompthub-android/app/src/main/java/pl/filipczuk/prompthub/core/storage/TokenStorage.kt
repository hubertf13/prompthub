package pl.filipczuk.prompthub.core.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TokenStorage(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit { putString(KEY_TOKEN, token) }
    }

    fun getToken(): String? =
        prefs.getString(KEY_TOKEN, null)

    fun clear() {
        prefs.edit { clear() }
    }

    companion object {
        private const val KEY_TOKEN = "jwt_token"
    }
}
