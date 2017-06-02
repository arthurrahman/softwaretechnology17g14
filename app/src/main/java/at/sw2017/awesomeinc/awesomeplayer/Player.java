package at.sw2017.awesomeinc.awesomeplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by ramiro on 04.05.2017.
 */

public class Player extends AppCompatActivity implements View.OnClickListener{
    static MediaPlayer media_player;

    SeekBar seekbar;
    Button bt_play, bt_fast_fw, bt_rew, bt_next, bt_prev;
    TextView txt_songname;
    RatingBar rab_stars;

    protected static boolean is_playing(){
        if(media_player==null)
            return false;
        return media_player.isPlaying();
    }

    public static int getCurrentPosition(){
        return media_player.getCurrentPosition();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);

        bt_play = (Button) findViewById(R.id.bt_play);
        bt_fast_fw = (Button) findViewById(R.id.bt_fast_fw);
        bt_rew = (Button) findViewById(R.id.bt_rew);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_prev = (Button) findViewById(R.id.bt_prev);
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
            }
        });


        seekbar = (SeekBar) findViewById(R.id.seekBar);
        txt_songname = (TextView) findViewById(R.id.txt_songname);

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
            bt_play.setBackgroundResource(android.R.drawable.ic_media_play);
            media_player.pause();
            Database.setIsNotPlaying();
        }
        else {
            bt_play.setBackgroundResource(android.R.drawable.ic_media_pause);
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

        txt_songname.setText(s.getTitle());
        rab_stars.setRating(s.getRating());
        seekbar.setMax(media_player.getDuration());
        handleSeekbar();
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