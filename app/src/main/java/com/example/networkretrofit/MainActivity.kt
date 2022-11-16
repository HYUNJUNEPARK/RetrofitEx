package com.example.networkretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.networkretrofit.databinding.ActivityMainBinding
import com.example.networkretrofit.model.server.ErrorResponse
import com.example.networkretrofit.model.server.SearchUserResponse
import com.example.networkretrofit.retrofit.git.GitRetrofitClient
import com.example.networkretrofit.retrofit.server.ServerRetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    companion object {
        const val TAG = "testLog"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var gitRetrofitClient: GitRetrofitClient
    private lateinit var serverRetrofitClient: ServerRetrofitClient

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            binding.mainActivity = this

            gitRetrofitClient = GitRetrofitClient()
            serverRetrofitClient = ServerRetrofitClient()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    //Call : getUsersAsync
    fun testButton1() {
        gitRetrofitClient.getUsersAsync()
    }

    //Call : getUsersSync
    //코루틴 블럭에서 실행하지 않으면 Null 값이 나옴
    fun testButton2() {
        launch(coroutineContext) {
            gitRetrofitClient.getUsersSync().let {
                Log.d(TAG, "getUsersSync Result : $it")
            }
        }
    }

    //Response : registerUser
    fun testButton3() {
        launch(coroutineContext) {
            val response = serverRetrofitClient.registerUser(
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
    fun testButton4() {
        launch(coroutineContext) {
            val response: Any = serverRetrofitClient.searchUser(
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
}

