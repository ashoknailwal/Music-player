package com.example.ashok.mediaplayer5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashok on 28-05-2015.
 */
public class SongAdapter extends ArrayAdapter<Song> {

    Context mcontext;
    int rId;
    ArrayList<Song> msong;


    public SongAdapter(Context context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        this.rId = resource;
        this.msong = objects;
    }
    @Override
    public Song getItem(int position){      return super.getItem(position);  }
    @Override
    public View getView(int position, View row, ViewGroup parent){


        SongHolder holder = null;

        if(row==null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            row = inflater.inflate(rId, parent, false);

            holder = new SongHolder();
            holder.nameView = (TextView) row.findViewById(R.id.song_title);
            holder.artistName = (TextView) row.findViewById(R.id.song_artist);
            holder.imageView = (ImageView) row.findViewById(R.id.song_indicator);
            row.setTag(holder);
        }else{
            holder = (SongHolder) row.getTag();
        }

        Song name = msong.get(position);

       holder.nameView.setText(name.getmTitle());
        holder.artistName.setText(name.getmArtist());
        int resId = mcontext.getResources().getIdentifier("image1","drawable",mcontext.getPackageName());
        holder.imageView.setImageResource(resId);

        return row;

    }

    public static class  SongHolder{
        TextView nameView;
        TextView artistName;
        ImageView imageView;
    }
}
