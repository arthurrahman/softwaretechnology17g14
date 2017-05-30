package at.sw2017.awesomeinc.awesomeplayer;

/**
 * Created by julian on 19.04.17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
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

    public MusicListAdapter(ArrayList<Song> songList) {
        if(songList == null)
            songList = new ArrayList<>();

        this.trackList = songList;
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
        // get title for cardView presentation
        musicItemViewHolder.txt_title.setText(selectedSong.getTitleForCardView());
        musicItemViewHolder.txt_artist.setText(selectedSong.getArtist());
        musicItemViewHolder.txt_duration.setText(selectedSong.getDuration());

        musicItemViewHolder.bind(i);
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

        public void bind(final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    context.startActivity(new Intent(context,Player.class).putExtra("pos",position).putExtra("songlist", (Serializable) trackList));

                }
            });
        }
    }
}
