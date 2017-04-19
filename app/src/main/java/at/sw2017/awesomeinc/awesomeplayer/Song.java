package at.sw2017.awesomeinc.awesomeplayer;

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

