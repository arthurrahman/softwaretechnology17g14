package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ramiro on 04.05.2017.
 */

public class Player extends AppCompatActivity implements View.OnClickListener{
    static MediaPlayer media_player;
    ArrayList<Song> song_list;
    int position;
    Uri uri;

    SeekBar seekbar;
    Button bt_play, bt_fast_fw, bt_rew, bt_next, bt_prev;
    TextView txt_songname;

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

        bt_play.setOnClickListener(this);
        bt_fast_fw.setOnClickListener(this);
        bt_rew.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        bt_prev.setOnClickListener(this);

        seekbar = (SeekBar) findViewById(R.id.seekBar);


        if(media_player!=null){
            media_player.stop();
            media_player.release();
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();
        song_list = (ArrayList) b.getParcelableArrayList("songlist");
        position = b.getInt("pos",0);

        uri = Uri.parse(song_list.get(position).getURI());
        media_player = MediaPlayer.create(getApplicationContext(),uri);

        media_player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        media_player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                media_player.start();
                seekbar.setMax(media_player.getDuration());
                handleSeekbar();
            }
        });
        media_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(position + 1 < song_list.size()){
                    nextSong();
                }else{
                    media_player.stop();
                    media_player.reset();
                    uri = Uri.parse(song_list.get(0).getURI());
                    media_player = MediaPlayer.create(getApplicationContext(),uri);
                    txt_songname.setText(song_list.get(0).getTitle());
                    seekbar.setMax(media_player.getDuration());
                    handleSeekbar();
                    play();
                }
            }
        });

        bt_play.setBackgroundResource(android.R.drawable.ic_media_pause);
        seekbar.setMax(media_player.getDuration());

        txt_songname = (TextView) findViewById(R.id.txt_songname);
        txt_songname.setText(song_list.get(position).getTitle());
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

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int totalDuration = media_player.getDuration();
                    int currentPosition = 0;
                    while (media_player != null && currentPosition < totalDuration) {
                        currentPosition = media_player.getCurrentPosition();
                        seekbar.setProgress(currentPosition);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Log.e("Seekbar-Thread", e.getMessage(), e);
                        }
                    }
                }catch (Exception e){
                    Log.e("Seekbar-Thread2", e.getMessage(), e);
                }
            }
        }).start();
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
        }
        else {
            bt_play.setBackgroundResource(android.R.drawable.ic_media_pause);
            media_player.start();
        }
    }

    public void fastFoward(){
        media_player.seekTo(media_player.getCurrentPosition()+5000);
    }

    public void rewind(){
        media_player.seekTo(media_player.getCurrentPosition()-5000);
    }

    public void nextSong(){
        media_player.stop();
        media_player.reset();
        position = (position+1)%song_list.size();
        uri = Uri.parse(song_list.get(position).getURI());
        media_player = MediaPlayer.create(getApplicationContext(),uri);
        txt_songname.setText(song_list.get(position).getTitle());
        seekbar.setMax(media_player.getDuration());
        handleSeekbar();
        //media_player.start();
        play();
    }

    public void previousSong(){
        media_player.stop();
        media_player.reset();
        position = (position-1<0)? song_list.size()-1: position-1;
        uri = Uri.parse(song_list.get(position).getURI());
        media_player = MediaPlayer.create(getApplicationContext(),uri);
        txt_songname.setText(song_list.get(position).getTitle());
        seekbar.setMax(media_player.getDuration());
        // handleSeekbar();
        //media_player.start();
        play();
    }

}