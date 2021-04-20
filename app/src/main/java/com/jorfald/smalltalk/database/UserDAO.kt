package com.jorfald.smalltalk.database

import androidx.room.*

@Dao
interface UserDAO {
    @Delete
    fun deleteUser(userToDelete: UserObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userToInsert: UserObject)

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): UserObject
}