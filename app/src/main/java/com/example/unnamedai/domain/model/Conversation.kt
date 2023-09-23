package com.example.unnamedai.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class Conversation(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val aiName: String,
    val infoAboutYou: String,
    val infoAboutAi: String,
    val date: String,
    val talk: MutableList<Msg>,
)

data class Msg(
    val from: From,
    val content: String
)

enum class From{
    You,
    YourAi
}