package at.sw2017.awesomeinc.awesomeplayer;

import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;

/**
 * Created by julian on 19.04.17.
 */

public class Song {
    private String id;
    private String title;
    private String artist;
    private String album;
    private String display_name;
    private String duration;
    private String uri;
    private boolean isPlayable;


    public Song(String trackName) {
        this.id = "1";
        this.isPlayable = false;
        this.title = trackName;
        this.display_name = trackName;
        this.duration = "over 9000"; //TODO: Change me

    }

    public Song(Cursor cur) {
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
    }


    public boolean isPlayable() {
        return isPlayable;
    }

    public String getAlbum() {
        return album;
    }

    public String getURI() {
        return uri;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setPlayable(boolean playable) {
        isPlayable = playable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setURI(String uri) {
        this.uri = uri;
    }
}

