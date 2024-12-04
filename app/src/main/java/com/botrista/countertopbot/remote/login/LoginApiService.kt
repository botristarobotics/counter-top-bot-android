package com.botrista.countertopbot.remote.login

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/machine/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}