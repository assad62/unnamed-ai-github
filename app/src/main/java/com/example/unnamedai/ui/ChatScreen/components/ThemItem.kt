package com.example.unnamedai.ui.ChatScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unnamedai.MainEvents
import com.example.unnamedai.MainViewModel
import com.example.unnamedai.R
import com.example.unnamedai.domain.model.Msg
import com.example.unnamedai.util.theme.Black
import com.example.unnamedai.util.theme.Blue
import com.example.unnamedai.util.theme.White
import com.example.unnamedai.util.theme.abel

@Composable
fun ThemItem(item: Msg, name: String, viewmodel: MainViewModel = hiltViewModel()) {

    var animation by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        animation = true
    }

    AnimatedVisibility(
        visible = animation, enter = fadeIn()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
                .background(White, RoundedCornerShape(10.dp))

        ) {
            Box(
                Modifier
                    .offset(y = -20.dp)
                    .padding(horizontal = 20.dp)
                    .size(40.dp)
                    .background(Blue, RoundedCornerShape(100))
                    .clip(RoundedCornerShape(100)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.white_ball),
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 15.dp),
                text = item.content,
                textAlign = TextAlign.Start,
                fontFamily = abel,
                lineHeight = 28.sp,
                fontSize = 22.sp,
                color = Black,
            )

            Row(
                Modifier
                    .height(34.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                ButtonWithPopup(
                    Pair("Edit") { viewmodel.onEvent(MainEvents.ShowPopUp(item)) },
                    Pair("Delete") { viewmodel.onEvent(MainEvents.DeleteMsgConversation(item)) },
                    Black
                )
            }

            Text(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .alpha(.5f),
                text = "Them (${name})",
                fontFamily = abel,
                fontSize = 16.sp,
                lineHeight = 21.sp,
                color = Black,
            )

        }
    }
}
