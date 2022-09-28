package com.example.networkretrofit.retrofit.mona

import android.util.Log
import com.example.networkretrofit.retrofit.git.GitApiService
import com.example.networkretrofit.retrofit.git.GitRetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MonaRetrofitClient : CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun retrofitResponseCreate() = withContext(coroutineContext) {
        try {
            when (
                val response = controlApiResponse { GitRetrofitClient.GitRetrofitClient.retrofit.getUsersResponse() }
            ) {
                is Result.Success -> {
                    Log.d("testLog", "${response.data}")
                }
                is Result.Error -> {
                    Log.d("testLog", "Fail")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("testLog", "Response: $e")
        }
    }


    suspend fun monaResponse(userId: String) = withContext(coroutineContext) {
//        try {
//            when(val response = controlApiResponse { RetrofitClient.retrofit.searchUser(userId)}) {
//                is Result.Success -> {
//                    Log.d("testLog", "Success: ")
//                }
//                is Result.Error -> {
//                    Log.d("testLog", "Error: ${response.exception}")
//                }
//            }
//        }catch (e: Exception) {
//            e.printStackTrace()
//            Log.e("testLog", "monaResponse: $e", )
//        }
    }


    suspend fun<T: Any> controlApiResponse(call: suspend() -> Response<T>): Result<T> {
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
        data class Error(val exception: String): Result<Nothing>()
    }




    object RetrofitClientMona {
        val retrofit: GitApiService by lazy {
            Retrofit.Builder()
                .baseUrl("http://220.72.230.41:9010")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitApiService::class.java)
        }
    }



}