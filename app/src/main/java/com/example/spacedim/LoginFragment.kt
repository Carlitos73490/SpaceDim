package com.example.spacedim

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.spacedim.databinding.ActivityLoginBinding
import com.example.spacedim.databinding.FragmentLoginBinding
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONException
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            //setContentView(R.layout.activity_login)

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

                                GoWaitingRoom()
                            } else{


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


                            }else{

                                throw IOException("Unexpected code $response")
                            }
                        }
                    }
                })


            }




    }

    @JsonClass(generateAdapter = true)
    data class Player(var id: Int?,var name : String?,var avatar: String?,var score: Int?,var state: String?)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

}