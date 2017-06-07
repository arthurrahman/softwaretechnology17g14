package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AlbumViewInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void test_useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.awesomeinc.awesomeplayer", appContext.getPackageName());
    }

    @Test
    public void test_MenuAlbums() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Albums")).perform(click());
        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));
    }

    @Test
    public void test_AlbumSelection() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Albums")).perform(click());
        onView(withId(R.id.content_main)).perform(waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        Thread.sleep(100);

        onView(withId(R.id.lst_album)).perform(actionOnItemAtPosition(0,  click()));

        Thread.sleep(100);

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));

        Thread.sleep(100);

        Assert.assertTrue(Player.is_playing());
    }

    public static ViewAction waitId(final Matcher<View> viewMatcher, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return withId(R.id.content_main);
            }

            @Override
            public String getDescription() {
                return "wait for a specific view  during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }

}
