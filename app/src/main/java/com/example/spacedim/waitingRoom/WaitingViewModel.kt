package com.example.spacedim.waitingRoom

import android.util.Log
import androidx.lifecycle.*
import com.example.spacedim.api.SocketListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class WaitingViewModel() : ViewModel() {

    val roomName = "annecyRoom823"

    val httpClient = OkHttpClient()
    val request =
        Request.Builder()
            .url("ws://spacedim.async-agency.com:8081/ws/join/$roomName/10")
            .build()
    val listener = SocketListener()
    var webSocket = httpClient.newWebSocket(request, listener)

    fun startChat(): LiveData<Any> {
        viewModelScope.launch(Dispatchers.IO) {
            httpClient.dispatcher.executorService.shutdown()
        }
        return listener.liveData
    }

    fun readyState() {
        webSocket.send("{\"type\":\"READY\", \"value\":true}")
    }

    init {
        Log.i("WaitingViewModel", "INIT WAITING VIEW MODEL")
    }

}