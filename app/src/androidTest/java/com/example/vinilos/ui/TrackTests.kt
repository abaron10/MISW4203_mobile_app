package com.example.vinilos.ui

import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
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
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*


@RunWith(AndroidJUnit4ClassRunner::class)
class TrackTests {
    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    fun goToAlbumsViewAsCollector() {
        Espresso.onView(withId(R.id.btn_sign_as_collector)).check(
            ViewAssertions.matches(isDisplayed())
        )
        Espresso.onView(withId(R.id.btn_sign_as_collector)).perform(
            click()
        )
        Espresso.onView(withId(R.id.drawer_layout)).check(
            ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.LEFT))
        ).perform(
            DrawerActions.open()
        )
        Espresso.onView(withId(R.id.nav_view)).perform(
            NavigationViewActions.navigateTo(R.id.nav_item_two)
        )
        Thread.sleep(5000)
    }

    fun goToCreateAlbum(){
        Espresso.onView(withId(R.id.floating_add_button)).check(
            ViewAssertions.matches(isDisplayed())
        )
        Espresso.onView(withId(R.id.floating_add_button)).perform(
            click()
        )
        Thread.sleep(1000)
    }

    fun createAlbum(albumName: String){
        Espresso.onView(withId(R.id.album_name)).perform(
            click(), replaceText(albumName)
        )

        Espresso.onView(withId(R.id.cover_image_url)).perform(
            click(), replaceText("www.google.com")
        )

        Espresso.onView(withId(R.id.release_date)).perform(
            click(), replaceText("01/01/2000")
        )

        Espresso.onView(withId(R.id.album_description))
            .perform(scrollTo())

        Espresso.onView(withId(R.id.album_description)).perform(
            click(), replaceText(albumName + albumName)
        )

        Espresso.onView(withId(R.id.genre))
            .perform(scrollTo())

        Espresso.onView(withId(R.id.genre)).perform(
            click()
        )

        Espresso.onView(withText("Salsa"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())

        Espresso.onView(withId(R.id.record_label))
            .perform(scrollTo())

        Espresso.onView(withId(R.id.record_label)).perform(
            click()
        )

        Espresso.onView(withText("Elektra"))
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(click())

        Espresso.onView(withId(R.id.btn_add_album))
            .perform(scrollTo())

        Espresso.onView(withId(R.id.btn_add_album)).perform(
            click()
        )

        Thread.sleep(5000)
        goToAlbumsViewAsCollector()
        Thread.sleep(5000)

    }

    @Test
    fun test_create_track_ok() {
        goToAlbumsViewAsCollector()
        goToCreateAlbum()

        val albumName = "Test grupo 10 "+ Random.nextInt(0, 10000000)
        val trackName = "Test grupo 10 track"+ Random.nextInt(0, 10000000)
        createAlbum(albumName)

        Espresso.onView(withId(R.id.search_box_field)).perform(
            click(), replaceText(albumName)
        )
        Thread.sleep(5000)

        Espresso.onView(
            withId(R.id.album_recycler_view)
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        Thread.sleep(5000)

        Espresso.onView(
            withId(R.id.album_add_track)
        ).perform(click())

        Espresso.onView(withId(R.id.track_name)).perform(
            click(), replaceText(trackName)
        )

        Espresso.onView(withId(R.id.track_duration)).perform(
            click(), replaceText("11:13")
        )

        Espresso.onView(withId(R.id.btn_add_track)).perform(
            click()
        )
        Thread.sleep(5000)

        Espresso.onView(withId(R.id.search_box_field)).perform(
            click(), replaceText(albumName)
        )
        Thread.sleep(5000)

        Espresso.onView(
            withId(R.id.album_recycler_view)
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        Thread.sleep(5000)

        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.track_item_name),
                withText(trackName)
            )
        ).check(
            ViewAssertions.matches(isDisplayed())
        )
    }
}