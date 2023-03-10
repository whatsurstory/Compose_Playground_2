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

    var number by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.firstVisibleItemIndex}
            .collect {
                number = it
                Log.d(TAG, "ScrollList: number $number")
            }
    }

    val scope = rememberCoroutineScope()

    var display by remember {
        mutableStateOf(true)
    }

    var gridItems by remember {
        mutableStateOf(
            (1..100).map {
                ListItem(it , false)
            }
        )
    }

    val result by remember {
        derivedStateOf {
            mutableStateOf(gridItems.size - number)
        }
    }

    val result2 by remember {
        derivedStateOf {
            mutableStateOf(gridItems.size - (number * 2))
        }
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
        items(3, key = { it }, contentType = { it }
        ) {
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
            items(gridItems.size
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
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
                            }
                    )
                }
            }
        } else {
            items((gridItems.windowed(2, 2, true))
            ) {
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
                        }
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(80.dp).fillMaxWidth()) }
    }
    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        ScrollToTopButton(if (display) result2.value else result.value, onClick = {
            scope.launch {
                lazyListState.animateScrollToItem(0) //position number
            }
        }
        )
    }
}

@Composable
fun ScrollToTopButton(sum : Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 36.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .shadow(10.dp)
                .clip(RectangleShape)
                .size(160.dp, 90.dp)
                .border(1.dp, Color.Black)
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "剩餘 ${sum + 2} 項商品")
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Back To Top",
                    modifier = Modifier.clickable {
                        onClick()
                    }
                )
            }
        }
    }

}

data class ListItem(
    val title: Int,
    var isSelected: Boolean
)
