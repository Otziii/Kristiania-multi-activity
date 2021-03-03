package com.jorfald.moreactivities.chat

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView

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
        holder.view.setAuthorText(chatObject.userName.capitalize())

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
