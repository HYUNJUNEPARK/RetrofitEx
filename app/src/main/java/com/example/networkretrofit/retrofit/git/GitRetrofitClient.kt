package com.example.networkretrofit.retrofit.git

import android.util.Log
import com.example.networkretrofit.Util.TAG
import com.example.networkretrofit.Util.showCurrentThread
import com.example.networkretrofit.Util.showResponseDetail
import com.example.networkretrofit.model.git.Repository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/*
Call 사용 예시
-레트로핏을 사용하여 서버로부터 응답을 받을 때 사용하는 일반적인 방법

1. enqueue()
-명시적으로 성공/실패가 나눠져 콜백으로 오며 그에 따른 동작 처리가 가능
"Call" is useful when we are willing to use its enqueue callback function-Async
-enqueue 를 사용하고 비동기처리를 따로 하지 않는 다면 콜백은 MainThread 에서 처리

2. execute()
-네트워크 작업을 동기적으로 처리하며 MainThread 에서 작업을 하면 에러가 발생
"android.os.NetworkOnMainThreadException"
-비동기 처리를 따로 해줘야하며 코루틴으로 비동기 처리를 한 경우 네트워크 결과를 코루틴 블럭 내부에서 반환 받을 수 있음
*/
class GitRetrofitClient {
    object GitRetrofitClient {
        val retrofit: GitApiService by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitApiService::class.java)
        }
    }

    //enqueue() example
    fun getUsers_enqueue() {
        try {
            GitRetrofitClient
                .retrofit
                .getUsers()
                .enqueue(object : Callback<Repository> {
                    //서버 응답 받은 경우
                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        showResponseDetail(response)
                    }
                    //서버 응답이 없는 경우
                    override fun onFailure(call: Call<Repository>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //execute() example
    fun getUsers_execute(): Any? {
        try {
            val response = GitRetrofitClient
                .retrofit
                .getUsers()
                .execute()

            showResponseDetail(response)

            return when(response.code()) {
                200 -> {
                    response.body()
                }
                400 -> {
                    response.errorBody()
                }
                else -> {
                    response.message()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}