package com.example.networkretrofit.network

import android.util.Log
import com.example.networkretrofit.Constants.APP_TAG
import com.example.networkretrofit.network.NetworkConstants.ERROR_CODE
import com.example.networkretrofit.network.NetworkConstants.EXCEPTION_BODY_EMPTY
import com.example.networkretrofit.network.NetworkConstants.NETWORK_TAG
import com.example.networkretrofit.network.model.exception.ResponseException
import com.example.networkretrofit.network.model.response.Repository
import com.google.gson.Gson
import retrofit2.Response

/**
 * 서버 응답을 반환한다.
 */
class ResponseUtil {

    private val gson = Gson()

    fun showResponse(response: Response<Repository>) {
        try {
            Log.d(NETWORK_TAG,
                "Response\n" +
                    "[\nheaders : ${response.headers()}]\n" +
                    "[ body : ${response.body()} ]\n" +
                    "[ errorBody : ${response.errorBody()?.string()} ]\n" +
                    "[ raw :  ${response.raw()}]"
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //response 코드에 따라 서버 응답을 JSON String 으로 반환한다.
    fun showResponse(response: Response<Any>): String {
        Log.d(APP_TAG,
            "Response\n" +
                    "[\nheaders : ${response.headers()}]\n" +
                    "[ body : ${response.body()} ]\n" +
                    "[ errorBody : ${response.errorBody().toString()} ]\n" +
                    "[ raw :  ${response.raw()}]"
        )

        when (response.code()) {
            //Success
            200, 201 -> {
                if (response.body() != null) {
                    return gson.toJson(response.body())
                }
                return handleExceptionResponse(response)
            }
            //Bad Request, Not found
            400, 404 -> {
                if (response.errorBody() != null) {
                    return response.errorBody()!!.string()
                }
                return handleExceptionResponse(response)
            }
            //Internal error
            500 -> {
                if (response.errorBody() != null) {
                    return response.errorBody()!!.string()
                }
                return handleExceptionResponse(response)
            }
            else -> {
                return handleExceptionResponse(response)
            }
        }
    }

    //response 코드와 메시지를 JSON String 으로 반환한다.
    private fun handleExceptionResponse(response: Response<Any>): String {
        try {
            val message = if (response.errorBody() != null) {
                response.errorBody()!!.string()
            } else if (response.body() != null) {
                response.body().toString()
            } else {
                EXCEPTION_BODY_EMPTY
            }
            //DataClass -> JSON String
            return gson.toJson(
                ResponseException(
                    code = response.code().toString(),
                    message = message
                )
            )
        } catch (e: Exception) {
            return handleExceptionResponse(e.toString())
        }
    }

    //Overloading method
    fun handleExceptionResponse(message: String): String {
        //DataClass -> JSON String
        return gson.toJson(
            ResponseException(
                code = ERROR_CODE,
                message = message
            )
        )
    }
}