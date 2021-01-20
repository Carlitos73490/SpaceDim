package com.example.spacedim.login

import android.os.Bundle
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController

import com.example.spacedim.R
import com.google.android.play.core.internal.v

@BindingAdapter("LoginApiStatus")
fun bindStatus(view: View, status: LoginApiStatus?) {
    when (status) {
       LoginApiStatus.CONNECTED -> {
            println("connected")
           val bundle = Bundle()  // Construction of passed arguments to waiting room.
           bundle.putString("RoomName", "TestAnnecyGo")
           bundle.putString("UserName", "carl")

           view.findNavController().navigate(R.id.action_loginFragment_to_waitingRoomFragment,bundle)
        }
        LoginApiStatus.ERROR -> {
            println("casser")
        }
    }
}