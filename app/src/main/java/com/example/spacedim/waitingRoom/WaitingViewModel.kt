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

    val roomName = "annecyRoom8"
  /*  private val _Ws = MutableLiveData<WebSocket>()
    val ws: LiveData<WebSocket> get() = _Ws*/

    fun startChat(): LiveData<Any> {
        val listener = SocketListener()

        viewModelScope.launch(Dispatchers.IO) {
            val httpClient = OkHttpClient()
            val request =
                Request.Builder()
                    .url("ws://spacedim.async-agency.com:8081/ws/join/$roomName/10")
                    .build()

            val webSocket = httpClient.newWebSocket(request, listener)
            //webSocket.
            httpClient.dispatcher.executorService.shutdown()
        }
        return listener.liveData
    }

    init {
        Log.i("WaitingViewModel", "init listUser viewModel")
    }

}