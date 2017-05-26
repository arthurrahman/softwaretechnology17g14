package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class XmlInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test_addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_write_one_song() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        XmlSongList xml = new XmlSongList("Songs", context);
        ArrayList<Song> al = new ArrayList<>();
        Song s = new Song("testsong");
        s.setURI("myCoolTestUri");

        al.add(s);

        xml.SaveAllSongs(al);

        File f = new File(mainActivityActivityTestRule.getActivity().getExternalFilesDir(null), "Songs.xml");

        assertEquals(true, f.exists());
    }

    @Test
    public void test_read_one_song() throws Exception {
        test_write_one_song();

        XmlSongList xml = new XmlSongList("Songs", mainActivityActivityTestRule.getActivity().getApplicationContext());
        ArrayList<Song> al = xml.getAllSongs();
        assertEquals(1, al.size());
        assertEquals("myCoolTestUri",al.get(0).getURI());
    }
}
