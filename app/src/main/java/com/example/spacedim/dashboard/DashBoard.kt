package com.example.spacedim.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.spacedim.ends.End
import com.example.spacedim.R
import com.example.spacedim.ends.Win
import com.example.spacedim.databinding.ActivityDashBoardBinding

class DashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Full screen app
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        getSupportActionBar()?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        val binding : ActivityDashBoardBinding =
            DataBindingUtil.setContentView(this,
                R.layout.activity_dash_board
            )

        binding.buttonEndGame.setOnClickListener {
            //Intent pour ouvrir l'activité suivante
            val intent = Intent(this, End::class.java)
            //Lancement de l'intent (changement d'écran)
            startActivity(intent)
        }

        binding.buttonWinGame.setOnClickListener {
            //Intent pour ouvrir l'activité suivante
            val int = Intent(this, Win::class.java)
            //Lancement de l'intent (changement d'écran)
            startActivity(int)
        }
    }
}