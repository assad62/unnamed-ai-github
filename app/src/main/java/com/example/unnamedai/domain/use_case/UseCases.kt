package com.example.unnamedai.domain.use_case

import com.example.unnamedai.domain.use_case.local.DeleteConversation
import com.example.unnamedai.domain.use_case.local.GetAllConversation
import com.example.unnamedai.domain.use_case.local.SaveConversation
import com.example.unnamedai.domain.use_case.remote.AskChatGBT

data class UseCases(
    val getAllConversation: GetAllConversation,
    val saveConversation: SaveConversation,
    val deleteConversation: DeleteConversation,
    val askChatGBT: AskChatGBT,
)