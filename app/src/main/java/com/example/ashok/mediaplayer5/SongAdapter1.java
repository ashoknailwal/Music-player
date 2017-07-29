package com.example.ashok.mediaplayer5;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ashok on 01-06-2015.
 */
public class SongAdapter1 extends ArrayAdapter<Song> {

    Context mContext1;
    int rId1;
    ArrayList<Song> msong1;
    Random rand;


    public SongAdapter1(Context context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);
        this.mContext1 = context;
        this.rId1 = resource;
        this.msong1 = objects;
    }


    @Override
    public Song getItem(int position) {
        return super.getItem(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SongHolder1 holder1 = null;
        rand = new Random();
        int r,g,b;



        if(convertView==null) {

            LayoutInflater inflater = LayoutInflater.from(mContext1);
            convertView = inflater.inflate(rId1, parent, false);

            holder1 = new SongHolder1();

            holder1.nameView1 = (TextView) convertView.findViewById(R.id.song_title1);
            holder1.artistName1 = (TextView) convertView.findViewById(R.id.song_artist1);
            holder1.sideBar1 = (View) convertView.findViewById(R.id.side_color);
            convertView.setTag(holder1);
        }
        else{
            holder1 = (SongHolder1)convertView.getTag();
        }


        Song songName = msong1.get(position);

        holder1.nameView1.setText(songName.getmTitle());
        holder1.artistName1.setText(songName.getmArtist());

        r =  rand.nextInt()-20;
        g = rand.nextInt()-40;
        b = rand.nextInt()-20;

        int randomColor = Color.rgb(r,g,b);
        holder1.nameView1.setTextColor(randomColor);
        holder1.sideBar1.setBackgroundColor(randomColor);



        return convertView;
    }


    public  static class SongHolder1{
        TextView nameView1;
        TextView artistName1;
        View sideBar1;

    }



}

