package at.sw2017.awesomeinc.awesomeplayer;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ramiro on 04.05.17.
 */
@RunWith(AndroidJUnit4.class)
public class PlayerInstrumentedTest {


    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    public ActivityTestRule<Player> playerActivity = new ActivityTestRule<Player>(Player.class);


    @Test
    public void test_useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("at.sw2017.awesomeinc.awesomeplayer", appContext.getPackageName());
    }

    @Test
    public void test_player_is_playing() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0, HelperFunction.clickChildViewWithId(R.id.title)));

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_next_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0, HelperFunction.clickChildViewWithId(R.id.title)));

        onView(withId(R.id.bt_next)).perform(click());

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_fast_fw_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0, HelperFunction.clickChildViewWithId(R.id.title)));

        Thread.sleep(1000);
        int startPosition = Player.getCurrentPosition();

        onView(withId(R.id.bt_fast_fw)).perform(click());

        Thread.sleep(1000);
        int endPosition = Player.getCurrentPosition();

        Assert.assertTrue(endPosition-startPosition>2500);
    }

    @Test
    public void test_player_rew_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0, HelperFunction.clickChildViewWithId(R.id.title)));
        Thread.sleep(1000);
        onView(withId(R.id.seekBar)).perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER));
        //Thread.sleep(1000);
        int startPosition = Player.getCurrentPosition();

        onView(withId(R.id.bt_rew)).perform(click());

        int endPosition = Player.getCurrentPosition();

        Assert.assertTrue((startPosition-endPosition > 2500) || (endPosition < 1000));
    }

    @Test
    public void test_player_pause() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0, HelperFunction.clickChildViewWithId(R.id.title)));
        onView(withId(R.id.bt_play)).perform(click());

        Thread.sleep(1000);
        Assert.assertFalse(Player.is_playing());
    }

    @Test
    public void test_player_pre_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0, HelperFunction.clickChildViewWithId(R.id.title)));
        onView(withId(R.id.bt_prev)).perform(click());

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_scratch() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0, HelperFunction.clickChildViewWithId(R.id.title)));
        onView(withId(R.id.seekBar)).perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER));

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_back_new_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0, HelperFunction.clickChildViewWithId(R.id.title)));

        onView(withId(R.id.player)).perform(pressBack());
        Thread.sleep(1000);
        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0, HelperFunction.clickChildViewWithId(R.id.title)));

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }


}
