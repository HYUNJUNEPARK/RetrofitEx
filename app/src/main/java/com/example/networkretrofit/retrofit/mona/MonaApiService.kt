package com.example.networkretrofit.retrofit.mona

import com.example.networkretrofit.models.mona.RegisterUser
import com.example.networkretrofit.models.mona.RegisterUserResponse
import com.example.networkretrofit.models.mona.SearchUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MonaApiService {
    @GET("api/users")
    suspend fun searchUser(
        @Query("user_id") userId: String
    ): Response<SearchUserResponse>

    @POST("api/users")
    suspend fun registerUser(
        @Body user: RegisterUser
    ): Response<RegisterUserResponse>
}