package com.example.spacedim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.spacedim.databinding.ActivityLoginBinding
import com.example.spacedim.databinding.ActivityWaitingRoomBinding

class WaitingRoom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Full screen app
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        getSupportActionBar()?.hide()

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_waiting_room)
        val binding : ActivityWaitingRoomBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_waiting_room)

        binding.buttonStartGame.setOnClickListener {
            //Intent pour ouvrir l'activité suivante
            val intent = Intent(this, DashBoard::class.java)
            //Lancement de l'intent (changement d'écran)
            startActivity(intent)
        }
    }

}