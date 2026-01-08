package pl.filipczuk.prompthub.features.auth.data.repository

import android.content.Context
import pl.filipczuk.prompthub.core.data.Result
import pl.filipczuk.prompthub.core.network.RetrofitProvider
import pl.filipczuk.prompthub.core.network.safeApiCall
import pl.filipczuk.prompthub.core.storage.AuthDataStorage
import pl.filipczuk.prompthub.features.auth.data.remote.AuthApi
import pl.filipczuk.prompthub.features.auth.data.remote.LoginRequest
import pl.filipczuk.prompthub.features.auth.data.remote.RegisterRequest
import pl.filipczuk.prompthub.features.auth.domain.model.AuthState

class AuthRepository(
    context: Context
) {

    private val authDataStorage = AuthDataStorage(context)

    private val retrofit = RetrofitProvider(authDataStorage).retrofit
    private val api = retrofit.create(AuthApi::class.java)

    suspend fun login(email: String, password: String): Result<Unit, String> = safeApiCall {
        val response = api.login(
            LoginRequest(
                email = email,
                password = password
            )
        )
        authDataStorage.saveAuthData(response.token, response.username)
        AuthState.isLoggedIn.value = true
    }

    suspend fun register(username: String, email: String, password: String): Result<Unit, String> =
        safeApiCall {
            val response = api.register(
                RegisterRequest(
                    username = username,
                    email = email,
                    password = password
                )
            )
            authDataStorage.saveAuthData(response.token, response.username)
            AuthState.isLoggedIn.value = true
        }

    suspend fun logout() = safeApiCall {
        try {
            api.logout()
        } finally {
            authDataStorage.clear()
            AuthState.isLoggedIn.value = false
        }
    }

    fun getUsername(): String? = authDataStorage.getUsername()
}