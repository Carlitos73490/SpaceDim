package com.example.spacedim

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.spacedim.databinding.ActivityLoginBinding
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
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
            val moshi = Moshi.Builder().build()
            val playerJsonAdapter = moshi.adapter(Player::class.java)
            val request = Request.Builder()
                .url("https://spacedim.async-agency.com/api/user/find/" + binding.editTextLogin.text)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use {

                        if(response.isSuccessful) {
                            val player = playerJsonAdapter.fromJson(response.body!!.source())
                            println(player!!.id)
                            println(player!!.name)


                            runOnUiThread {
                                Toast.makeText(
                                    applicationContext,
                                    " " + player!!.id + " " + player!!.name + " Connected",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                            GoWaitingRoom()
                        } else{

                            runOnUiThread {
                                binding.textViewResponseHttp.text = "Connection failed $response"
                            }
                            throw IOException("Unexpected code $response")
                        }
                    }
                }
            })

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
            val moshi = Moshi.Builder().build()
            val playerJsonAdapter = moshi.adapter(Player::class.java)
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
                        if (response.isSuccessful) {
                            val player = playerJsonAdapter.fromJson(response.body!!.source())
                            println(player!!.id)
                            println(player!!.name)

                            runOnUiThread {
                                binding.textViewResponseHttp.text =
                                    "Player " + player!!.name + " " + player!!.id + " Sucessfuly created, you can now launch"
                            }
                        }else{

                            runOnUiThread {
                                binding.textViewResponseHttp.text =
                                    "Registration failed $response"
                            }
                            throw IOException("Unexpected code $response")
                        }
                    }
                }
            })


        }



    }

    fun  GoWaitingRoom(){
        //Intent pour ouvrir l'activité suivante
        val intent = Intent(this, WaitingRoom::class.java)
        //Lancement de l'intent (changement d'écran)
        startActivity(intent)
    }

    @JsonClass(generateAdapter = true)
    data class Player(var id: Int?,var name : String?,var avatar: String?,var score: Int?,var state: String?)

}