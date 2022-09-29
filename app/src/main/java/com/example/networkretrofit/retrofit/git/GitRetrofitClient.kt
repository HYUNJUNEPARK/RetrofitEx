package com.example.networkretrofit.retrofit.git

import android.util.Log
import com.example.networkretrofit.MainActivity.Companion.TAG
import com.example.networkretrofit.models.git.ErrorResponse
import com.example.networkretrofit.models.git.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class GitRetrofitClient: CoroutineScope {
    companion object {
        const val GIT_BASE_URL = "https://api.github.com/"
    }
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    //깃 서버 유저 조회
    //url=https://api.github.com/users/Kotlin/repos
    suspend fun getUsers() = withContext(coroutineContext) {
        try {
            GitRetrofitClient
                .retrofit
                .getUsers()
                .enqueue(object : Callback<Repository> {
                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        //code200 응답 : 성공적인 응답
                        if (response.isSuccessful) {
                            val response200 = response.body() as Repository

                            Log.d(TAG, "code200 Response headers : ${response.headers()}")
                            Log.d(TAG, "code200 Response Body : $response200")
                            Log.d(TAG, "code200 Response raw : ${response.raw()}")

                            // 리사이클러뷰와 사용 시
                            // adapter.userList = response.body() as Repository
                            // adapter.notifyDataSetChanged()
                        }
                        //code400 응답 : 예외 응답
                        else {
                            if (response.errorBody() != null) {
                                //errorBody 를 Json 타입으로 캐스팅한 후 ErrorResponse 데이터 클래스에 넣은 방법으로 더 좋은 방법이 있을 수 있음
                                val errorBodyJsonObj = JSONObject(response.errorBody()!!.string())
                                val response400 = ErrorResponse(
                                    message = errorBodyJsonObj["message"].toString(),
                                    documentationUrl = errorBodyJsonObj["documentation_url"].toString()
                                )
                                Log.d(TAG, "code400 Response headers : ${response.headers()}")
                                Log.d(TAG, "code400 Response raw : ${response.raw()}")
                                Log.d(TAG, "code400 Response errorBody : $response400")
                            }
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

