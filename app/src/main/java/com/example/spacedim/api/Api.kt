package com.example.spacedim.api

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener


class SocketListener() : WebSocketListener() {

    private val _liveData = MutableLiveData<Any>()
    val liveData: LiveData<Any> get() = _liveData

    private val poly = PolymorphicAdapter.eventGameParser

    override fun onOpen(webSocket: WebSocket, response: Response) {
        println(response)
    }

    override fun onMessage(webSocket: WebSocket, response: String) {
        println("onMessage")
        val resp = poly.fromJson(response)
        _liveData.postValue(resp)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        println(t.message)
    }
}
