package com.example.spacedim.login

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import com.example.spacedim.R
import network.LoginApi
import network.PlayerObjetName
import network.PlayerProperty
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class LoginViewModel : ViewModel() {

    private val _status = MutableLiveData<LoginApiStatus>()

    val status: LiveData<LoginApiStatus>
        get() = _status

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    // The internal MutableLiveData String that stores the most recent response
    private val _TextLogin = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val TextLogin: LiveData<String>
        get() = _TextLogin

    // The internal MutableLiveData String that stores the most recent response
    private val _TextRoom = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val TextRoom: LiveData<String>
        get() = _TextRoom


    init {
    }
    /**
     * Sets the value of the status LiveData to the Login API status.
     */
     fun getConnectionProperty() {
        //_status.value = LoginApiStatus.LOADING


        LoginApi.retrofitService.getPropertyConnection(_TextLogin.value).enqueue(
            object: Callback<PlayerProperty> {
                override fun onFailure(call: Call<PlayerProperty>, t: Throwable) {
                    _response.value =
                        "Connection Failed for " + _TextLogin.value
                    _status.value = LoginApiStatus.ERROR
                }

                override fun onResponse(
                    call: Call<PlayerProperty>,
                    response: Response<PlayerProperty>
                ) {
                    if(response.isSuccessful){
                        _response.value = "Connected "
                        _status.value = LoginApiStatus.CONNECTED
                    } else {
                        _response.value = "Login incorrect"
                        _status.value = LoginApiStatus.ERROR
                    }



                }

            })
    }

    fun getRegistrationProperty() {


        val newPlayer = PlayerObjetName(_TextLogin.value)


        LoginApi.retrofitService.postPropertyRegistration(newPlayer).enqueue(
            object: Callback<PlayerProperty> {
                override fun onFailure(call: Call<PlayerProperty>, t: Throwable) {
                    _response.value =
                        "Connection Failed"

                }

                override fun onResponse(
                    call: Call<PlayerProperty>,
                    response: Response<PlayerProperty>
                ) {
                    _response.value =
                        "Success: ${response.body()?.name}"

                }

            })
    }

    fun onTextChangedLogin(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        println(("$s"))
        _TextLogin.value = ("$s")
    }
    fun onTextChangedRoom(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        _TextRoom.value = ("$s")
    }
}
