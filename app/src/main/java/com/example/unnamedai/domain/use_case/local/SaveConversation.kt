package com.example.unnamedai.domain.use_case.local

import com.example.unnamedai.domain.model.Conversation
import com.example.unnamedai.domain.repository.DatabaseRepository

class SaveConversation(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(conv: Conversation){
        return databaseRepository.saveConversation(conv)
    }
}