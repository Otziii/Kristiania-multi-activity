package com.jorfald.smalltalk.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jorfald.smalltalk.R
import com.jorfald.smalltalk.UserManager
import com.jorfald.smalltalk.tabbar.MainActivity

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    private lateinit var circleTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var firstNameTextView: TextView
    private lateinit var logOutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        circleTextView = view.findViewById(R.id.circle_image_text_view)
        usernameTextView = view.findViewById(R.id.user_name_text_view)
        firstNameTextView = view.findViewById(R.id.first_name_text_view)
        logOutButton = view.findViewById(R.id.log_out_button)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = UserManager.loggedInUser

        circleTextView.text = user.firstName.firstOrNull()?.toString() ?: "X"
        usernameTextView.text = user.userName
        firstNameTextView.text = user.firstName

        logOutButton.setOnClickListener {
            (activity as? MainActivity)?.logOutUser()
        }
    }
}