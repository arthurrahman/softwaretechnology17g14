package at.sw2017.awesomeinc.awesomeplayer;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

/**
 * Created on 26.05.17.
 */

public class Playlist implements Serializable{
    private int id;
    private String title;
    private int duration;
    // TODO: Find suitable songs link

    public Playlist(String title, int id) {
        this.id = id;
        this.title = title;
        recalcDuration();
    }

    /*public Playlist(Cursor cur) {
        int id_Id, id_Artist, id_Title, id_Data, id_Display_Name, id_Duration, id_Album;

        id_Id = cur.getColumnIndex(MediaStore.Audio.Media._ID);
        id_Artist = cur.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        id_Title = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
        id_Data = cur.getColumnIndex(MediaStore.Audio.Media.DATA);
        id_Display_Name = cur.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        id_Duration = cur.getColumnIndex(MediaStore.Audio.Media.DURATION);
        id_Album = cur.getColumnIndex(MediaStore.Audio.Media.ALBUM);

        this.id = cur.getString(id_Id);
        this.title = cur.getString(id_Title);
        this.artist = cur.getString(id_Artist);
        this.display_name = cur.getString(id_Display_Name);
        this.duration = cur.getString(id_Duration);
        this.uri = cur.getString(id_Data);
        this.album = cur.getString(id_Album);


        this.isPlayable = true;
        //this.pointer = cur;
    }*/

    private void recalcDuration() {
        // TODO: calculate whole duration on list changes
        this.duration = 0;
    }

    public int getId() {
        return id;
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

        Long time = Long.valueOf(duration);

        long seconds = time/1000;
        long minutes = seconds/60;
        seconds = seconds % 60;
        String format_duration = String.valueOf(minutes)+":"+ String.valueOf(seconds);

        if(seconds <10) {
            format_duration = String.valueOf(minutes)+":0"+ String.valueOf(seconds);
        }

        return format_duration;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addSong(Song song) {
        // TODO: add song
    }

    public void removeSong(String id) {
        // TODO: remove song with id
    }

    public void loadPlaylist() {
        // TODO: load Playlist from XML file
    }

    public void savePlaylist() {
        // TODO: Save playlist to XML file
    }
}

