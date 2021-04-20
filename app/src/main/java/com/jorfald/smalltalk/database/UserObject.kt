package com.jorfald.smalltalk.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserObject(
    @PrimaryKey
    val id: String,
    val userName: String,
    val firstName: String
)