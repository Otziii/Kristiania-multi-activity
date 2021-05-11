package com.jorfald.smalltalk

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jorfald.smalltalk.database.AppDatabase
import com.jorfald.smalltalk.database.ChatDAO
import com.jorfald.smalltalk.database.ChatObject
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatDAOTests {

    private lateinit var appDatabase: AppDatabase
    private lateinit var chatDao: ChatDAO

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        chatDao = appDatabase.chatDAO()
    }

    @Test
    fun testGetAllMessages() {
        assertEquals(emptyList<ChatObject>(), chatDao.getAllMessages())

        val msg1 = ChatObject(10, "42", "klogi", "message", 1)
        val msg2 = ChatObject(11, "42", "klogi", "message 2", 2)
        val chatObjects = listOf(msg1, msg2)
        chatDao.insertChatMessages(chatObjects)

        val retrievedMsgs = chatDao.getAllMessages()
        assertEquals(2, retrievedMsgs.size)
        assertEquals(10, retrievedMsgs[0].id)
        assertEquals("42", retrievedMsgs[1].userId)
    }

    @Test
    fun testDeleteAllMessages() {
        assertEquals(emptyList<ChatObject>(), chatDao.getAllMessages())
        val msg1 = ChatObject(10, "42", "klogi", "message", 1)
        val msg2 = ChatObject(11, "42", "klogi", "message 2", 2)
        val chatObjects = listOf(msg1, msg2)
        chatDao.insertChatMessages(chatObjects)
        assertEquals(chatObjects, chatDao.getAllMessages())

        chatDao.deleteAllMessages()
        assertEquals(emptyList<ChatObject>(), chatDao.getAllMessages())
    }
}