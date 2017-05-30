package at.sw2017.awesomeinc.awesomeplayer;

/**
 * Created by julian on 19.04.17.
 */

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
        List<String> lst = new ArrayList<>();
        lst.add("asdf1");
        lst.add("asdf2");
        MusicListAdapter da = new MusicListAdapter(lst);

        assertNotEqual(null, da);
        assertEquals(2, da.getItemCount() );

    }

    @Test
    public void test_invalidDataAdapter() throws Exception {
        MusicListAdapter da = new MusicListAdapter((ArrayList<Song>)null);

        assertNotEqual(null, da);
        assertEquals(0, da.getItemCount() );

    }

    @Test
    public void test_createDataAdapterWithList() throws Exception {
        ArrayList<String> lst = new ArrayList<>();
        lst.add("asdf1");
        lst.add("asdf2");
        MusicListAdapter da = new MusicListAdapter(lst);

        assertNotEqual(null, da);
        assertEquals(2, da.getItemCount() );
    }

 @Test
    public void test_createPlaylistDataAdapter() throws Exception {
        ArrayList<Playlist> lst = new ArrayList<>();
        lst.add(new Playlist("asdf1"));
        lst.add(new Playlist("asdf2"));
        PlaylistAdapter da = new PlaylistAdapter(lst);

        assertNotEqual(null, da);
        assertEquals(2, da.getItemCount() );

    }


 @Test
    public void test_invalidPlaylistDataAdapter() throws Exception {
        PlaylistAdapter da = new PlaylistAdapter(null);

        assertNotEqual(null, da);
        assertEquals(0, da.getItemCount() );

    }
}
