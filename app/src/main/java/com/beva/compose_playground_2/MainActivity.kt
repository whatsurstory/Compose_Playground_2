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
import com.beva.compose_playground_2.ui.GridsScroll
import com.beva.compose_playground_2.ui.theme.Compose_Playground_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_Playground_2Theme {

                val scrollState = rememberScrollState()

                Column(modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth()
                    .height(1050.dp)) {
                    ScrollList()
                    GridsScroll()
                }
            }
        }
    }
}
