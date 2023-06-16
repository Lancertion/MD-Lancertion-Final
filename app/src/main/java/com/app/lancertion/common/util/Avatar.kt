package com.app.lancertion.common.util

import com.app.lancertion.R

class Avatar {

    val listAvatar = listOf(
        R.drawable.avatar_1,
        R.drawable.avatar_2,
        R.drawable.avatar_3,
        R.drawable.avatar_4,
        R.drawable.avatar_5,
    )

    fun getFromId(id: Int): Int {
        val mod = id % 5
        return listAvatar[mod]
    }

}