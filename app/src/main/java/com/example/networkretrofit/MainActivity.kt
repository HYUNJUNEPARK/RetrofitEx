package com.example.networkretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.networkretrofit.databinding.ActivityMainBinding
import com.example.networkretrofit.model.response.ErrorResponse
import com.example.networkretrofit.model.response.SearchUserResponse
import com.example.networkretrofit.retrofit.call.CallRetrofitClient
import com.example.networkretrofit.retrofit.response.ResponseRetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    companion object {
        const val TAG = "testLog"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var callRetrofitClient: CallRetrofitClient
    private lateinit var responseRetrofitClient: ResponseRetrofitClient

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            binding.mainActivity = this

            callRetrofitClient = CallRetrofitClient()
            responseRetrofitClient = ResponseRetrofitClient()
        } catch(e: Exception) {
            e.printStackTrace()
        }

    }

    //Call : getUsers
    fun testButton1() {
        launch(coroutineContext) {
            callRetrofitClient.getUsers()
        }
    }

    //Response : registerUser
    fun testButton2() {
        launch(coroutineContext) {
            val response = responseRetrofitClient.registerUser(
                userId = binding.editText1.text.toString(),
                nickname = binding.editText2.text.toString()
            )
            when (response) {
                is SearchUserResponse -> {
                    Log.d(TAG, "SearchUserResponse: $response")
                }
                is ErrorResponse -> {
                    Log.e(TAG, "ErrorResponse: $response")
                }
                else -> {
                    Log.e(TAG, "Exception: $response")
                }
            }
        }
    }

    //Response : searchUser
    fun testButton3() {
        launch(coroutineContext) {
            val response: Any = responseRetrofitClient.searchUser(
                userId = binding.editText1.text.toString()
            )
            when (response) {
                is SearchUserResponse -> {
                    Log.d(TAG, "SearchUserResponse: $response")
                }
                is ErrorResponse -> {
                    Log.e(TAG, "ErrorResponse: $response")
                }
                else -> {
                    Log.e(TAG, "Exception: $response")
                }
            }
        }
    }

    fun testButton4() {
        launch(coroutineContext) {

        }
    }
}

