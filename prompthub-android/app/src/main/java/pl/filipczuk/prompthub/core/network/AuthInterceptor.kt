package pl.filipczuk.prompthub.core.network

import androidx.compose.runtime.getValue
import okhttp3.Interceptor
import okhttp3.Response
import pl.filipczuk.prompthub.features.auth.domain.model.AuthState
import pl.filipczuk.prompthub.core.storage.AuthDataStorage

class AuthInterceptor(
    private val authDataStorage: AuthDataStorage
) : Interceptor {

    val isLoggedIn by AuthState.isLoggedIn

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        if (isLoggedIn) {
            authDataStorage.getToken()?.let { token ->
                requestBuilder.addHeader(
                    "Authorization",
                    "Bearer $token"
                )
            }
        } else {
            authDataStorage.clear()
        }

        return chain.proceed(requestBuilder.build())
    }
}