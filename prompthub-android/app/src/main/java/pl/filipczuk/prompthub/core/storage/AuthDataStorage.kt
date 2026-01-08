package pl.filipczuk.prompthub.core.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AuthDataStorage(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveAuthData(token: String, username: String) {
        prefs.edit {
            putString(KEY_TOKEN, token)
            putString(KEY_USERNAME, username)
        }
    }

    fun getToken(): String? =
        prefs.getString(KEY_TOKEN, null)

    fun getUsername(): String? =
        prefs.getString(KEY_USERNAME, null)

    fun clear() {
        prefs.edit { clear() }
    }

    companion object {
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_USERNAME = "username"
    }
}