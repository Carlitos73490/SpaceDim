package com.example.spacedim.ends

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R

import com.example.spacedim.databinding.FragmentEndBinding
import com.example.spacedim.waitingRoom.WaitingViewModel

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class EndFragment : Fragment() {

    private var fragmentEndBinding: FragmentEndBinding? = null
    private val viewModel: WaitingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentEndBinding.inflate(inflater, container, false)
        fragmentEndBinding = binding

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
                .navigate(R.id.action_endFragment_to_loginFragment)
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
                    .navigate(R.id.action_endFragment_to_waitingRoomFragment,bundle)
        }
    }
}