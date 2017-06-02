package at.sw2017.awesomeinc.awesomeplayer;

/**
 * Created by julian on 19.04.17.
 */

import android.database.Cursor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.test.MoreAsserts.assertNotEqual;
import static junit.framework.Assert.assertEquals;

public class ListViewUnitTest {

    @Test
    public void test_addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test_createDataAdapter() throws Exception {
        Song s = new Song();
        ArrayList<Song> song_list = new ArrayList<>();
        song_list.add(s);
        Database.setVisibleSongs(song_list);
        MusicListAdapter da = new MusicListAdapter();
        assertNotEqual(null, da);

    }
}
