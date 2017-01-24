package com.example.dventus_hq.demohandheldapp.Bluetooth;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.dventus_hq.demohandheldapp.Database_and_File_Managment.HandheldDatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Aman on 1/20/2017.
 */
public class Parser {
    private long consumption;
    private int flowRate;
    private String event;
    private String meterId;

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    private ArrayList<Byte> incomingData;

    public Parser() {
        incomingData = new ArrayList();
    }

    public long getConsumption() {
        return consumption;
    }

    public void setConsumption(long consumption) {
        this.consumption = consumption;
    }

    public long getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(int flowRate) {
        this.flowRate = flowRate;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
    public void parseFrame(byte[] frame,HandheldDatabaseHelper dbHandler,SQLiteDatabase db,Handler mHandler){
            for(byte data:frame){
                incomingData.add(data);
            }

        if(incomingData.size() >= 20)
        {
            StringBuilder meterIDBuilder = new StringBuilder();
            for (int i = 3; i < 11; i++) {
                meterIDBuilder.append(String.format("%02X", incomingData.get(i)));
            }
            setMeterId(meterIDBuilder.toString());
            StringBuilder consumptionBuilder = new StringBuilder();
            for (int i = 11; i < 15; i++) {
                consumptionBuilder.append(String.format("%02X", incomingData.get(i)));
            }
            setConsumption(Long.parseLong(consumptionBuilder.toString(), 16));
            StringBuilder flowRateBuilder = new StringBuilder();
            for (int i = 15; i < 17; i++) {
                flowRateBuilder.append(String.format("%02X", incomingData.get(i)));
            }
            setFlowRate(Integer.parseInt(flowRateBuilder.toString(), 16));
            StringBuilder EventBuilder = new StringBuilder();
            for (int i = 17; i < 19; i++) {
                EventBuilder.append(String.format("%02X", incomingData.get(i)));
            }
            setEvent(EventBuilder.toString());
            dbHandler.insertMeterConsumption(db,getConsumption(),getFlowRate(),getEvent(),getMeterId());
            Message msg = mHandler.obtainMessage(1);
            Bundle bundle = new Bundle();
            bundle.putString("Data","Update");
            msg.setData(bundle);
            mHandler.sendMessage(msg);
            incomingData.clear();
        }


    }
}
