package com.botrista.countertopbot.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botrista.countertopbot.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationsViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    @ExperimentalStdlibApi
    fun testLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase.execute("db0xfantasy001")
            Log.d("Fan", "Login result: $result")
        }
    }
}