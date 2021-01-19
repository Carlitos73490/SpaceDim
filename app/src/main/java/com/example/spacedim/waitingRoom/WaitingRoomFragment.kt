package com.example.spacedim.waitingRoom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
import com.example.spacedim.api.SocketListener
import com.example.spacedim.databinding.FragmentWaitingRoomBinding
import okhttp3.*

class WaitingRoomFragment : Fragment() {

    private var fragmentWaitingRoomBinding: FragmentWaitingRoomBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_waiting_room)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentWaitingRoomBinding.inflate(inflater, container, false)
        fragmentWaitingRoomBinding = binding

        val client = OkHttpClient()
        val request =
            Request.Builder()
                .url("ws://spacedim.async-agency.com:8081/ws/join/annecyGoRoom2/1")
                .build()
        val listener = SocketListener()
        val ws = client.newWebSocket(request, listener)

        binding.buttonStartGame.setOnClickListener {
            GoDashBoard()
        }
        return binding.root
        //return inflater.inflate(R.layout.fragment_waiting_room, container, false)
    }

    fun GoDashBoard() {
        requireActivity().runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_waitingRoomFragment_to_dashBoardFragment)
        }
    }
}