package com.example.networkretrofit.retrofit.mona

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.networkretrofit.MainActivity.Companion.TAG
import com.example.networkretrofit.models.mona.RegisterUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class MonaRetrofitClient(val context: Context) : CoroutineScope{
    companion object {
        const val MONA_BASE_URL = "http://220.72.230.41:9010"
    }
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
                }
            ) {
                is Result.Success -> {


                    //Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    Log.d("testLog", "Result.Success ${response.data}")
                }
                is Result.Error -> {



                    Log.d(TAG, "searchUser response.exception: ${response.exception}")
                    //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "searchUser Exception: $e")
        }
    }

    private suspend fun<T: Any> controlApiResponse(call: suspend() -> Response<T>): Result<T> {
        val response = call.invoke()
        try {
            if (response.isSuccessful) {
                Log.d(TAG, "controlApiResponse isSuccessful Response headers : ${response.headers()}")
                Log.d(TAG, "controlApiResponse isSuccessful Response Body : ${response.body()}")
                Log.d(TAG, "controlApiResponse isSuccessful Response raw : ${response.raw()}")
                return Result.Success(response.body()!!)
            } else {
                Log.d(TAG, "controlApiResponse unSuccessful Response headers : ${response.headers()}")
                Log.d(TAG, "controlApiResponse unSuccessful Response errorBody : ${response.body()}")
                //https://50billion-dollars.tistory.com/entry/Android-Retrofit-errorBody-%EA%B0%92-%ED%99%95%EC%9D%B8%ED%95%98%EA%B8%B0
                Log.d(TAG, "controlApiResponse unSuccessful Response Body"+response.errorBody()?.string())
                Log.d(TAG, "controlApiResponse unSuccessful Response raw : ${response.raw()}")
                return Result.Error(response.message() ?: "Something goes wrong")
            }
        } catch (e: Exception) {
            Log.d(TAG, "catch: ${response.body()!!}")
            return Result.Error(e.message ?: "Internet error runs")
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
                .baseUrl(MONA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MonaApiService::class.java)
        }
    }
}