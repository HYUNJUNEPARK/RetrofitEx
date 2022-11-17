package com.example.networkretrofit.retrofit.git

import com.example.networkretrofit.model.git.Repository
import com.example.networkretrofit.model.server.RegisterUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GitApiService {
    @GET("users/Kotlin/repos")
    fun getUsersCallEx(): Call<Repository>

    @GET("users/Kotlin/repos")
    fun getUsersCallObjectEx(): Call<Object>

    @GET("users/Kotlin/repos")
    suspend fun getUsersResponseEx(): Response<Repository>

//그 외 인터페이스 샘플
    //"BaseURL/api/users?user_id=userId"
    //URL 끝에 Query 를 추가해 Request 를 보낸다
    @GET("api/users")
    suspend fun searchUser(
        @Query("user_id") userId: String
    ): Response<Object>

    //"BaseURL/api/users"
    @POST("api/users")
    //URL 에는 나타나진 않지만 Body 에 데이터를 담아 Request 를 보낸다
    suspend fun registerUser(
        @Body user: RegisterUser
    ): Response<Object>

    //"BaseURL/profile/preference/{profileId}?parentId=parentId"
    //@Headers 에는 서버에서 지정한 권한을 넣어주고 @Path 에는 유동적으로 변하는 값을 넣어준다
    @Headers("Auth")
    @GET("profile/preference/{profileId}")
    fun getPreferences(
        @Path("profileId")
        profileId: String,
        @Query("parentId")
        parentId: Int
    ): Call<Object>



//TODO Add description
    @Headers("Auth")
    @PUT("profile/preference/{profileId}")
    fun addPreferences(
        @Path("profileId")
        profileId: String,
        @Query("categoryIds")
        categoryIds: Array<Int>
    ): Call<Object>

    @Headers("Auth")
    @DELETE("profile/preference/{profileId}")
    fun deletePreferences(
        @Path("profileId")
        profileId: String,
        @Query("categoryIds")
        categoryIds: Array<Int>
    ): Call<Object>
}