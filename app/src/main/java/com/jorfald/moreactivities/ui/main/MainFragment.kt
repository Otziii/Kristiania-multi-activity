package com.jorfald.moreactivities.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        logOutButton.setOnClickListener {
            val sharedPref = activity?.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
            sharedPref?.edit()?.putBoolean(LoginActivity.LOGGED_IN_KEY, false)?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {


        recyclerView.layoutManager = LinearLayoutManager(context)

        chatAdapter = ChatAdapter(
            listOf(
                ChatObject("Hei på deg", "Per", false),
                ChatObject("Du er kul", "Ola", true),
                ChatObject("Takk for det.", "Per", false)
            )
        )
        recyclerView.adapter = chatAdapter
    }
}