package com.example.unnamedai.ui.HistoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unnamedai.MainEvents
import com.example.unnamedai.MainViewModel
import com.example.unnamedai.R
import com.example.unnamedai.ui.ChatScreen.components.ButtonWithPopup
import com.example.unnamedai.util.theme.Black
import com.example.unnamedai.util.theme.Input
import com.example.unnamedai.util.theme.White
import com.example.unnamedai.util.theme.abel

@Composable
fun HistoryScreen(viewmodel: MainViewModel = hiltViewModel()) {

    val state = viewmodel.state.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            Modifier
                .height(72.dp)
                .padding(start = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(100)),
                onClick = {
                    viewmodel.onEvent(MainEvents.ClickBacKFromHistory)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_square_left),
                    contentDescription = null
                )
            }
            Text(
                text = "Menu",
                fontFamily = abel,
                fontSize = 28.sp,
                color = White,
            )

        }

        Box(
            modifier = Modifier
                .background(White, RoundedCornerShape(20))
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                    viewmodel.onEvent(MainEvents.ClickStartNewChat)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Start New Chat",
                fontFamily = abel,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = Black,
            )
        }

        Text(
            modifier = Modifier.padding(24.dp),
            text = "Chat History",
            fontFamily = abel,
            fontSize = 25.sp,
            lineHeight = 22.sp,
            color = White,
        )

        LazyColumn(Modifier.fillMaxSize()) {
            items(state.history) {
                Column(
                    Modifier
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(.2.dp)
                            .background(Input)
                            .alpha(.1f)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            modifier = Modifier
                                .padding(start = 24.dp)
                                .alpha(.5f),
                            text = it.date,
                            fontFamily = abel,
                            fontSize = 16.sp,
                            lineHeight = 21.sp,
                            color = White,
                        )

                        ButtonWithPopup(
                            Pair(
                                "Select"
                            ) { viewmodel.onEvent(MainEvents.SelectConversationFromHistory(it)) },
                            Pair("Delete") {
                                viewmodel.onEvent(MainEvents.DeleteConversationFromHistory(it.id!!))
                            },
                            White
                        )

                    }

                    Text(
                        modifier = Modifier.padding(start = 24.dp),
                        text = it.name + " & " + it.aiName,
                        fontFamily = abel,
                        fontSize = 18.sp,
                        lineHeight = 22.sp,
                        color = White,
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

    }
}