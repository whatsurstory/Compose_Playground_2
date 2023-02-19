package com.beva.compose_playground_2.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollList() {

    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp),
        state = lazyListState
    ) {
        items(3) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "promo")
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "promo")
                }
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(text = "promo")
                }
            }
        }

        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 8.dp)
                    .padding(horizontal = 8.dp)
                    .background(Color.LightGray)
                    .border(1.dp, Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "filter"
                )
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "filter icon",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(16.dp)
                        .clickable {

                        }
                )
            }
        }

    }
}

@Composable
fun GridsScroll() {

    var items by remember {
        mutableStateOf(
            (1..100).map {
                ListItem("Good $it", false)
            }
        )
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 8.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(items.size) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(240.dp)
                        .padding(horizontal = 8.dp)
                        .padding(top = 16.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = items[it].title)
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        tint = if (items[it].isSelected) Color.Black else Color.LightGray,
                        contentDescription = "Like Button",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .clickable {
                                items = items.mapIndexed { index, listItem ->
                                    if (it == index) {
                                        listItem.copy(
                                            isSelected = !listItem.isSelected
                                        )
                                    } else {
                                        listItem
                                    }
                                }
                            }
                    )
                }
            }
        }
    )
}

data class ListItem(
    val title: String,
    val isSelected: Boolean
)