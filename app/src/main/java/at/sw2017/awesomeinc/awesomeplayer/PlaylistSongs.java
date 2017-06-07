package at.sw2017.awesomeinc.awesomeplayer;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by rahman on 31.05.2017.
 */

public class PlaylistSongs extends Fragment {
    private RecyclerView lst_tracklist;
    private ArrayList<Song> songs;
    private Activity activity;
    private String plName = "";
    public PlaylistSongs() {
        songs = new ArrayList<Song>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.songs, container, false);
    }

    public void getPlaylist(String plName) {
        this.plName = plName;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        lst_tracklist = (RecyclerView) view.findViewById(R.id.lst_tracklist);
        lst_tracklist.setNestedScrollingEnabled(false);
        lst_tracklist.setLayoutManager(new LinearLayoutManager(view.getContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {

                songs = Database.getSongsOfPlaylist(plName);

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
