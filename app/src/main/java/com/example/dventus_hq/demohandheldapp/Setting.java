package com.example.dventus_hq.demohandheldapp;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.dventus_hq.demohandheldapp.Database_and_File_Managment.HandheldDatabaseHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class Setting extends Fragment  implements View.OnClickListener {
    private Switch connectivity;
    private SQLiteDatabase db;
    private HandheldDatabaseHelper dbHandler;
    private Button clearDatabase;
    public Setting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        try {
            dbHandler = new HandheldDatabaseHelper(getActivity());

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        // Get the input and output streams, using temp objects because
        db = dbHandler.getWritableDatabase();
        connectivity = (Switch)rootView.findViewById(R.id.switch1);
        clearDatabase = (Button) rootView.findViewById(R.id.clearButton);
        clearDatabase.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.clearButton){
            dbHandler.clearDatabase(db);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle("Settings");
    }
}
