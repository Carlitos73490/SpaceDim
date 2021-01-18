package com.example.spacedim.waitingRoom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.spacedim.dashboard.DashBoard
import com.example.spacedim.R
//import com.example.spacedim.databinding.ActivityWaitingRoomBinding
import okhttp3.*

class WaitingRoom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Full screen app
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        getSupportActionBar()?.hide()

        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_waiting_room)
      /*  val binding : ActivityWaitingRoomBinding =
            DataBindingUtil.setContentView(this,
                R.layout.activity_waiting_room
            )*/


        val ws = SocketListener().run()

     /*   binding.buttonStartGame.setOnClickListener {
            //Intent pour ouvrir l'activité suivante
            val intent = Intent(this, DashBoard::class.java)
            //Lancement de l'intent (changement d'écran)
            startActivity(intent)
        }*/
    }

}

class SocketListener : WebSocketListener() {
    fun run() {
        println("new web socket")
        val client = OkHttpClient()
        val request =
            Request.Builder()
                .url("ws://spacedim.async-agency.com:8081/ws/join/roomTest/1")
                .build()
        val ws = client.newWebSocket(request, this)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        println("onOpen")
        println(response)
    }

    override fun onMessage(webSocket: WebSocket, response: String) {
        println("onMessage")
        println(response)
    }
}
