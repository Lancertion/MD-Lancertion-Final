package com.app.lancertion.presentation.screen.community

import com.app.lancertion.data.remote.dto.PostDto

data class CommunityState(
    var posts: List<PostDto> = emptyList(),
    var isSuccess: Boolean = false,
    var isLoading: Boolean = false,
    var isError: Boolean = false
)