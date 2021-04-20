package com.jorfald.smalltalk.chat

data class ChatObject(
    val userId: String,
    val userName: String,
    val message: String,
    val timestamp: Long = 0
)
