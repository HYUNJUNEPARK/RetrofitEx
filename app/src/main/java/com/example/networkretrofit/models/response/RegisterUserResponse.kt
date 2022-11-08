package com.example.networkretrofit.models.response


import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(
    @SerializedName("result")
    val result: String?
)