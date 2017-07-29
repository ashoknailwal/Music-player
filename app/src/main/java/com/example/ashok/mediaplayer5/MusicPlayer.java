package com.example.ashok.mediaplayer5;

import android.content.ContentResolver;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MusicPlayer extends ActionBarActivity {


    public static ArrayList<Song> songList;
    private ListView songView;
    public static int mpos;
    public static boolean mflag = false;

    private SongAdapter1 msongAdapter;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);


        songList = new ArrayList<Song>();
        songView = (ListView)findViewById(R.id.song_list);


        getSongsList();
        Collections.sort(songList, new Comparator<Song>() {
            @Override
            public int compare(Song lhs, Song rhs) {
                return lhs.getmTitle().compareTo(rhs.getmTitle());
            }
        });

        msongAdapter = new SongAdapter1(getApplicationContext(),R.layout.row1,songList);
        songView.setAdapter(msongAdapter);

        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent = new Intent(getApplication(),PlayBack.class);
                mpos = position;
                mflag = true;
                startActivity(intent);

            }
        });
    }


    public  void getSongsList(){

        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri,null,null,null,null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get cloumns
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
