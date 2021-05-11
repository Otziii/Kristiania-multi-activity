package com.jorfald.smalltalk.chat

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.jorfald.smalltalk.database.ChatObject
import java.text.SimpleDateFormat
import java.util.*

// TODO: add test coverage
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

    // TODO: add test coverage
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
