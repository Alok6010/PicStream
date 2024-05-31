package com.example.test.Model

data class MessageModel(
    val senderId: String? = "",
    val message: String? = "",

    val messageId: String? = null,
    val timestamp: Long? = null,
    val type: String = "text",

    val currentTime: String? = "",
    val currentDate: String? = ""

)
