package com.example.unnamedai.ui.ChatScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unnamedai.MainEvents
import com.example.unnamedai.MainViewModel
import com.example.unnamedai.util.theme.Black
import com.example.unnamedai.util.theme.White
import com.example.unnamedai.util.theme.abel
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditTextPopUp(viewmodel: MainViewModel = hiltViewModel()) {

    val state = viewmodel.state.value

    val showKeyboard = remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.popupControl) {
        if (showKeyboard.value && state.popupControl) {
            focusRequester.requestFocus()
            delay(100)
            keyboard?.show()
        }
    }


    if (state.popupControl) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .pointerInput(Unit) { detectTapGestures { viewmodel.onEvent(MainEvents.HidePopUp) } },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(40.dp)
                    .background(Black, RoundedCornerShape(5))
                    .fillMaxWidth()
            ) {
                TextField(
                    value = state.editTF,
                    onValueChange = {
                        viewmodel.onEvent(MainEvents.EditTfChanged(it))
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .border(BorderStroke(1.dp, Color.Transparent))
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        viewmodel.onEvent(MainEvents.PressDoneOnEditKeyboard)
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
                        fontSize = 20.sp,
                        color = White,
                    )
                )
            }
        }
    }
}