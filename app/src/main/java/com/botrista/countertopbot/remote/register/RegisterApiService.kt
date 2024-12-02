package com.botrista.countertopbot.remote.register

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService {
    @POST("/machine/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterInfo>
}