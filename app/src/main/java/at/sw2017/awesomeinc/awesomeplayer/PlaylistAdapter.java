package at.sw2017.awesomeinc.awesomeplayer;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahman on 27.05.2017.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistItemViewHolder> {
    private List<Playlist> playlist;

    public PlaylistAdapter(ArrayList<Playlist> playLists) {
        if(playLists == null)
            playLists = new ArrayList<>();

        this.playlist = playLists;
    }


    @Override
    public PlaylistAdapter.PlaylistItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_playlist, null);
        return new PlaylistItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaylistItemViewHolder playlistViewHolder, int i) {
        final Playlist selectedPlaylist = playlist.get(i);
        playlistViewHolder.txt_playlist.setText(selectedPlaylist.getTitleForCardView());
        playlistViewHolder.bind(i, selectedPlaylist.getTitleForCardView());
    }

    @Override
    public int getItemCount() {
        return this.playlist.size();
    }
    public List<Playlist> getPlaylist(){
        return this.playlist;
    }

    class PlaylistItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView txt_playlist;
        public PlaylistItemViewHolder(View v){
            super(v);
            this.txt_playlist = (TextView) v.findViewById(R.id.playlist_name);
        }

        public void bind(final int position, String plName){
            final String plN = plName;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    PlaylistSongs s = new PlaylistSongs();
                    s.getPlaylist(plN);
                    ((RecyclerView) v.getParent()).setAdapter(new PlaylistAdapter(new ArrayList<Playlist>()));
                    FragmentManager manager = ((Activity) context).getFragmentManager();
                    manager.beginTransaction().replace(R.id.content_main, s).commit();
                }
            });
        }
    }
}
