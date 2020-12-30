package com.example.spacedim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.spacedim.databinding.ActivityDashBoardBinding
import com.example.spacedim.databinding.ActivityLoginBinding

class DashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        val binding : ActivityDashBoardBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_dash_board)

        binding.buttonEndGame.setOnClickListener {
            //Intent pour ouvrir l'activité suivante
            val intent = Intent(this, End::class.java)
            //Lancement de l'intent (changement d'écran)
            startActivity(intent)
        }
    }
}