package com.example.doubletapphomework3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.doubletapphomework3.navigation.Navigation
import com.example.doubletapphomework3.ui.theme.DoubletappHomework3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DoubletappHomework3Theme {
                Navigation().Create()
            }
        }
    }
}