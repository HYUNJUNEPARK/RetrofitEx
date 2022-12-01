package com.example.networkretrofit.network

import com.example.networkretrofit.network.util.ResponseUtil

class RetrofitResponseEx {
    private val responseUtil = ResponseUtil()


//    //TODO Add Response Description
//    sealed class Result<out T: Any> {
//        data class Success<out T : Any>(val body: T) : Result<T>()
//        data class Error<out T : Any>(val body: T): Result<T>()
//        data class Exception(val exception: String): Result<Nothing>()
//    }

//    private fun handleExceptionResponse(response: Response<Object>): String {
//        if (response.code() != null) {
//            //data class -> json string
//            //TODO 더 좋은 코드가 있을것 같음
//            val message = if (response.errorBody() != null) {
//                response.errorBody()!!.string()
//            } else if (response.body() != null) {
//                response.body().toString()
//            } else {
//                UNKNOWN_ERROR_MESSAGE
//            }
//
//            return Gson().toJson(
//                ServerResponse(
//                    ExecStatus(
//                        code = response.code().toString(),
//                        message = message
//                    )
//                )
//            )
//        }
//        return handleExceptionResponse(UNKNOWN_ERROR_MESSAGE)
//    }

//    private fun handleExceptionResponse(message: String): String{
//        return Gson().toJson(
//            ServerResponse(
//                ExecStatus(
//                    code = CODE_ERROR,
//                    message = message
//                )
//            )
//        )
//    }
}