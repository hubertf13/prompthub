package pl.filipczuk.prompthub.repository

import android.content.Context
import pl.filipczuk.prompthub.api.FcmTokenApi
import pl.filipczuk.prompthub.core.network.RetrofitProvider
import pl.filipczuk.prompthub.core.storage.TokenStorage
import pl.filipczuk.prompthub.dto.UpdateFcmTokenRequest

class FcmTokenRepository(context: Context) {

    private val tokenStorage = TokenStorage(context)
    private val api = RetrofitProvider(tokenStorage)
        .retrofit
        .create(FcmTokenApi::class.java)

    suspend fun updateUserToken(fcmToken: String) {
        if (tokenStorage.getToken() != null) {
            api.updateUserToken(UpdateFcmTokenRequest(fcmToken))
        }
    }
}