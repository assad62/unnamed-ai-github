package com.example.unnamedai.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unnamedai.MainEvents
import com.example.unnamedai.MainViewModel
import com.example.unnamedai.ui.StartScreen.Components.ChatSetter
import com.example.unnamedai.ui.StartScreen.Components.SplachScreen
import com.example.unnamedai.ui.StartScreen.Components.WelcomePopUp


@Composable
fun StartScreen(modifier: Modifier = Modifier, viewmodel: MainViewModel = hiltViewModel()) {

    val state = viewmodel.state.value

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier.fillMaxSize()) {

            SplachScreen(
                Modifier
                    .weight(.7f)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(onDragEnd = {
                            viewmodel.onEvent(MainEvents.SwipeSplashScreen)
                        }) { change, dragAmount -> }
                    })

            AnimatedVisibility(
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        topStartPercent = 10,
                        topEndPercent = 10
                    )
                ),
                visible = state.wlcVisibility && !state.setterVisibility,
            ) {

                WelcomePopUp(
                    Modifier
                        .weight(.3f)
                )

            }
        }

        val density = LocalDensity.current


        AnimatedVisibility(
            enter = slideInVertically {
                with(density) { 300.dp.roundToPx() }
            },
            exit = slideOutVertically() + shrinkVertically() + fadeOut(),
            visible = state.setterVisibility
        ) {
            ChatSetter()
        }
    }
}