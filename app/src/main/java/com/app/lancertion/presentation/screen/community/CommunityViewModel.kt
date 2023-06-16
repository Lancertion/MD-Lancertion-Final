package com.app.lancertion.presentation.screen.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lancertion.common.Resource
import com.app.lancertion.data.remote.request.PostBody
import com.app.lancertion.domain.use_case.delete_post.DeletePostUseCase
import com.app.lancertion.domain.use_case.get_auth.GetAuthUseCase
import com.app.lancertion.domain.use_case.get_posts.GetPostUseCase
import com.app.lancertion.domain.use_case.send_post.SendPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val getAuthUseCase: GetAuthUseCase,
    private val sendPostUseCase: SendPostUseCase,
    private val deletePostUseCase: DeletePostUseCase
): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _state = MutableStateFlow(CommunityState())
    val state: StateFlow<CommunityState> = _state.asStateFlow()

    private val _uiState = MutableStateFlow(CommunityUiState(""))
    val uiState: StateFlow<CommunityUiState> = _uiState.asStateFlow()

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> = _token.asStateFlow()

    private val _userId = MutableStateFlow(0)
    val userId: StateFlow<Int> = _userId.asStateFlow()

    private val _deletePostId = MutableStateFlow(0)
    val deletePostId: StateFlow<Int> = _deletePostId.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    init {
        getToken()
        getId()
    }

    fun getPosts() {
        getPostUseCase(token.value).onEach {result ->
            when(result) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    _state.value.posts = result.data!!
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

    fun getId() {
        viewModelScope.launch {
            val id = getAuthUseCase.getId() ?: 0
            _userId.value = id
        }
    }

    fun setPost(post: String) {
        _uiState.value = uiState.value.copy(post)
    }

    fun sendPost() {
        _isLoading.value = true

        val body = PostBody(
            content = uiState.value.post
        )

        setPost("")

        sendPostUseCase(token.value, body).onEach { result ->
            when(result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    getPosts()
                }
                is Resource.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun deletePost() {
        _isLoading.value = true

        deletePostUseCase(token.value, deletePostId.value).onEach { result ->
            when(result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    getPosts()
                }
                is Resource.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun setDeletePostId(postId: Int) {
        _deletePostId.value = postId
    }

    fun dialogOpen() {
        _showDialog.value = true
    }

    fun dialogClose() {
        _showDialog.value = false
    }

}