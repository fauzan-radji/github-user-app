package com.fauzan.githubuser.ui.home

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.fauzan.githubuser.R
import com.fauzan.githubuser.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
internal class HomeFragmentTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    // Test 1: Check search feature and click the first item in the recyclerview
    @Test
    fun checkSearchFeature() {
        // Step 1: Check if the search bar is displayed
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()))

        // Step 2: Check if the empty state is displayed
        onView(withId(R.id.iv_empty_state)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.tv_empty_state)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        // Step 3: Click the search bar
        onView(withId(R.id.search_bar)).perform(click())

        // Step 4: Check if the searchview is displayed
        onView(withId(R.id.search_view)).check(matches(isDisplayed()))

        // Step 5: Type "fauzan" in the searchview
        onView(withId(R.id.search_view)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("fauzan"))

        // Step 6: Submit the query
        onView(withId(R.id.search_view)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(pressImeActionButton())

        // Step 7: Check if the empty state is not displayed
        onView(withId(R.id.iv_empty_state)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        onView(withId(R.id.tv_empty_state)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        // Step 8: Check if the progress bar is displayed
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        // Step 9: Check if the progress bar is not displayed after 5 seconds
        Thread.sleep(5000)
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        // Step 10: Check if the recyclerview is displayed
        onView(withId(R.id.rv_users)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))

        // Step 11: Check if the recyclerview has at least 1 item
        onView(withId(R.id.rv_users)).check(matches(hasMinimumChildCount(1)))

        // Step 12: Click the first item in the recyclerview
        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }
}