package com.example.networkretrofit.retrofit.git

import com.example.networkretrofit.model.git.Repository
import com.example.networkretrofit.model.server.RegisterUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GitApiService {
    //@GET : Read, 정보 조회용도, @Body 를 사용하지 않으며 URL 에 쿼리스트링을 포함해 모든 정보 표현
    //-@Path : 동적 URI 을 생성할 때 사용
    //-@Query : URI 에 쿼리스트링을 추가할 때 사용
    //-@QueryMap : 다중 쿼리 사용 시 사용

    //@POST : Create, @Body 에 전송할 데이터를 담아서 서버에 생성
    //-@Body : URL 에는 나타나진 않지만 Body 에 데이터를 담아 요청을 보낼 때 사용
    //-@Field : 인자를 form-urlencoded 방식으로 전송
    //-@FieldMap : @QueryMap 처럼 @Field 를 Map 으로 한번에 전송할 때 사용

    //TODO https://jaejong.tistory.com/38

    //@PUT : Update, @POST 와 같이 Body 에 데이터를 담아서 전송

    //@DELETE : 데이터 삭제

    //@Headers : 서버에서 지정한 권한을 넣을 때 사용

    //Call<DataClass> Ex
    @GET("users/Kotlin/repos")
    fun getUsersCallEx(): Call<Repository>

    //Call<Object> Ex
    @GET("users/Kotlin/repos")
    fun getUsersCallObjectEx(): Call<Object>

    //Response<DataClass> Ex
    @GET("users/Kotlin/repos")
    suspend fun getUsersResponseEx(): Response<Repository>


//그 외 인터페이스 샘플
    //@GET, @Query Ex
    //"BaseURL/api/users?user_id=userId"
    @GET("api/users")
    suspend fun searchUser(
        @Query("user_id")
        userId: String,
    ): Response<Object>

    //@GET, @QueryMap Ex
    //"BaseURL/users/Kotlin/repos?userId=10&id=96"
    @GET("users/Kotlin/repos")
    fun getUsersQueryMapEx(
        @QueryMap
        query: MutableMap<String, String>
    ): Call<Object>

    //@POST, @Body
    //"BaseURL/api/users"
    @POST("api/users")
    suspend fun registerUser(
        @Body
        user: RegisterUser,
    ): Response<Object>

    //@Headers, @GET, @Path Ex
    //"BaseURL/profile/preference/{profileId}?parentId=parentId"
    @Headers("Auth")
    @GET("profile/preference/{profileId}")
    fun getPreferences(
        @Path("profileId")
        profileId: String,
        @Query("parentId")
        parentId: Int,
    ): Call<Object>




    //TODO Add description
    //@Query 가 Array 인 경우 : "BaseURL/profile/preference/{profileId}?categoryIds=1&categoryIds=2"
    //@PUT
    @Headers("Auth")
    @PUT("profile/preference/{profileId}")
    fun addPreferences(
        @Path("profileId")
        profileId: String,
        @Query("categoryIds")
        categoryIds: Array<Int>,
    ): Call<Object>

    //@DELETE
    @Headers("Auth")
    @DELETE("profile/preference/{profileId}")
    fun deletePreferences(
        @Path("profileId")
        profileId: String,
        @Query("categoryIds")
        categoryIds: Array<Int>,
    ): Call<Object>
}