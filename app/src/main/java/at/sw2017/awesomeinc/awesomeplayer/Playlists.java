package at.sw2017.awesomeinc.awesomeplayer;

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
import java.util.List;


/**
 * Created on 26.05.2017.
 */

public class Playlists extends Fragment {
    private RecyclerView playlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playlist = (RecyclerView) view.findViewById(R.id.playlist);
        playlist.setNestedScrollingEnabled(false);
        playlist.setLayoutManager(new LinearLayoutManager(this.getContext()));

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO: Load playlists from file ...
                // ------------------------------------------------------
                // Just for testing, need to be replaced by XML File
                List<String> pLists = new ArrayList<String>();
                pLists.add("Taylor Swift Best Of");
                pLists.add("Hip Hop");
                pLists.add("Charts");
                pLists.add("Party Tracks");
                // ------------------------------------------------------

                final PlaylistAdapter pl = new PlaylistAdapter(pLists);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playlist.setAdapter(pl);
                        playlist.setId(R.id.playlist);
                    }
                });
            }
        }).start();

        getActivity().setTitle("Playlists");
    }
}
