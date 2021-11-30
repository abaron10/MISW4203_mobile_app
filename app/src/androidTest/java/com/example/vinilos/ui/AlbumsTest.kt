package com.example.vinilos.ui

import android.view.Gravity
import androidx.test.espresso.Espresso
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
import kotlin.random.Random
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText


@RunWith(AndroidJUnit4ClassRunner::class)
class AlbumsTest {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    fun goToAlbumsView() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_as_user)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(ViewMatchers.withId(R.id.btn_sign_as_user)).perform(
            click()
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
            click()
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
            click()
        )
        Thread.sleep(1000)
    }

    @Test
    fun test_get_albums() {
        goToAlbumsView()

        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                withText("A Night at the Opera")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                withText("Tagopia")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

    @Test
    fun test_get_albums_filter_existing_album() {
        goToAlbumsView()

        Espresso.onView(ViewMatchers.withId(R.id.search_box_field)).perform(
            click(), replaceText("A Night at the Opera")
        )
        Thread.sleep(5000);
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                withText("A Night at the Opera")
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                withText("Tagopia")
            )
        ).check(
            ViewAssertions.doesNotExist()
        )
    }

    @Test
    fun test_get_albums_filter_non_existing_album() {
        goToAlbumsView()

        Espresso.onView(ViewMatchers.withId(R.id.search_box_field)).perform(
            click(), replaceText("Random non existing text")
        )
        Thread.sleep(5000);
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                withText("A Night at the Opera")
            )
        ).check(
            ViewAssertions.doesNotExist()
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                withText("Tagopia")
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
            click()
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

    @Test
    fun test_create_album_ok() {
        goToAlbumsViewAsCollector()
        goToCreateAlbum()

        val albumName = "Test grupo 10 "+ Random.nextInt(0, 10000000)

        Espresso.onView(ViewMatchers.withId(R.id.album_name)).perform(
            click(), replaceText(albumName)
        )

        Espresso.onView(ViewMatchers.withId(R.id.cover_image_url)).perform(
            click(), replaceText("www.google.com")
        )

        Espresso.onView(ViewMatchers.withId(R.id.release_date)).perform(
            click(), replaceText("01/01/2000")
        )

        Espresso.onView(ViewMatchers.withId(R.id.album_description))
            .perform(scrollTo())

        Espresso.onView(ViewMatchers.withId(R.id.album_description)).perform(
            click(), replaceText(albumName + albumName)
        )

        Espresso.onView(ViewMatchers.withId(R.id.genre))
            .perform(scrollTo())

        Espresso.onView(ViewMatchers.withId(R.id.genre)).perform(
            click()
        )

        Espresso.onView(withText("Salsa"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.record_label))
            .perform(scrollTo())

        Espresso.onView(ViewMatchers.withId(R.id.record_label)).perform(
            click()
        )

        Espresso.onView(withText("Elektra"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())

        Espresso.onView(ViewMatchers.withId(R.id.btn_add_album))
            .perform(scrollTo())

        Espresso.onView(ViewMatchers.withId(R.id.btn_add_album)).perform(
            click()
        )

        Thread.sleep(5000)
        goToAlbumsViewAsCollector()
        Thread.sleep(5000)

        Espresso.onView(ViewMatchers.withId(R.id.search_box_field)).perform(
            click(), replaceText(albumName)
        )
        Thread.sleep(5000)
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.album_item_name),
                withText(albumName)
            )
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

    }

}