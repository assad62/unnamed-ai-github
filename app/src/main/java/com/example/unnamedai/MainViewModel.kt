package com.example.unnamedai

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unnamedai.domain.model.Conversation
import com.example.unnamedai.domain.model.From
import com.example.unnamedai.domain.model.Msg
import com.example.unnamedai.domain.use_case.UseCases
import com.example.unnamedai.util.ext.getCurrentDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


data class PopUpItem(
    val compose: @Composable () -> Unit
)


sealed class MainEvents {
    object SwipeSplashScreen : MainEvents()

    object ClickWelcome : MainEvents()

    data class ClickChatSetter(val context: Context) : MainEvents()

    object PressDoneOnKeyboard : MainEvents()

    object ClickStartNewChat : MainEvents()
    object ClickGoToHistory : MainEvents()
    object ClickBacKFromHistory : MainEvents()
    data class DeleteConversationFromHistory(val id: Int) : MainEvents()
    data class SelectConversationFromHistory(val conversation: Conversation) : MainEvents()

    data class ChatTfChanged(val it: String) : MainEvents()
    data class EditTfChanged(val it: String) : MainEvents()

    data class YouTfChanged(val it: String) : MainEvents()
    data class YouWhoTfChanged(val it: String) : MainEvents()
    data class ThemTfChanged(val it: String) : MainEvents()
    data class ThemWhoTfChanged(val it: String) : MainEvents()

    data class ShowPopUp(val it: Msg) : MainEvents()
    object HidePopUp : MainEvents()
    object PressDoneOnEditKeyboard : MainEvents()

    data class DeleteMsgConversation(val it: Msg) : MainEvents()

}

