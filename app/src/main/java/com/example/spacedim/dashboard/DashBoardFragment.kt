package com.example.spacedim.dashboard

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.compose.ui.layout.Layout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.spacedim.R
import com.example.spacedim.api.Event
import com.example.spacedim.api.State
import com.example.spacedim.api.UIElement
import com.example.spacedim.databinding.FragmentDashBoardBinding
import com.example.spacedim.waitingRoom.WaitingViewModel
import okhttp3.WebSocket


class DashBoardFragment : Fragment() {
    private var fragmentDashBoardBinding: FragmentDashBoardBinding? = null

    //private lateinit var viewModel: DashBoardViewModel
    private val viewModel: WaitingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        fragmentDashBoardBinding = binding

        var UIList = viewModel.listUI.value

        if (UIList != null) {
            refreshButton(UIList, binding)
        }

        viewModel.startChat().observe(viewLifecycleOwner, Observer {

            if(it is Event.NextAction) {
                binding.actionName.text = it.action.sentence
            }

            if(it is Event.GameOver) {

                viewModel._score.value = it.score
                viewModel._level.value = it.level

                if(!viewModel.gameOver.value!!) {

                    viewModel._gameOver.value = true

                    if(!it.win) {
                        GoEnd()
                    } else {
                        GoWin()
                    }
                }

            }

            if(it is Event.NextLevel) {
                refreshButton(it.uiElementList, binding)
            }
        })

        return binding.root
    }

    fun refreshButton(UIList: List<UIElement>, binding: FragmentDashBoardBinding) {
        if (UIList != null) {
            for(elem in UIList) {
                if(elem is UIElement.Button) {
                    binding.actionLayout.addView(createButton(elem.id, elem.content))
                }

                if(elem is UIElement.Switch) {
                    binding.actionLayout.addView(createSwitch(elem.id, elem.content))
                }
            }
        }
    }

    fun GoEnd() {
        requireActivity().runOnUiThread {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_dashBoardFragment_to_endFragment)
        }
    }

    fun GoWin() {
        requireActivity().runOnUiThread {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_dashBoardFragment_to_winFragment)
        }
    }

    fun createButton(id: Int, text: String): Button {

        var button = Button(this.context)
        button.text = text
        button.setTextColor(Color.BLACK)
        button.setTextSize(15F)

        button.setOnClickListener{
            viewModel.newPlayerAction("BUTTON", id, text)
        }

        return button
    }

    fun createSwitch(id: Int, text: String): LinearLayout {
        var layout = LinearLayout(this.context)
        layout.setOrientation(LinearLayout.HORIZONTAL);

        var textView = TextView(this.context)
        textView.text = text
        textView.setTextColor(Color.parseColor("#FFE436"));

        var switchButton = Switch(this.context)


        switchButton.setOnClickListener{
            viewModel.newPlayerAction("SWITCH", id, text)
        }

        layout.addView(textView)
        layout.addView(switchButton)

        return layout

    }
}