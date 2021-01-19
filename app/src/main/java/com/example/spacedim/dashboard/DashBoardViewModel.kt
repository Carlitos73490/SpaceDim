package com.example.spacedim.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacedim.api.Event
import com.example.spacedim.api.SocketListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class DashBoardViewModel() : ViewModel() {

   /* val roomName = "annecyRoom82"

    val httpClient = OkHttpClient()
    val request =
        Request.Builder()
            .url("ws://spacedim.async-agency.com:8081/ws/join/$roomName/10")
            .build()
    val listener = SocketListener()
    var webSocket = httpClient.newWebSocket(request, listener)*/

    fun startChat(): LiveData<Any> {
        println("IN START CHAT VM")

        viewModelScope.launch(Dispatchers.IO) {
            OkHttpClient().dispatcher.executorService.shutdown()
        }

        println(SocketListener().liveData)

        return SocketListener().liveData
    }

    init {
        Log.i("WaitingViewModel", "init listUser viewModel")
    }

}