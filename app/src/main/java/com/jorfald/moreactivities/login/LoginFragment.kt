package com.jorfald.moreactivities.login

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
import com.jorfald.moreactivities.ui.main.MainActivity
import com.jorfald.moreactivities.R

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        loginButton = view.findViewById(R.id.login_button)
        usernameEditText = view.findViewById(R.id.login_username_input)
        passwordEditText = view.findViewById(R.id.login_password_input)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().toLowerCase()
            val password = passwordEditText.text.toString().toLowerCase()

            if (viewModel.correctCredentials(username, password)) {
                val sharedPref = activity?.getSharedPreferences(
                    "shared_prefs",
                    Context.MODE_PRIVATE
                )
                sharedPref?.edit()?.putBoolean(LoginActivity.LOGGED_IN_KEY, true)?.apply()

                val intent = Intent(activity, MainActivity::class.java)
                intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            } else {
                Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_LONG).show()
            }
        }
    }
}