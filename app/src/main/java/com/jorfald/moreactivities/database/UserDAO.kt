package com.jorfald.moreactivities.database

import androidx.room.*

@Dao
interface UserDAO {
    @Delete
    fun removeUser(user: UserObject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserObject)

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser() : UserObject?
}