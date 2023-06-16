package com.app.lancertion.presentation.screen.community_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.lancertion.presentation.screen.community.CommunityCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityDetailScreen(
    viewModel: CommunityDetailViewModel,
    navigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val userId by viewModel.userId.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier.clickable {
                    navigateBack()
                }
                .padding(horizontal = 16.dp)
        )

        if(!isLoading) {
            state.post?.let {
                CommunityCard(
                    author = it.pengirim,
                    userId = it.user_id,
                    date = it.tanggal,
                    topic = it.content,
                    modifier = Modifier.padding(top = 16.dp),
                    showDeleteButton = false,
                    delete = { }
                ) {

                }
            }

            TextField(
                value = uiState.reply,
                onValueChange = { viewModel.setReply(it) },
                label = { Text(text = "Balas")},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Reply,
                        contentDescription = "balas"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { viewModel.sendReply() }) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "send"
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .padding(horizontal = 16.dp)
            )

            state.comments?.let { listComment ->
                if(listComment.isEmpty()) {
                    Text("Belum ada balasan", modifier = Modifier.padding(horizontal = 16.dp))
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(top = 8.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                    ) {
                        items(listComment) { comment ->
                            CommunityCard(
                                author = comment.pengirim,
                                userId = comment.user_id,
                                date = comment.tanggal,
                                topic = comment.comment,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(),
                                showDeleteButton = comment.user_id == userId,
                                delete = {
                                    viewModel.dialogOpen()
                                    viewModel.setDeleteCommentId(comment.id)
                                }
                            ) { }
                        }
                    }
                }

            }
        }
    }

    if(showDialog) {
        Alert(closeDialog = {
            viewModel.dialogClose()
        }) {
            viewModel.deleteComment()
        }
    }
}

@Composable
fun Alert(
    closeDialog: () -> Unit,
    deleteData: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            closeDialog()
        },
        icon = { Icon(Icons.Filled.DeleteSweep, contentDescription = null) },
        confirmButton = {
            TextButton(onClick = {
                deleteData()
                closeDialog()
            }) {
                Text(text = "Iya")
            }
        },
        text = {
            Text(text = "Apakah anda yakin akan menghapus komentar ini?")
        }
    )

}