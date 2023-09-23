package com.example.unnamedai.domain.use_case.local

import com.example.unnamedai.domain.model.Conversation
import com.example.unnamedai.domain.repository.DatabaseRepository

class GetAllConversation (
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(): List<Conversation>{
        return databaseRepository.getAllConversation()
    }
}