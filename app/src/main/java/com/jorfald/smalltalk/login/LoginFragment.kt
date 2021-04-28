package com.jorfald.smalltalk.login

import android.content.Context
import android.content.Intent
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
import com.jorfald.smalltalk.ConnectionManager
import com.jorfald.smalltalk.R
import com.jorfald.smalltalk.tabbar.MainActivity

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var loginLoader: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        loginButton = view.findViewById(R.id.login_button)
        usernameEditText = view.findViewById(R.id.login_username_input)
        passwordEditText = view.findViewById(R.id.login_password_input)
        loginLoader = view.findViewById(R.id.login_loader)
        loginLoader.isVisible = false

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        requireActivity().getSharedPreferences("", Context.MODE_PRIVATE)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
        bindObservers()
    }

    private fun setButtons() {
        loginButton.setOnClickListener {
            if (ConnectionManager.isOnline()) {
                val username = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()

                viewModel.logInUser(
                    username,
                    password
                )
            } else {
                // Show dialog for no network
                Toast.makeText(context, "No internet!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bindObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { showLoader ->
            loginLoader.isVisible = showLoader
        }

        viewModel.loginSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            } else {
                Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_LONG).show()
            }
        }
    }
}