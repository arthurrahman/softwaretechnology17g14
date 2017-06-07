package at.sw2017.awesomeinc.awesomeplayer;

import org.junit.Test;

import static android.test.MoreAsserts.assertNotEqual;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class AlbumViewUnitTest {

    @Test
    public void test_addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_createDataAdapter() throws Exception {
        Album album = new Album();
        assertNotEqual(null, album);
        AlbumListAdapter adapter = new AlbumListAdapter(album);
        assertNotEqual(null, adapter);
    }

    @Test
    public void test_addAlbumToList() throws Exception {
        Album album = new Album();
        assertNotEqual(null, album);
        AlbumListAdapter adapter = new AlbumListAdapter(album);
        assertNotEqual(null, adapter);
        adapter.addAlbum("Hallo Welt!");
        assertTrue(adapter.getItemCount() >= 1);
    }

    @Test
    public void test_addAlbumToListExt() throws Exception {
        Album album = new Album();
        assertNotEqual(null, album);
        AlbumListAdapter adapter = new AlbumListAdapter(album);
        assertNotEqual(null, adapter);
        adapter.addAlbum("Hallo Welt!");
        assertTrue(adapter.getAlbumList().contains("Hallo Welt!"));
    }
}
