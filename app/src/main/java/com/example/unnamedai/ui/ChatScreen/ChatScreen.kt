package com.example.unnamedai.ui.ChatScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unnamedai.MainEvents
import com.example.unnamedai.MainViewModel
import com.example.unnamedai.R
import com.example.unnamedai.domain.model.From
import com.example.unnamedai.ui.ChatScreen.components.LoadingBall
import com.example.unnamedai.ui.ChatScreen.components.ThemItem
import com.example.unnamedai.ui.ChatScreen.components.YouItem
import com.example.unnamedai.util.theme.Input
import com.example.unnamedai.util.theme.White
import com.example.unnamedai.util.theme.abel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChatScreen(modifier: Modifier = Modifier, viewmodel: MainViewModel = hiltViewModel()) {

    val state = viewmodel.state.value

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dark_ic_unnamed),
                    contentDescription = null
                )
                IconButton(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100)),
                    onClick = {
                        viewmodel.onEvent(MainEvents.ClickGoToHistory)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = null
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .background(Input, RoundedCornerShape(topEndPercent = 20, topStartPercent = 20))
                    .clip(
                        RoundedCornerShape(topEndPercent = 20, topStartPercent = 20)
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                TextField(
                    enabled = !state.popupControl,
                    value = state.chatTF,
                    onValueChange = { viewmodel.onEvent(MainEvents.ChatTfChanged(it)) },
                    modifier = Modifier
                        .border(BorderStroke(1.dp, Color.Transparent))
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (!state.loadingChatRespond) {
                            viewmodel.onEvent(MainEvents.PressDoneOnKeyboard)
                        }
                    }),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = White
                    ),
                    textStyle = TextStyle(
                        fontFamily = abel,
                        lineHeight = 22.sp,
                        fontSize = 18.sp,
                        color = White,
                    ),
                    placeholder = {
                        Text(
                            modifier = Modifier.alpha(.5f),
                            text = "Write anything...",
                            fontFamily = abel,
                            lineHeight = 22.sp,
                            fontSize = 18.sp,
                            color = White,
                        )
                    }
                )
            }
        },
        backgroundColor = Color.Black
    ) {
        LazyColumn(
            reverseLayout = true,
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                Spacer(modifier = Modifier.height(72.dp))
            }

            item {
                if (state.loadingChatRespond) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingBall()
                    }
                }
            }


            items(state.currentConversation!!.talk.reversed()) { item ->
                if (item.from == From.You) {
                    YouItem(item, state.youTF)
                } else {
                    Spacer(modifier = Modifier.height(40.dp))
                    ThemItem(item, state.themTF)
                }
            }

            item {
                AnimatedVisibility(
                    visible = state.currentConversation.talk.size == 0,
                    enter = fadeIn(),
                    exit = fadeOut() + shrinkOut()
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        text = "Lets get this conversation between ${state.youTF} and ${state.themTF} started!",
                        textAlign = TextAlign.Center,
                        fontFamily = abel,
                        lineHeight = 28.sp,
                        fontSize = 22.sp,
                        color = White,
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(72.dp))
            }
        }
    }
}





