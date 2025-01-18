package hua.dit.mobdev.ec.appl10.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hua.dit.mobdev.ec.appl10.R;

public class MyFragment1 extends Fragment {

    private static final String TAG = "L10-MyFragment1";

    public MyFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my1, container, false);

        view.findViewById(R.id.fragment_1_button).setOnClickListener((v)->{
            Log.d(TAG, "Button 1");
        });

        return view;
    }

}