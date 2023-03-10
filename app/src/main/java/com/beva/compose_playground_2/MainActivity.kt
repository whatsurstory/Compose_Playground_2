package com.beva.compose_playground_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.beva.compose_playground_2.ui.ScrollList
import com.beva.compose_playground_2.ui.theme.Compose_Playground_2Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_Playground_2Theme {
                    ScrollList()
            }
        }
    }
}
