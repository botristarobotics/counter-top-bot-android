package com.botrista.countertopbot

import android.app.Application
import com.botrista.countertopbot.di.appModule
import com.botrista.countertopbot.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, networkModule)
        }
    }
}