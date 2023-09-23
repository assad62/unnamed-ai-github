package com.example.unnamedai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unnamedai.ui.ChatScreen.ChatScreen
import com.example.unnamedai.ui.ChatScreen.components.EditTextPopUp
import com.example.unnamedai.ui.HistoryScreen.HistoryScreen
import com.example.unnamedai.ui.StartScreen
import com.example.unnamedai.util.theme.UnnamedAiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnnamedAiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(viewmodel: MainViewModel = hiltViewModel()) {

    val state = viewmodel.state.value

    Box(modifier = Modifier.fillMaxSize()) {

        AnimatedVisibility(
            exit = fadeOut(),
            visible = !state.showChatScreen
        ) {
            StartScreen()
        }

        AnimatedVisibility(
            enter = fadeIn(),
            exit = fadeOut(),
            visible = state.showChatScreen
        ) {
            ChatScreen()
        }

        AnimatedVisibility(
            visible = state.showHistoryScreen,
            enter = slideInHorizontally(
                initialOffsetX = { 300 },
                animationSpec = tween(durationMillis = 300)
            ) + fadeIn(),
            exit = slideOutHorizontally(
                targetOffsetX = { 300 },
                animationSpec = tween(durationMillis = 300)
            ) + fadeOut()
        ) {
            HistoryScreen()
        }

        AnimatedVisibility(
            visible = state.popUpItem != null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            state.popUpItem?.compose?.invoke()
        }

        EditTextPopUp()

    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    UnnamedAiTheme {
        App()
    }
}