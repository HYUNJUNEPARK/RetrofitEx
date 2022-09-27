package com.example.networkretrofit.retrofit

import android.util.Log
import android.widget.Toast
import com.example.networkretrofit.RecyclerViewAdapter
import com.example.networkretrofit.models.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

/*
okhttp client를 Retrofit에 등록하여 cookie나 header를 지정하거나 logging을 사용할 수도 있다.
 */

class GitUserRetrofit : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun retrofitCallCreate(adapter: RecyclerViewAdapter) = withContext(coroutineContext) {
        try {
            val callGetUser = RetrofitClient.retrofit.getUsersCall()

            callGetUser.enqueue(object : Callback<Repository> {
                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        Log.d("testLog", "Response: ${response.body()}")
//                        adapter.userList = response.body() as Repository
//                        adapter.notifyDataSetChanged()
                    }
                    override fun onFailure(call: Call<Repository>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun retrofitResponseCreate(adapter: RecyclerViewAdapter) = withContext(coroutineContext) {
        try {
//            val response = RetrofitClient.retrofit.getUsersResponse()
//            val body = response.body() ?: return@withContext

            when (
                val response = controlApiResponse { RetrofitClient.retrofit.getUsersResponse() }
            ) {
                is Result.Success -> {
                    Log.d("testLog", "${response.data}")
                }
                is Result.Error -> {
                    Log.d("testLog", "Fail")
                }
            }

//            if (body!=null) {
//                Log.d("testLog", "Response: $body")
////                adapter.userList = body
////                adapter.notifyDataSetChanged()
//            } else {
//
//            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("testLog", "Response: $e")
        }
    }




    suspend fun<T: Any> controlApiResponse(call: suspend() -> Response<T>): Result<T>  {
        return try {
            val myResp = call.invoke()

            if (myResp.isSuccessful) {
                Result.Success(myResp.body()!!)
            } else {
                Result.Error(myResp.message() ?: "Something goes wrong")
            }

        } catch (e: Exception) {
            Result.Error(e.message ?: "Internet error runs")
        }
    }

    sealed class Result<out T: Any> {
        data class Success<out T : Any>(val data: T) : Result<T>()
        data class Error(val exception: String): Result<Nothing>()
    }





    object RetrofitClient {
        val retrofit: GitApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitApi::class.java)
        }
    }
}

