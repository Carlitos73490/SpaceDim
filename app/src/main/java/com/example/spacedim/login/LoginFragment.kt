package com.example.spacedim.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
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
    private var  fragmentLoginBinding: FragmentLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        fragmentLoginBinding = binding

        binding.buttonConnection.setOnClickListener {
            val client = OkHttpClient()
            val moshi = Moshi.Builder().build()
            val playerJsonAdapter = moshi.adapter(LoginFragment.Player::class.java)
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

                            activity!!.runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
                            Toast.makeText(
                                activity,
                                " " + player!!.id + " " + player!!.name + " Connected",
                                Toast.LENGTH_SHORT
                            ).show()
                            }

                            GoWaitingRoom()
                        } else{


                            activity!!.runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
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
            val playerJsonAdapter = moshi.adapter(LoginFragment.Player::class.java)
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
                            activity!!.runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                binding.textViewResponseHttp.text =
                                    "Player " + player!!.name + " " + player!!.id + " Sucessfuly created, you can now launch"
                            }

                        }else{

                            activity!!.runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                binding.textViewResponseHttp.text = "registration failed $response"
                            }
                            throw IOException("Unexpected code $response")
                        }
                    }
                }
            })
        }
        return binding.root
        //return inflater.inflate(R.layout.fragment_login, container, false)
    }

    fun  GoWaitingRoom(){
        requireActivity().runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_loginFragment_to_waitingRoomFragment)
        }
    }

    @JsonClass(generateAdapter = true)
    data class Player(var id: Int?,var name : String?,var avatar: String?,var score: Int?,var state: String?)

}