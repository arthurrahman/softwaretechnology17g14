package at.sw2017.awesomeinc.awesomeplayer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SongUnitTest {
    @Test
    public void test_addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_Song_Title() throws Exception {
        Song s = new Song("testTitle");
        s.setTitle("TitleTitle");
        assertEquals("TitleTitle", s.getTitle());
    }

    @Test
    public void test_Song_Title_Card_View() throws Exception {
        Song s = new Song("testTitle");
        s.setTitle("ThisIsALooongTitleForCardView");
        assertEquals("ThisIsALooongTitleForCard..", s.getTitleForCardView());
    }

    @Test
    public void test_Song_Album() throws Exception {
        Song s = new Song("testAlbum");
        s.setAlbum("albumTitle");
        assertEquals("albumTitle", s.getAlbum());
    }

    @Test
    public void test_Song_URI() throws Exception {
        Song s = new Song("testURI");
        s.setURI("URITitle");
        assertEquals("URITitle", s.getURI());
    }

    @Test
    public void test_Song_Artist() throws Exception {
        Song s = new Song("testURI");
        s.setArtist("URITitle");
        assertEquals("URITitle", s.getArtist());
    }

    @Test
    public void test_Song_Id() throws Exception {
        Song s = new Song("testId");
        s.setId("IdTitle");
        assertEquals("IdTitle", s.getId());
    }

    @Test
    public void test_Song_Display_name() throws Exception {
        Song s = new Song("testDisplay_name");
        s.setDisplay_name("Display_nameTitle");
        assertEquals("Display_nameTitle", s.getDisplay_name());
    }

    @Test
    public void test_Song_Duration() throws Exception {
        Song s = new Song("testDuration");
        s.setDuration("2:29");
        assertEquals("2:29", s.getDurationStringValue());
    }

    @Test
    public void test_Song_DurationEmpty() throws Exception {
        Song s = new Song("testDuration");
        s.setDuration("0:00");
        assertEquals("0:00", s.getDurationStringValue());
    }

    @Test
    public void test_Song_Playable() throws Exception {
        Song s = new Song("testPlayable");
        s.setPlayable(false);
        assertEquals(false, s.isPlayable());
        s.setPlayable(true);
        assertEquals(true, s.isPlayable());
    }

    @Test
    public void test_Rating() throws  Exception {
        Song s = new Song("testRating");
        s.setRating(0);
        assertEquals(0, s.getRating());
        s.setRating(5);
        assertEquals(5, s.getRating());
    }

    @Test
    public void test_Rating_Empty() throws Exception {
        Song s = new Song("testRatingEmpty");
        assertEquals(0, s.getRating());
    }
}