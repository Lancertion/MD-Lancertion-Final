package com.app.lancertion.presentation.screen.community_detail

import com.app.lancertion.data.remote.dto.CommentDto
import com.app.lancertion.domain.model.Post

data class CommunityDetailState(
    var post: Post? = null,
    var comments: List<CommentDto>? = null,
    var isSuccess: Boolean = false,
    var isLoading: Boolean = false,
    var isError: Boolean = false
)