package com.example.networkretrofit.retrofit.mona

import android.util.Log
import com.example.networkretrofit.MainActivity.Companion.TAG
import com.example.networkretrofit.models.mona.ErrorResponse
import com.example.networkretrofit.models.mona.RegisterUser
import com.example.networkretrofit.models.mona.RegisterUserResponse
import com.example.networkretrofit.models.mona.SearchUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

/*
sealed class
제네릭 in out
You can only access response.body.string() once after that it will return null.
https://50billion-dollars.tistory.com/entry/Android-Retrofit-errorBody-%EA%B0%92-%ED%99%95%EC%9D%B8%ED%95%98%EA%B8%B0
*/

class MonaRetrofitClient() : CoroutineScope{
    companion object {
        const val MONA_BASE_URL = "http://220.72.230.41:9010"
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
            if (response.isSuccessful) {
                Log.d(TAG, "code200 Response headers : ${response.headers()}")
                Log.d(TAG, "code200 Response Body : ${response.body()}")
                Log.d(TAG, "code200 Response raw : ${response.raw()}")
                return Result.Success(response.body()!!)
            }
            //code400 응답 : 예외 응답
            else {
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
        }
        //서버 응답 조차 없는 경우
        catch (e: Exception) {
            return Result.Exception(e.message ?: "Internet error runs")
        }
    }

    sealed class Result<out T: Any> {
        data class Success<out T : Any>(val body: T) : Result<T>()
        data class Error<out T : Any>(val body: T): Result<T>()
        data class Exception(val exception: String): Result<Nothing>()
    }

    object MonaRetrofitClient {
        val retrofit: MonaApiService by lazy {
            Retrofit.Builder()
                .baseUrl(MONA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MonaApiService::class.java)
        }
    }
}