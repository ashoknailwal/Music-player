package com.example.ashok.mediaplayer5;

import android.app.FragmentManager;
import android.content.ContentUris;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;



public class PlayBack extends ActionBarActivity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, SeekBar.OnSeekBarChangeListener{



    private ImageView albumArt;
    private TextView songTitle;
    private TextView songArtist;
    private TextView covered;
    private TextView total;
    private ImageButton play;
    private ImageButton previous;
    private ImageButton next;
    private SeekBar songProgress;
    private ArrayList<Song> songs= MusicPlayer.songList;
    private PlayerController playerController;
    public Utilities util = new Utilities();

    private int mProgressValue = 0;
    private int o;

    public Handler mHandler = new Handler();
    private MyFragment fragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back);

        // Get fragment Manager and associate tag with the fragment
        FragmentManager fm = getFragmentManager();
        fragment = (MyFragment) fm.findFragmentByTag("data");

        o = getOrientation();

        if(fragment == null){
            fragment = new MyFragment();
            fm.beginTransaction().add(fragment,"data").commit();
            fragment.setOrientation(o);
        }



        albumArt = (ImageView) findViewById(R.id.album_art);
        songTitle = (TextView) findViewById(R.id.show_title);
        songArtist = (TextView) findViewById(R.id.show_artist);
        play = (ImageButton) findViewById(R.id.play);
        previous = (ImageButton) findViewById(R.id.previous);
        next = (ImageButton) findViewById(R.id.next);
        songProgress = (SeekBar) findViewById(R.id.song_progress);
        covered = (TextView) findViewById(R.id.covered);
        total = (TextView) findViewById(R.id.total);



        //Initialise playerController object
        playerController = new PlayerController(play);


        if(getOrientation()==fragment.getOrientation() || MusicPlayer.mflag == true ) {
            play(songs.get(MusicPlayer.mpos).getmId());
            MusicPlayer.mflag = false;
        }

        else {
            layoutUpdate();
            playerController.setPlay_plauseImage();
        }





        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayerController.myMPlayer.isPlaying()){
                    playerController.pause();

                } else{
                    playerController.start();

                }
            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayer.mpos++;
                if(MusicPlayer.mpos<songs.size()){
                    play(songs.get(MusicPlayer.mpos).getmId());

                } else{
                    Toast.makeText(getApplicationContext(),"SORRY SONGS LIST END",Toast.LENGTH_LONG).show();
                    MusicPlayer.mpos=1;
                    play(songs.get(MusicPlayer.mpos).getmId());
                }
            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicPlayer.mpos--;
                if(MusicPlayer.mpos>=0){
                    play(songs.get(MusicPlayer.mpos).getmId());
                } else{
                    MusicPlayer.mpos=0;
                    Toast.makeText(getApplicationContext(),"THIS IS THE 1ST SONG",Toast.LENGTH_LONG).show();
                    play(songs.get(MusicPlayer.mpos).getmId());
                }
            }
        });




    }


    public void setAttributes(int position){
        String t = songs.get(position).getmTitle();
        String a = songs.get(position).getmArtist();
        songArtist.setText(a);
        songTitle.setText(t);
    }

    public int getOrientation(){
        Display display =((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getOrientation();
        return orientation;
    }


    //Method to set resource for the media Player instance
    public void play(long sid){

        playerController.stop();
        Uri mySongUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, sid);
        PlayerController.myMPlayer = new MediaPlayer();
        PlayerController.myMPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            PlayerController.myMPlayer.setDataSource(getApplicationContext(), mySongUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PlayerController.myMPlayer.prepareAsync();
        PlayerController.state = PlayerController.State.Prepared;
        PlayerController.myMPlayer.setOnPreparedListener(this);

    }





    //All the Listener methods************************************************


    // method to get call, from media player, on completion  of current song
    @Override
    public void onCompletion(MediaPlayer mp) {
        playerController.stop();
        if(MusicPlayer.mpos<songs.size()-1)
            MusicPlayer.mpos++;
        else
            MusicPlayer.mpos = 0;

            play(songs.get(MusicPlayer.mpos).getmId());

    }

    //method to get asynchronous prepared state callback
    @Override
    public void onPrepared(MediaPlayer mp) {

        PlayerController.myMPlayer = mp;
        playerController.start();
        layoutUpdate();



    }

    public void layoutUpdate(){

        String text;
        PlayerController.myMPlayer.setOnCompletionListener(this);
        songProgress.setMax(PlayerController.myMPlayer.getDuration());
        text = util.milliSecondsToTimer(PlayerController.myMPlayer.getDuration());
        total.setText(text);
        text = util.milliSecondsToTimer(PlayerController.myMPlayer.getCurrentPosition());
        covered.setText(text);
        setAttributes(MusicPlayer.mpos);
        songProgress.setOnSeekBarChangeListener(this);
        seekUpdation();
    }




    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        PlayerController.myMPlayer.seekTo(seekBar.getProgress());
    }

    // New thread for dynamic updation of seekbar*********************
    // Seekbar controls
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    // Update Seekbar  position
    public void seekUpdation(){
        String text;
        songProgress.setProgress(PlayerController.myMPlayer.getCurrentPosition());
        mHandler.postDelayed(runnable,100);
        text = util.milliSecondsToTimer(PlayerController.myMPlayer.getCurrentPosition());
        covered.setText(text);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_back, menu);
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

    @Override
    public void onDestroy(){
        super.onDestroy();
        fragment.setOrientation(o);
    }

}
