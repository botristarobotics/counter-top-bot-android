package com.botrista.countertopbot.remote.hotpot

import com.botrista.countertopbot.util.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HotpotApiService {

    @GET("/drinkbot/hotpot")
    suspend fun getHotpot(@Header(Const.API_HEADER_AUTHORIZATION) token: String): Response<HotpotResponse>
}