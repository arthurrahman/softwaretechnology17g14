package at.sw2017.awesomeinc.awesomeplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.test.espresso.core.deps.guava.base.Function;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;

/**
 * Created by julian on 01.06.17.
 */

public class Database {
    private static Context context;
    private static ArrayList<Song> all_songs = new ArrayList<Song>();
    private static HashMap<String, Song> all_songs_by_uri = new HashMap<>();
    private static ArrayList<Song> visible_songs = new ArrayList<Song>();
    private static XmlSongList xmlSongs;
    private static int currentIndex;
    private static boolean isPlaying = false;
    private static String prev_searchstring = new String("");
    private static ArrayList<Integer> randomList;

    private static boolean initialized = false;

    public static void init(Context context) {
        if(context == null || initialized)
            return;

        Database.context = context;
        xmlSongs = new XmlSongList("Songs", Database.context);
        rebuildDatabase();
        resetVisibleSongs();
        currentIndex = 0;

        isPlaying = false;
        initialized = true;
    }

    private static void rebuildDatabase() {

        try {
            all_songs = xmlSongs.getAllSongs();
        } catch (Exception e) {
            Log.e("Songs", "CRITICAL ERROR: " + e.getMessage());
            return;
        }

        HashSet<String> uris = new HashSet<>();
        for(Song song : all_songs) {
            uris.add(song.getURI());
            all_songs_by_uri.put(song.getURI(), song);
        }

        // get Media Data --------------------------------------------------------------------------
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC" ; //+ MediaStore.Audio.Media.ALBUM + " ASC, " + MediaStore.Audio.Media.TRACK + " ASC";
        Cursor cur;
        String selection = null;
        String [] selection_args = null;

        selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0 AND " + MediaStore.Audio.Media.DURATION + "> 1000";

        cur = cr.query(uri, null, selection, selection_args, sortOrder);
        // -----------------------------------------------------------------------------------------

        //check for new and removed songs
        all_songs.ensureCapacity(cur.getCount());
        while (cur.moveToNext()) {
            int id_Data = cur.getColumnIndex(MediaStore.Audio.Media.DATA);
            String u = cur.getString(id_Data);
            if (uris.contains(u))
                uris.remove(u);
            else {
                Song s = new Song(cur);
                all_songs.add(s);
                all_songs_by_uri.put(s.getURI(), s);
            }
        }

        //at this point, all known uris got deleted, or unknown uris got created. All uris
        //which are still present are entries of removed files
        for(int i = 0; i < all_songs.size() && uris.size() > 0; i++) {
            Song song = all_songs.get(i);
            if (uris.contains(song.getURI())) {
                all_songs.remove(i);
                uris.remove(song.getURI());
                all_songs_by_uri.remove(song.getURI());
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

    public static Song getSongByURI(String uri) {
        return all_songs_by_uri.get(uri);
    }

    /***
     * Recreates the visble_songs to all_songs list and returns it
     * @return the full song list
     */
    public static ArrayList<Song> resetVisibleSongs() {
        //visible_songs = new ArrayList<>(all_songs);
        Log.d("Database", "Reset visible songs called");

        visible_songs.clear();
        for (int i = 0; i < all_songs.size(); i++) {
            visible_songs.add(all_songs.get(i));
        }
        return visible_songs;
    }

    /***
     * Copies the given songlist to set a new instance as the new visible_songs pointer
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
        XmlPlaylist xml = new XmlPlaylist(name, context);
        Log.d("Database", "Size of visible_songs: " + visible_songs.size());

        visible_songs = xml.getAllSongs();
        Log.d("Database", "Size of visible_songs now: " + visible_songs.size());
        return visible_songs;
    }


    public static ArrayList<Song> applyFilterToVisibleSongsAllAttr(String searchstring) {
        Log.d("Database", "Not implemented yet");

        if(searchstring.isEmpty())
        {
            visible_songs.clear();
            visible_songs.addAll(all_songs);
            return visible_songs;
        }

        // If the search string is longer, remove further cases
        if(searchstring.length() >= prev_searchstring.length())
        {

            for(int i = 0; i < visible_songs.size(); i++)
            {
                Song song = visible_songs.get(i);
                if(! (song.getTitle().toLowerCase().contains(searchstring.toLowerCase()) ||
                      song.getAlbum().toLowerCase().contains(searchstring.toLowerCase()) ||
                      song.getArtist().toLowerCase().contains(searchstring.toLowerCase())))
                {
                    visible_songs.remove(i);
                    i = 0;
                }
            }

            ListIterator iter = visible_songs.listIterator();
            while (iter.hasNext())
            {
                Song song = (Song)iter.next();
                if(! (song.getTitle().toLowerCase().contains(searchstring.toLowerCase()) ||
                        song.getAlbum().toLowerCase().contains(searchstring.toLowerCase()) ||
                        song.getArtist().toLowerCase().contains(searchstring.toLowerCase())))
                {
                    iter.remove();
                }
            }
        }
        else // If the search string is shorter, add cases that were deleted before
        {
            for(int i = 0; i < all_songs.size(); i++)
            {
                Song song = all_songs.get(i);
                if((song.getTitle().toLowerCase().contains(searchstring.toLowerCase()) ||
                      song.getAlbum().toLowerCase().contains(searchstring.toLowerCase()) ||
                      song.getArtist().toLowerCase().contains(searchstring.toLowerCase())))
                {
                    if(!visible_songs.contains(song))
                    {
                        visible_songs.add(song);
                    }

                }
            }
        }

        currentIndex = 0;
        prev_searchstring = searchstring;
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
                        return song.getTitle().toLowerCase();
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

        if(selection.isEmpty())
        {
            return applyFilterToVisibleSongsAllAttr(searchstring);
        }

        if(searchstring.isEmpty())
        {
            visible_songs.clear();
            visible_songs.addAll(all_songs);
            return visible_songs;
        }


        Function<Song, String> songfield = songfieldCreator(selection);
        searchstring = searchstring.toLowerCase();

        // If the search string is longer, remove further cases
        if(searchstring.length() >= prev_searchstring.length())
        {
            ListIterator iter = visible_songs.listIterator();
            while (iter.hasNext())
            {
                if (!songfield.apply((Song)iter.next()).contains(searchstring))
                {
                    iter.remove();
                }
            }
        }
        else // If the search string is shorter, add cases that were deleted before
        {
            for(int i = 0; i < all_songs.size(); i++)
            {
                Song song = all_songs.get(i);
                if(songfield.apply(song).contains(searchstring))
                {
                    if(!visible_songs.contains(song))
                    {
                        visible_songs.add(song);
                    }

                }
            }
        }

        prev_searchstring = searchstring;
        return visible_songs;
    }


    /***
     * As the database acts as a player helper, it handles a player pointer and thus can return the current pointed song
     * @return the current pointed song
     */
    public static Song currentSong() {
        if(currentIndex >= visible_songs.size())
            currentIndex = 0;

        if(currentIndex >= visible_songs.size())
            return null;

        return visible_songs.get(currentIndex);
    }

    /***
     * Increments the current song pointer and returns the song after the current, or if at end of list, the first one
     * @return the next Song after the current or the firstOne, if at the end of the list
     */
    public static Song nextSong() {
        currentIndex++;
        if(currentIndex >= visible_songs.size())
            currentIndex = 0;
        return visible_songs.get(currentIndex);
    }

    public static Song nextSongInformation() {
        if (currentIndex != all_songs.size())
        {
            if (currentIndex + 1 >= visible_songs.size())
                return visible_songs.get(0);
            else
                return visible_songs.get(currentIndex + 1);
        }

        return null;
    }

    /***
     * Decrements the current song pointer and returns the song before the current, or if at beginning of list, the last one
     * @return the previous song before the current or the last one, if at the beginning of the list
     */
    public static Song previousSong() {
        currentIndex--;
        if(currentIndex <= 0)
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

    public static void updateSongForRating()
    {
        int pos = all_songs.indexOf(currentSong());
        all_songs.get(pos).setRating(currentSong().getRating());
        saveDatabase();
    }
    
    public static void saveDatabase() {
        xmlSongs.SaveAllSongs(all_songs);
    }

    public static ArrayList<String> getAllAlbumNames()
    {
        ArrayList<String> album_names = new ArrayList<String>();
        for (int i = 0; i < all_songs.size(); i++)
        {
            if (!album_names.contains(all_songs.get(i).getAlbum()))
                album_names.add(all_songs.get(i).getAlbum());
        }
        return album_names;
    }
}
