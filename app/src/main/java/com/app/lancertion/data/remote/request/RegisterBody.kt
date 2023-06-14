package com.app.lancertion.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @SerializedName("full_name") val fullName: String,
    val email: String,
    val password: String,
    val number: String = "082121762"
)
