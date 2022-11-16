package com.example.networkretrofit.model.server


import com.google.gson.annotations.SerializedName

data class RegisterUser(
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("user_id")
    val userId: String?
)