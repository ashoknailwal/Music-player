package com.example.ashok.mediaplayer5;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by ashok on 29-05-2015.
 */
public class PlayerController {
    public static MediaPlayer myMPlayer;
    private ImageButton mplay_pause;



    public static enum State{Initialised,Prepared,Play,Paused,Resume,Stop};
    public static State state=State.Initialised;


    public PlayerController(ImageButton play_plause){
        this.mplay_pause = play_plause;
    }



    //Method to Start PlayBack
    public void start(){
        if(state.equals(State.Prepared)|| state.equals(State.Paused)){
            myMPlayer.start();
            state = State.Play ;
            setPlay_plauseImage();

        }
    }

    //Method to pause the PlayBack
    public void pause(){
        if(state.equals(State.Play)){
            myMPlayer.pause();
            state = State.Paused;
            setPlay_plauseImage();
        }
    }

    // Method to release the Media Player resources
    public void stop(){
        if(state.equals(State.Play)||state.equals(State.Paused)|| state.equals(State.Prepared)) {
            myMPlayer.stop();
            myMPlayer.release();
            state = State.Stop;

        }

    }




   public void setPlay_plauseImage(){
       if(state.equals(State.Play))
           mplay_pause.setImageResource(android.R.drawable.ic_media_pause);
       else if(state.equals(State.Paused))
           mplay_pause.setImageResource(android.R.drawable.ic_media_play);
   }







}
