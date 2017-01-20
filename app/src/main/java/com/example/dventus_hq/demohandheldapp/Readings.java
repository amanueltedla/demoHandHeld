package com.example.dventus_hq.demohandheldapp;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dventus_hq.demohandheldapp.Adapters.ReadingListCustomAdapter;
import com.example.dventus_hq.demohandheldapp.model.ReadingsModel;

import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class Readings extends Fragment {
private ListView readingLists;

    public Readings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_readings, container, false);
        readingLists = (ListView) rootView.findViewById(R.id.readingList);
        String[] readingDates = new String[ReadingsModel.readings.length];
        boolean[] viewStatus = new boolean[ReadingsModel.readings.length];
        for (int i = 0; i < readingDates.length; i++) {
            readingDates[i] = ReadingsModel.readings[i].getDate();
            viewStatus[i] = ReadingsModel.readings[i].isViewed();
        }
        ReadingListCustomAdapter readingListCustomAdapter = new ReadingListCustomAdapter(getActivity(),readingDates,viewStatus);
        readingLists.setAdapter(readingListCustomAdapter);
        readingLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        Fragment fragment = new singleReading();
        loadFragment(fragment);
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
