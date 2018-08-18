package ivanovic.nosel.albumsearch

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import ivanovic.nosel.albumsearch.ui.SearchActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(SearchActivity::class.java, false, true)

    @Test
    fun touchTunesAssignment() {

        onView(withId(R.id.search_results)).check(matches(isDisplayed()))

        onView(withId(R.id.action_search))
                .check(matches(isDisplayed())).perform(click())

        onView(withId(android.support.v7.appcompat.R.id.search_src_text))
                .check(matches(isDisplayed()))
                .perform(typeText("micheal jackson"))

        //TODO add test for matching a view in the list

    }
}
