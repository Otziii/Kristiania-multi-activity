package com.jorfald.smalltalk.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.jorfald.smalltalk.R
import com.jorfald.smalltalk.UserManager
import com.jorfald.smalltalk.database.AppDatabase
import com.jorfald.smalltalk.database.ChatDAO
import com.jorfald.smalltalk.database.ChatObject
import com.jorfald.smalltalk.extensions.hideKeyboard
import java.util.*
import kotlin.concurrent.fixedRateTimer

class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatInput: EditText
    private lateinit var sendButton: Button
    private lateinit var loader: ProgressBar

    private var timer: Timer? = null
    private val user = UserManager.loggedInUser
    private lateinit var chatDAO: ChatDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        recyclerView = view.findViewById(R.id.chat_recycler_view)

        chatInput = view.findViewById(R.id.chat_input)
        sendButton = view.findViewById(R.id.send_message_button)

        loader = view.findViewById(R.id.loader)
        loader.isVisible = false

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatDAO = AppDatabase.getDatabase(requireContext()).chatDAO()

        bindObservers()
        setButtonListeners()
        initRecyclerView()
        viewModel.getChatMessagesFromDatabase()
    }

    override fun onResume() {
        super.onResume()

        startChatFetchTimer()
    }

    private fun bindObservers() {
        viewModel.chatMessagesLiveData.observe(viewLifecycleOwner, { newList ->
            viewModel.saveChat(newList)

            activity?.runOnUiThread {
                chatAdapter.updateData(newList)
                scrollToBottom()
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            loader.isVisible = isLoading
        })
    }

    private fun setButtonListeners() {
        sendButton.setOnClickListener {
            val text = chatInput.text.toString()
            val chatObject = ChatObject(0, user.id, user.userName, text, Date().time)

            if (text.isNotEmpty()) {
                viewModel.sendChatMessage(
                    chatObject
                ) { success ->
                    if (success) {
                        activity?.hideKeyboard()
                        chatInput.setText("")
                        chatAdapter.addInstance(chatObject)
                        scrollToBottom()
                    } else {
                        Toast.makeText(
                            context,
                            "Noe gikk galt. Kunne ikke sende melding.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        chatAdapter = ChatAdapter(
            listOf(),
            user.id
        )

        recyclerView.adapter = chatAdapter
    }

    private fun getChatMessages() {
        viewModel.getChatMessages(
            user.id
        ) {
            Toast.makeText(
                context,
                "Noe gikk galt. Kunne ikke hente meldinger.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition((recyclerView.adapter?.itemCount ?: 1) - 1)
    }

    private fun startChatFetchTimer() {
        if (timer != null) {
            return
        }

        timer = fixedRateTimer("chatFetchTimer", false, 0L, 15 * 1000) {
            getChatMessages()
        }
    }

    override fun onPause() {
        super.onPause()

        timer?.cancel()
        timer = null
    }
}
















