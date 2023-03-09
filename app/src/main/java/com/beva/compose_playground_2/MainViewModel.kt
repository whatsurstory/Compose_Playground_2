package com.beva.compose_playground_2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beva.compose_playground_2.ui.ListItem

class MainViewModel: ViewModel() {

    val _items = MutableLiveData<List<ListItem>>()
    val items: LiveData<List<ListItem>>
    get() = _items

    init {
        _items.value = (1..100).map {
            ListItem(it, false)
        }
        Log.d("init", "${_items.value}")
    }

    fun getItemSize(): Int {
        return _items.value?.size ?: 0
    }

    fun getResultNumber(number: Int): Int {
        Log.d("sum", "${_items.value?.size}")
        return _items.value?.size?.minus(number) ?: 0
    }

    fun getBoolean(items: List<ListItem>, position: Int) {
       _items.value = items.mapIndexed { index, listItem ->
            if (position == index) {
                listItem.copy(isSelected = !listItem.isSelected)
            } else {
                listItem
            }
        }
    }

}


