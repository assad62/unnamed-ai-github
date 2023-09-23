package com.example.unnamedai.ui.StartScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unnamedai.MainEvents
import com.example.unnamedai.MainViewModel
import com.example.unnamedai.util.theme.Black
import com.example.unnamedai.util.theme.abel


@Composable
fun ChatSetter(modifier: Modifier = Modifier, viewmodel: MainViewModel = hiltViewModel()) {

    val state = viewmodel.state.value
    val context = LocalContext.current

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(), reverseScrolling = true)
            .background(
                Color.White,
            )
    ) {

        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 40.dp, bottom = 20.dp)
                .fillMaxWidth(),
            text = "Lets setup a chat!",
            letterSpacing = 0.36.sp,
            fontFamily = abel,
            fontSize = 28.sp,
            color = Black,
        )

        Text(
            modifier = Modifier
                .padding(start = 20.dp, bottom = 20.dp)
                .fillMaxWidth(),
            text = "We need a few details to get started.\n(you can change this at any time)",
            textAlign = TextAlign.Start,
            fontFamily = abel,
            lineHeight = 20.sp,
            fontSize = 17.sp,
            color = Black,
        )

        UnnamedTextField(placeholder = "Enter your name/alias", value = state.youTF){
            viewmodel.onEvent(MainEvents.YouTfChanged(it))
        }
        UnnamedTextField(placeholder = "Enter some information about yourself.", value = state.youWhoTF){
            viewmodel.onEvent(MainEvents.YouWhoTfChanged(it))
        }

        Spacer(modifier = Modifier.height(24.dp))

        UnnamedTextField(placeholder = "Enter the name of  “Your Ai”.", value = state.themTF){
            viewmodel.onEvent(MainEvents.ThemTfChanged(it))
        }
        UnnamedTextField(placeholder = "Enter some information about “Your Ai”", value = state.themWhoTF){
            viewmodel.onEvent(MainEvents.ThemWhoTfChanged(it))
        }

        UnnamedButton(text = "Start Chat") {
            viewmodel.onEvent(MainEvents.ClickChatSetter(context))
        }


    }
}

