package at.sw2017.awesomeinc.awesomeplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView lst_tracklist = (RecyclerView) findViewById(R.id.lst_tracklist);
        lst_tracklist.setLayoutManager(new LinearLayoutManager(this));

        ContentResolver cr = this.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0 AND " + MediaStore.Audio.Media.DURATION + "> 1000";
        String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC" ; //+ MediaStore.Audio.Media.ALBUM + " ASC, " + MediaStore.Audio.Media.TRACK + " ASC";
        //Cursor cur = cr.query(uri, null, selection, null, null);
        int count = 0;
        Cursor cur = cr.query(uri, null, selection, null, null);


        MusicListAdapter da = new MusicListAdapter(cur);

        lst_tracklist.setAdapter(da);
    }
}
