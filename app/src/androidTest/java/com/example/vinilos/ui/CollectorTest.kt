package com.example.vinilos.ui

import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.vinilos.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*


@RunWith(AndroidJUnit4ClassRunner::class)
class CollectorTest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    fun goToCollectorView() {
        Espresso.onView(withId(R.id.btn_sign_as_user)).check(
            ViewAssertions.matches(isDisplayed())
        )
        Espresso.onView(withId(R.id.btn_sign_as_user)).perform(
            click()
        )
        Espresso.onView(withId(R.id.drawer_layout)).check(
            ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT))
        ).perform(
            DrawerActions.open()
        )
        Espresso.onView(withId(R.id.nav_view)).perform(
            NavigationViewActions.navigateTo(R.id.nav_item_four)
        )
        Thread.sleep(5000);
    }

    @Test
    fun test_get_collector() {
        goToCollectorView()

        Espresso.onView(withId(R.id.search_box_field)).perform(
            click(), replaceText("Manolo")
        )
        Thread.sleep(5000)

        Espresso.onView(
            withId(R.id.collector_recycler_view)
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        Thread.sleep(5000)

        Espresso.onView(withId(R.id.collector_detail_contacto)).check(
            ViewAssertions.matches(isDisplayed())
        )
        Espresso.onView(withId(R.id.collector_detail_email)).check(
            ViewAssertions.matches(isDisplayed())
        )
        Espresso.onView(withId(R.id.collector_detail_name)).check(
            ViewAssertions.matches(isDisplayed())
        )
        Espresso.onView(withId(R.id.collector_detail_telephone)).check(
            ViewAssertions.matches(isDisplayed())
        )
    }
}