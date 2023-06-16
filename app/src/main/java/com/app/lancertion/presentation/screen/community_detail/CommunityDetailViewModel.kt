package com.app.lancertion.presentation.screen.community_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.dto.toPost
import com.app.lancertion.data.remote.request.CommentBody
import com.app.lancertion.domain.use_case.delete_comment.DeleteCommentUseCase
import com.app.lancertion.domain.use_case.get_auth.GetAuthUseCase
import com.app.lancertion.domain.use_case.get_comments.GetCommentsUseCase
import com.app.lancertion.domain.use_case.get_posts.GetPostUseCase
import com.app.lancertion.domain.use_case.send_comment.SendCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityDetailViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getAuthUseCase: GetAuthUseCase,
    private val sendCommentUseCase: SendCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _state = MutableStateFlow(CommunityDetailState())
    val state: StateFlow<CommunityDetailState> = _state.asStateFlow()

    private val _uiState = MutableStateFlow(CommunityDetailUiState(""))
    val uiState: StateFlow<CommunityDetailUiState> = _uiState.asStateFlow()

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token.asStateFlow()

    private val _id = MutableStateFlow(0)
    val postId: StateFlow<Int> = _id.asStateFlow()

    private val _userId = MutableStateFlow(0)
    val userId: StateFlow<Int> = _userId

    private val _deleteCommentId = MutableStateFlow(0)
    val deleteCommentId: StateFlow<Int> = _deleteCommentId.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    init {
        getToken()
        getUserId()
    }

    fun getUserId() {
        viewModelScope.launch {
            val id = getAuthUseCase.getId() ?: 0
            _userId.value = id
        }
    }

    fun getPostId(id: Int) {
        _id.value = id
        getPostUseCase(token.value).onEach { result ->
            when(result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    _state.value.post = result.data?.find {
                        it.post_id == postId.value
                    }!!.toPost()
                    getComments(postId.value)
                }
                is Resource.Error -> {
                    _state.value.isLoading = false
                    _state.value.isError = true
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getComments(id: Int) {
        getCommentsUseCase(token.value, id).onEach { result ->
            when(result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    _state.value.comments = result.data
                    _isLoading.value = false
                }
                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }


    fun getToken() {
        viewModelScope.launch {
            val token = getAuthUseCase.getToken() ?: ""
            _token.value = token
        }
    }

    fun setReply(reply: String) {
        _uiState.value = uiState.value.copy(reply = reply)
    }

    fun sendReply() {
        _isLoading.value = true

        val body = CommentBody(
            comment = uiState.value.reply
        )

        setReply("")

        sendCommentUseCase(token.value, body, postId.value).onEach { result ->
            when(result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    getComments(postId.value)
                }
                is Resource.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun setDeleteCommentId(commentId: Int) {
        _deleteCommentId.value = commentId
    }

    fun dialogOpen() {
        _showDialog.value = true
    }

    fun dialogClose() {
        _showDialog.value = false
    }

    fun deleteComment() {
        _isLoading.value = true
        deleteCommentUseCase(token.value, deleteCommentId.value).onEach { result ->
            when(result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    getComments(postId.value)
                }
                is Resource.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

}