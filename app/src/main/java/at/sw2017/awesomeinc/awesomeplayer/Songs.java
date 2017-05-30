package at.sw2017.awesomeinc.awesomeplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by rahman on 19.04.2017.
 */

public class Songs extends Fragment {
    private RecyclerView lst_tracklist;
    private final XmlSongList xmlSongs;
    private ArrayList<Song> songs;
    private Activity activity;
    public Songs() {
        songs = new ArrayList<Song>();
        xmlSongs = new XmlSongList("Songs", null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.songs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.xmlSongs.setContext(view.getContext());

        lst_tracklist = (RecyclerView) view.findViewById(R.id.lst_tracklist);
        lst_tracklist.setNestedScrollingEnabled(false);
        lst_tracklist.setLayoutManager(new LinearLayoutManager(this.getContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {

                songs = xmlSongs.getAllSongs();

                HashSet<String> uris = new HashSet<String>();
                for(Song song : songs) {
                    uris.add(song.getURI());
                }

                // get Media Data --------------------------------------------------------------------------
                ContentResolver cr = getActivity().getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0 AND " + MediaStore.Audio.Media.DURATION + "> 1000";
                String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC" ; //+ MediaStore.Audio.Media.ALBUM + " ASC, " + MediaStore.Audio.Media.TRACK + " ASC";
                final Cursor cur = cr.query(uri, null, selection, null, null);
                // -----------------------------------------------------------------------------------------

                //check for new and removed songs
                songs.ensureCapacity(cur.getCount());
                while (cur.moveToNext()) {
                    int id_Data = cur.getColumnIndex(MediaStore.Audio.Media.DATA);
                    String u = cur.getString(id_Data);
                    if (uris.contains(u))
                        uris.remove(u);
                    else
                        songs.add(new Song(cur));
                }

                //at this point, all known uris got deleted, or unknown uris got created. All uris
                //which are still present are entries of removed files
                for(int i = 0; i < songs.size() && uris.size() > 0; i++) {
                    Song song = songs.get(i);
                    if (uris.contains(song.getURI())) {
                        songs.remove(i);
                        uris.remove(song.getURI());
                    }
                }

                xmlSongs.SaveAllSongs(songs);

                final MusicListAdapter da = new MusicListAdapter(songs);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lst_tracklist.setAdapter(da);
                        lst_tracklist.setId(R.id.lst_tracklist);
                    }
                });

            }
        }).start();

        getActivity().setTitle("Songs");
    }
}
