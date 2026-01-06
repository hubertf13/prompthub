package pl.filipczuk.prompthub.core.network

import androidx.compose.runtime.getValue
import okhttp3.Interceptor
import okhttp3.Response
import pl.filipczuk.prompthub.core.auth.AuthState
import pl.filipczuk.prompthub.core.storage.TokenStorage

class AuthInterceptor(
    private val tokenStorage: TokenStorage
) : Interceptor {

    val isLoggedIn by AuthState.isLoggedIn

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        if (isLoggedIn) {
            tokenStorage.getToken()?.let { token ->
                requestBuilder.addHeader(
                    "Authorization",
                    "Bearer $token"
                )
            }
        } else {
            tokenStorage.clear()
        }

        return chain.proceed(requestBuilder.build())
    }
}