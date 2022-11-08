package com.example.networkretrofit.retrofit.response

import com.example.networkretrofit.models.response.RegisterUser
import com.example.networkretrofit.models.response.RegisterUserResponse
import com.example.networkretrofit.models.response.SearchUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServerApiService {
    @GET("api/users")
    suspend fun searchUser(
        @Query("user_id") userId: String
    ): Response<SearchUserResponse>

    @POST("api/users")
    suspend fun registerUser(
        @Body user: RegisterUser
    ): Response<RegisterUserResponse>
}