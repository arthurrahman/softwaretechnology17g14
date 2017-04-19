package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ListViewInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Test
    public void test_useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.awesomeinc.awesomeplayer", appContext.getPackageName());
    }

    @Test
    public void test_ListViewExists() throws Exception {
        assertNotEquals(null, onView(withId(R.id.lst_tracklist)) );
    }

    @Test
    public void test_ListViewScroll() throws Exception {
        onView(withId(R.id.lst_tracklist)).perform(ViewActions.scrollTo()).check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
    public void test_ListViewSongNameClick() throws Exception {
        onView(withId(R.id.lst_tracklist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

}
