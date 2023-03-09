package com.beva.compose_playground_2.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

const val TAG = "Beva"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollList() {

    val lazyListState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    var display by remember {
        mutableStateOf(true)
    }

    var gridItems by remember {
        mutableStateOf(
            (1..100).map {
                ListItem(it, false)
            }
        )
    }

    var sum by remember {
        mutableStateOf(0)
    }

    val showButton by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 2 //position number
        }
    }


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
                            display = !display
                            if (showButton) {
                                scope.launch {
                                    lazyListState.animateScrollToItem(0) //position number
                                }
                            }
                        }
                )
            }
        }

        if (!display) {
            items(gridItems.size) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .padding(horizontal = 8.dp)
                        .padding(top = 16.dp)
                        .border(1.dp, Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Good ${gridItems[it].title}")
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        tint = if (gridItems[it].isSelected) Color.Black else Color.LightGray,
                        contentDescription = "Like Button",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .clickable {

                                gridItems = gridItems.mapIndexed { index, listItem ->
                                    if (it == index) {
                                        listItem.copy(isSelected = !listItem.isSelected)
                                    } else {
                                        listItem
                                    }
                                }
                                Log.d(TAG, "ScrollList: one line $gridItems")
//                                gridItems[it].isSelected = !gridItems[it].isSelected
                            }
                    )
                    sum = gridItems.size.minus(gridItems[it].title)
                }
            }
        } else {
            items(gridItems.windowed(2, 2, true)) {
                Row(Modifier.fillMaxWidth()) {
                    it.forEach { item ->
                        Box(
                            modifier = Modifier
                                .fillParentMaxWidth(0.5f)
                                .height(240.dp)
                                .padding(horizontal = 8.dp)
                                .padding(top = 16.dp)
                                .border(1.dp, Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Good ${item.title}")
                            Icon(
                                imageVector = Icons.Default.ThumbUp,
                                contentDescription = "$item Like Button",
                                tint = if (item.isSelected) Color.DarkGray else Color.LightGray,
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                                    .clickable {
                                        gridItems = gridItems.mapIndexed { index, listItem ->
                                            if (gridItems.indexOf(item) == index) {
                                                listItem.copy(isSelected = !listItem.isSelected)
                                            } else {
                                                listItem
                                            }
                                        }
                                    }
                            )
                            sum = gridItems.size.minus(item.title)
                        }
                    }
                }
            }
        }
    }
    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        ScrollToTopButton(sum, onClick = {
            scope.launch {
                lazyListState.animateScrollToItem(0) //position number
            }
        }
        )
    }
}

@Composable
fun ScrollToTopButton(sum: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 36.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .shadow(10.dp)
                .clip(RectangleShape)
                .size(160.dp, 90.dp)
                .border(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Gray,
                contentColor = Color.Black
            )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "剩餘 $sum 項商品")
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Back To Top"
                )
            }
        }
    }

}

data class ListItem(
    val title: Int,
    var isSelected: Boolean
)
