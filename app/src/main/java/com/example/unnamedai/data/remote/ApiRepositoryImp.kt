package com.example.unnamedai.data.remote

import com.example.unnamedai.domain.model.ChatRequest
import com.example.unnamedai.domain.model.From
import com.example.unnamedai.domain.model.Message
import com.example.unnamedai.domain.model.Msg
import com.example.unnamedai.domain.repository.ApiRepository
import com.example.unnamedai.util.OPEN_AI_API_KEY

class ApiRepositoryImp(private val caravanApi: UnnamedAiApi) : ApiRepository {
    override suspend fun askChatGBT(question: String, qList: List<Msg>, prompt: String): String {

        val oldMesseges = qList.toMessagList()

        val chatRequest = ChatRequest(
            model = "gpt-4"/*"gpt-3.5-turbo"*/,
            messages =
            listOf(Message(role = "system", content = prompt)) + oldMesseges + Message(role = "user", content = question)
        )

        val response = caravanApi.askChat2(
            authorization = "Bearer $OPEN_AI_API_KEY",
            request = chatRequest
        )

        return response.body()?.choices?.last()?.message?.content ?: "sorry no data"
    }
}

private fun List<Msg>.toMessagList(): List<Message> {
    val result = mutableListOf<Message>()

    this.forEach {
        result.add(
            Message(
                content = it.content,
                role = if (it.from == From.You) "user" else "assistant"
            )
        )
    }

    return result.toList()
}
