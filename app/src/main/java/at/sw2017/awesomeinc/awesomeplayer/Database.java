package at.sw2017.awesomeinc.awesomeplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.test.espresso.core.deps.guava.base.Function;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by julian on 01.06.17.
 */

public class Database {
    private static Context context;
    private static ArrayList<Song> all_songs;
    private static ArrayList<Song> visible_songs;
    private static XmlSongList xmlSongs;
    private static int currentIndex;
    private static boolean isPlaying = false;

    private static boolean initialized = false;

    public static void init(Context context) {
        if(context == null || initialized)
            return;

        Database.context = context;
        xmlSongs = new XmlSongList("Songs", Database.context);
        rebuildDatabase();
        resetVisibleSongs();
        currentIndex = 0;

        initialized = true;
    }

    private static void rebuildDatabase() {

        try {
            all_songs = xmlSongs.getAllSongs();
        } catch (Exception e) {
            Log.e("Songs", "CRITICAL ERROR: " + e.getMessage());
            return;
        }

        HashSet<String> uris = new HashSet<String>();
        for(Song song : all_songs) {
            uris.add(song.getURI());
        }

        // get Media Data --------------------------------------------------------------------------
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC" ; //+ MediaStore.Audio.Media.ALBUM + " ASC, " + MediaStore.Audio.Media.TRACK + " ASC";
        Cursor cur;
        String selection = null;
        String [] selection_args = null;
//        if (search_text != null)
//        {
//            selection = MediaStore.Audio.Media.ARTIST + " LIKE ?";
//            selection_args = new String[]{"%" + search_text + "%"};
//
//        }
//        else {
            selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0 AND " + MediaStore.Audio.Media.DURATION + "> 1000";

//        }
        cur = cr.query(uri, null, selection, selection_args, sortOrder);
        // -----------------------------------------------------------------------------------------

        //check for new and removed songs
        all_songs.ensureCapacity(cur.getCount());
        while (cur.moveToNext()) {
            int id_Data = cur.getColumnIndex(MediaStore.Audio.Media.DATA);
            String u = cur.getString(id_Data);
            if (uris.contains(u))
                uris.remove(u);
            else
                all_songs.add(new Song(cur));
        }

        //at this point, all known uris got deleted, or unknown uris got created. All uris
        //which are still present are entries of removed files
        for(int i = 0; i < all_songs.size() && uris.size() > 0; i++) {
            Song song = all_songs.get(i);
            if (uris.contains(song.getURI())) {
                all_songs.remove(i);
                uris.remove(song.getURI());
            }
        }

        try {
            xmlSongs.SaveAllSongs(all_songs);
        } catch(Exception e){
            Log.e("Songs", "CRITICAL ERROR: " + e.getMessage());
            return;
        }

    }


    public static ArrayList<Song> getAllSongs() {
        return all_songs;
    }

    public static ArrayList<Song> getVisibleSongs() {
        return visible_songs;
    }


    /***
     * Recreates the visble_songs to all_songs list and returns it
     * @return the full song list
     */
    public static ArrayList<Song> resetVisibleSongs() {
        visible_songs = new ArrayList<>(all_songs);
        return visible_songs;
    }

    /***
     * Cppies the given songlist to set a new instance as the new visible_songs pointer
     * @param songs the songlist
     * @return the given song list
     */
    public static ArrayList<Song> setVisibleSongs(ArrayList<Song> songs) {
        visible_songs = new ArrayList<>(songs);
        return songs;
    }

    /***
     * Returns the list of songs of the given playlist and sets the visible_songs list to the playlist
     * @return the playlists song list
     */
    public static ArrayList<Song> getSongsOfPlaylist(String name) {
        Log.d("Database", "Not implemented yet");
        return all_songs;
    }

    public static ArrayList<Song> applyFilterToVisibleSongsAllAttr(String searchstring) {
        Log.d("Database", "Not implemented yet");
        if(searchstring.isEmpty())
            return visible_songs;

        for(int i = 0; i < visible_songs.size(); i++)
        {
            Song song = visible_songs.get(i);
            if(! (
                    song.getTitle().toLowerCase().contains(searchstring.toLowerCase()) ||
                    song.getAlbum().toLowerCase().contains(searchstring.toLowerCase()) ||
                    song.getArtist().toLowerCase().contains(searchstring.toLowerCase())
                )
            ){
                visible_songs.remove(i);
            }
        }
        currentIndex = 0;
        return visible_songs;
    }


    private static Function<Song, String> songfieldCreator(String selection) {
        switch(selection.substring(0, 1)) {
            case "A":
                return new Function<Song, String>() {
                    @Override
                    public String apply( Song song) {
                        return song.getTitle().toLowerCase();
                    }
                };
            case "B":
                return new Function<Song, String>() {
                    @Override
                    public String apply( Song song) {
                        return song.getAlbum().toLowerCase();
                    }
                };
            case "C":
                return new Function<Song, String>() {
                    @Override
                    public String apply( Song song) {
                        return song.getArtist().toLowerCase();
                    }
                };
            default:
                return new Function<Song, String>() {
                    @Override
                    public String apply( Song song) {
                        return "";
                    }
                };
        }
    }

    /***
     * removes all songs from the current visible list which do not meet the given filter.
     * @param searchstring The filter criteria which should be met (.contains(searchstring))
     * @param selection A = Title, B = Album, C = Artist, otherwise the list will get empty as nothing matches
     * @return the filtered visible_songs list
     */
    public static ArrayList<Song> applyFilterToVisibleSongsByAttr(String searchstring, String selection) {

        if(searchstring.isEmpty() || selection.isEmpty())
            return visible_songs;


        Function<Song, String> songfield = songfieldCreator(selection);
        searchstring = searchstring.toLowerCase();

        for(int i = 0; i < visible_songs.size(); i++)
        {
            Song song = visible_songs.get(i);
            if (! songfield.apply(song).contains(searchstring)) {
                visible_songs.remove(i);
            }
        }

        return visible_songs;
    }


    /***
     * As the database acts as a player helper, it handles a player pointer and thus can return the current pointed song
     * @return the current pointed song
     */
    public static Song currentSong() {
        return visible_songs.get(currentIndex);
    }

    /***
     * Increments the current song pointer and returns the song after the current, or if at end of list, the first one
     * @return the next Song after the current or the firstOne, if at the end of the list
     */
    public static Song nextSong() {
        currentIndex++;
        if(currentIndex == visible_songs.size())
            currentIndex = 0;
        return visible_songs.get(currentIndex);
    }

    /***
     * Decrements the current song pointer and returns the song before the current, or if at beginning of list, the last one
     * @return the previous song before the current or the last one, if at the beginning of the list
     */
    public static Song previousSong() {
        currentIndex--;
        if(currentIndex == 0)
            currentIndex = visible_songs.size() - 1;
        return visible_songs.get(currentIndex);
    }

    public static void setCurrentSong(Song song) {
        currentIndex = visible_songs.indexOf(song);
    }

    public static void setCurrentSong(int position) {
        currentIndex = position;
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

    public static void setIsPlaying() {
        isPlaying = true;
    }

    public static void setIsNotPlaying() {
        isPlaying = false;
    }

    public static void togglePlaying() {
        isPlaying = !isPlaying;
    }



}
