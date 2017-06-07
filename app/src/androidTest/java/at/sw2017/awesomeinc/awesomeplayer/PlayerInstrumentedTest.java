package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.Tap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

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

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_next_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));

        onView(withId(R.id.bt_next)).perform(click());

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_test_boundaries_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));

        onView(withId(R.id.bt_prev)).perform(click());
        Thread.sleep(500);
        onView(withId(R.id.bt_next)).perform(click());
        Thread.sleep(500);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_fast_fw_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));

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

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));
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

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));
        onView(withId(R.id.bt_play)).perform(click());

        Thread.sleep(1000);
        Assert.assertFalse(Player.is_playing());
    }

    @Test
    public void test_player_pre_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(500);
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));
        onView(withId(R.id.bt_prev)).perform(click());

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_scratch() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        Thread.sleep(500);
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));
        onView(withId(R.id.seekBar)).perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER));

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_back_new_song() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));

        onView(withId(R.id.player)).perform(pressBack());
        Thread.sleep(1000);
        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_set_rating_three_star() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());
        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));
        Thread.sleep(1000);
        onView(withId(R.id.rating)).perform(click());
        Thread.sleep(1000);
        Assert.assertEquals(3, Database.currentSong().getRating());

    }

    @Test
    public void test_repeat() throws Exception{
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());
        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));
        Thread.sleep(1000);
        onView(withId(R.id.repeat)).perform(click());
        Thread.sleep(1000);
        Assert.assertTrue(Player.is_looping());

    }


    @Test
    public void test_player_auto_next_on_end_of_first() throws Exception {
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));


        onView(withId(R.id.seekBar)).perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER_RIGHT, Press.FINGER));

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }

    @Test
    public void test_player_auto_next_on_end_of_last() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        XmlSongList xml = new XmlSongList("Songs", context);

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(xml.getAllSongs().size() - 1,  click()));


        onView(withId(R.id.seekBar)).perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER_RIGHT, Press.FINGER));

        Thread.sleep(1000);
        Assert.assertTrue(Player.is_playing());
    }
}
