package com.example.networkretrofit.retrofit.git

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.networkretrofit.MainActivity.Companion.TAG
import com.example.networkretrofit.models.git.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class GitRetrofitClient() : CoroutineScope {
    companion object {
        const val GIT_BASE_URL = "https://api.github.com/"
    }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    //깃 서버 유저 조회
    //url=https://api.github.com/users/Kotlin/repos

    suspend fun tt(): String = withContext(coroutineContext) {
        return@withContext "aa"
    }

    suspend fun getUsers() = withContext(coroutineContext) {
        try {
            GitRetrofitClient
                .retrofit
                .getUsers()
                .enqueue(object : Callback<Repository> {
                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        //code=200 응답 : 성공적인 응답
                        if (response.isSuccessful) {
                            Log.d(TAG, "code=200 Response headers : ${response.headers()}")
                            Log.d(TAG, "code=200 Response Body : ${response.body()}")
                            Log.d(TAG, "code=200 Response raw : ${response.raw()}")
                        }
                        //code=400 응답 : 예외 응답
                        else {
                            Log.d(TAG, "code=400 Response headers : ${response.headers()}")
                            Log.d(TAG, "code=400 Response Body :"+ response.errorBody()?.string())
                            Log.d(TAG, "code=400 Response raw : ${response.raw()}")
                        }
                    }
                    //서버 응답 조차 없는 경우
                    override fun onFailure(call: Call<Repository>, t: Throwable) {
                        Log.e(TAG, "onFailure : $t")
                        t.printStackTrace()
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    object GitRetrofitClient {
        val retrofit: GitApiService by lazy {
            Retrofit.Builder()
                .baseUrl(GIT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitApiService::class.java)
        }
    }
}

