package com.example.vinilos.ui

import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.vinilos.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AlbumsTest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    fun goToAlbumsView() {
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
            NavigationViewActions.navigateTo(R.id.nav_item_two)
        )
        Thread.sleep(5000);
    }

    @Test
    fun test_get_albums() {
        goToAlbumsView()

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                ViewMatchers.withText("A Night at the Opera")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                ViewMatchers.withText("Tagopia")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun test_get_albums_filter_existing_album() {
        goToAlbumsView()

        Espresso.onView(ViewMatchers.withId(R.id.search_box_field)).perform(
            ViewActions.click(), ViewActions.replaceText("A Night at the Opera")
        )
        Thread.sleep(5000);
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                ViewMatchers.withText("A Night at the Opera")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                ViewMatchers.withText("Tagopia")
            )
        ).check(
            ViewAssertions.doesNotExist()
        )
    }

    @Test
    fun test_get_albums_filter_non_existing_album() {
        goToAlbumsView()

        Espresso.onView(ViewMatchers.withId(R.id.search_box_field)).perform(
            ViewActions.click(), ViewActions.replaceText("Random non existing text")
        )
        Thread.sleep(5000);
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                ViewMatchers.withText("A Night at the Opera")
            )
        ).check(
            ViewAssertions.doesNotExist()
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                ViewMatchers.withText("Tagopia")
            )
        ).check(
            ViewAssertions.doesNotExist()
        )
        Espresso.onView(ViewMatchers.withId(R.id.no_albums_text)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }
}