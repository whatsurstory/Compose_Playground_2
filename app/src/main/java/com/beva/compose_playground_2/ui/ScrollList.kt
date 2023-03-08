package com.beva.compose_playground_2.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.LifecycleOwner
import com.beva.compose_playground_2.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollList(vm : MainViewModel, lc: LifecycleOwner) {

    val lazyListState = rememberLazyListState()
    var display by remember {
        mutableStateOf(true)
    }

    val gridItems by remember {
        mutableStateOf(
            (1..100).map {
                ListItem("Good $it", false)
            }
        )
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
                        }
                )
            }
        }

        if (!display) {
            VerticalList(vm, lc)
        } else {
            items(gridItems.windowed(2,2,true)) {
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
                        Text(text = item.title)
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "$item Like Button",
                            tint = if (item.isSelected) Color.DarkGray else Color.LightGray,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                                .clickable {
                                    item.isSelected = !item.isSelected
                                    Log.d("like ", "${item.isSelected}")
                                }
                        )
                    }
                    }
                }
            }
        }
    }
}


fun LazyListScope.VerticalList(vm: MainViewModel, lc: LifecycleOwner) {

    vm.items.observe(lc){ items ->

        items(vm.getItemSize()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
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
                            items[it].isSelected = !items[it].isSelected
                            Log.d("selected", "${items[it].isSelected}")
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
        columns = GridCells.Fixed(2)
    ) {
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
}

data class ListItem(
    val title: String,
    var isSelected: Boolean
)