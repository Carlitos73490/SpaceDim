package com.example.spacedim.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacedim.api.SocketListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class DashBoardViewModel() : ViewModel() {
/*
    fun startChat(): LiveData<Any> {
        viewModelScope.launch(Dispatchers.IO) {
            httpClient.dispatcher.executorService.shutdown()
        }
        return listener.liveData
    }
    */
    init {
        Log.i("WaitingViewModel", "INIT WAITING VIEW MODEL")
    }

}