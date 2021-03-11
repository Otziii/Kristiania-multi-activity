package com.jorfald.moreactivities.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.jorfald.moreactivities.R

class ChatBubbleView(context: Context) : ConstraintLayout(context) {

    private val chatLeftTextView: TextView
    private val authorLeftTextView: TextView
    private val chatRightTextView: TextView
    private val authorRightTextView: TextView

    private val view: View = LayoutInflater.from(context).inflate(R.layout.chat_bubble, this)

    init {
        chatLeftTextView = view.findViewById(R.id.chat_left_text_view)
        authorLeftTextView = view.findViewById(R.id.chat_left_author_text_view)
        chatRightTextView = view.findViewById(R.id.chat_right_text_view)
        authorRightTextView = view.findViewById(R.id.chat_right_author_text_view)
    }

    fun setChatText(chatText: String) {
        chatRightTextView.text = chatText
        chatLeftTextView.text = chatText
    }

    fun setAuthorText(authorName: String) {
        authorRightTextView.text = authorName
        authorLeftTextView.text = authorName
    }

    fun setSelfAuthor(isSelfAuthor: Boolean) {
        chatLeftTextView.isVisible = !isSelfAuthor
        authorLeftTextView.isVisible = !isSelfAuthor

        chatRightTextView.isVisible = isSelfAuthor
        authorRightTextView.isVisible = isSelfAuthor
    }
}
