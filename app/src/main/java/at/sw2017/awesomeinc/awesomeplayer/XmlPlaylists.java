package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by julian on 30.05.17.
 */

public class XmlPlaylists extends XmlHandler {
    private HashMap<String, Playlist> playlists = null;

    public XmlPlaylists(String name, Context context) {
        super(name, context);
        playlists = new HashMap<>();
        if(context != null)
            rebuildMap();
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        rebuildMap();
    }

    private void rebuildMap() {
        playlists = new HashMap<>();
        try {
            readStart();
            while (xmlReader.next() != XmlPullParser.END_DOCUMENT) {
                if (xmlReader.getEventType() != XmlPullParser.START_TAG)
                    continue;
                String title = readTag();
                Playlist p = new Playlist(title);
                p.loadPlaylist(context);
                playlists.put(title, p);
            }
            readEnd();
        } catch (Exception e) {
            Log.e("XmlPlaylists", "Critical error during rebuildMap: " + e.getMessage());
        }

    }

    public Playlist newPlaylist(String title) {
        Playlist p = getPlaylist(title);

        if(p != null)
            return p;

        p = new Playlist(title);
        playlists.put(title, p);

        saveAllPlaylists();
        return p;
    }


    public void saveAllPlaylists() {
        try {
            writeStart();
            for (Playlist p: playlists.values()) {
                writeTag("Title", p.getTitle());
                p.savePlaylist(context);
            }
            writeEnd();
        } catch (Exception e) {
            Log.e("XmlPlaylists", "Critical error during newPlaylist: " + e.getMessage());
            return;
        }
    }

    public ArrayList<String> getAllPlaylistNames()  {
        return new ArrayList<>(playlists.keySet());
    }

    public ArrayList<Playlist> getAllPlaylists()  {
        return new ArrayList<>(playlists.values());
    }

    public Playlist getPlaylist(String title)   {
        if(playlists.containsKey(title))
            return playlists.get(title);
        else
            return null;
    }

    public void deleteAllPlaylists(){
        playlists = new HashMap<>();
        saveAllPlaylists();
    }
}
