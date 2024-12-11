package com.botrista.countertopbot.remote.hotpot

import com.botrista.countertopbot.util.Const
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HotpotApiServiceTest {

    //TODO: extract to common logic
    private val token = ""//TODO: get token from login response

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(Const.HOST_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(HotpotApiService::class.java)


    @Test
    fun getHotpot_returnsSuccessfulResponse() = runBlocking {
        val response = service.getHotpot(token)

        assertNotNull(response)
        assertEquals(true, response.isSuccessful)
        assertNotNull(response.body())
    }

}