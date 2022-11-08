package com.example.networkretrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.networkretrofit.databinding.ActivityMainBinding
import com.example.networkretrofit.models.response.ErrorResponse
import com.example.networkretrofit.models.response.SearchUserResponse
import com.example.networkretrofit.retrofit.call.GitRetrofitClient
import com.example.networkretrofit.retrofit.response.ServerRetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    companion object {
        const val TAG = "testLog"
    }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var gitRetrofitClient: GitRetrofitClient
    private lateinit var serverRetrofitClient: ServerRetrofitClient
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            gitRetrofitClient = GitRetrofitClient()
            serverRetrofitClient = ServerRetrofitClient()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    fun testButton1(v: View) {
        launch(coroutineContext) {
            withContext(Dispatchers.IO) {
                gitRetrofitClient.getUsers()
            }
        }
    }

    fun testButton2(v: View) {
        val input1: String = binding.editText1.text.toString()
        val input2: String = binding.editText2.text.toString()

        if (input1.isEmpty() || input2.isEmpty()) {
            return
        }

        launch(coroutineContext) {
            withContext(Dispatchers.IO) {
                serverRetrofitClient.registerUser(
                    userId = input1,
                    nickname = input2
                )
            }
        }
    }

    fun testButton3(v: View) {
        val input1: String = binding.editText1.text.toString()

        launch(coroutineContext) {
            withContext(Dispatchers.IO) {
                val response: Any = serverRetrofitClient.searchUser(input1)

                if(response is SearchUserResponse) {
                    Log.e(TAG, "SearchUserResponse: $response")
                    return@withContext
                }
                if (response is ErrorResponse) {
                    Log.e(TAG, "ErrorResponse: $response")
                    return@withContext
                }
                else {
                    Log.e(TAG, "Exception: $response")
                    return@withContext
                }
            }
        }
    }

    fun testButton4(v: View) {
        launch(coroutineContext) {

        }
    }
}

