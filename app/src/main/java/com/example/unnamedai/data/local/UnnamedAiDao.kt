package com.example.unnamedai.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unnamedai.domain.model.Conversation

@Dao
interface UnnamedAiDao {

    @Query("SELECT * FROM conversations")
    suspend fun getAllConversation(): List<Conversation>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveConversation(conv: Conversation)

    @Query("DELETE FROM conversations WHERE id = :id")
    suspend fun deleteConversation(id: Int)

}