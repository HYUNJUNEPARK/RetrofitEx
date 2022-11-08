package com.example.networkretrofit.retrofit.response

import android.util.Log
import com.example.networkretrofit.MainActivity.Companion.TAG
import com.example.networkretrofit.models.response.ErrorResponse
import com.example.networkretrofit.models.response.RegisterUser
import com.example.networkretrofit.models.response.RegisterUserResponse
import com.example.networkretrofit.models.response.SearchUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class ServerRetrofitClient: CoroutineScope{
    object MonaRetrofitClient {
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

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    //유저 등록 API
    suspend fun registerUser(userId: String, nickname: String): Any {
        try {
            val response = controlApiResponse {
                MonaRetrofitClient.retrofit.registerUser(
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
                MonaRetrofitClient.retrofit.searchUser(userId)
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

    private suspend fun<T: Any> controlApiResponse(call: suspend() -> Response<T>): Result<Any> {
        val response = call.invoke()
        try {
            //code200 응답 : 성공적인 응답
            if (response.code() == 200) {
                Log.d(TAG, "code200 Response headers : ${response.headers()}")
                Log.d(TAG, "code200 Response Body : ${response.body()}")
                Log.d(TAG, "code200 Response raw : ${response.raw()}")
                return Result.Success(response.body()!!)
            }
            //code400 응답 : 예외 응답
            if (response.code() == 400) {
                if (response.errorBody() == null) {
                    return Result.Exception("errorBody is null")
                }

                val errorBodyJsonObj = JSONObject(response.errorBody()!!.string())
                val response400 = ErrorResponse(
                    message = errorBodyJsonObj["message"].toString(),
                    errorCode = errorBodyJsonObj["error_code"].toString()
                )
                Log.d(TAG, "code400 Response headers : ${response.headers()}")
                Log.d(TAG, "code400 Response errorBody :$response400")
                Log.d(TAG, "code400 Response raw : ${response.raw()}")
                return Result.Error(response400)
            }
            else {
                return Result.Exception(response.errorBody()?.string()+"Internet error runs")
            }
        }
        //서버 응답 조차 없는 경우
        catch (e: Exception) {
            return Result.Exception(e.message ?: "Internet error runs")
        }
    }
}