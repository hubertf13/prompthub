package pl.filipczuk.prompthub.core.network

import okhttp3.Interceptor
import okhttp3.Response
import pl.filipczuk.prompthub.core.storage.TokenStorage

class AuthInterceptor(
    private val tokenStorage: TokenStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        tokenStorage.getToken()?.let { token ->
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $token"
            )
        }

        return chain.proceed(requestBuilder.build())
    }
}