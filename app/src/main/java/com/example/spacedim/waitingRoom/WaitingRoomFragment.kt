package com.example.spacedim.waitingRoom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
import com.example.spacedim.api.Event
import com.example.spacedim.api.State
import com.example.spacedim.databinding.FragmentWaitingRoomBinding
import com.example.spacedim.main.MainActivity



class WaitingRoomFragment : Fragment() {


    private var fragmentWaitingRoomBinding: FragmentWaitingRoomBinding? = null

   // private lateinit var viewModel: WaitingViewModel
    private val viewModel: WaitingViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            viewModel._roomName.value = it.getString("RoomName")
            viewModel._idUser.value = it.getString("UserName")

            viewModel.startSocket(viewModel.roomName.value!!, viewModel.idUser.value!!)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentWaitingRoomBinding.inflate(inflater, container, false)
        fragmentWaitingRoomBinding = binding

        viewModel._gameOver.value = false

        viewModel.startChat().observe(viewLifecycleOwner, Observer {

            if(it is Event.WaitingForPlayer) {
                var list = it as Event.WaitingForPlayer
                println(list.userList)
                var lUser = list.userList

                var layout = binding.LayoutWaitroom

                layout.removeAllViews()
                for(elem in lUser) {
                    val name = TextView(this.context)
                    name.textSize = 50f;
                    name.text = elem.name.toUpperCase();
                    name.setTextColor(Color.parseColor("#FFE436"));


                    if(elem.state == State.READY) {
                        name.setTextColor(Color.parseColor("#2BDF46"));
                    }

                    layout.addView(name)
                }
            }

            if(it is Event.GameStarted) {
                viewModel._listUI.value = it.uiElementList
                GoDashBoard()
            }
        })

        binding.WaitPlayer.text = viewModel._roomName.value

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