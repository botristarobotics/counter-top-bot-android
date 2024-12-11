package com.botrista.countertopbot.di

import android.content.Context
import com.botrista.countertopbot.MainViewModel
import com.botrista.countertopbot.local.LocalStorage
import com.botrista.countertopbot.local.SharedPreferencesLocalStorage
import com.botrista.countertopbot.remote.hotpot.HotpotApiService
import com.botrista.countertopbot.remote.login.LoginApiService
import com.botrista.countertopbot.remote.register.RegisterApiService
import com.botrista.countertopbot.repository.hotpot.HotpotRepository
import com.botrista.countertopbot.repository.hotpot.HotpotRepositoryImpl
import com.botrista.countertopbot.repository.login.LoginRepository
import com.botrista.countertopbot.repository.login.LoginRepositoryImpl
import com.botrista.countertopbot.repository.register.RegisterRepository
import com.botrista.countertopbot.repository.register.RegisterRepositoryImpl
import com.botrista.countertopbot.ui.notifications.NotificationsViewModel
import com.botrista.countertopbot.usecase.HotpotUseCase
import com.botrista.countertopbot.usecase.LoginUseCase
import com.botrista.countertopbot.util.Const
import com.botrista.countertopbot.util.key.KeyManager
import com.botrista.countertopbot.util.key.SECP256K1KeyManager
import com.botrista.countertopbot.util.time.TimeProvider
import com.botrista.countertopbot.util.time.TimeProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import java.security.PrivateKey
import java.security.PublicKey

val appModule = module {
    single(named(Const.NAMED_LOGIN_API_SERVICE)) {
        get<Retrofit>().create(
            LoginApiService::class.java
        )
    }
    single(named(Const.NAMED_REGISTER_API_SERVICE)) {
        get<Retrofit>().create(
            RegisterApiService::class.java
        )
    }


    single {
        LoginRepositoryImpl(
            get(named(Const.NAMED_LOGIN_API_SERVICE)),
            get()
        )
    } bind LoginRepository::class
    single { RegisterRepositoryImpl(get(named(Const.NAMED_REGISTER_API_SERVICE))) } bind RegisterRepository::class
    single<TimeProvider> { TimeProviderImpl() }
    single<KeyManager<PublicKey, PrivateKey>> { SECP256K1KeyManager(get()) }
    single<LocalStorage> {
        SharedPreferencesLocalStorage(
            androidContext().getSharedPreferences(
                Const.PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
        )
    }

    factoryOf(::LoginUseCase)

    //Hotpot
    factory(named(Const.NAMED_HOTPOT_API_SERVICE)) {
        get<Retrofit>().create(
            HotpotApiService::class.java
        )
    }
    single {
        HotpotRepositoryImpl(
            get(named(Const.NAMED_HOTPOT_API_SERVICE)),
        )
    } bind HotpotRepository::class
    factoryOf(::HotpotUseCase)


    viewModelOf(::NotificationsViewModel)
    viewModelOf(::MainViewModel)
}