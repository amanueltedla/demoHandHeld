package com.example.dventus_hq.demohandheldapp;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.dventus_hq.demohandheldapp.fileActions.FileReader;
import com.example.dventus_hq.demohandheldapp.parameters.MeterData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener {
    private FrameLayout reading;
    private FrameLayout setting;
    private FrameLayout liveReading;

    public Home() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        reading = (FrameLayout) rootView.findViewById(R.id.readingButton);
        reading.setOnClickListener(this);
        setting = (FrameLayout) rootView.findViewById(R.id.settingButton);
        setting.setOnClickListener(this);
        liveReading = (FrameLayout) rootView.findViewById(R.id.liveReadingButton);
        liveReading.setOnClickListener(this);
        return rootView;
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.readingButton){
            Fragment fragment = new Readings();
            loadFragment(fragment,"readingHistory");
        }
        else if(view.getId() == R.id.settingButton){
            Fragment fragment = new Setting();
            loadFragment(fragment,"Setting");
        }
        else if(view.getId() == R.id.liveReadingButton){
            Fragment fragment = new LiveConsumption();
            MainActivity.setListener((MainActivity.LiveEventInterface) fragment);
            loadFragment(fragment,"LiveConsumption");
        }

    }
    private void loadFragment(Fragment fragment,String fragmentTag){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,fragment,fragmentTag);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
