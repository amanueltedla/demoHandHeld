package com.example.dventus_hq.demohandheldapp.Database_and_File_Managment;

import java.util.ArrayList;

/**
 * Created by dVentus-hq on 25/8/2016.
 */
public class Data_Parser {
    private byte[] incomingData = {0,22,24,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,29,77};
    public static ArrayList<byte[]> byteArrayList;
    private byte[] outgoingData;
    private String inLength;
    private String inMessageID;
    private String inPayload;
    private String inMeterAddress;
    private String inAcknowlege;
    private String inChecksum;
    private String outLength;
    private String outMessageID;
    private String outPayload;
    private String outMeterAddress;
    private String outAcknowlege;
    private String outChecksum;
    public Boolean dataToBeSent;

    public Boolean getDataToBeSent() {
        return dataToBeSent;
    }

    public Data_Parser() {

    }
    public void incomingMessage(byte[] bytes)
    {
        dataToBeSent = true;
    }

    public byte[] getOutgoingData() {
        return outgoingData;
    }
}
