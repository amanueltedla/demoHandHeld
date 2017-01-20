package com.example.dventus_hq.demohandheldapp;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.dventus_hq.demohandheldapp.fileActions.FileReader;
import com.example.dventus_hq.demohandheldapp.parameters.MeterData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class singleReading extends Fragment {
    private ListView readingList;
    private String[] readingCategories;

    public singleReading() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_single_reading, container, false);
        readingList = (ListView)rootView.findViewById(R.id.singleReadingList);
        readingCategories = getActivity().getResources().getStringArray(R.array.ReadingCategories);
        readingList.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1, readingCategories));
        readingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                openFragment(position);
            }
        });
        return rootView;
    }
    private void openFragment(int position)
    {
        if(position == 0){
            Fragment fragment = new MeterInfo();
            loadFragment(fragment);
        }
        else if(position == 1){
            Fragment fragment = new DailyConsumption();
            loadFragment(fragment);
        }
        else if(position == 2){
            Fragment fragment = new Event();
            loadFragment(fragment);
        }
    }
    private void loadFragment(Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame,fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle("Readings");
    }
}