data class MainState(
    val loadingChatRespond: Boolean = false,
    //main
    val showChatScreen: Boolean = false,
    val showHistoryScreen: Boolean = false,
    //start,
    var wlcVisibility: Boolean = false,
    var setterVisibility: Boolean = false,
    //text fields,
    var youTF: String = "",
    var youWhoTF: String = "",
    var themTF: String = "",
    var themWhoTF: String = "",
    //chat,
    var chatTF: String = "",
    var editTF: String = "",
    var popupControl: Boolean = false,
    var selectedMsgIndex: Int = 0,
    //data
    val currentConversation: Conversation? = null,
    var history: List<Conversation> = listOf(),
    var popUpItem: PopUpItem? = null,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _state = mutableStateOf(MainState())
    val state: State<MainState> = _state


    fun onEvent(event: MainEvents) {
        when (event) {
            MainEvents.SwipeSplashScreen -> _state.value = state.value.copy(wlcVisibility = true)

            MainEvents.ClickWelcome -> _state.value = state.value.copy(setterVisibility = true, wlcVisibility = false)

            is MainEvents.ClickChatSetter -> {
                if (
                    state.value.youTF.isBlank() ||
                    state.value.youWhoTF.isBlank() ||
                    state.value.themTF.isBlank() ||
                    state.value.themWhoTF.isBlank()
                ) {

                    Toast.makeText(
                        event.context,
                        "Please fill all the fields",
                        Toast.LENGTH_SHORT
                    ).show()

                    return
                }

                _state.value = state.value.copy(
                    setterVisibility = false,
                    showChatScreen = true,
                    currentConversation = Conversation(
                        id = (0..999999).random(),
                        name = state.value.youTF,
                        aiName = state.value.themTF,
                        infoAboutYou = state.value.youWhoTF,
                        infoAboutAi = state.value.themWhoTF,
                        date = getCurrentDate(),
                        talk = mutableListOf()
                    )
                )

                viewModelScope.launch {
                    useCases.saveConversation(state.value.currentConversation!!)
                }

            }

            MainEvents.PressDoneOnKeyboard -> {
                val content = state.value.chatTF

                _state.value =
                    state.value.copy(
                        currentConversation = state.value.currentConversation.apply {
                            this!!.talk.add(
                                Msg(From.You, content)
                            )
                        },
                        chatTF = "",
                        loadingChatRespond = true
                    )

                viewModelScope.launch(Dispatchers.Main) {
                    val aiRespond = withContext(Dispatchers.IO) {
                        useCases.askChatGBT(
                            content,
                            state.value.currentConversation!!.talk,
                            state.value.currentConversation!!.let {
                                "act like ${it.aiName}, a ${it.infoAboutAi}." +
                                        " my name is ${it.name} and I am a ${it.infoAboutYou}"
                            }
                        )
                    }

                    _state.value =
                        state.value.copy(
                            currentConversation = state.value.currentConversation
                                .apply { this!!.talk.add(Msg(From.YourAi, aiRespond)) },
                            loadingChatRespond = false
                        )

                    withContext(Dispatchers.IO) {
                        useCases.saveConversation(state.value.currentConversation!!)
                    }
                }
            }

            MainEvents.ClickGoToHistory -> {

                viewModelScope.launch(Dispatchers.Main) {
                    val dbData = withContext(Dispatchers.IO) {
                        useCases.getAllConversation()
                    }

                    _state.value =
                        state.value.copy(
                            showHistoryScreen = true,
                            history = dbData
                        )
                }


            }

            MainEvents.ClickBacKFromHistory -> _state.value = state.value.copy(showHistoryScreen = false)

            MainEvents.ClickStartNewChat -> _state.value = state.value.copy(
                setterVisibility = true,
                showHistoryScreen = false,
                showChatScreen = false,
                wlcVisibility = false,
                youTF = "",
                youWhoTF = "",
                themTF = "",
                themWhoTF = "",
                chatTF = "",
            )

            is MainEvents.DeleteMsgConversation -> {

                val newConversation =
                    state.value.currentConversation.apply { this!!.talk.remove(event.it) }

                _state.value = state.value.copy(loadingChatRespond = true)

                viewModelScope.launch(Dispatchers.Main) {

                    withContext(Dispatchers.IO) {
                        useCases.saveConversation(state.value.currentConversation!!)
                    }

                    _state.value =
                        state.value.copy(
                            currentConversation = newConversation,
                            loadingChatRespond = false
                        )

                }
            }

            is MainEvents.DeleteConversationFromHistory -> {
                viewModelScope.launch(Dispatchers.Main) {
                    val dbData = withContext(Dispatchers.IO) {
                        useCases.deleteConversation(event.id)
                        useCases.getAllConversation()
                    }

                    _state.value = state.value.copy(history = dbData)
                }
            }

            is MainEvents.SelectConversationFromHistory -> _state.value = state.value.copy(
                currentConversation = event.conversation,
                youTF = event.conversation.name,
                themTF = event.conversation.aiName,
                youWhoTF = event.conversation.infoAboutYou,
                themWhoTF = event.conversation.infoAboutAi,
                chatTF = "",
                showHistoryScreen = false,
                showChatScreen = true,
                wlcVisibility = false
            )

            is MainEvents.ChatTfChanged -> _state.value = state.value.copy(chatTF = event.it)
            is MainEvents.EditTfChanged -> _state.value = state.value.copy(editTF = event.it)

            is MainEvents.YouTfChanged -> _state.value = state.value.copy(youTF = event.it)
            is MainEvents.YouWhoTfChanged -> _state.value = state.value.copy(youWhoTF = event.it)
            is MainEvents.ThemTfChanged -> _state.value = state.value.copy(themTF = event.it)
            is MainEvents.ThemWhoTfChanged -> _state.value = state.value.copy(themWhoTF = event.it)

            is MainEvents.ShowPopUp -> {

                _state.value = state.value.copy(
                    selectedMsgIndex = state.value.currentConversation!!.talk.indexOf(event.it),
                    popupControl = true, editTF = event.it.content
                )
            }

            MainEvents.HidePopUp -> _state.value = state.value.copy(popupControl = false)
            MainEvents.PressDoneOnEditKeyboard -> {
                if (state.value.selectedMsgIndex == -1) {
                    _state.value = state.value.copy(popupControl = false)
                    return
                }
                if (state.value.editTF == state.value.currentConversation!!.talk[state.value.selectedMsgIndex].content) {
                    _state.value = state.value.copy(popupControl = false)
                    return
                }


                val newConversation =
                    state.value.currentConversation.apply {
                        this!!.talk.set(
                            state.value.selectedMsgIndex,
                            Msg(
                                from = this.talk[state.value.selectedMsgIndex].from,
                                state.value.editTF
                            )
                        )
                    }

                _state.value = state.value.copy(loadingChatRespond = true)

                viewModelScope.launch(Dispatchers.Main) {

                    withContext(Dispatchers.IO) {
                        useCases.saveConversation(state.value.currentConversation!!)
                    }

                    _state.value =
                        state.value.copy(
                            currentConversation = newConversation,
                            popupControl = false,
                            loadingChatRespond = false
                        )

                }

            }
        }
    }
}