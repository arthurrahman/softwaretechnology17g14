package at.sw2017.awesomeinc.awesomeplayer;

/**
 * Created by julian on 19.04.17.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

    private List<Song> displayed_trackList;
    private List<Song> trackList;


    public MusicListAdapter(List<String> tracks) {
        this.trackList = new ArrayList<Song>() ;
        this.displayed_trackList = new ArrayList<Song>() ;

        for (String track : tracks) {
            this.trackList.add(new Song(track));
        }
        displayed_trackList.addAll(trackList);
        //displayed_trackList = trackList;

    }

    public MusicListAdapter(Cursor queryCursor) {
        this.trackList = new ArrayList<Song>() ;
        this.displayed_trackList = new ArrayList<Song>() ;

        if(queryCursor != null && queryCursor.moveToFirst())
        {
            while(queryCursor.moveToNext()) {
                this.trackList.add(new Song(queryCursor));
            }
            displayed_trackList.addAll(trackList);
            //displayed_trackList = trackList;
        }

    }

    public MusicListAdapter(ArrayList<Song> songList) {
        this.trackList = songList;
        this.displayed_trackList = new ArrayList<Song>() ;
        displayed_trackList.addAll(trackList);
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

    public List<Song> getTrackList(){
        return this.trackList;
    }

    /*public void filterSongsArtist(String searchstring)
    {
        List<Song> filtered_list = new ArrayList();

        for(Song song : trackList)
        {
            if(searchstring.isEmpty())
            {
                filtered_list.add(song);
            }
            else if(song.getArtist().toLowerCase().contains(searchstring.toLowerCase()))
            {
                filtered_list.add(song);
            }
        }
        // update recylcerview
        displayed_trackList.clear();
        if(!filtered_list.isEmpty())
            displayed_trackList.addAll(filtered_list);
        notifyDataSetChanged();
        return;
    }

    public void filterSongsTitle(String searchstring)
    {
        List<Song> filtered_list = new ArrayList();

        for(Song song : trackList)
        {
            if(searchstring.isEmpty())
            {
                filtered_list.add(song);
            }
            else if(song.getTitle().toLowerCase().contains(searchstring.toLowerCase()))
            {
                filtered_list.add(song);
            }
        }
        // update recylcerview
        displayed_trackList.clear();
        if(!filtered_list.isEmpty())
            displayed_trackList.addAll(filtered_list);
        notifyDataSetChanged();
        return;
    }

    public void filterSongsAlbum(String searchstring)
    {
        List<Song> filtered_list = new ArrayList();

        for(Song song : trackList)
        {
            if(searchstring.isEmpty())
            {
                filtered_list.add(song);
            }
            else if(song.getAlbum().toLowerCase().contains(searchstring.toLowerCase()))
            {
                filtered_list.add(song);
            }
        }
        // update recylcerview
        displayed_trackList.clear();
        if(!filtered_list.isEmpty())
            displayed_trackList.addAll(filtered_list);
        notifyDataSetChanged();
        return;
    }*/

    public void filterSongsByAttributes(String searchstring, String selection) {
        List<Song> filtered_list = new ArrayList();

        for(Song song : trackList)
        {
            if(searchstring.isEmpty() || selection.isEmpty())
            {
                filtered_list.add(song);
            } else {
                boolean added = false;

                if (selection.contains("A")) {
                    if (song.getTitle().toLowerCase().contains(searchstring.toLowerCase())) {
                        filtered_list.add(song);
                        added = true;
                    }
                }
                if (selection.contains("B") && !(added)) {
                    if (song.getAlbum().toLowerCase().contains(searchstring.toLowerCase())) {
                        filtered_list.add(song);
                        added = true;
                    }
                }
                if (selection.contains("C") && !(added)) {
                    if (song.getArtist().toLowerCase().contains(searchstring.toLowerCase())) {
                        filtered_list.add(song);
                    }
                }
            }
        }

        // update recylcerview
        displayed_trackList.clear();
        if(!filtered_list.isEmpty())
            displayed_trackList.addAll(filtered_list);
        notifyDataSetChanged();
        return;
    }

    public void filterSongsAllAttributes(String searchstring)
    {
        List<Song> filtered_list = new ArrayList();

        for(Song song : trackList)
        {
            if(searchstring.isEmpty())
            {
                filtered_list.add(song);
            }
            else if(song.getTitle().toLowerCase().contains(searchstring.toLowerCase()) ||
                    song.getAlbum().toLowerCase().contains(searchstring.toLowerCase()) ||
                    song.getArtist().toLowerCase().contains(searchstring.toLowerCase()))
            {
                filtered_list.add(song);
            }
        }
        // update recylcerview
        displayed_trackList.clear();
        if(!filtered_list.isEmpty())
            displayed_trackList.addAll(filtered_list);
        notifyDataSetChanged();
        return;
    }

    public void displayAllSongs()
    {
        displayed_trackList = trackList;
        notifyDataSetChanged();
        return;
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
                    context.startActivity(new Intent(context,Player.class).putExtra("pos",position).putExtra("songlist", (Serializable) displayed_trackList));

                }
            });
        }
    }
}
