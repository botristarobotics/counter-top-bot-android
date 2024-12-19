package com.botrista.countertopbot.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botrista.countertopbot.usecase.ConnectUseCase
import com.botrista.countertopbot.usecase.HotpotUseCase
import com.botrista.countertopbot.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val loginUseCase: LoginUseCase,
    private val hotpotUseCase: HotpotUseCase,
    private val connectUseCase: ConnectUseCase,
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    @ExperimentalStdlibApi
    fun testLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase.execute("db0xfantasy001")

            val hotpot = hotpotUseCase.execute()
            Log.d("Fan", "Hotpot result: $hotpot")
        }
    }

    fun testConnectUseCase() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = connectUseCase.execute()
            Log.d("Fan", "Connect result: $result")
        }
    }

}