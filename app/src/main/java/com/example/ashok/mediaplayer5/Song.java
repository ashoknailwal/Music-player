package com.example.ashok.mediaplayer5;

/**
 * Created by ashok on 28-05-2015.
 */
public class Song {

    long mId;
    String mTitle;
    String mArtist;

    public Song(long id, String title, String artist){
        this.mId = id;
        this.mTitle = title;
        this.mArtist = artist;

    }
    public long getmId(){ return mId;}
    public String getmTitle() { return mTitle;}
    public String getmArtist() { return mArtist;}


}
