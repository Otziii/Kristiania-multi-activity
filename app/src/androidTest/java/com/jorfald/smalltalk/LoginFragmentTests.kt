package com.jorfald.smalltalk

import android.app.Activity
import android.app.Application
import android.app.Instrumentation
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jorfald.smalltalk.chat.ChatFragment
import com.jorfald.smalltalk.chat.ChatViewModel
import com.jorfald.smalltalk.database.UserObject
import com.jorfald.smalltalk.login.LoginActivity
import com.jorfald.smalltalk.login.LoginFragment
import com.jorfald.smalltalk.login.LoginViewModel
import com.jorfald.smalltalk.tabbar.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class LoginFragmentTests {

    @Test
    fun testBindObservers() {
        UserManager.loggedInUser =  UserObject("42", "klogi", "Konstantin")
        val scenario = launchFragmentInContainer<LoginFragment>()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        val countdown = CountDownLatch(1)

        (InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application).registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityPaused(p0: Activity) {
                }

                override fun onActivityStarted(p0: Activity) {
                }

                override fun onActivityDestroyed(p0: Activity) {
                }

                override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
                }

                override fun onActivityStopped(p0: Activity) {
                }

                override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                    assertTrue(p0 is MainActivity)
                    countdown.countDown()
                }

                override fun onActivityResumed(p0: Activity) {
                }

            }
        )

        scenario.withFragment {
            //     this.viewModel = mockedViewModel
            this.viewModel.loginSuccess.postValue(true)
        }

        countdown.await()

    }

    @Test
    fun testOnButtonClick() {

        val mockedViewModel = mock<LoginViewModel>()
        doNothing().whenever(mockedViewModel).logInUser(any(), any())

        val scenario = launchFragmentInContainer<LoginFragment>()
        scenario.moveToState(Lifecycle.State.CREATED)
        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.withFragment {
            this.viewModel = mockedViewModel
        }


        onView(withId(R.id.login_username_input)).perform(replaceText("klogi"))
        onView(withId(R.id.login_password_input)).perform(replaceText(":-)"))

        verify(mockedViewModel, times(0)).logInUser(any(), any())

        onView(withId(R.id.login_button)).perform(click())

        verify(mockedViewModel, times(1)).logInUser(any(), any())

    }
}