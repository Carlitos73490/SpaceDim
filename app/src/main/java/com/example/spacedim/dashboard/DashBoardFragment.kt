package com.example.spacedim.dashboard

import android.content.Context.SENSOR_SERVICE
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
import com.example.spacedim.api.Event
import com.example.spacedim.api.UIElement
import com.example.spacedim.databinding.FragmentDashBoardBinding
import com.example.spacedim.waitingRoom.WaitingViewModel


class DashBoardFragment : Fragment() {
    private var fragmentDashBoardBinding: FragmentDashBoardBinding? = null

    private val viewModel: WaitingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDashBoardBinding.inflate(inflater, container, false)
        fragmentDashBoardBinding = binding

        var UIList = viewModel.listUI.value

        if (UIList != null) {
            refreshButton(UIList, binding)
        }

        /*
        Observe livedata
         */
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

    /*
    Refresh button list
     */
    fun refreshButton(UIList: List<UIElement>, binding: FragmentDashBoardBinding) {
        if (UIList != null) {

            binding.actionLayout.removeAllViews()

            for(elem in UIList) {
                if(elem is UIElement.Button) {
                    binding.actionLayout.addView(createButton(elem.id, elem.content))
                }

                if(elem is UIElement.Switch) {
                    binding.actionLayout.addView(createSwitch(elem.id, elem.content))
                }

                if(elem is UIElement.Shake) {
                    createShake(elem.id, elem.content)
                }
            }
        }
    }

    /*
    Go to fragment end
     */
    fun GoEnd() {
        requireActivity().runOnUiThread {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_dashBoardFragment_to_endFragment)
        }
    }

    /*
    Go to fragment win
     */
    fun GoWin() {
        requireActivity().runOnUiThread {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_dashBoardFragment_to_winFragment)
        }
    }

    /*
    Create click action button
     */
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

    /*
    Create switch action button
     */
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

    /*
    Create shake action button
     */
    fun createShake(id: Int, text: String) {

    }
}
