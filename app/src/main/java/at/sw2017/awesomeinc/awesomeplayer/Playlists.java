package at.sw2017.awesomeinc.awesomeplayer;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                createPlaylist(this.getView());
                return true;
            case 2:
                deleteAllPlaylists();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllPlaylists() {
        xmlPlaylists.deleteAllPlaylists();
        playlists.clear();
        vw_playlist.setAdapter(new PlaylistAdapter(new ArrayList<Playlist>()));
        FragmentManager manager = this.getActivity().getFragmentManager();
        Playlists p = new Playlists();
        manager.beginTransaction().replace(R.id.content_main, p).commit();
    }

    public void createPlaylist(View view) {
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.new_playlist_dialog);
        dialog.show();

        Button saveButton = (Button) dialog.findViewById(R.id.savePlaylist);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText plName = (EditText) dialog.findViewById(R.id.newPlaylistName);

                if(!plName.getText().toString().matches("")) {
                    boolean exists = false;
                    for(Playlist list : xmlPlaylists.getAllPlaylists())
                    {
                        if(list.getTitle().toString().matches(plName.getText().toString()))
                        {
                            exists = true;
                        }
                    }
                    if(!exists)
                        playlists.add(xmlPlaylists.newPlaylist(plName.getText().toString()));
                    else
                    {
                        // Toast that playlist exists already
                        CharSequence text = "Playlist already exists!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getActivity(), text, duration);
                        toast.show();
                    }
                }
                else
                {
                    // toast that playlist name is invalid
                    CharSequence text = "Invalid Playlist Name!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                }

                vw_playlist.setAdapter(new PlaylistAdapter(playlists));
                dialog.dismiss();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.playlist, container, false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, 1, 0, R.string.action_addToPlaylist);
        menu.add(0, 2, 0, R.string.action_deleteAllPlaylists);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.song_item_menu, menu);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        xmlPlaylists.setContext(view.getContext());

        vw_playlist = (RecyclerView) view.findViewById(R.id.playlist);
        vw_playlist.setNestedScrollingEnabled(false);
        vw_playlist.setLayoutManager(new LinearLayoutManager(view.getContext()));


        new Thread(new Runnable() {
            @Override
            public void run() {
                // ------------------------------------------------------
                // Load XML
                playlists.addAll(xmlPlaylists.getAllPlaylists());
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
