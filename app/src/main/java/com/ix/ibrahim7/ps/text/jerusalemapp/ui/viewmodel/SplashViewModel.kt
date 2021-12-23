package com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val mutableLiveData = MutableLiveData<SplashState>()
    val liveData: LiveData<SplashState>
        get() = mutableLiveData

    init {
        GlobalScope.launch {
            delay(2500)
            mutableLiveData.postValue(SplashState.SplashFragment)
        }
    }

    sealed class SplashState {
        object SplashFragment : SplashState()
    }
}