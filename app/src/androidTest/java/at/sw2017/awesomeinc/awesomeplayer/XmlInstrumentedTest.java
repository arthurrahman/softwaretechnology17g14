package at.sw2017.awesomeinc.awesomeplayer;

import android.app.Activity;
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
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();
        assertEquals(4, 2 + 2);

    }

    @Test
    public void test_write_one_song() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();
        XmlSongList xml = new XmlSongList("Test_read_write", context);
        ArrayList<Song> al = new ArrayList<>();
        Song s = new Song("testsong");
        s.setURI("myCoolTestUri");

        al.add(s);

        xml.SaveAllSongs(al);

        File f = new File(activity.getExternalFilesDir(null), xml.getFilename());

        assertEquals(true, f.exists());
    }

    @Test
    public void test_read_one_song() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();
        test_write_one_song();

        XmlSongList xml = new XmlSongList("Test_read_write", context);
        ArrayList<Song> al = xml.getAllSongs();
        assertEquals(1, al.size());
        assertEquals("myCoolTestUri",al.get(0).getURI());
    }

    @Test
    public void test_instantiate_without_context() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();

        XmlSongList xml = new XmlSongList("Test_read_write", null);
        assertEquals(null, xml.getContext());

        xml.setContext(context);
        assertEquals(context, xml.getContext());
    }

    @Test
    public void test_write_empty_list() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();

        XmlSongList xml = new XmlSongList("test_write_empty", null);
        xml.setContext(context);
        xml.SaveAllSongs(new ArrayList<Song>());

        File f = new File(activity.getExternalFilesDir(null), xml.getFilename());
        assertEquals(true, f.exists());

        ArrayList<Song> list = xml.getAllSongs();
        assertEquals(0, list.size());
    }

    @Test
    public void test_read_non_existing_file() throws Exception {
        Context context = mainActivityActivityTestRule.getActivity().getApplicationContext();
        Activity activity = mainActivityActivityTestRule.getActivity();

        String name = "test_non_existend";
        XmlSongList xml = new XmlSongList(name, context);

        File f = new File(context.getExternalFilesDir(null), xml.getFilename());

        if(f.exists())
            f.delete();

        assertEquals(false, f.exists());


        ArrayList<Song> list = xml.getAllSongs();
        f = new File(context.getExternalFilesDir(null), xml.getFilename());
        assertEquals(true, f.exists());
        assertEquals(0, list.size());
    }


}
