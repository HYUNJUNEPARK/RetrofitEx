package com.example.networkretrofit.retrofit.git

import com.example.networkretrofit.Util.showResponseDataClassDetail
import com.example.networkretrofit.Util.showResponseObjectDetail
import com.example.networkretrofit.model.git.Repository
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/*
1. Retrofit 라이브러리
-인터페이스를 해석해 HTTP 로 데이터를 통신하는 라이브러리
장점 : 응답을 받을 객체를 설정해 두고 전체 응답(Json)에서 원하는 값만 뽑아 올 수 있음
단점 : 응답 받을 객체를 만들어 주려면 응답값이 어떤 것들이 있는지 미리 알아야하며, 응답값은 성공/실패에 따라 다를 수 있음

2. Call 사용 예시
-레트로핏을 사용하여 서버로부터 응답을 받을 때 사용하는 일반적인 방법

2.1 enqueue()
-명시적으로 성공/실패가 나눠져 콜백으로 오며 그에 따른 동작 처리가 가능
"Call" is useful when we are willing to use its enqueue callback function-Async
-enqueue 를 사용하고 비동기처리를 따로 하지 않는 다면 콜백을 MainThread 에서 처리

2.2 execute()
-네트워크 작업을 동기적으로 처리하기 때문에 MainThread 에서 작업을 하면 에러가 발생 "android.os.NetworkOnMainThreadException"
-비동기 처리를 따로 해줘야하며 코루틴으로 비동기 처리를 한 경우 네트워크 결과를 코루틴 블럭 내부에서 반환 받을 수 있음

3. Response 사용 예시
-

4. Retrofit 라이브러리 단점 보안 : Object 를 사용한 전체 JSONObject 반환
-

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

    //Call<DataClass> enqueue()Ex
    fun getUsersEnqueueEx() {
        try {
            GitRetrofitClient
                .retrofit
                .getUsersCallEx()
                .enqueue(object : Callback<Repository> {
                    //서버 응답 받은 경우
                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        showResponseDataClassDetail(response)
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

    //Call<DataClass> execute()Ex
    fun getUsersExecuteEx(): Any? {
        try {
            val response = GitRetrofitClient
                .retrofit
                .getUsersCallEx()
                .execute()

            showResponseDataClassDetail(response)

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

    //Call<Object> QueryMapEx
    fun getUsersQueryMapEx(param1: String, param2: String): JSONObject? {
        try {
            val query: MutableMap<String, String> = HashMap()
            query["userId"] = param1
            query["id"] = param2

            val response = GitRetrofitClient
                .retrofit
                .getUsersQueryMapEx(query)
                .execute()

            showResponseObjectDetail(response)

            when(response.code()) {
                200 -> {
                    if (response.body() != null) {
                        val data = Gson().toJson(response.body())
                        return JSONObject(data)
                    }
                    return null
                }
                400 -> {
                    if (response.errorBody() != null) {
                        return JSONObject(response.errorBody()!!.string())
                    }
                    return null
                }
                else -> {
                    return null
                }
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }










    //TODO Add Response Description
    sealed class Result<out T: Any> {
        data class Success<out T : Any>(val body: T) : Result<T>()
        data class Error<out T : Any>(val body: T): Result<T>()
        data class Exception(val exception: String): Result<Nothing>()
    }
}