package com.example.unnamedai.ui.StartScreen.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unnamedai.MainViewModel
import com.example.unnamedai.R
import com.example.unnamedai.util.theme.White
import com.example.unnamedai.util.theme.abel

@Composable
fun SplachScreen(modifier: Modifier = Modifier,viewmodel: MainViewModel = hiltViewModel()) {

    val state = viewmodel.state.value

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black), contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = R.drawable.ic_unamed_ui), contentDescription = null)

        AnimatedVisibility(
            exit = fadeOut(),
            visible = !state.wlcVisibility, modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .padding(bottom = 60.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontFamily = abel,
                fontSize = 25.sp,
                color = White,
            )
        }
    }
}