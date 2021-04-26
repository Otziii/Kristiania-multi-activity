package com.jorfald.smalltalk.chat

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.jorfald.smalltalk.database.ChatObject
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    private var dataSet: List<ChatObject>,
    private val userId: String
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = ChatBubbleView(parent.context)

        view.layoutParams = ViewGroup.LayoutParams(
            MATCH_PARENT,
            WRAP_CONTENT
        )

        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatObject = dataSet[position]

        holder.view.setChatText(chatObject.message)

        val dateOfMessage = Date(chatObject.timestamp)
        val calendar = Calendar.getInstance()
        calendar.time = dateOfMessage
        val timeFormat = SimpleDateFormat("HH:mm dd/MM/yyyy")

        val authorText = "${chatObject.userName.capitalize()} - ${timeFormat.format(calendar.time)}"

        holder.view.setAuthorText(authorText)

        holder.view.setSelfAuthor(chatObject.userId == userId)

        var previousItem: ChatObject? = null
        if (position > 0) {
            previousItem = dataSet[position - 1]
        }

        val thisItem = dataSet[position]

        var nextItem: ChatObject? = null
        if (position < dataSet.size - 1) {
            nextItem = dataSet[position + 1]
        }


        if (previousItem?.userId == thisItem.userId) {
            if (nextItem?.userId == thisItem.userId) {
                // Midten av gruppe
            } else {
                // Slutt på gruppe
            }
        } else {
            if (nextItem?.userId == thisItem.userId) {
                // Starten av gruppe
            } else {
                // Enkel-chatbilde
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateData(newData: List<ChatObject>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    fun addInstance(chatObject: ChatObject) {
        updateData(dataSet + chatObject)
    }

    inner class ChatViewHolder(
        val view: ChatBubbleView
    ) : RecyclerView.ViewHolder(view)
}
