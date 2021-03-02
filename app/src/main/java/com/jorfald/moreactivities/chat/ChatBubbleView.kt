package com.jorfald.moreactivities.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.jorfald.moreactivities.R

class ChatBubbleView(context: Context) : ConstraintLayout(context) {

    private val chatTextView: TextView
    private val authorTextView: TextView

    private val view: View = LayoutInflater.from(context).inflate(R.layout.chat_bubble, this)

    init {
        chatTextView = view.findViewById(R.id.chat_text_view)
        authorTextView = view.findViewById(R.id.chat_author_view)
    }

    fun setChatText(chatText: String) {
        chatTextView.text = chatText
    }

    fun setAuthorText(authorName: String) {
        authorTextView.text = authorName
    }

    fun setSelfAuthor(isSelfAuthor: Boolean) {
        val chatTextParams = chatTextView.layoutParams as LayoutParams

        if (isSelfAuthor) {
            chatTextView.background = ContextCompat.getDrawable(context, R.drawable.rounded_blue_bg)

            authorTextView.textAlignment = TEXT_ALIGNMENT_VIEW_END

            chatTextParams.leftToLeft = LayoutParams.UNSET
            chatTextParams.rightToRight = authorTextView.id


        } else {
            chatTextView.background =
                ContextCompat.getDrawable(context, R.drawable.rounded_green_bg)

            authorTextView.textAlignment = TEXT_ALIGNMENT_VIEW_START

            chatTextParams.rightToRight = LayoutParams.UNSET
            chatTextParams.leftToLeft = authorTextView.id
        }
    }
}
