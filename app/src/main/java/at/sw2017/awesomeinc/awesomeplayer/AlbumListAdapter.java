package at.sw2017.awesomeinc.awesomeplayer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 02.06.17
 */

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListItemViewHolder> {

    private List<String> album_list;
    private Album parent;

    public AlbumListAdapter(Album parent) {
        this.parent = parent;
        this.album_list = Database.getAllAlbumNames();
    }

    @Override
    public AlbumListAdapter.AlbumListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_album, null);
        return new AlbumListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AlbumListItemViewHolder albumListViewHolder, int i) {
        albumListViewHolder.album_name.setText(album_list.get(i));

        albumListViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return this.album_list.size();
    }

    public List<String> getAlbumList(){
        return this.album_list;
    }

    public void addAlbum(String album) { this.album_list.add(album); }

    class AlbumListItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView album_name;
        public AlbumListItemViewHolder(View v){
            super(v);
            this.album_name = (TextView) v.findViewById(R.id.album_name);
        }

        public void bind(final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Context context = v.getContext();
                    //context.startActivity(new Intent(context, Songs.class));
                    parent.changeToSongView(album_list.get(position));
                }
            });
        }
    }
}
