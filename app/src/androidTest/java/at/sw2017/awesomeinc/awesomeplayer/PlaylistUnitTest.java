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
    public void test_Playlist_Duration() throws Exception {
        Playlist playlist = new Playlist("test title");
        assertEquals("0:00", playlist.getDuration());
    }

    // TODO: more tests as more features arise

}