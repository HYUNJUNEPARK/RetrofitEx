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

/*
okhttp client를 Retrofit에 등록하여 cookie나 header를 지정하거나 logging을 사용할 수도 있다.
 */

class GitRetrofitClient(val context: Context) : CoroutineScope {
    companion object {
        const val GIT_BASE_URL = "https://api.github.com/"
    }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun getUsers() = withContext(coroutineContext) {
        try {
            GitRetrofitClient
                .retrofit
                .getUsers()
                .enqueue(object : Callback<Repository> {
                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        if (response.isSuccessful) {
                            Log.d(TAG, "isSuccessful Response headers : ${response.headers()}")
                            Log.d(TAG, "isSuccessful Response Body : ${response.body()}")
                            Log.d(TAG, "isSuccessful Response raw : ${response.raw()}")
                        } else {
                            Log.d(TAG, "unSuccessful Response headers : ${response.headers()}")
                            Log.d(TAG, "unSuccessful Response Body : ${response.body()}")
                            Log.d(TAG, "unSuccessful Response raw : ${response.raw()}")
                        }
                        Toast.makeText(context, "onResponse // code : ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                    override fun onFailure(call: Call<Repository>, t: Throwable) {
                        t.printStackTrace()
                        Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show()

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

