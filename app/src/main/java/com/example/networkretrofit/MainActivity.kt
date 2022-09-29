package com.example.networkretrofit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.networkretrofit.databinding.ActivityMainBinding
import com.example.networkretrofit.models.mona.ErrorResponse
import com.example.networkretrofit.models.mona.SearchUserResponse
import com.example.networkretrofit.retrofit.git.GitRetrofitClient
import com.example.networkretrofit.retrofit.mona.MonaRetrofitClient
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
    private lateinit var monaRetrofitClient: MonaRetrofitClient
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            gitRetrofitClient = GitRetrofitClient()
            monaRetrofitClient = MonaRetrofitClient()
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

        if (input1.isEmpty() || input2.isEmpty()) return

        launch(coroutineContext) {
            withContext(Dispatchers.IO) {
                monaRetrofitClient.registerUser(
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
                val tt = monaRetrofitClient.searchUser(input1)
                if(tt is SearchUserResponse) {
                    Log.e(TAG, "SearchUserResponse: $tt", )
                }
                if (tt is ErrorResponse) {
                    Log.e(TAG, "ErrorResponse: $tt", )
                }
                else {
                    Log.e(TAG, "Exception: $tt", )
                }
            }
        }
    }

    fun testButton4(v: View) {
        launch(coroutineContext) {

        }
    }
}

