package com.example.spacedim.waitingRoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
import com.example.spacedim.api.Event
import com.example.spacedim.databinding.FragmentWaitingRoomBinding

class WaitingRoomFragment : Fragment() {

    private var fragmentWaitingRoomBinding: FragmentWaitingRoomBinding? = null
    var list = null
    private lateinit var viewModel: WaitingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentWaitingRoomBinding.inflate(inflater, container, false)
        fragmentWaitingRoomBinding = binding

        viewModel = ViewModelProvider(this).get(WaitingViewModel::class.java)

        viewModel.startChat().observe(viewLifecycleOwner, Observer {

            if(it is Event.WaitingForPlayer) {
                var list = it as Event.WaitingForPlayer
                println(list.userList)
                var lUser = list.userList

                var layout = binding.LayoutWaitroom

                layout.removeAllViews()
                for(elem in lUser) {
                    val name = TextView(this.context)
                    name.textSize = 20f
                    name.text = elem.name

                    layout.addView(name)
                }
            }

            if(it is Event.GameStarted) {
                GoDashBoard()
            }
        })

        binding.WaitPlayer.text = viewModel.roomName

        binding.buttonStartGame.setOnClickListener {
            viewModel.readyState()
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