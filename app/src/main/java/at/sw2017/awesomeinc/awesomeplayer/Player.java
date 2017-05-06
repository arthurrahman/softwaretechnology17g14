package at.sw2017.awesomeinc.awesomeplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ramiro on 04.05.2017.
 */

public class Player extends AppCompatActivity implements View.OnClickListener{
    static MediaPlayer media_player;
    ArrayList<Song> song_list;
    int position;
    Uri uri;
    Thread updateSeekBar;

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
        updateSeekBar = new Thread(){
            @Override
            public void run() {
                int totalDuration = media_player.getDuration();
                int currentPosition = 0;
                while (currentPosition < totalDuration){
                    try {
                        sleep(500);
                        currentPosition = media_player.getCurrentPosition();
                        seekbar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //super.run();
            }
        };

        if(media_player!=null){
            media_player.stop();
            media_player.release();
            updateSeekBar.interrupt();
            try {
                updateSeekBar.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Intent i = getIntent();
        Bundle b = i.getExtras();
        song_list = (ArrayList) b.getParcelableArrayList("songlist");
        position = b.getInt("pos",0);


        //  u = Uri.parse(mySongs.get(position).toString());
        uri = Uri.parse(song_list.get(position).getURI());
        media_player = MediaPlayer.create(getApplicationContext(),uri);
        media_player.start();
        bt_play.setBackgroundResource(android.R.drawable.ic_media_pause);
        seekbar.setMax(media_player.getDuration());

        txt_songname = (TextView) findViewById(R.id.txt_songname);
        txt_songname.setText(song_list.get(position).getTitle());

        updateSeekBar.start();

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
                if(media_player.isPlaying()){
                    bt_play.setBackgroundResource(android.R.drawable.ic_media_play);
                    media_player.pause();
                }
                else {
                    bt_play.setBackgroundResource(android.R.drawable.ic_media_pause);
                    media_player.start();
                }

                break;
            case R.id.bt_fast_fw:
                media_player.seekTo(media_player.getCurrentPosition()+5000);
                break;
            case R.id.bt_rew:
                media_player.seekTo(media_player.getCurrentPosition()-5000);
                break;
            case R.id.bt_next:
                media_player.stop();
                //mp.release();
                position = (position+1)%song_list.size();
                uri = Uri.parse(song_list.get(position).getURI());
                media_player = MediaPlayer.create(getApplicationContext(),uri);
                media_player.start();
                seekbar.setMax(media_player.getDuration());
                break;
            case R.id.bt_prev:
                media_player.stop();
                //mp.release();
                position = (position-1<0)? song_list.size()-1: position-1;
                uri = Uri.parse(song_list.get(position).getURI());
                media_player = MediaPlayer.create(getApplicationContext(),uri);
                media_player.start();
                seekbar.setMax(media_player.getDuration());
                break;
        }
    }
}