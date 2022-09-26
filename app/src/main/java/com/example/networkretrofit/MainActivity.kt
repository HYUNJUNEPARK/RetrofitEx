package com.example.networkretrofit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkretrofit.databinding.ActivityMainBinding
import com.example.networkretrofit.retrofit.GitUserRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var gitUserRetrofit: GitUserRetrofit
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            gitUserRetrofit = GitUserRetrofit()
            initRecyclerView()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initRecyclerView() = with(binding) {
        adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }

    fun requestButtonClicked(v: View) {
        launch(coroutineContext) {
            gitUserRetrofit.retrofitCreate(adapter)
        }
    }
}
/*
https://deep-dive-dev.tistory.com/39 : 공변성 / 반공변성
*/