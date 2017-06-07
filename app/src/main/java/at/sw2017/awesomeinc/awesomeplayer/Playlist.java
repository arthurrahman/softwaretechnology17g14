package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created on 26.05.17.
 */

public class Playlist implements Serializable{
    private String title;
    private long duration;
    private final XmlPlaylist xmlPlaylist;
    private ArrayList<Song> songs;

    public Playlist(String title) {
        this.title = title;
        xmlPlaylist = new XmlPlaylist(this.title, null);
        songs = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getTitleForCardView() {
        if (title.length() > 25)
            return title.substring(0,25) + "..";
        return title;
    }

    public String getDuration() {

        if(duration == 0) {
            return "0:00";
        }

        Long time = duration;

        long seconds = time/1000;
        long minutes = seconds/60;
        seconds = seconds % 60;
        String format_duration = String.valueOf(minutes)+":"+ String.valueOf(seconds);

        if(seconds <10) {
            format_duration = String.valueOf(minutes)+":0"+ String.valueOf(seconds);
        }

        return format_duration;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void loadPlaylist(Context context) {
        if(context != null)
            xmlPlaylist.setContext(context);

        songs = xmlPlaylist.getAllSongs();
    }

    public void savePlaylist(Context context) {
        if (context != null)
            xmlPlaylist.setContext(context);

        xmlPlaylist.saveAllSongs(songs);
    }
}

