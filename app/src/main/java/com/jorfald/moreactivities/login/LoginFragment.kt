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
import com.android.volley.toolbox.Volley
import com.jorfald.moreactivities.*
import com.jorfald.moreactivities.tabbar.MainActivity

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

        // TEST KOMMENTAR 123

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtons()
    }

    private fun setButtons() {
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            viewModel.logInUser(
                Volley.newRequestQueue(context),
                username,
                password
            ) { user ->
                if (user != null) {
                    val sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()

                    editor.putString(SHARED_PREFS_ID_KEY, user.id)
                    editor.putString(SHARED_PREFS_USERNAME_KEY, user.userName)
                    editor.putString(SHARED_PREFS_FIRSTNAME_KEY, user.firstName)

                    editor.apply()

                    val intent = Intent(activity, MainActivity::class.java)
                    intent.flags = intent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "Wrong username or password!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}