package com.example.networkretrofit.deprecated.retrofit.server

import android.util.Log
import com.example.networkretrofit.Constants.APP_TAG
import com.example.networkretrofit.deprecated.server.ErrorResponse
import com.example.networkretrofit.deprecated.server.RegisterUser
import com.example.networkretrofit.deprecated.server.RegisterUserResponse
import com.example.networkretrofit.deprecated.server.SearchUserResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerRetrofitClient{
    object ResponseRetrofitClient {
        val retrofit: ServerApiService by lazy {
            Retrofit.Builder()
                .baseUrl("http://220.72.230.41:9010")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServerApiService::class.java)
        }
    }

    sealed class Result<out T: Any> {
        data class Success<out T : Any>(val body: T) : Result<T>()
        data class Error<out T : Any>(val body: T): Result<T>()
        data class Exception(val exception: String): Result<Nothing>()
    }

    //유저 등록 API
    suspend fun registerUser(userId: String, nickname: String): Any {
        try {
            val response = controlApiResponse {
                ResponseRetrofitClient.retrofit.registerUser(
                    RegisterUser(
                        userId = userId,
                        nickname = nickname
                    )
                )
            }
            return when (response) {
                is Result.Success -> {
                    response.body as RegisterUserResponse
                }
                is Result.Error -> {
                    response.body as ErrorResponse
                }
                is Result.Exception -> {
                    response.exception
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Exception(e.message ?: "Internet error runs")
        }
    }

    //유저 검색 API
    suspend fun searchUser(userId: String): Any {
        try {
            val response = controlApiResponse {
                ResponseRetrofitClient.retrofit.searchUser(userId)
            }
            return when (response) {
                is Result.Success -> {
                    response.body as SearchUserResponse
                }
                is Result.Error -> {
                    response.body as ErrorResponse
                }
                is Result.Exception -> {
                    response.exception
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Exception(e.message ?: "Internet error runs")
        }
    }

    private suspend fun<T: Any> controlApiResponse(
        call: suspend() -> Response<T>
    ): Result<Any> {
        try {
            val response = call.invoke()
            when(response.code()) {
                //200 Response : 성공적인 응답
                200 -> {
                    Log.d(APP_TAG,"200 Response\n" +
                            "[ headers : ${response.headers()} ]\n" +
                            "[ body :  ${response.body()} ]\n" +
                            "[ raw : ${response.raw()} ]")
                    return Result.Success(response.body()!!)
                }
                //400 Response : 예외 응답
                400 -> {
                    if (response.errorBody() == null) {
                        return Result.Exception("errorBody is null")
                    }
                    val errorBodyJsonObj = JSONObject(response.errorBody()!!.string())
                    val errorResponse = ErrorResponse(
                        message = errorBodyJsonObj["message"].toString(),
                        errorCode = errorBodyJsonObj["error_code"].toString()
                    )
                    Log.d(APP_TAG,"200 Response\n" +
                            "[ headers : ${response.headers()}]\n" +
                            "[ errorBody :  $errorResponse ]\n" +
                            "[ raw : ${response.raw()} ]")
                    return Result.Error(errorResponse)
                }
                //Exception
                else -> {
                    return Result.Exception(
                                response.errorBody()?.string()+
                                "Internet error runs"
                            )
                }
            }
        }
        //서버 응답이 없는 경우
        catch (e: Exception) {
            return Result.Exception(e.message ?: "Internet error runs")
        }
    }
}