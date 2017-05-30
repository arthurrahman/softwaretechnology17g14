package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by julian on 30.05.17.
 */

public class XmlPlaylist extends XmlHandler {
    public XmlPlaylist(String name, Context context) {
        super(name, context);
    }

    public ArrayList<Song>  getAllSongs()  throws XmlPullParserException, IOException {
        ArrayList<Song> songList = new ArrayList<>();

        readStart();
        while (xmlReader.next() != XmlPullParser.END_DOCUMENT) {
            if (xmlReader.getEventType() != XmlPullParser.START_TAG)
                continue;

            Song s = new Song();
            readOneObject("Song", s);

            songList.add(s);
        }
        readEnd();

        return songList;

    }

    public void saveAllSongs(ArrayList<Song> songs) throws IOException {

        writeStart();
        for (Song s: songs) {
            writeOneObject("Song", s);
        }
        writeEnd();

    }
}