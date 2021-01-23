package com.example.spacedim.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
import com.example.spacedim.api.Event
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
enum class LoginApiStatus { CONNECTED,ERROR}

class LoginFragment : Fragment() {

    /**
     * Lazily initialize our [LoginViewModel].
     */
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }


    private var  fragmentLoginBinding: FragmentLoginBinding? = null


    private val bundle = Bundle()  // Construction of passed arguments to waiting room.



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        fragmentLoginBinding = binding

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the LoginViewModel
        binding.loginViewModel = viewModel

        viewModel.idPlayer.observe(viewLifecycleOwner, Observer {
            if(it != -1) {
                if (it != null) {
                    GoWaitingRoom(it)
                }
            }
        })

        binding.buttonConnection.setOnClickListener {
            viewModel.getConnectionProperty()
        }
      /*  //binding.buttonConnection.setOnClickListener {
            viewModel.getConnectionProperty()
        //    GoWaitingRoom()
      //}*/


        return binding.root
        //return inflater.inflate(R.layout.fragment_login, container, false)
    }

    fun  GoWaitingRoom(id : Int){
        bundle.putString("RoomName", viewModel.TextRoom.value)
        bundle.putString("UserName", id.toString())
        requireActivity().runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_loginFragment_to_waitingRoomFragment,bundle)
        }
    }

/*    @JsonClass(generateAdapter = true)
    data class Player(var id: Int?,var name : String?,var avatar: String?,var score: Int?,var state: String?)*/

}