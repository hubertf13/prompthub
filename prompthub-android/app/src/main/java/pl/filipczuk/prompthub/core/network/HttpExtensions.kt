package pl.filipczuk.prompthub.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

suspend fun HttpException.toApiErrorMessage(): String = withContext(Dispatchers.IO) {
    response()?.errorBody()?.string()?.let {
        runCatching { JSONObject(it).getString("message") }.getOrNull()
    } ?: "An unexpected error occurred"
}