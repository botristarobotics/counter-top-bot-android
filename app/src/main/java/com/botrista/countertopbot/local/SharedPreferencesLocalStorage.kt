package com.botrista.countertopbot.local

import android.content.SharedPreferences
import com.botrista.countertopbot.util.Const

class SharedPreferencesLocalStorage(private val sharedPreferences: SharedPreferences) :
    LocalStorage {
    override fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(Const.PREFERENCE_KEY_ACCESS_TOKEN, token).apply()
    }

    override fun saveRefreshToken(token: String) {
        sharedPreferences.edit().putString(Const.PREFERENCE_KEY_REFRESH_TOKEN, token).apply()
    }

    override fun getAccessToken(): String? {
        return sharedPreferences.getString(Const.PREFERENCE_KEY_ACCESS_TOKEN, null)
    }

    override fun getRefreshToken(): String? {
        return sharedPreferences.getString(Const.PREFERENCE_KEY_REFRESH_TOKEN, null)
    }
}