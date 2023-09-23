package com.example.unnamedai.data.local

import com.example.unnamedai.domain.model.Conversation
import com.example.unnamedai.domain.repository.DatabaseRepository

class DatabaseRepositoryImp(private val dao: UnnamedAiDao):DatabaseRepository {

    override suspend fun saveConversation(conv: Conversation) {
        dao.saveConversation(conv)
    }

    override suspend fun deleteConversation(id: Int) {
        dao.deleteConversation(id)
    }

    override suspend fun getAllConversation(): List<Conversation> {
        return dao.getAllConversation() ?: emptyList()
    }
}