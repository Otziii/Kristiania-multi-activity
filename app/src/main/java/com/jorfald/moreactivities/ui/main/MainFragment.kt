package com.jorfald.moreactivities.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jorfald.moreactivities.R
import com.jorfald.moreactivities.login.LoginActivity

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var logOutButton: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        logOutButton = view.findViewById(R.id.log_out_button)
        recyclerView = view.findViewById(R.id.chat_recycler_view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonListeners()
        initRecyclerView()

        viewModel.getChatMessages(
            requireContext(),
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

    private fun setButtonListeners() {
        logOutButton.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            sharedPref?.edit()?.putBoolean(LoginActivity.LOGGED_IN_KEY, false)?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
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
}