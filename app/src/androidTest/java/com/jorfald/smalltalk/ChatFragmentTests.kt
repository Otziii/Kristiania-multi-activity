package com.jorfald.smalltalk

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.jorfald.smalltalk.chat.ChatFragment
import com.jorfald.smalltalk.chat.ChatViewModel
import com.jorfald.smalltalk.database.UserObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.mockito.Mockito.doNothing
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ChatFragmentTests {

    @Test
    fun testChatFragmentInitialization() {

        val mockedViewModel = mock<ChatViewModel>()
        doNothing().whenever(mockedViewModel).sendChatMessage(any(), any())

        UserManager.loggedInUser =  UserObject("42", "klogi", "Konstantin")
        val scenario = launchFragmentInContainer<ChatFragment>()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.withFragment {
            this.viewModel = mockedViewModel
            assertNotNull(this.timer)
        }

        onView(withId(R.id.send_message_button)).check(matches(isClickable()))
        onView(withId(R.id.send_message_button)).perform(click())
        verify(mockedViewModel, times(0)).sendChatMessage(any(), any())

        onView(withId(R.id.chat_input)).perform(replaceText("Hi everyone!"))
        onView(withId(R.id.chat_input)).perform(closeSoftKeyboard())

        Thread.sleep(1000)
        onView(withId(R.id.send_message_button)).check(matches(isClickable()));
        onView(withId(R.id.send_message_button)).perform(click())

        verify(mockedViewModel, times(1)).sendChatMessage(any(), any());
    }
}