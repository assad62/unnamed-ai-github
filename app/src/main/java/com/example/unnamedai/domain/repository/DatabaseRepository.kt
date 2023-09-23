package com.example.unnamedai.domain.repository

import com.example.unnamedai.domain.model.Conversation

interface DatabaseRepository{
    suspend fun saveConversation(conv: Conversation)
    suspend fun deleteConversation(id: Int)
    suspend fun getAllConversation(): List<Conversation>
}