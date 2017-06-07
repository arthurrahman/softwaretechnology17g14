package at.sw2017.awesomeinc.awesomeplayer;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;



/**
 * Created by rahman on 19.04.2017.
 */

public class Songs extends Fragment {
    private RecyclerView lst_tracklist;
    //private String search_text = null;
    private final XmlSongList xmlSongs;
    private ArrayList<Song> songs;
    //private Activity activity;

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
        //search_text = this.getArguments().getString("search_item");

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                final MusicListAdapter adapter = new MusicListAdapter();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lst_tracklist.setAdapter(adapter);
                        lst_tracklist.setId(R.id.lst_album);
                    }
                });
            }
        }).start();*/

        final MusicListAdapter da = new MusicListAdapter();

        lst_tracklist.setAdapter(da);
        lst_tracklist.setId(R.id.lst_tracklist);

        getActivity().setTitle("Songs");
    }

    public RecyclerView getRecyclerView(){return this.lst_tracklist;}

}
