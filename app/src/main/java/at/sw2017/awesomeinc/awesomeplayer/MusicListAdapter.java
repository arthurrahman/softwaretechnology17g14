package at.sw2017.awesomeinc.awesomeplayer;

/**
 * Created by julian on 19.04.17.
 */


import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 08.03.17.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicItemViewHolder> {

    private List<Song> trackList;

    public MusicListAdapter(List<String> tracks) {
        this.trackList = new ArrayList<Song>() ;

        for (String track : tracks) {
            this.trackList.add(new Song(track));
        }

    }

    public MusicListAdapter(Cursor queryCursor) {
        this.trackList = new ArrayList<>() ;

        if(queryCursor != null && queryCursor.moveToFirst())
        {
            while(queryCursor.moveToNext())
                this.trackList.add(new Song(queryCursor));
        }

    }

    @Override
    public MusicListAdapter.MusicItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.musiclist_item, null);
        //return new MusicItemViewHolder(v);

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, null);
        return new MusicItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MusicItemViewHolder musicItemViewHolder, final int i) {
        final Song selectedSong = trackList.get(i);
        musicItemViewHolder.txt_title.setText(selectedSong.getTitle());
        musicItemViewHolder.txt_artist.setText(selectedSong.getArtist());
        musicItemViewHolder.txt_duration.setText(selectedSong.getDuration());

        musicItemViewHolder.txt_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), selectedSong.getDuration(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.trackList.size();
    }


    class MusicItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView txt_artist;
        protected TextView txt_title;
        protected TextView txt_duration;
        public MusicItemViewHolder(View v){
            super(v);
            this.txt_artist = (TextView) v.findViewById(R.id.artist);
            this.txt_title = (TextView) v.findViewById(R.id.title);
            this.txt_duration = (TextView) v.findViewById(R.id.duration);
        }
    }
}
