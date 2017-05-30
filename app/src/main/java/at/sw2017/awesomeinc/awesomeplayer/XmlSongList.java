package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by julian on 26.05.17.
 */

public class XmlSongList extends XmlHandler {
    public XmlSongList(String name, Context context) {
        super(name, context);
    }

    public ArrayList<Song> getAllSongs()  {
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
            Log.e("XmlSongList", "Critical error during rebuildMap: " + e.getMessage());
        }

            return songList;

    }

    public void SaveAllSongs(ArrayList<Song> songs)  {
        try {
            writeStart();
            for (Song s: songs) {
                writeOneObject("Song", s);
            }
            writeEnd();
        } catch (Exception e) {
            Log.e("XmlSongList", "Critical error during rebuildMap: " + e.getMessage());
        }

    }
}
