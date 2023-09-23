package com.example.unnamedai.domain.use_case.remote

import com.example.unnamedai.domain.model.Msg
import com.example.unnamedai.domain.repository.ApiRepository

class AskChatGBT(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(q: String, qList: List<Msg>, prompt: String): String{
        return apiRepository.askChatGBT(q, qList, prompt)
    }
}