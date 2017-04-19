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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.musiclist_item, null);
        return new MusicItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MusicItemViewHolder musicItemViewHolder, final int i) {
        final Song selectedSong = trackList.get(i);
        musicItemViewHolder.txt_songname.setText(selectedSong.getTitle());

        musicItemViewHolder.txt_songname.setOnClickListener(new View.OnClickListener() {
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
        protected TextView txt_songname;

        public MusicItemViewHolder(View v){
            super(v);
            this.txt_songname = (TextView) v.findViewById(R.id.txt_songname);
        }
    }
}
