package com.example.unnamedai.domain.model

data class ChatRequest(
    val model: String,
    val messages: List<Message>
)