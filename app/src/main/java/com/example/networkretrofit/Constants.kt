package com.example.networkretrofit

import android.util.Log
import com.example.networkretrofit.network.model.response.Repository
import retrofit2.Response

object Constants {
    const val APP_TAG = "testLog"

    fun showResponseDataClassDetail(response: Response<Repository>) {
        Log.d(APP_TAG,
            "Response\n" +
                "[\nheaders : ${response.headers()}]\n" +
                "[ body : ${response.body()} ]\n" +
                "[ errorBody : ${response.errorBody()?.string()} ]\n" +
                "[ raw :  ${response.raw()}]"
        )
    }

    fun showResponseAnyDetail(response: Response<Any>) {
        Log.d(APP_TAG,
            "Response\n" +
                "[\nheaders : ${response.headers()}]\n" +
                "[ body : ${response.body()} ]\n" +
                "[ errorBody : ${response.errorBody().toString()} ]\n" +
                "[ raw :  ${response.raw()}]"
        )
    }

    fun showCurrentThread() {
        Log.d(APP_TAG, "Thread: ${Thread.currentThread().name}")
    }

    fun showCurrentThread(position: String) {
        Log.d(APP_TAG, "$position Thread: ${Thread.currentThread().name}")
    }
}