package com.example.spacedim.ends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.spacedim.R

class End : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Full screen app
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        getSupportActionBar()?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)
    }
}