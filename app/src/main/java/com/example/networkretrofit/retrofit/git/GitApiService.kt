package com.example.networkretrofit.retrofit.git

import com.example.networkretrofit.model.git.Repository
import retrofit2.Call
import retrofit2.http.GET

interface GitApiService {
    @GET("users/Kotlin/repos")
    fun getUsers(): Call<Repository>
}