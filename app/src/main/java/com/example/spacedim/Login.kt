package com.example.spacedim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.spacedim.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {






    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        val binding : ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.buttonConnection.setOnClickListener {
            buttonClicked()
        }


    }



    fun buttonClicked(){
        //Intent pour ouvrir l'activité suivante
        val intent = Intent(this, WaitingRoom::class.java)
        //Lancement de l'intent (changement d'écran)
        startActivity(intent)
    }
}