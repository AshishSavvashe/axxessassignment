package demo.com.axxessasignment


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import androidx.core.util.Preconditions.checkNotNull
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import demo.com.axxessasignment.view.MainActivity
import demo.com.axxessasignment.view.ResultFragment


class RecycleViewTest {


    @Rule
    @JvmField
    var activityRule = ActivityTestRule(
        MainActivity::class.java)

    /*Check Fragment Launching and View*/
    @Test
    fun testFragmentInit() {
        activityRule.activity.runOnUiThread { val resultFragment = startVoiceFragment() }
        Espresso.onView(withId(R.id.rvImageData)).check(matches(isDisplayed()))
    }

    private fun startVoiceFragment(): ResultFragment {
        val activity = activityRule.activity as MainActivity
        val transaction = activity.supportFragmentManager.beginTransaction()
        val resultFragment = ResultFragment()
        transaction.add(resultFragment, "resultFragment")
        transaction.commitAllowingStateLoss()
        return resultFragment
    }

    /* Check Recycle View Availability*/
    @Test
    fun testSampleRecyclerVisible() {
        Espresso.onView(withId(R.id.rvImageData))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.activity.window.decorView)
                )
            )
            .check(matches(isDisplayed()))
    }

    /*To Test Recycle View Item Click*/
    @Test
    fun testCaseForRecyclerClick() {
        Espresso.onView(withId(R.id.rvImageData))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.activity.window.decorView)
                )
            )
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    /*To Test Recycele View Scrolling*/
    @Test
    fun testCaseForRecyclerScroll() {

        // Get total item of RecyclerView
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.rvImageData)
        val itemCount = recyclerView.adapter!!.itemCount

        // Scroll to end of page with position
        Espresso.onView(withId(R.id.rvImageData))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(activityRule.activity.window.decorView)
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    /*To Check A Particular Title Present or not in the Recycle View*/
    @Test
    fun testRecycleCheckItemFieldValue() {
        Espresso.onView(withId(R.id.rvImageData))
            .check(matches(atPosition(0, hasDescendant(withText("Beavers")))))
    }

   /* *//*To Test Item View Click*//*
    @Test
    fun testRecycleSpecificItemAction() {
        Espresso.onView(withId(R.id.rvImageData)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickChildViewWithId(R.id.)
            )
        )
    }*/

    private fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }

    private fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        checkNotNull(itemMatcher)
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position)
                    ?: // has no item on such position
                    return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }
    }


}
