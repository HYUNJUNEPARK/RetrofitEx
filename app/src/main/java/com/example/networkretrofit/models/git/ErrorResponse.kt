package com.example.networkretrofit.models.git


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("documentation_url")
    val documentationUrl: String?,
    @SerializedName("message")
    val message: String?
)