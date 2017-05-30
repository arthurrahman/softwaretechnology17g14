package at.sw2017.awesomeinc.awesomeplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created on 26.05.2017.
 */

public class Playlists extends Fragment {
    private RecyclerView vw_playlist;
    private final XmlPlaylists xmlPlaylists;
    private final ArrayList<Playlist> playlists;

    public Playlists() {
        xmlPlaylists = new XmlPlaylists("Playlists", null);
        playlists = new ArrayList<>();
    }

    public Playlist newPlaylist(String title) {
        Playlist p = new Playlist(title);
        playlists.add(p);
        try {
            xmlPlaylists.saveAllPlaylists(playlists);
        } catch (Exception e) {
            Log.e("Playlists", "Critical internal error happened: " + e.getMessage());
            return null;
        }

        return p;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        xmlPlaylists.setContext(view.getContext());

        vw_playlist = (RecyclerView) view.findViewById(R.id.playlist);
        vw_playlist.setNestedScrollingEnabled(false);
        vw_playlist.setLayoutManager(new LinearLayoutManager(this.getContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                // ------------------------------------------------------
                // Load XML
                List<String> playlistNames = new ArrayList<String>();
                try {
                    playlistNames = xmlPlaylists.getAllPlaylistNames();
                } catch (Exception e) {
                    Log.e("Playlists", "Critical internal error happened: " + e.getMessage());
                    return;
                }

                for(String title: playlistNames) {
                    try {
                        playlists.add(xmlPlaylists.getPlaylist(title));
                    } catch (Exception e) {
                        Log.e("Playlists", "Critical internal error happened: " + e.getMessage());
                        return;
                    }
                }

                // ------------------------------------------------------

                final PlaylistAdapter pl = new PlaylistAdapter(playlists);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vw_playlist.setAdapter(pl);
                        vw_playlist.setId(R.id.playlist);
                    }
                });
            }
        }).start();

        getActivity().setTitle("Playlists");
    }
}
