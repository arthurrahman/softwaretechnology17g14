package at.sw2017.awesomeinc.awesomeplayer;

import android.support.annotation.MainThread;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Martin on 02.06.2017.
 */

public class Album extends Fragment{
    private RecyclerView vw_album;
    private ViewGroup container;

    public Album() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        return inflater.inflate(R.layout.album, container, false);
    }

    public void changeToSongView(String title) {
        MainActivity main = (MainActivity) getActivity();
        //FragmentManager manager = main.getSupportFragmentManager();
        //android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
        //ft.remove(this);
        //ft.replace(R.id.content_main, main.getSongFragment());
        //ft.replace(R.id.content_main, main.getSongFragment()).addToBackStack(null).commit();
        //ft.addToBackStack(null).commit();
        //ft.commit();
        //this.onDestroy();
        //manager.executePendingTransactions();

        main.displaySelectedScreen(R.id.nav_songs);
        main.setSongSearch(title, "B");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vw_album = (RecyclerView) view.findViewById(R.id.lst_album);
        vw_album.setNestedScrollingEnabled(false);
        vw_album.setLayoutManager(new LinearLayoutManager(this.getContext()));

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                final AlbumListAdapter adapter = new AlbumListAdapter(Album.this);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vw_album.setAdapter(adapter);
                        vw_album.setId(R.id.lst_album);
                    }
                });
            }
        }).start();*/

        final AlbumListAdapter adapter = new AlbumListAdapter(Album.this);
        vw_album.setAdapter(adapter);
        vw_album.setId(R.id.lst_album);

        getActivity().setTitle("Albums");
    }
}
