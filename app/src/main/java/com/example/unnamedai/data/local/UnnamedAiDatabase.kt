package com.example.unnamedai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.unnamedai.domain.model.Conversation
import com.example.unnamedai.domain.model.Msg
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Database(
    entities = [Conversation::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class UnnamedAiDatabase: RoomDatabase() {
    abstract val unnamedAiDao: UnnamedAiDao

    companion object {
        const val Database_Name = "database"
    }
}

class Converters {

    @TypeConverter
    fun fromMsg(msg: List<Msg?>): String{
        return Gson().toJson(msg)
    }

    @TypeConverter
    fun toMsg(json: String): List<Msg?> {
        return Gson().fromJson<List<Msg?>>(json)
    }
}

inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object : TypeToken<T>() {}.type)