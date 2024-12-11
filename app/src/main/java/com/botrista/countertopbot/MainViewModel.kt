package com.botrista.countertopbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.botrista.countertopbot.usecase.HotpotUseCase
import com.botrista.countertopbot.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val loginUseCase: LoginUseCase,
    private val hotpotUseCase: HotpotUseCase
) : ViewModel() {

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase.execute("db0xfantasy001")
            val hotpot = hotpotUseCase.execute()
        }
    }

}