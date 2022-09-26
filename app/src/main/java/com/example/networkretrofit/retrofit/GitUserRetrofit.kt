package com.example.networkretrofit.retrofit

import com.example.networkretrofit.RecyclerViewAdapter
import com.example.networkretrofit.model.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class GitUserRetrofit : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun retrofitCreate(adapter: RecyclerViewAdapter) = withContext(coroutineContext) {
        try {
            MyRetrofit.myRetrofit
                .getUsersCall() //Call<Repository> 반환
                .enqueue(object : Callback<Repository> { //Call 객체의 enqueue() 함수를 호출 해 네트워크 통신 수행
                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        adapter.userList = response.body() as Repository
                        adapter.notifyDataSetChanged()
                    }
                    override fun onFailure(call: Call<Repository>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    object MyRetrofit {
        val myRetrofit: GitApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitApiService::class.java)
        }
    }
}

