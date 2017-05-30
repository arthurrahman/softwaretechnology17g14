package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by julian on 30.05.17.
 */

public class XmlPlaylists extends XmlHandler {
    private ArrayList<String> names = null;

    public XmlPlaylists(String name, Context context) {
        super(name, context);
    }


    public void saveAllPlaylists(ArrayList<Playlist> playlists) throws IOException {

        writeStart();
        for (Playlist p: playlists) {
            writeTag("Title", p.getTitle());
            p.savePlaylist(context);
        }
        writeEnd();
    }

    public ArrayList<String> getAllPlaylistNames()  throws XmlPullParserException, IOException {
        if(names != null)
            return names;

        names = new ArrayList<>();

        readStart();
        while (xmlReader.next() != XmlPullParser.END_DOCUMENT) {
            if (xmlReader.getEventType() != XmlPullParser.START_TAG)
                continue;

            names.add(readTag());
        }
        readEnd();

        return names;

    }


    public Playlist getPlaylist(String title)  throws XmlPullParserException, IOException  {
        if(names == null)
            getAllPlaylistNames();

        Playlist p = new Playlist(title);
        p.loadPlaylist(context);

        return p;
    }
}
