package com.jorfald.moreactivities.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.jorfald.moreactivities.R
import com.jorfald.moreactivities.login.LoginActivity
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var logOutButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatInput: EditText
    private lateinit var sendButton: Button

    private lateinit var timer: Timer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        logOutButton = view.findViewById(R.id.log_out_button)
        recyclerView = view.findViewById(R.id.chat_recycler_view)

        chatInput = view.findViewById(R.id.chat_input)
        sendButton = view.findViewById(R.id.send_message_button)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonListeners()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()

        startChatFetchTimer()
    }

    private fun setButtonListeners() {
        logOutButton.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            sharedPref?.edit()?.putBoolean(LoginActivity.LOGGED_IN_KEY, false)?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }

        sendButton.setOnClickListener {
            val text = chatInput.text.toString()
            val chatObject = ChatObject("wgV7RMZXD2", "Otziii", text)

            if (text.isNotEmpty()) {
                viewModel.sendChatMessage(Volley.newRequestQueue(context), chatObject) { success ->
                    if (success) {
                        chatInput.setText("")
                        chatAdapter.addInstance(chatObject)
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
            listOf()
        )

        recyclerView.adapter = chatAdapter
    }

    private fun getChatMessages() {
        viewModel.getChatMessages(
            Volley.newRequestQueue(context),
            { chatMessages ->
                chatAdapter.updateData(chatMessages)
                recyclerView.scrollToPosition(chatMessages.size - 1)
            },
            {
                Toast.makeText(
                    context,
                    "Noe gikk galt. Kunne ikke hente meldinger.",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

    private fun startChatFetchTimer() {
        timer = fixedRateTimer("chatFetchTimer", false, 0L, 5 * 1000) {
            getChatMessages()
        }
    }

    override fun onPause() {
        super.onPause()

        timer.cancel()
    }
}
















