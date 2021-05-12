package com.jorfald.smalltalk

import android.content.Context
import com.jorfald.smalltalk.database.AppDatabase
import com.jorfald.smalltalk.database.UserDAO
import com.jorfald.smalltalk.database.UserObject
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class UserDAOTests {

    private lateinit var appDatabase: AppDatabase
    private lateinit var userDao: UserDAO

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        userDao = appDatabase.userDAO()
    }

    @Test
    fun testInsertUser() {
        assertNull("Checking that user table is empty", userDao.getUser())
        userDao.insertUser(UserObject(id = "42", userName = "klogi", firstName = "Konstantin"))
        assertNotNull("Checking if adding a user succeed", userDao.getUser())
        val user = userDao.getUser()
        assertEquals("42", user?.id)
        assertEquals("klogi", user?.userName)
        assertEquals("Konstantin", user?.firstName)
    }

    @Test
    fun testInsertAndDeleteUser() {
        assertNull("Checking that user table is empty", userDao.getUser())
        val user = UserObject(id = "42", userName = "klogi", firstName = "Konstantin")
        userDao.insertUser(user)
        assertEquals("42", userDao.getUser()?.id)
        userDao.deleteUser(user)
        assertNull("Checking that user table is empty", userDao.getUser())
    }
}