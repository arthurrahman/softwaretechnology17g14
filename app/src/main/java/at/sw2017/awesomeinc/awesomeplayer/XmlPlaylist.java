package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import java.util.ArrayList;

/**
 * Created by julian on 30.05.17.
 */

public class XmlPlaylist extends XmlHandler {
    public XmlPlaylist(String name, Context context) {
        super(name, context);
    }

    public ArrayList<Song>  getAllSongs()  {
        ArrayList<Song> songList = new ArrayList<>();
        try {
            readStart();
            while (xmlReader.next() != XmlPullParser.END_DOCUMENT) {
                if (xmlReader.getEventType() != XmlPullParser.START_TAG)
                    continue;

                Song s = new Song();
                readOneObject("Song", s);

                songList.add(s);
            }
            readEnd();
        } catch (Exception e) {
            Log.d("XmlPlaylist", "Critical error during getAllSongs: " + e.getMessage());
        }
        return songList;

    }

    public void saveAllSongs(ArrayList<Song> songs)  {

        try {
            writeStart();
            for (Song s: songs) {
                writeOneObject("Song", s);
            }
            writeEnd();
        } catch (Exception e) {
            Log.d("XmlPlaylist", "Critical error during saveAllSongs: " + e.getMessage());
        }

    }
}
