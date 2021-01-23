package com.example.spacedim.waitingRoom

import android.util.Log
import androidx.lifecycle.*
import com.example.spacedim.api.SocketListener
import com.example.spacedim.api.UIElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class WaitingViewModel() : ViewModel() {

    val _gameOver = MutableLiveData<Boolean>()
    val gameOver: LiveData<Boolean> get () = _gameOver

    val _listUI = MutableLiveData<List<UIElement>>()
    val listUI: LiveData<List<UIElement>> get () = _listUI

    val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    val _level = MutableLiveData<Int>()
    val level: LiveData<Int> get() = _level

    val _roomName = MutableLiveData<String>()
    val roomName: LiveData<String> get() = _roomName

    val _idUser = MutableLiveData<String>()
    val idUser: LiveData<String> get() = _idUser

    val _httpClient = MutableLiveData<OkHttpClient>()
    val httpClient: LiveData<OkHttpClient> get() = _httpClient

    val _listener = MutableLiveData<SocketListener>()
    val listener: LiveData<SocketListener> get() = _listener

    val _ws = MutableLiveData<WebSocket>()
    val ws: LiveData<WebSocket> get() = _ws

    fun startSocket(room: String, id: String) {

        _roomName.value = room
        _idUser.value = id

        val NhttpClient = OkHttpClient()
        _httpClient.value = NhttpClient

        val request =
            Request.Builder()
                .url("ws://spacedim.async-agency.com:8081/ws/join/$room/$id")
                .build()
        val Nlistener = SocketListener()
        _listener.value = Nlistener

        var webSocket = NhttpClient.newWebSocket(request, Nlistener)
        _ws.value = webSocket
    }

    fun startChat(): LiveData<Any> {
        viewModelScope.launch(Dispatchers.IO) {
            httpClient.value!!.dispatcher.executorService.shutdown()
        }
        return listener.value!!.liveData
    }

    fun readyState() {
        ws.value!!.send("{\"type\":\"READY\", \"value\":true}")
    }

    fun newPlayerAction(type: String, id: Int, content: String) {
        println("player action")
        ws.value!!.send("{\"type\":\"PLAYER_ACTION\", \"uiElement\":{\"type\":\""+type+"\",\"id\":"+id+",\"content\":\""+content+"\"}}")
    }

    init {
        Log.i("WaitingViewModel", "INIT WAITING VIEW MODEL")
    }

}