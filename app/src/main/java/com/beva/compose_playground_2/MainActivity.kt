package com.beva.compose_playground_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beva.compose_playground_2.ui.ScrollList
import com.beva.compose_playground_2.ui.theme.Compose_Playground_2Theme

class MainActivity : ComponentActivity() {

     lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainViewModel()
        setContent {
            Compose_Playground_2Theme {

                    ScrollList(viewModel, this)

            }
        }
    }
}
