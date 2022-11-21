package com.example.networkretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.networkretrofit.Util.TAG
import com.example.networkretrofit.databinding.ActivityMainBinding
import com.example.networkretrofit.model.server.ErrorResponse
import com.example.networkretrofit.model.server.SearchUserResponse
import com.example.networkretrofit.retrofit.git.GitRetrofitClient
import com.example.networkretrofit.retrofit.server.ServerRetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var gitRetrofitClient: GitRetrofitClient
    private lateinit var serverRetrofitClient: ServerRetrofitClient

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

    //Call
    fun callExample() {
        //enqueue() Ex
//        gitRetrofitClient.getUsersEnqueueEx()

        //execute() Ex
//        CoroutineScope(Dispatchers.IO).launch {
//            gitRetrofitClient.getUsersExecuteEx().let {
//                Log.d(TAG, "getUsersExecuteEx(): $it")
//            }
//        }

        CoroutineScope(Dispatchers.IO).launch {
            gitRetrofitClient.getUsersCallAnyEx().let {
                Log.d(TAG, "getUsersCallAnyEx(): $it")
            }
        }


//아래부터 실제 동작 하지 않은 코드
        //@QueryMap Ex
//        CoroutineScope(Dispatchers.IO).launch {
//            gitRetrofitClient.getUsersQueryMapEx(
//                param1 = "10",
//                param2 = "96"
//            ).let {
//                Log.d(TAG, "getUsersQueryMapEx(): $it")
//            }
//        }
    }









    fun testButton2() {

    }

    //Response : registerUser
    fun testButton3() {
        CoroutineScope(Dispatchers.IO).launch {
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
        CoroutineScope(Dispatchers.IO).launch {
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

