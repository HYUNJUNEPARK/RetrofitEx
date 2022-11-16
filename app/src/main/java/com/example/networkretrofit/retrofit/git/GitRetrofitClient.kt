package com.example.networkretrofit.retrofit.git

import android.util.Log
import com.example.networkretrofit.MainActivity.Companion.TAG
import com.example.networkretrofit.model.git.ErrorResponse
import com.example.networkretrofit.model.git.Repository
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    //깃서버 유저 조회
    //반환 값은 따로 없으며 단순하게 결과를 로그로 찍음
    fun getUsersAsync() {
        try {
            GitRetrofitClient
                .retrofit
                .getUsers()
                .enqueue(object : Callback<Repository> {
                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        //200 Response : 성공적인 응답
                        if (response.isSuccessful) {
                            val responseBody = response.body() as Repository

                            Log.d(TAG, "code 200 Response\n" +
                                    "[ headers : ${response.headers()} ]\n" +
                                    "[ body : $responseBody ]\n" +
                                    "[ raw :  ${response.raw()}]")
                            // 리사이클러뷰와 사용 예시
                            // adapter.userList = response.body() as Repository
                            // adapter.notifyDataSetChanged()
                        }
                        //400 Response : 예외 응답
                        else {
                            if (response.errorBody() != null) {
                                //errorBody 를 Json 타입으로 캐스팅한 후, ErrorResponse 데이터 클래스에 넣은 방법.
                                //더 좋은 방법이 있을 수 있음
                                val errorBodyJsonObj = JSONObject(response.errorBody()!!.string())
                                val responseBody = ErrorResponse(
                                    message = errorBodyJsonObj["message"].toString(),
                                    documentationUrl = errorBodyJsonObj["documentation_url"].toString()
                                )
                                Log.d(TAG, "code 400 Response\n" +
                                        "[ headers : ${response.headers()} ]\n" +
                                        "[ body : $responseBody ]\n" +
                                        "[ raw :  ${response.raw()}]")
                            }
                        }
                    }
                    //서버 응답이 없는 경우
                    override fun onFailure(call: Call<Repository>, t: Throwable) {
                        Log.e(TAG, "onFailure : $t")
                        t.printStackTrace()
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getUsersSync(): Any? {
        try {
            val result = GitRetrofitClient
                .retrofit
                .getUsers()
                .execute()

            return when(result.code()) {
                200 -> {
                    Log.d(TAG, "200 response ")
                    result.body()
                }
                400 -> {
                    Log.e(TAG, "400 response ")
                    result.errorBody()
                }
                else -> {
                    Log.e(TAG, "Exception response ")
                    result.message()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}