package com.nikialeksey.interview.imagesearch.show

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.nikialeksey.interview.imagesearch.show.impl.R
import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@LargeTest
class BackButtonTest {

    @get:Rule
    val activityRule = ActivityTestRule(Activity::class.java)

    @Before
    fun setup() {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).wakeUp()
    }

    @Test
    fun backButtonAccessible() {
        println("1")
        var wasClick = false
        println("2")
        activityRule.activity.findViewById<View>(R.id.show_back).setOnClickListener {
            wasClick = true
        }
        println("3")

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(2))
        println("4")
        Espresso.onView(ViewMatchers.withId(R.id.show_back)).perform(clickXY(10, 10))
        println("5")

        Assert.assertThat(wasClick, IsEqual.equalTo(true))
        println("6")
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