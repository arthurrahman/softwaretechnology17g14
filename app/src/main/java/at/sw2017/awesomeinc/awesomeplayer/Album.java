package at.sw2017.awesomeinc.awesomeplayer;

import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class Album extends Fragment{
    private RecyclerView vw_album;

    public Album() {

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.song_item_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.album, container, false);
    }

    public void changeToSongView(String title) {
        MainActivity main = (MainActivity) getActivity();

        main.displaySelectedScreen(R.id.nav_songs);
        main.setSongSearch(title, "B");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vw_album = (RecyclerView) view.findViewById(R.id.lst_album);
        vw_album.setNestedScrollingEnabled(false);
        vw_album.setLayoutManager(new LinearLayoutManager(this.getContext()));

        final AlbumListAdapter adapter = new AlbumListAdapter(Album.this);
        vw_album.setAdapter(adapter);
        vw_album.setId(R.id.lst_album);

        getActivity().setTitle("Albums");
    }
}
