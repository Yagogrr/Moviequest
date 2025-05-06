package com.example.moviequest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistreActivityUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(Register_activity::class.java)

    @Test
    fun testNomUsuariBuit() {
        onView(withId(R.id.name)).perform(clearText())
        onView(withId(R.id.iniciSessio)).perform(click())
        onView(withId(R.id.name))
            .check(matches(hasErrorText("El nom no put estar buit")))
    }
}
