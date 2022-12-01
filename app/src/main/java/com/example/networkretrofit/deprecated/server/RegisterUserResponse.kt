package com.example.networkretrofit.deprecated.server


import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("result")
    val result: String?
)