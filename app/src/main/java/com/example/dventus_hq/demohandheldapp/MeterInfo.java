package com.example.dventus_hq.demohandheldapp;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.dventus_hq.demohandheldapp.fileActions.FileReader;
import com.example.dventus_hq.demohandheldapp.parameters.MeterData;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeterInfo extends Fragment {
    private EditText meterIdText;
    private EditText clockText;
    private TextView powerLevelText;
    private static final  SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");

    public MeterInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_meter_info, container, false);
        RoundCornerProgressBar progress1 = (RoundCornerProgressBar) rootView.findViewById(R.id.progress_1);
        meterIdText = (EditText) rootView.findViewById(R.id.meterIdText);
        powerLevelText = (TextView) rootView.findViewById(R.id.powerLevelText);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/digital-7.ttf");
        clockText = (EditText) rootView.findViewById(R.id.clockText);
        clockText.setTypeface(tf);
        progress1.setProgressColor(Color.parseColor("#4CAF50"));
        progress1.setProgressBackgroundColor(Color.parseColor("#F5F5F5"));
        progress1.setMax(100);
        MeterData meterData = this.getMeterModel();
        meterIdText.setText(meterData.getMeterID());
        clockText.setText(formatter.format(FileReader.getTimeStamp(meterData.getEvents().getSysTime()).toDate()));
        powerLevelText.setText("" + meterData.getEvents().getBatLevel().getLevel() + "%");
        progress1.setProgress(meterData.getEvents().getBatLevel().getLevel());
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle("Meter Info");

    }

    private MeterData getMeterModel() {
        FileReader fileReader = new FileReader();
        String path = Environment.getExternalStorageDirectory().toString() + "/" + "MeterData";
        File f = new File(path);
        File[] file = f.listFiles();
        //InputStream file = getResources().openRawResource(R.raw.data);
        final List<String[]> fileList = fileReader.ReadFile(file[0]);
        System.out.println(" The size of the list is :" + fileList.size());

        System.out.println("Processing the single packets");
         /*for(final String[] singlePacket: fileList){

			 fR.ProcessFrames(singlePacket);
		 }*/
        MeterData meterDataFinal = new MeterData();
        MeterData meterDataTemp;
        //List<MeterData> meterDatas =  new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            meterDataTemp = fileReader.ProcessFrames(fileList.get(i));
            if (meterDataTemp.getConsumption() != null) {
                meterDataFinal.setConsumption(meterDataTemp.getConsumption());
                meterDataFinal.setMeterID(meterDataTemp.getMeterID());
            } else if (meterDataTemp.getEvents() != null) {
                meterDataFinal.setEvents(meterDataTemp.getEvents());
                meterDataFinal.setMeterID(meterDataTemp.getMeterID());
            }
        }
        System.out.println("File processed...");
        return meterDataFinal;
    }
}
