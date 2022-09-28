package com.example.networkretrofit.models.mona


import com.google.gson.annotations.SerializedName

//SearchUser, RegisterUser
data class ErrorMessage(
    @SerializedName("error_code")
    val errorCode: String?,
    @SerializedName("message")
    val message: String?
)