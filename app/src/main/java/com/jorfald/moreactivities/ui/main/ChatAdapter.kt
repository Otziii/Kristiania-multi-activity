package com.jorfald.moreactivities.ui.main

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private var dataSet: List<ChatObject>
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

        holder.view.setSelfAuthor(chatObject.userId == "9ujioisdsh80")
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateData(newData: List<ChatObject>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    inner class ChatViewHolder(
        val view: ChatBubbleView
    ) : RecyclerView.ViewHolder(view)
}
