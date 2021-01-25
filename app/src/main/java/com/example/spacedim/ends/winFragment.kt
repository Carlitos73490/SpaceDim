package com.example.spacedim.ends

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
import com.example.spacedim.databinding.FragmentWinBinding
import com.example.spacedim.waitingRoom.WaitingViewModel


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class winFragment : Fragment() {

    private var fragmentWinBinding: FragmentWinBinding? = null
    private val viewModel: WaitingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentWinBinding.inflate(inflater, container, false)
        fragmentWinBinding = binding

        binding.score.text = viewModel.score.value.toString()


        binding.buttonRetry.setOnClickListener {
            GoWaitingRoom()
        }

        binding.buttonLeave.setOnClickListener {
            GoLogin()
        }

        return binding.root
    }

    /*
    Go to fragment login
     */
    fun  GoLogin(){
        requireActivity().runOnUiThread {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_winFragment_to_loginFragment)
        }
    }

    /*
    Go to fragment waiting room
     */
    fun  GoWaitingRoom(){
        var bundle = Bundle()
        bundle.putString("RoomName", viewModel.roomName.value)
        bundle.putString("UserName", viewModel.idUser.value)
        requireActivity().runOnUiThread {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_winFragment_to_waitingRoomFragment,bundle)
        }
    }
}
// {"type":"DONE", "value":true}
// {"type":"PLAYER_ACTION", "uiElement":{"type":"BUTTON","id":6,"content":"Arrêter de se plaindre"}}