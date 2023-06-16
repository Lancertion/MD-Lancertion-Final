package com.app.lancertion.presentation.screen.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.lancertion.R
import com.app.lancertion.common.util.Avatar
import com.app.lancertion.common.util.Date
import com.app.lancertion.data.remote.dto.PostDto
import com.app.lancertion.presentation.ui.theme.Warning

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    viewModel: CommunityViewModel,
    navigateToDetail: (PostDto) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val userId by viewModel.userId.collectAsState()
    val showDialog by viewModel.showDialog.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Community", fontSize = 24.sp)
        }

        TextField(
            value = uiState.post,
            onValueChange = {
                viewModel.setPost(it)
            },
            label = { Text(text = "Posting")},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.PostAdd,
                    contentDescription = "posting"
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.sendPost()
                }) {
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

        LazyColumn(
            contentPadding = PaddingValues(top = 8.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
        ) {
            if(!isLoading)  {
                items(state.posts.sortedBy {
                    it.post_id
                }.reversed()) {
                    CommunityCard(
                        modifier = Modifier,
                        userId = it.user_id,
                        author = it.pengirim,
                        date = it.tanggal,
                        topic = it.content,
                        showDeleteButton = userId == it.user_id,
                        delete = {
                            viewModel.dialogOpen()
                            viewModel.setDeletePostId(it.post_id)
                        }
                    ) {
                        navigateToDetail(it)
                    }
                }
            }
        }
    }

    if(showDialog) {
        Alert(closeDialog = {
            viewModel.dialogClose()
        }) {
            viewModel.deletePost()
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
            Text(text = "Apakah anda yakin akan menghapus post ini?")
        }
    )

}


@Composable
fun CommunityGuestScreen() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Community", fontSize = 24.sp)
        }
        Text(
            text = "Untuk bergabung dengan komunitas, kamu harus login terlebih dahulu",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.doctor_2),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityCard(
    modifier: Modifier,
    userId: Int,
    author: String,
    date: String,
    topic: String,
    showDeleteButton: Boolean,
    delete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEEEEEE)
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = Avatar().getFromId(userId)),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(48.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = author,
                    )
                    Text(
                        text = Date().getFromPost(date),
                    )
                }
                if(showDeleteButton) {
                    IconButton(onClick = { delete() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Warning
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Text(
                text = topic,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}