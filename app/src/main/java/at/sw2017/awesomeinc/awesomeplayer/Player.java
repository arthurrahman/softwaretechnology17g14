package at.sw2017.awesomeinc.awesomeplayer;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Created by ramiro on 04.05.2017.
 */

public class Player extends AppCompatActivity implements View.OnClickListener{
    static MediaPlayer media_player;

    SeekBar seekbar;
    //Button bt_play, bt_fast_fw, bt_rew, bt_next, bt_prev;
    FloatingActionButton bt_play, bt_fast_fw, bt_rew, bt_next, bt_prev;
    TextView txt_songname;
    TextView txt_Artist;
    TextView txt_nSongname;
    TextView txt_nArtist;
    RatingBar rab_stars;
    Thread seekbar_thread;

    protected static boolean is_playing(){
        /*if(media_player==null)
            return false;*/
        return media_player.isPlaying();
    }

    public static int getCurrentPosition(){
        return media_player.getCurrentPosition();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        /*
        bt_play = (Button) findViewById(R.id.bt_play);
        bt_fast_fw = (Button) findViewById(R.id.bt_fast_fw);
        bt_rew = (Button) findViewById(R.id.bt_rew);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_prev = (Button) findViewById(R.id.bt_prev);
        rab_stars = (RatingBar) findViewById(R.id.rating);
        */
        bt_play = (FloatingActionButton) findViewById(R.id.bt_play);
        bt_fast_fw = (FloatingActionButton) findViewById(R.id.bt_fast_fw);
        bt_rew = (FloatingActionButton) findViewById(R.id.bt_rew);
        bt_next = (FloatingActionButton) findViewById(R.id.bt_next);
        bt_prev = (FloatingActionButton) findViewById(R.id.bt_prev);
        rab_stars = (RatingBar) findViewById(R.id.rating);

        bt_play.setOnClickListener(this);
        bt_fast_fw.setOnClickListener(this);
        bt_rew.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_prev.setOnClickListener(this);


        rab_stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Database.currentSong().setRating((int) rating);
                Database.updateSongForRating();
            }
        });


        seekbar = (SeekBar) findViewById(R.id.seekBar);
        txt_songname = (TextView) findViewById(R.id.txt_songname);
        txt_Artist = (TextView) findViewById(R.id.txt_Artist);
        txt_nArtist = (TextView) findViewById(R.id.txt_nextArtist);
        txt_nSongname = (TextView) findViewById(R.id.txt_nextSong);

        if(! Database.isPlaying()) {
            reset_mediaplayer();
            play();
        } else {
            handleSeekbar();
        }
    }

    public void handleSeekbar(){
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                media_player.seekTo(seekBar.getProgress());
            }
        });


        if(seekbar_thread == null)
            seekbar_thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while(true) {
                        if(Database.isPlaying()) {
                            seekbar.setMax((int)Database.currentSong().getDurationValue());
                            seekbar.setProgress(media_player.getCurrentPosition());
                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            //don't care
                        }
                    }
                }
            });
        if(!seekbar_thread.isAlive())
            seekbar_thread.start();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.bt_play:
                play();
                break;
            case R.id.bt_fast_fw:
                fastFoward();
                break;
            case R.id.bt_rew:
                rewind();
                break;
            case R.id.bt_next:
                nextSong();
                break;
            case R.id.bt_prev:
                previousSong();
                break;
        }
    }

    public void play(){
        if(media_player.isPlaying()){
            //bt_play.setBackgroundResource(android.R.drawable.ic_media_play);
            Drawable dr = getDrawable(android.R.drawable.ic_media_play);
            bt_play.setImageDrawable(dr);
            media_player.pause();
            Database.setIsNotPlaying();
        }
        else {
            //bt_play.setBackgroundResource(android.R.drawable.ic_media_pause);
            Drawable dr = getDrawable(android.R.drawable.ic_media_pause);
            bt_play.setImageDrawable(dr);
            media_player.start();
            Database.setIsPlaying();
        }
    }

    public void fastFoward(){
        media_player.seekTo(media_player.getCurrentPosition()+5000);
    }

    public void rewind(){
        media_player.seekTo(media_player.getCurrentPosition()-5000);
    }

    public void reset_mediaplayer() {

        if(media_player!=null){
            media_player.stop();
            media_player.reset();
            Database.setIsNotPlaying();
        }

        Song s = Database.currentSong();

        media_player = MediaPlayer.create(getApplicationContext(), Uri.parse(s.getURI()));

        media_player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        media_player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                media_player.start();
                Database.setIsPlaying();
                seekbar.setMax(media_player.getDuration());
                handleSeekbar();
            }
        });
        media_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("Media_player", "Completed");
                nextSong();
            }
        });

        media_player.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(MediaPlayer mp, TimedText text) {
                Log.d("media_player", "Got timed text " + text);
            }
        });

        txt_songname.setText(trimmString(s.getTitle()));
        txt_Artist.setText(s.getArtist());

        Song s_next = Database.nextSongInformation();
        if(s_next != null) {
            txt_nSongname.setText(s_next.getTitle());
            txt_nArtist.setText(s_next.getArtist());
        }

        rab_stars.setRating(s.getRating());
        seekbar.setMax(media_player.getDuration());
        handleSeekbar();
    }

    public String trimmString(String word) {
        String trimmed = word;
        if(word.length() >= 17) {
            trimmed = word.substring(0, 15) + "...";
        }
        return trimmed;
    }

    public void nextSong(){
        Database.nextSong();
        reset_mediaplayer();
        play();
    }

    public void previousSong(){
        Database.previousSong();
        reset_mediaplayer();
        play();
    }

}