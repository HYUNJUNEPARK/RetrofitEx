package com.example.networkretrofit.models.response


import com.google.gson.annotations.SerializedName

data class RegisterUser(
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("user_id")
    val userId: String?
)