package com.example.unnamedai.domain.repository

import com.example.unnamedai.domain.model.Msg

interface ApiRepository {
    suspend fun askChatGBT(question: String, qList: List<Msg>, prompt: String): String
}