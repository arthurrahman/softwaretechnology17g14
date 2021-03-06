package at.sw2017.awesomeinc.awesomeplayer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PlaylistUnitTest {
    @Test
    public void test_Playlist_Title() throws Exception {
        Playlist playlist = new Playlist("test title");
        playlist.setTitle("changed title");
        assertEquals("changed title", playlist.getTitle());
    }

    @Test
    public void test_Playlist_Title_Card_View() throws Exception {
        Playlist playlist = new Playlist("test title");
        playlist.setTitle("this title is more than 25 char long!");
        assertEquals("this title is more than 2..", playlist.getTitleForCardView());
    }

    @Test
    public void test_Playlist_addSong() throws Exception {
        Playlist playlist = new Playlist("test title");

        Song s1 = new Song();
        Song s2 = new Song();

        playlist.addSong(s1);
        assertEquals(1, playlist.getSongs().size());

        playlist.addSong(s2);
        assertEquals(2, playlist.getSongs().size());
    }

    @Test
    public void test_Playlist_removeSong() throws Exception {
        Playlist playlist = new Playlist("test title");

        Song s1 = new Song();
        Song s2 = new Song();

        playlist.addSong(s1);
        playlist.addSong(s2);
        assertEquals(2, playlist.getSongs().size());

        playlist.removeSong(s2);
        assertEquals(1, playlist.getSongs().size());
        assertEquals(s1, playlist.getSongs().get(0));

    }

    @Test
    public void test_Playlist_Duration() throws Exception {
        Playlist playlist = new Playlist("test title");
        assertEquals("0:00", playlist.getDuration());
    }
}