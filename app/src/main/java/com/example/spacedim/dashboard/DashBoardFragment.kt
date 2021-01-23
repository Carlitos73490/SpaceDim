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
            for(elem in UIList) {
                if(elem is UIElement.Button) {
                    binding.actionLayout.addView(createButton(elem.id, elem.content))
                }

                if(elem is UIElement.Switch) {
                    binding.actionLayout.addView(createSwitch(elem.id, elem.content))
                }
            }
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
        })

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

    fun createButton(id: Int, text: String): Button {

        /*
        <Button
        android:id="@+id/button2"
        style="@style/button_yellow"
        android:text="@string/button2"
        app:cornerRadius="10dp" />
         */

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

        var switchButton = Switch(this.context)

        switchButton.setOnClickListener{
            viewModel.newPlayerAction("SWITCH", id, text)
        }

        layout.addView(textView)
        layout.addView(switchButton)

        return layout

    }

    fun CreateLayout() {
        /*

        <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal"
        android:layout_weight="1">

        <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="10dp"
        android:layout_weight="1"
        android:background="@drawable/switch_border"
        android:gravity="center"
        android:orientation="vertical"
        app:cornerRadius="10dp">

        <TextView
        android:id="@+id/switch1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/button1"
        android:textColor="@color/black"
        android:textSize="15dip"
        android:textStyle="bold"/>

        <Switch
        android:id="@+id/simpleSwitch1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:checked="false" />

        </LinearLayout>

        <Button
        android:id="@+id/button2"
        style="@style/button_yellow"
        android:text="@string/button2"
        app:cornerRadius="10dp" />

        </LinearLayout>

        <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal"
        android:layout_weight="1">

        <Button
        android:id="@+id/button3"
        android:text="@string/button3"
        style="@style/button_yellow"
        app:cornerRadius="10dp"/>

        <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="10dp"
        android:layout_weight="1"
        android:background="@drawable/switch_border"
        android:gravity="center"
        android:orientation="vertical"
        app:cornerRadius="10dp">

        <TextView
        android:id="@+id/switch2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/button4"
        android:textColor="@color/black"
        android:textSize="15dip"
        android:textStyle="bold"/>

        <Switch
        android:id="@+id/simpleSwitch2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:checked="false" />

        </LinearLayout>

        </LinearLayout>

        */
    }

}