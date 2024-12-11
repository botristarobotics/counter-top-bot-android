package com.botrista.countertopbot.di

import com.botrista.countertopbot.util.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideOkHttpClientWithoutToken() }
    single { provideRetrofit(get()) }
}

fun provideOkHttpClientWithoutToken(): OkHttpClient {
    return OkHttpClient.Builder().addInterceptor(getLogInterceptor()).build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(Const.HOST_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

private fun getLogInterceptor(): HttpLoggingInterceptor {
    val logInterceptor = HttpLoggingInterceptor()
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return logInterceptor
}