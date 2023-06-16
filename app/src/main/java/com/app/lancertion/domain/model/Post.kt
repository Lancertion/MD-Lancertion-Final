package com.app.lancertion.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val content: String,
    val pengirim: String,
    val post_id: Int,
    val tanggal: String,
    val user_id: Int
): Parcelable
