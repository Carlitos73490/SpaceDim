package com.example.spacedim

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Win : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // Full screen app
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)
    }

}