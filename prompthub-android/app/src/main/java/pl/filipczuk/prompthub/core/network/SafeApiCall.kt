package pl.filipczuk.prompthub.core.network

import pl.filipczuk.prompthub.core.data.Result
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T, String> {
    return try {
        Result.Success(apiCall())
    } catch (e: HttpException) {
        Result.Error(e.toApiErrorMessage())
    } catch (_: Exception) {
        Result.Error("An unexpected error occurred")
    }
}