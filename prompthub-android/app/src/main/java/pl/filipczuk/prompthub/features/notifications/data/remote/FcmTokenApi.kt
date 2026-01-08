package pl.filipczuk.prompthub.features.notifications.data.remote

import retrofit2.http.Body
import retrofit2.http.PATCH

interface FcmTokenApi {

    @PATCH("fcm-token/me")
    suspend fun updateUserToken(@Body request: UpdateFcmTokenRequest)
}