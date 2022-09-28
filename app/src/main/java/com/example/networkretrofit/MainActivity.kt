package com.example.networkretrofit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkretrofit.databinding.ActivityMainBinding
import com.example.networkretrofit.retrofit.git.GitRetrofitClient
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
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            gitRetrofitClient = GitRetrofitClient(this)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    fun testButton1(v: View) {
        launch(coroutineContext) {
            gitRetrofitClient.retrofitCallCreate()
        }
    }

    fun testButton2(v: View) {
        launch(coroutineContext) {

        }
    }

    fun testButton3(v: View) {
        launch(coroutineContext) {

        }
    }

    fun testButton4(v: View) {
        launch(coroutineContext) {

        }
    }
}

/*
https://deep-dive-dev.tistory.com/39 : 공변성 / 반공변성
*/