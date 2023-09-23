package com.example.unnamedai.data.remote

import com.example.unnamedai.domain.model.ChatRequest
import com.example.unnamedai.domain.model.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST



interface UnnamedAiApi {

    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun askChat2(
        @Header("Authorization") authorization: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>

}