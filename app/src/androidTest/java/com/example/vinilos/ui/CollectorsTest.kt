package com.example.vinilos.ui

import android.view.Gravity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.vinilos.R
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CollectorsTest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    fun goToCollectorListView() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_as_user)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_as_user)).perform(
            ViewActions.click()
        )
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).check(
            ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT))
        ).perform(
            DrawerActions.open()
        )
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(
            NavigationViewActions.navigateTo(R.id.nav_item_four)
        )
        Thread.sleep(5000);
    }

    @Test
    fun test_get_three_collectors_displayed() {
        goToCollectorListView()

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.collector_item_name),
                ViewMatchers.withText("Manolo Bellon")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.collector_item_name),
                ViewMatchers.withText("Goldarina Sappell")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.collector_item_name),
                ViewMatchers.withText("Tandi Annis")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun test_search_existing_collector() {
        goToCollectorListView()

        Espresso.onView(ViewMatchers.withId(R.id.search_box_field)).perform(
            ViewActions.click(), ViewActions.replaceText("Nikolas Follen")
        )
        Thread.sleep(5000);
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.collector_item_name),
                ViewMatchers.withText("Nikolas Follen")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun test_search_non_existing_collector() {
        goToCollectorListView()

        Espresso.onView(ViewMatchers.withId(R.id.search_box_field)).perform(
            ViewActions.click(), ViewActions.replaceText("Random non existing collector")
        )
        Thread.sleep(5000);
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.collector_item_name),
                ViewMatchers.withText("Remus Northridge")
            )
        ).check(
            ViewAssertions.doesNotExist()
        )

        Espresso.onView(ViewMatchers.withId(R.id.no_collectors_text)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }
}