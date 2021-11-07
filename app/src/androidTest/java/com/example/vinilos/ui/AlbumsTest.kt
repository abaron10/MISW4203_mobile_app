package com.example.vinilos.ui

import android.view.Gravity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
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
        Thread.sleep(5000)
    }

    fun goToAlbumsViewAsCollector() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_as_collector)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_as_collector)).perform(
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
        Thread.sleep(5000)
    }

    fun goToCreateAlbum(){
        Espresso.onView(ViewMatchers.withId(R.id.floating_add_button)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(ViewMatchers.withId(R.id.floating_add_button)).perform(
            ViewActions.click()
        )
        Thread.sleep(1000)
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

    @Test
    fun test_create_album_input_error(){
        goToAlbumsViewAsCollector()
        goToCreateAlbum()

        Espresso.onView(ViewMatchers.withId(R.id.scroll_create_album))
            .perform(swipeUp())

        Espresso.onView(ViewMatchers.withId(R.id.btn_add_album)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.scroll_create_album))
            .perform(swipeDown())

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_name),
                ViewMatchers.hasErrorText("Album name cannot be empty")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.cover_image_url),
                ViewMatchers.hasErrorText("Cover image url cannot be empty")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )


        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.release_date),
                ViewMatchers.hasErrorText("Date cannot be empty")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_description),
                ViewMatchers.hasErrorText("Description cannot be empty")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.scroll_create_album))
            .perform(swipeUp())

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.genre),
                ViewMatchers.hasErrorText("Genre was not selected")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.record_label),
                ViewMatchers.hasErrorText("Record label was not selected")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

    }
}