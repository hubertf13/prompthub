package pl.filipczuk.prompthub.auth

import android.content.Context
import pl.filipczuk.prompthub.core.auth.AuthState
import pl.filipczuk.prompthub.core.network.RetrofitProvider
import pl.filipczuk.prompthub.core.storage.TokenStorage

class AuthRepository(
    context: Context
) {

    private val tokenStorage = TokenStorage(context)

    private val retrofit = RetrofitProvider(tokenStorage).retrofit
    private val api = retrofit.create(AuthApi::class.java)

    suspend fun login(email: String, password: String) {
        val response = api.login(
            LoginRequest(
                email = email,
                password = password
            )
        )

        tokenStorage.saveToken(response.token)
        AuthState.isLoggedIn.value = true
    }

    suspend fun register(username: String, email: String, password: String) {
        val response = api.register(
            RegisterRequest(
                username = username,
                email = email,
                password = password
            )
        )

        tokenStorage.saveToken(response.token)
        AuthState.isLoggedIn.value = true
    }

    suspend fun logout() {
        try {
            api.logout()
        } finally {
            tokenStorage.clear()
            AuthState.isLoggedIn.value = false
        }
    }
}