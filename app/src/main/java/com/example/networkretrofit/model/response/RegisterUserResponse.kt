package com.example.networkretrofit.model.response


import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("result")
    val result: String?
)