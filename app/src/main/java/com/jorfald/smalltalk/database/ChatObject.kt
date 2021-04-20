package com.jorfald.smalltalk.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_message_table")
data class ChatObject(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val userName: String,
    val message: String,
    val timestamp: Long = 0
)
