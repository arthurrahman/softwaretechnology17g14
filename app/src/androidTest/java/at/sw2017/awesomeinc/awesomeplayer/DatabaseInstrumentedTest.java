package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by julian on 02.06.17.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void test_addition_isCorrect() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        assertEquals(4, 2 + 2);

    }

    @Test
    public void test_database_get_all_songs() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();

        Database.init(context);

        assertTrue("one song in database", Database.getAllSongs().size() > 0);
    }

    @Test
    public void test_rebuildDatabase() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();

        Database.init(context);
        // Call a second time to rebuild database
        Database.init(context);

        assertTrue("one song in database", Database.getAllSongs().size() > 0);
    }


    @Test
    public void test_get_next_song() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Database.init(context);

        Song s1 = Database.currentSong();
        Song s2 =  Database.nextSong();
        assertTrue("Next song is not current song", s2 != s1);
        s1 = Database.currentSong();

        assertEquals(s2 ,s1);

    }

    @Test
    public void test_get_previous_song() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Database.init(context);
        Database.resetVisibleSongs();

        Song s1 = Database.currentSong();
        Song s2 =  Database.previousSong();
        assertTrue("Next song is not current song", s2 != s1);
        s1 = Database.currentSong();

        assertEquals(s2 ,s1);

    }

    @Test
    public void test_rating() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Database.init(context);
        Database.resetVisibleSongs();

        ArrayList<Song> list = new ArrayList<>();

        list.add(new Song());
        list.add(new Song());
        list.add(new Song());
        list.add(new Song());

        list.get(1).setRating(3);
        list.get(2).setRating(2);

        Database.setVisibleSongs(list);
        Database.setCurrentSong(1);
        assertEquals(3, Database.currentSong().getRating());
        Database.setCurrentSong(list.get(2));
        assertEquals(2, list.get(2).getRating());

        Database.resetVisibleSongs();
        Database.setCurrentSong(0);
        Database.currentSong().setRating(4);
        Database.updateSongForRating();

        Database.resetVisibleSongs();
        assertEquals(4, Database.currentSong().getRating());
    }

    @Test
    public void test_toggle_play() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Database.init(context);
        Database.resetVisibleSongs();

        Database.setIsPlaying();
        assertEquals(true, Database.isPlaying());
        Database.setIsNotPlaying();
        assertEquals(false, Database.isPlaying());
        Database.togglePlaying();
        assertEquals(true, Database.isPlaying());
    }

    @Test
    public void test_template() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Database.init(context);
    }


    @Test
    public void test_filtering() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Database.init(context);
        Database.resetVisibleSongs();

        int initSize = Database.getVisibleSongs().size();

        Database.applyFilterToVisibleSongsAllAttr("A");
        int newSize = Database.getVisibleSongs().size();
        assertTrue("Lower amount of songs now", newSize <= initSize);
        initSize = newSize;

        Database.applyFilterToVisibleSongsAllAttr("b");
        newSize = Database.getVisibleSongs().size();
        assertTrue("Lower amount of songs now", newSize <= initSize);
        initSize = newSize;

        Database.applyFilterToVisibleSongsByAttr("c", "A");
        newSize = Database.getVisibleSongs().size();
        assertTrue("Lower amount of songs now", newSize <= initSize);

    }

    @Test
    public void test_filteringCases() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Database.init(context);

        int initSize = Database.getVisibleSongs().size();

        Database.applyFilterToVisibleSongsByAttr("A", "A");
        int newSize = Database.getVisibleSongs().size();
        assertTrue("Lower amount of songs now", newSize <= initSize);

        Database.applyFilterToVisibleSongsByAttr("A", "B");
        newSize = Database.getVisibleSongs().size();
        assertTrue("Lower amount of songs now", newSize <= initSize);

        Database.applyFilterToVisibleSongsByAttr("A", "C");
        newSize = Database.getVisibleSongs().size();
        assertTrue("Lower amount of songs now", newSize <= initSize);

        Database.applyFilterToVisibleSongsByAttr("A", "D");
        newSize = Database.getVisibleSongs().size();
        assertTrue("Lower amount of songs now", newSize <= initSize);
    }

    @Test
    public void test_getSongsPlaylist() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Database.init(context);

        int initSize = Database.getVisibleSongs().size();
        int databaseSize = Database.getSongsOfPlaylist("Not implemented yet!").size();
    }

    @Test
    public void test_filteringEmptyText() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Database.init(context);

        int initSize = Database.getVisibleSongs().size();

        Database.applyFilterToVisibleSongsAllAttr("");
        int newSize = Database.getVisibleSongs().size();
    }

}