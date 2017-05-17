package at.sw2017.awesomeinc.awesomeplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by rahman on 19.04.2017.
 */

public class Songs extends Fragment {
    private RecyclerView lst_tracklist;
    private String search_text = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.songs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lst_tracklist = (RecyclerView) view.findViewById(R.id.lst_tracklist);
        lst_tracklist.setNestedScrollingEnabled(false);
        lst_tracklist.setLayoutManager(new LinearLayoutManager(this.getContext()));
        search_text = this.getArguments().getString("search_item");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // get Media Data --------------------------------------------------------------------------
                ContentResolver cr = getActivity().getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC" ; //+ MediaStore.Audio.Media.ALBUM + " ASC, " + MediaStore.Audio.Media.TRACK + " ASC";
                Cursor cur;
                String selection = null;
                String [] selection_args = null;
                if (search_text != null)
                {
                    selection = MediaStore.Audio.Media.ARTIST + "=?";
                    selection_args = new String[]{search_text};

                }
                else {
                    selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0 AND " + MediaStore.Audio.Media.DURATION + "> 1000";

                }
                cur = cr.query(uri, null, selection, selection_args, sortOrder);
                // -----------------------------------------------------------------------------------------
                final MusicListAdapter da = new MusicListAdapter(cur);
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
