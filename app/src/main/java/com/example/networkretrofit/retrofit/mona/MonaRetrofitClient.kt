package com.example.networkretrofit.retrofit.mona

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.networkretrofit.MainActivity.Companion.TAG
import com.example.networkretrofit.models.mona.RegisterUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MonaRetrofitClient(val context: Context) : CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun registerUser(userId: String, nickname: String) = withContext(coroutineContext) {
        try {
            when (
                val response = controlApiResponse {
                    MonaRetrofitClient.retrofit.registerUser(
                        RegisterUser(
                            userId = userId,
                            nickname = nickname
                        )
                    )
                }
            ) {
                is Result.Success -> {
                    //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    Log.d("testLog", "${response.data}")
                }
                is Result.Error -> {
                    //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "register Exception: $e")
        }
    }

    suspend fun searchUser(userId: String) = withContext(coroutineContext) {
        try {
            when (
                val response = controlApiResponse {
                    MonaRetrofitClient.retrofit.searchUser(userId)
//                    MonaRetrofitClient.retrofit.registerUser(
//                        RegisterUser(
//                            userId = userId
//                        )
//                    )
                }
            ) {
                is Result.Success -> {
                    //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    Log.d("testLog", "${response.data}")
                }
                is Result.Error -> {
                    Log.d(TAG, "searchUser Error: ")
                    //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "searchUser Exception: $e")
        }
    }

    private suspend fun<T: Any> controlApiResponse(call: suspend() -> Response<T>): Result<T> {
        return try {
            val response = call.invoke()

            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(response.message() ?: "Something goes wrong")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Internet error runs")
        }
    }

    sealed class Result<out T: Any> {
        data class Success<out T : Any>(val data: T) : Result<T>()
        //data class Error(val exception: String): Result<Nothing>()
        data class Error(val exception: String): Result<Nothing>()
    }

    object MonaRetrofitClient {
        val retrofit: MonaApiService by lazy {
            Retrofit.Builder()
                .baseUrl("http://220.72.230.41:9010")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MonaApiService::class.java)
        }
    }
}