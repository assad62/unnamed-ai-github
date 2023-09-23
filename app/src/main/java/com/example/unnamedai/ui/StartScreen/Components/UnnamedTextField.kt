package com.example.unnamedai.ui.StartScreen.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unnamedai.util.theme.Border
import com.example.unnamedai.util.theme.Grey
import com.example.unnamedai.util.theme.Input
import com.example.unnamedai.util.theme.abel

@Composable
fun UnnamedTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onChanged: (it: String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 20.dp),
        value = value,
        onValueChange = { onChanged(it) },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = abel,
                lineHeight = 22.sp,
                fontSize = 18.sp,
                color = Grey,
            )
        },
        shape = RoundedCornerShape(20),
        textStyle = TextStyle(
            color = Input, fontFamily = abel,
            lineHeight = 22.sp,
            fontSize = 18.sp
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Grey,
            unfocusedBorderColor = Border
        ),

        )
}