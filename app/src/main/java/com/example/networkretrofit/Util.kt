package com.example.networkretrofit

import android.util.Log
import com.example.networkretrofit.model.git.Repository
import retrofit2.Response

object Util {
    const val TAG = "testLog"

    fun showResponseDetail(response: Response<Repository>) {
        Log.d(TAG, "Response\n" +
                "[\nheaders : ${response.headers()}]\n" +
                "[ body : ${response.body()} ]\n" +
                "[ errorBody : ${response.errorBody()?.string()} ]\n" +
                "[ raw :  ${response.raw()}]")
    }

    fun showCurrentThread() {
        Log.d(TAG, "Thread: ${Thread.currentThread().name}")
    }

    fun showCurrentThread(position: String) {
        Log.d(TAG, "$position Thread: ${Thread.currentThread().name}")
    }
}