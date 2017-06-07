package at.sw2017.awesomeinc.awesomeplayer;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        try {
            onView(withId(R.id.player)).check(matches(isDisplayed()));
            onView(withId(R.id.player)).perform(pressBack());
        } catch(Exception e) {
            //expected - ignore it
        }

        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Playlists")).perform(click());
    }

    public void navigateToPlayer() {
        try {
            onView(withId(R.id.player)).check(matches(isDisplayed()));
            onView(withId(R.id.player)).perform(pressBack());
        } catch(Exception e) {
            //expected - ignore it
        }
        onView(withId(R.id.content_main)).perform(new GeneralSwipeAction(Swipe.FAST, GeneralLocation.CENTER_LEFT, GeneralLocation.CENTER, Press.FINGER));
        onView(withText("Songs")).perform(click());

        onView(withId(R.id.content_main)).perform(HelperFunction.waitId(withId(R.id.album_pic), TimeUnit.MINUTES.toMillis(5)));

        onView(withId(R.id.lst_tracklist)).perform(actionOnItemAtPosition(0,  click()));
        onView(withId(R.id.bt_play)).perform(click());
    }

    @Test
    public void test_addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

    }

    @Test
    public void test_Playlist_savePlaylist() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();


        XmlPlaylist xml = new XmlPlaylist("test title", context);
        File f = new File(activity.getExternalFilesDir(null), xml.getFilename());
        if(f.exists())
            assertTrue(f.delete());

        Playlist playlist = new Playlist("test title");

        Song s1 = new Song("song1");
        Song s2 = new Song("song2");


        playlist.addSong(s1);
        playlist.addSong(s2);
        assertEquals(2, playlist.getSongs().size());

        playlist.savePlaylist(context);

        f = new File(activity.getExternalFilesDir(null), xml.getFilename());
        assertTrue(f.exists());
    }


    @Test
    public void test_create_new_playlist() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();

        XmlPlaylists xml = new XmlPlaylists("Playlists", context);
        xml.deleteAllPlaylists();

        navigateToPlaylists();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Create new Playlist")).perform(click());
        onView(withId(R.id.newPlaylistName)).perform(replaceText("Test Playlist"));
        onView(withId(R.id.savePlaylist)).perform(click());

        navigateToPlayer();
        navigateToPlaylists();

        xml = new XmlPlaylists("Playlists", context);

        assertEquals(1, xml.getAllPlaylists().size());
        try {
            onView(withText("Test Playlist")).check(matches(isDisplayed()));
            assertTrue("Playlist created", true);
        } catch (Exception e) {
            assertTrue("Playlist was not created", false);
        }
    }

    @Test
    public void test_create_same_playlist_twice() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();

        XmlPlaylists xml = new XmlPlaylists("Playlists", context);
        xml.deleteAllPlaylists();

        navigateToPlaylists();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Create new Playlist")).perform(click());
        onView(withId(R.id.newPlaylistName)).perform(replaceText("Test Playlist"));
        onView(withId(R.id.savePlaylist)).perform(click());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Create new Playlist")).perform(click());
        onView(withId(R.id.newPlaylistName)).perform(replaceText("Test Playlist"));
        onView(withId(R.id.savePlaylist)).perform(click());

        navigateToPlayer();
        navigateToPlaylists();

        xml = new XmlPlaylists("Playlists", context);
        assertEquals(1, xml.getAllPlaylists().size());
    }

    @Test
    public void test_create_playlists_add_songs_remove_all_playlists() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();

        XmlPlaylists xml = new XmlPlaylists("Playlists", context);
        xml.deleteAllPlaylists();

        navigateToPlaylists();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Create new Playlist")).perform(click());
        onView(withId(R.id.newPlaylistName)).perform(replaceText("Test Playlist"));
        onView(withId(R.id.savePlaylist)).perform(click());

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Create new Playlist")).perform(click());
        onView(withId(R.id.newPlaylistName)).perform(replaceText("Test Playlist2"));
        onView(withId(R.id.savePlaylist)).perform(click());

        navigateToPlayer();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Test Playlist")).perform(click());

        navigateToPlaylists();
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Delete all Playlists")).perform(click());


        xml = new XmlPlaylists("Playlists", context);
        assertEquals(0, xml.getAllPlaylists().size());
    }
}