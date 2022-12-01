package com.example.networkretrofit.network

import com.example.networkretrofit.network.model.response.Repository
import com.example.networkretrofit.network.retrofit.Client
import com.example.networkretrofit.network.util.ResponseUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
1. Retrofit 라이브러리
-인터페이스를 해석해 HTTP 로 데이터를 통신하는 라이브러리
장점 : 응답을 받을 객체를 설정해 두고 전체 응답(Json)에서 원하는 값만 뽑아 올 수 있음
단점 : 응답 받을 객체를 만들어 주려면 응답값이 어떤 것들이 있는지 미리 알아야하며, 응답값은 성공/실패에 따라 다를 수 있음

2. Call 사용 예시
-레트로핏을 사용하여 서버로부터 응답을 받을 때 사용하는 일반적인 방법

3. Response 사용 예시
-

4. Retrofit 라이브러리 단점 보안 : Object 를 사용한 전체 JSONObject 반환
-

*/


class RetrofitCallEx {
    private val responseUtil = ResponseUtil()

    /**
     * 깃허브 유저 데이터를 가져온다.
     * BaseURL/users/Kotlin/repos
     * enqueue() 로 명시적으로 성공/실패가 나눠져 응답이 콜백으로 오며 동작 처리가 가능하다.
     * 비동기처리를 따로 하지 않는 다면 콜백을 MainThread 에서 처리한다.
     * "Call" is useful when we are willing to use its enqueue callback function-Async
     */
    fun getUsersCallDataClassEnqueue() {
        try {
            Client.RetrofitClient.retrofit
                .getUsersCallDataClass()
                .enqueue(object : Callback<Repository> {
                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        responseUtil.showResponse(response)
                    }
                    override fun onFailure(call: Call<Repository>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 깃허브 유저 데이터를 가져온다.
     * BaseURL/users/Kotlin/repos
     * execute() 로 응답 코드별로 데이터를 처리할 수 있으며 비동기 처리를 따로 해줘야하기 때문에
     * 코루틴으로 비동기 처리를 한 경우 네트워크 결과를 코루틴 블럭 내부에서 반환 받을 수 있다.
     * 네트워크 작업을 동기적으로 처리하기 때문에 MainThread 에서 작업을 하면 에러가 발생 "android.os.NetworkOnMainThreadException"
     * @return 요청에 대한 서버 응답. JSON String
     */
    fun getUsersCallAnyExecute(): String {
        try {
            val response = Client.RetrofitClient.retrofit
                .getUsersCallAny()
                .execute()
            return responseUtil.showResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return responseUtil.handleExceptionResponse(e.message.toString())
        }
    }

    /**
     * userId 와 grade, 두개의 다중쿼리를 보내는 예시(실제 동작하지 않음)
     * "BaseURL/users/Kotlin/repos?userId=10&grade=Gold"
     */
    fun getUsersQueryMapEx(userId: String, grade: String): String {
        try {
            val query: MutableMap<String, String> = HashMap()
            query["userId"] = userId
            query["id"] = grade

            val response = Client.RetrofitClient.retrofit
                .getUsersCallAnyQueryMap(query)
                .execute()
            return responseUtil.showResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            return responseUtil.handleExceptionResponse(e.message.toString())
        }
    }
}