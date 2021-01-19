package com.example.spacedim.ends

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
import com.example.spacedim.databinding.FragmentWinBinding


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class winFragment : Fragment() {
    private var fragmentWinBinding: FragmentWinBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentWinBinding.inflate(inflater, container, false)
        fragmentWinBinding = binding


        binding.buttonRetry.setOnClickListener {
            GoWaitingRoom()
        }

        binding.buttonLeave.setOnClickListener {
            GoLogin()
        }

        return binding.root
        //return inflater.inflate(R.layout.fragment_waiting_room, container, false)
    }
    fun  GoLogin(){
        requireActivity().runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_winFragment_to_loginFragment)
        }
    }
    fun  GoWaitingRoom(){
        requireActivity().runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_winFragment_to_waitingRoomFragment)
        }
    }
}