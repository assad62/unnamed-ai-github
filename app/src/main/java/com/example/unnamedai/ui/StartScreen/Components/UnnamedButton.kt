package com.example.unnamedai.ui.StartScreen.Components

import android.opengl.Visibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unnamedai.util.theme.Black
import com.example.unnamedai.util.theme.White
import com.example.unnamedai.util.theme.abel

@Composable
fun UnnamedButton(modifier: Modifier = Modifier, text: String, isVisible:Boolean = true, click : () -> Unit) {

    if (isVisible){

        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 20.dp, horizontal = 40.dp)
                .clickable { click() }
                .background(color = Black, RoundedCornerShape(20))
                .clip(RoundedCornerShape(20)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = text,
                lineHeight = 24.sp,
                fontFamily = abel,
                fontSize = 20.sp,
                color = White,
            )
        }


    }


}