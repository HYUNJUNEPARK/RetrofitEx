package com.example.networkretrofit.models.mona


import com.google.gson.annotations.SerializedName

data class SearchUserResult(
    @SerializedName("affine_x")
    val affineX: String?,
    @SerializedName("affine_y")
    val affineY: String?,
    @SerializedName("user_id")
    val userId: String?
)