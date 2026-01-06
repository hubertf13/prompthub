package pl.filipczuk.prompthub.api

import pl.filipczuk.prompthub.dto.UpdateFcmTokenRequest
import retrofit2.http.Body
import retrofit2.http.PATCH

interface FcmTokenApi {

    @PATCH("fcm-token/me")
    suspend fun updateUserToken(@Body request: UpdateFcmTokenRequest)
}