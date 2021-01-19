package com.example.spacedim.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
import com.example.spacedim.databinding.FragmentDashBoardBinding


class DashBoardFragment : Fragment() {
    private var fragmentDashBoardBinding: FragmentDashBoardBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_waiting_room)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        fragmentDashBoardBinding = binding


        binding.buttonEndGame.setOnClickListener {
            GoEnd()
        }

        binding.buttonWinGame.setOnClickListener {
            GoWin()
        }

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