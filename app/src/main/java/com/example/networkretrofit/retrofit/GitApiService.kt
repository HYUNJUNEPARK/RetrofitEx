package com.example.networkretrofit.retrofit

import com.example.networkretrofit.model.Repository
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

/*
 서비스 인터페이스
 네트워크 통신이 필요한 순간에 호출할 함수를 포함하는 인터페이스
 인터페이스를 해석해 레트로핏이 통신하는 클래스를 자동으로 만들어주며 어노테이션(@GET)을 보고 통신할 수 있는 코드 생성
 호출 방식, 세부 주소, 함수가 담겨 있음

 call
 일반적으로 retrofit 을 사용하여 서버로부터 응답을 받을 때 사용되는 기본 방법
 명시적으로 성공과 실패가 나눠져 그에 따른 동작 처리할 수 있음

 response
 간략한 코드로 서버로 부터 데이터를 받아 올 수 있음
 코루틴이나 RX 자바 등 비동기 실행을 한다면 response 를 사용하는게 더 좋다는 의견이 있음

 Retrofit call 과 Response 차이
 https://jeongupark-study-house.tistory.com/208
*/

interface GitApiService {
    @GET("users/Kotlin/repos")
    fun getUsersCall(): Call<Repository>

    @GET("users/Kotlin/repos")
    fun getUsersResponse(): Response<Repository>


}