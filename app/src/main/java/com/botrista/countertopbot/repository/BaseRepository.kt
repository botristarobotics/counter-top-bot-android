package com.botrista.countertopbot.repository

import com.botrista.countertopbot.repository.exception.ForbiddenException
import retrofit2.Response
import java.net.HttpURLConnection

open class BaseRepository {

    protected suspend fun <I, T> handleApiResponse(
        apiCall: suspend () -> Response<I>,
        mapper: (I) -> T
    ): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(mapper(body))
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                Result.failure(ForbiddenException())
            } else {//TODO: Handle other error codes
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}