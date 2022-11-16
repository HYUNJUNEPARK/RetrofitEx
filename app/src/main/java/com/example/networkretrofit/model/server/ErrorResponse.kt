package com.example.networkretrofit.model.server

import com.google.gson.annotations.SerializedName

//SearchUser, RegisterUser
data class ErrorResponse(
    @SerializedName("error_code")
    val errorCode: String?,
    @SerializedName("message")
    val message: String?
)