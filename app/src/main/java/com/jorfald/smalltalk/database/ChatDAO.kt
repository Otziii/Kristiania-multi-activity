package com.jorfald.smalltalk.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChatMessages(chatList: List<ChatObject>)

    @Query("DELETE FROM chat_message_table")
    fun deleteAllMessages()

    @Query("SELECT * FROM chat_message_table")
    fun getAllMessages(): List<ChatObject>

    @Query("SELECT * FROM chat_message_table ORDER BY timestamp DESC LIMIT 1")
    fun getLastMessage(): ChatObject
}