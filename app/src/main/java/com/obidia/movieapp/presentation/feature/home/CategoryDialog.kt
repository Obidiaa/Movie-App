package com.obidia.movieapp.presentation.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.obidia.movieapp.R
import com.obidia.movieapp.data.utils.Resource
import com.obidia.movieapp.domain.model.CategoryModel
import com.obidia.movieapp.ui.theme.robotoFamily


@Composable
fun CategoryDialog(
    listCategory: State<Resource<List<CategoryModel>>?>,
    isShowDialog: Boolean,
    onClickCategory: (category: String, id: Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    var isShow by remember {
        mutableStateOf(false)
    }

    isShow = isShowDialog

    if (isShow) Dialog(
        onDismissRequest = {
            isShow = false
            onDismissRequest.invoke()
        }, properties = DialogProperties(
            dismissOnClickOutside = true, usePlatformDefaultWidth = false
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (val list = listCategory.value) {
                is Resource.Error -> {
                }

                is Resource.Loading -> {}
                is Resource.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)),
                        verticalArrangement = Arrangement.spacedBy(36.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(36.dp))
                        }

                        items(items = list.data) {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp,
                                    fontFamily = robotoFamily,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Medium,
                                ),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        onClickCategory.invoke(it.name, it.id)
                                    },
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(160.dp))
                        }
                    }

                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = {
                            onDismissRequest.invoke()
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 100.dp),
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                            contentDescription = ""
                        )
                    }
                }

                null -> {}
            }
        }
    }
}