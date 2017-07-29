package com.example.ashok.mediaplayer5;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by ashok on 31-05-2015.
 */
public class MyFragment extends Fragment {

    private int orientation;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setOrientation(int data){
        this.orientation = data;
    }
    public int  getOrientation(){
        return orientation;
    }

}
