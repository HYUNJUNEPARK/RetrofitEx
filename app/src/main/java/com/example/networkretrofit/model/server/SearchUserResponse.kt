package com.example.networkretrofit.model.server


import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("affine_x")
    val affineX: String?,
    @SerializedName("affine_y")
    val affineY: String?,
    @SerializedName("user_id")
    val userId: String?
)