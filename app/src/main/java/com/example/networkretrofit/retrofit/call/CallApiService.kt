package com.example.networkretrofit.retrofit.call

import com.example.networkretrofit.model.call.Repository
import retrofit2.Call
import retrofit2.http.GET

interface CallApiService {
    @GET("users/Kotlin/repos")
    fun getUsers(): Call<Repository>
}