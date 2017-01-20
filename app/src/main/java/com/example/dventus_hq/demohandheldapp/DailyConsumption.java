package com.example.dventus_hq.demohandheldapp;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.dventus_hq.demohandheldapp.Adapters.ConsumptionListCustomAdapter;
import com.example.dventus_hq.demohandheldapp.fileActions.FileReader;
import com.example.dventus_hq.demohandheldapp.model.ConsumptionModel;
import com.example.dventus_hq.demohandheldapp.model.ReadingsModel;
import com.example.dventus_hq.demohandheldapp.parameters.MeterData;

import java.io.File;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyConsumption extends Fragment {
private ListView consumptionList;
private TableLayout table;
    public DailyConsumption() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_daily_consumption2, container, false);
        table = (TableLayout) rootView.findViewById(R.id.consumptionTable);
        TableRow row = (TableRow)LayoutInflater.from(getActivity()).inflate(R.layout.attrib_row, null);
        ((TextView)row.findViewById(R.id.startTime)).setText("StartTime");
        ((TextView)row.findViewById(R.id.endTime)).setText("EndTime");
        ((TextView)row.findViewById(R.id.consumption)).setText("Consumption(Liter)");
        table.addView(row);
        MeterData consumptionData = getMeterModel();
        for(int i: consumptionData.getConsumption().getDailyCons().getDailyCons()) {
            TableRow row2 = (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.consumption_data_rows, null);
            ((TextView) row2.findViewById(R.id.startTime)).setText("12/04/2009");
            ((TextView) row2.findViewById(R.id.endTime)).setText("13/04/2009");
            ((TextView) row2.findViewById(R.id.consumption)).setText("" + i);
            table.addView(row2);
        }
        table.requestLayout();
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setTitle("Consumption");
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
