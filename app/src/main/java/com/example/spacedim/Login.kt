package com.example.spacedim

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.spacedim.databinding.ActivityLoginBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONException
import org.json.JSONObject


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        // Full screen app
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        getSupportActionBar()?.hide()

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        val binding : ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.buttonConnection.setOnClickListener {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://spacedim.async-agency.com/api/user/find/" + binding.editTextLogin.text)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        for ((name, value) in response.headers) {
                            println("$name: $value")
                        }
                        println(response.body!!.string())
                    }
                }
            })

            //Intent pour ouvrir l'activité suivante
            val intent = Intent(this, WaitingRoom::class.java)
            //Lancement de l'intent (changement d'écran)
            startActivity(intent)
        }

        binding.buttonRegister.setOnClickListener {

            // create your json here
            val jsonObject = JSONObject()
            try {
                jsonObject.put("name", binding.editTextLogin.text)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val client = OkHttpClient()
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = jsonObject.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url("https://spacedim.async-agency.com/api/user/register")
                .post(body)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        for ((name, value) in response.headers) {
                            println("$name: $value")
                        }
                        println(response.body!!.string())
                    }
                }
            })
            //Intent pour ouvrir l'activité suivante
            val intent = Intent(this, WaitingRoom::class.java)
            //Lancement de l'intent (changement d'écran)
            startActivity(intent)
        }


    }




}