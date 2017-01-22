package com.example.dventus_hq.demohandheldapp.Bluetooth;

import java.util.ArrayList;

/**
 * Created by Aman on 1/20/2017.
 */
public class Parser {
    private int consumption;
    private int flowRate;
    private String event;
    private ArrayList<Byte> incomingData;

    public Parser() {
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public int getFlowRate() {
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
    public void parseFrame(byte[] frame){

        if(frame.length < 18){
            for(byte data:frame){
                incomingData.add(data);
            }
        }
        if(incomingData.size() == 18)
        {
            StringBuilder consumptionBuilder = new StringBuilder();
            for (int i = 11; i < 15; i++) {
                consumptionBuilder.append(String.format("%02X", incomingData.get(i)));
            }
            setConsumption(Integer.parseInt(consumptionBuilder.toString(), 16));
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
            incomingData.clear();
        }
    }
}
