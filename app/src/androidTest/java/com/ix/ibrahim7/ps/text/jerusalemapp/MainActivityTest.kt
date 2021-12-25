package com.ix.ibrahim7.ps.text.jerusalemapp

import android.content.Intent
import android.os.SystemClock
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.activity.MainActivity
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    lateinit var activitySearchScenario: ActivityScenario<MainActivity>

    @Test
    fun recyclerViewTest() {

        val i = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        i.putExtra("name", "Firase")
        i.putExtra("email", "Fir@gmail.com")
        i.putExtra("pass", "123456789")
        i.putExtra("pass2", "123456789")

        activitySearchScenario = ActivityScenario.launch(i)
        SystemClock.sleep(3000)

        onView(withId(R.id.btnSkip))
            .perform(click())

        onView(withId(R.id.btnGetStarted))
            .perform(click())

        SystemClock.sleep(3000)

        onView(withId(R.id.btnCreateNew))
            .perform(click())

        onView(withId(R.id.txtUsername))
            .perform(click())
            .perform(
                ViewActions.typeText(i.getStringExtra("name")),
                ViewActions.closeSoftKeyboard()
            )

        onView(withId(R.id.txtEmail))
            .perform(click())
            .perform(
                ViewActions.typeText(i.getStringExtra("email")),
                ViewActions.closeSoftKeyboard()
            )
        onView(withId(R.id.txtPassword))
            .perform(click())
            .perform(
                ViewActions.typeText(i.getStringExtra("pass")),
                ViewActions.closeSoftKeyboard()
            )
        onView(withId(R.id.txtConfirmPassword))
            .perform(click())
            .perform(
                ViewActions.typeText(i.getStringExtra("pass2")),
                ViewActions.closeSoftKeyboard()
            )

        onView(withId(R.id.btnSignUp))
            .perform(click())
    }

    @Test
    fun getTest() {
        val i = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        i.putExtra(
            "name",
            "You can use the Firebase KTX libraries to write idiomatic Kotlin. Change the firebase-crashlytics library dependency line in build.gradle to firebase-crashlytics-ktx."
        )

        activitySearchScenario = ActivityScenario.launch(i)
        SystemClock.sleep(3000)

//        onView(withId(R.id.rcPostList))
//            .perform(
//                RecyclerViewActions
//                    .scrollToPosition<RecyclerView.ViewHolder>(
//                        5
//                    )
//            )

        onView(withId(R.id.btnAdd))
            .perform(click())

        SystemClock.sleep(3000)

        onView(withId(R.id.txtPostDescription))
            .perform(click())
            .perform(
                ViewActions.typeText(i.getStringExtra("name")),
                ViewActions.closeSoftKeyboard()
            )
    }
}
