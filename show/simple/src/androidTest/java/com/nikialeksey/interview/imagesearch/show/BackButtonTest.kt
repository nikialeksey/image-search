package com.nikialeksey.interview.imagesearch.show

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.ViewAction
import com.nikialeksey.interview.imagesearch.show.impl.R
import org.hamcrest.core.IsEqual
import org.junit.Assert


@RunWith(AndroidJUnit4::class)
class BackButtonTest {

    @get:Rule
    val activityRule = ActivityTestRule(Activity::class.java)

    @Test
    fun backButtonAccessible() {
        var wasClick = false
        activityRule.activity.findViewById<View>(R.id.show_back).setOnClickListener {
            wasClick = true
        }

        Espresso.onView(ViewMatchers.withId(R.id.show_back)).perform(clickXY(10, 10))

        Assert.assertThat(wasClick, IsEqual.equalTo(true))
    }

    private fun clickXY(x: Int, y: Int): ViewAction {
        return GeneralClickAction(
            Tap.SINGLE,
            CoordinatesProvider { view ->
                val screenPos = IntArray(2)
                view.getLocationOnScreen(screenPos)

                val screenX = (screenPos[0] + x).toFloat()
                val screenY = (screenPos[1] + y).toFloat()

                floatArrayOf(screenX, screenY)
            },
            Press.FINGER
        )
    }

    class Activity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val args = Bundle()
            args.putString(
                "url",
                "https://2.bp.blogspot.com/-LnJ12R577oI/UvNUhxws84I/AAAAAAAABwQ/JI6NXVZ5SNU/s1600/images+(6).jpg"
            )
            args.putString("thumbnailUrl", "")

            val navHostFragment = NavHostFragment.create(R.navigation.navigation_show, args)
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, navHostFragment)
                .setPrimaryNavigationFragment(navHostFragment)
                .commit()
        }
    }
}