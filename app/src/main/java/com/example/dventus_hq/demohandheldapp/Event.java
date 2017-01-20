package com.example.dventus_hq.demohandheldapp;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.dventus_hq.demohandheldapp.Adapters.ConsumptionListCustomAdapter;
import com.example.dventus_hq.demohandheldapp.fileActions.FileReader;
import com.example.dventus_hq.demohandheldapp.model.ConsumptionModel;
import com.example.dventus_hq.demohandheldapp.parameters.MeterData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Event extends Fragment {
private ListView eventList;

    public Event() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        eventList = (ListView)rootView.findViewById(R.id.eventList);
        List<ConsumptionModel> eventLists = new ArrayList<>();
        MeterData eventData =  getMeterModel();
        if(eventData.getEvents().getBatLowEvent() != null){
           String batLevel = "Battery Low (" + eventData.getEvents().getBatLowEvent().getLevel() + "%)";
           String time =  eventData.getEvents().getBatLowEvent().getMeasureTime();
            //ConsumptionModel batLow = new ConsumptionModel();
        }
        String[] events = new String[ConsumptionModel.events.length];
        String[] eventTimeValues = new String[ConsumptionModel.events.length];
        for (int i = 0; i < events.length; i++) {
            events[i] = ConsumptionModel.events[i].getConsumption();
            eventTimeValues[i] = ConsumptionModel.events[i].getTime();
        }
        ConsumptionListCustomAdapter consumptionListCustomAdapter = new ConsumptionListCustomAdapter(getActivity(),events,eventTimeValues);
        eventList.setAdapter(consumptionListCustomAdapter);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle("Events");

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
