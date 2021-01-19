package com.example.spacedim.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.spacedim.R
import com.example.spacedim.api.Event
import com.example.spacedim.databinding.FragmentDashBoardBinding
import com.example.spacedim.waitingRoom.WaitingViewModel


class DashBoardFragment : Fragment() {
    private var fragmentDashBoardBinding: FragmentDashBoardBinding? = null
    private lateinit var viewModel: DashBoardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        fragmentDashBoardBinding = binding

        viewModel = ViewModelProvider(this).get(DashBoardViewModel::class.java)

        viewModel.startChat().observe(viewLifecycleOwner, Observer {
            if(it is Event.NextAction) {
                println("[DASHBOARD] NEXT ACTION")
            }

            if(it is Event.GameStarted) {
                println("[DASHBOARD] GAME START")
            }
        })

       /* binding.buttonEndGame.setOnClickListener {
            GoEnd()
        }

        binding.buttonWinGame.setOnClickListener {
            GoWin()
        }*/

        return binding.root
        //return inflater.inflate(R.layout.fragment_waiting_room, container, false)
    }


    fun GoEnd() {
        requireActivity().runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_dashBoardFragment_to_endFragment)
        }
    }

    fun GoWin() {
        requireActivity().runOnUiThread { // This code will always run on the UI thread, therefore is safe to modify UI elements.
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_dashBoardFragment_to_winFragment)
        }
    }

}