package com.example.networkretrofit.network.retrofit

import com.example.networkretrofit.network.model.response.Repository
import com.example.networkretrofit.deprecated.server.RegisterUser
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Service {
/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
//실제 동작하는 인터페이스 : Call
    @GET("users/Kotlin/repos")
    fun getUsersCallDataClass(): Call<Repository>

    @GET("users/Kotlin/repos")
    fun getUsersCallAny(): Call<Any>

/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
//실제 동작하는 인터페이스 : Response
    @GET("users/Kotlin/repos")
    fun getUsersResponseDataClass(): Response<Repository>

    @GET("users/Kotlin/repos")
    fun getUsersResponseAny(): Response<Any>

/*>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>*/
/*
GET 관련 Annotation Example
-@GET : Read, 정보 조회용도, @Body 를 사용하지 않으며 URL 에 쿼리스트링을 포함해 모든 정보 표현
-@Query : URI 에 쿼리스트링을 추가할 때 사용
-@QueryMap : 다중 쿼리 사용 시 사용
-@Path : 동적 URI 을 생성할 때 사용
-@Headers : 서버에서 지정한 권한을 넣을 때 사용
*/
    //"BaseURL/api/users?user_id=userId"
    @GET("api/users")
    fun searchUser(
        @Query("user_id")
        userId: String,
    ): Call<Any>

    //"BaseURL/users/Kotlin/repos?userId=10&id=96"
    @GET("users/Kotlin/repos")
    fun getUsersCallAnyQueryMap(
        @QueryMap
        query: MutableMap<String, String>
    ): Call<Any>

    //"BaseURL/profile/preference/{profileId}?parentId=parentId"
    @Headers("Auth")
    @GET("profile/preference/{profileId}")
    fun getPreferences(
        @Path("profileId")
        profileId: String,
        @Query("parentId")
        parentId: Int,
    ): Call<Any>

/*
Post 관련 Annotation Example
-@POST : Create, @Body 에 전송할 데이터를 담아서 서버에 생성
-@Body : URL 에는 나타나진 않지만 Body 에 데이터를 담아 요청을 보낼 때 사용
-@Field : 인자를 form-urlencoded 방식으로 전송
-@FieldMap : @QueryMap 처럼 @Field 를 Map 으로 한번에 전송할 때 사용
*/
    //"BaseURL/api/users"
    @POST("api/users")
    suspend fun registerUser(
        @Body
        user: RegisterUser,
    ): Call<Any>

/*
Put 관련 Annotation Example
-@PUT : Update, 서버 내 데이터를 수정하는 역할로 새로 생성하는 개념이 아니라 수정하는 의미
-@POST 와 마찬가지로 @Body/@Field/@FieldMap 으로 데이터 전송. 다른 점은 변경할 데이터를 선택해야 함
*/
    //"BaseURL/profile/preference/{profileId}?categoryIds=1&categoryIds=2"
    @Headers("Auth")
    @PUT("profile/preference/{profileId}")
    fun addPreferences(
        @Path("profileId")
        profileId: String,
        @Query("categoryIds")
        categoryIds: Array<Int>,
    ): Call<Any>

/*
@DELETE : 데이터 삭제
-반환되는 DTO 데이터는 없으며, 삭제 성공 시 응답코드 200 응답
*/
    //"BaseURL/profile/preference/{profileId}/category
    @Headers("Auth")
    @DELETE("profile/preference/{profileId}?categoryIds=1&categoryIds=2")
    fun deletePreferences(
        @Path("profileId")
        profileId: String,
        @Query("categoryIds")
        categoryIds: Array<Int>,
    ): Call<Any>
}