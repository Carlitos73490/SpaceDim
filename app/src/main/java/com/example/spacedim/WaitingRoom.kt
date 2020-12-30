package com.example.spacedim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.spacedim.databinding.ActivityLoginBinding
import com.example.spacedim.databinding.ActivityWaitingRoomBinding

class WaitingRoom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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