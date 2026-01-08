package pl.filipczuk.prompthub.features.notifications.data.repository

import android.content.Context
import pl.filipczuk.prompthub.core.network.RetrofitProvider
import pl.filipczuk.prompthub.core.network.safeApiCall
import pl.filipczuk.prompthub.core.storage.AuthDataStorage
import pl.filipczuk.prompthub.features.notifications.data.remote.FcmTokenApi
import pl.filipczuk.prompthub.features.notifications.data.remote.UpdateFcmTokenRequest

class FcmTokenRepository(context: Context) {

    private val authDataStorage = AuthDataStorage(context)
    private val api = RetrofitProvider(authDataStorage)
        .retrofit
        .create(FcmTokenApi::class.java)

    suspend fun updateUserToken(fcmToken: String) = safeApiCall {
        if (authDataStorage.getToken() != null) {
            api.updateUserToken(UpdateFcmTokenRequest(fcmToken))
        }
    }
}