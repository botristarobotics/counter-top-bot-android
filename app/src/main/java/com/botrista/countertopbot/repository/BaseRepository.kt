package com.botrista.countertopbot.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

open class BaseRepository {

    protected fun <T> handleApiResponse(apiCall: suspend () -> Response<T>): Flow<Result<T>> =
        flow {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    emit(Result.success(response.body()!!))
                } else {
                    emit(Result.failure(Exception("Error: ${response.errorBody()?.string()}")))
                }
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
}