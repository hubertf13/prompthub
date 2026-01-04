package pl.filipczuk.prompthub.auth

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/authenticate")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): AuthResponse

    @POST("auth/logout")
    suspend fun logout()
}