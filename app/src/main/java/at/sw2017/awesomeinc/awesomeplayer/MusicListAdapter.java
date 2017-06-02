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

import java.util.List;

/**
 * Created by root on 08.03.17.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicItemViewHolder> {

    private List<Song> displayed_trackList;



    public MusicListAdapter() {
        this.displayed_trackList = Database.setVisibleSongs(Database.getVisibleSongs());
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
        final Song selectedSong = displayed_trackList.get(i);
        // get title for cardView presentation
        musicItemViewHolder.txt_title.setText(selectedSong.getTitleForCardView());
        musicItemViewHolder.txt_artist.setText(selectedSong.getArtist());
        musicItemViewHolder.txt_duration.setText(selectedSong.getDuration());


        musicItemViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return this.displayed_trackList.size();
    }

    public void filterSongsByAttributes(String searchstring, String selection) {
        displayed_trackList.clear();
        displayed_trackList.addAll(Database.applyFilterToVisibleSongsByAttr(searchstring, selection));
        notifyDataSetChanged();
    }

    public void filterSongsAllAttributes(String searchstring)
    {
        displayed_trackList.clear();
        displayed_trackList.addAll(Database.applyFilterToVisibleSongsAllAttr(searchstring));
        notifyDataSetChanged();
    }

    public void displayAllSongs()
    {
        displayed_trackList = Database.resetVisibleSongs();
        notifyDataSetChanged();
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
                    Database.setCurrentSong(position);
                    Database.setIsNotPlaying();
                    context.startActivity(new Intent(context,Player.class));

                }
            });
        }
    }
}
