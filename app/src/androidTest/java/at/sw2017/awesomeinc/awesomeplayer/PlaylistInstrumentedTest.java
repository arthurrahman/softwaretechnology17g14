package at.sw2017.awesomeinc.awesomeplayer;

import android.app.Activity;
import android.content.Context;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PlaylistInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    public void navigateToPlaylists() {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Playlists")).perform(click());
    }

    @Test
    public void test_addition_isCorrect() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();
        assertEquals(4, 2 + 2);

    }

    @Test
    public void test_create_new_playlist() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();

        assert(true);
    }

    @Test
    public void test_save_all_playlists() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();
        assert(true);
    }

    @Test
    public void test_load_playlists() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();

        test_save_all_playlists();
        assert(true);
    }
}