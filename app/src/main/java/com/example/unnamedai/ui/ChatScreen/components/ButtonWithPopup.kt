package com.example.unnamedai.ui.ChatScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unnamedai.R
import com.example.unnamedai.util.theme.Black
import com.example.unnamedai.util.theme.White
import com.example.unnamedai.util.theme.abel

@Composable
fun ButtonWithPopup(item1: Pair<String, () -> Unit>, item2: Pair<String, () -> Unit>, tint: Color) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                modifier = Modifier
                    .padding(end = 40.dp)
                    .alpha(.5f),
                tint = tint,
                painter = painterResource(id = R.drawable.more),
                contentDescription = null
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Black)
                .width(IntrinsicSize.Min),
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(modifier = Modifier
                    .clickable {
                        item1.second.invoke()
                        expanded = false
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 8.dp),
                        text = item1.first,
                        fontFamily = abel,
                        fontSize = 20.sp,
                        lineHeight = 21.sp,
                        color = White,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier
                    .clickable {
                        item2.second.invoke()
                        expanded = false
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                        text = item2.first,
                        fontFamily = abel,
                        fontSize = 20.sp,
                        lineHeight = 21.sp,
                        color = White,
                    )
                }
            }
        }
    }
}
