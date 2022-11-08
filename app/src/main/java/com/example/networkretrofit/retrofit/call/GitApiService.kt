package com.example.networkretrofit.retrofit.call

import com.example.networkretrofit.models.call.Repository
import retrofit2.Call
import retrofit2.http.GET

interface GitApiService {
    @GET("users/Kotlin/repos")
    fun getUsers(): Call<Repository>
}