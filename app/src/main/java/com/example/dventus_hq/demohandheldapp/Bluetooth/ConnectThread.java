package com.example.dventus_hq.demohandheldapp.Bluetooth;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by AManuel on 7/23/2016.
 */

public class ConnectThread extends Thread {



    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    ConnectedThread receiveMessage;
    BluetoothAdapter bluetoothAdapter;
    private Handler mHandler;
    Context mcontext;
    TextView blue_status,valueFrom;
    Dialog dialog;
    private static final int success_connect = 0;
    private boolean isconnected;

    public ConnectThread()
    {

    }

    public static BluetoothSocket getMmSocket() {
        return mmSocket;
    }

    public ConnectThread(BluetoothDevice device, BluetoothAdapter bluetoothAdapter, Handler handler, Context context)
    {

        BluetoothSocket tmp = null;
        mmDevice = device;
        this.bluetoothAdapter = bluetoothAdapter;
        mHandler = handler;
        mcontext = context;
        isconnected = false;



        try {

            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);

        } catch (Exception e) { }

        mmSocket = tmp;
    }

    public boolean isconnected() {
        return isconnected;
    }

    @Override
    public void run() {
        // Cancel discovery because it will slow down the connection
        bluetoothAdapter.cancelDiscovery();

        try {
            if(Thread.interrupted())
                 return;
            mmSocket.connect();
            isconnected = true;


            if(mmSocket.isConnected()) {
                Message msg = mHandler.obtainMessage(0);
                Bundle bundle = new Bundle();
                bundle.putString("BluetoothMessage","Connected");
                msg.setData(bundle);
                mHandler.sendMessage(msg);
                return;
            }

        } catch (Exception connectException) {

                isconnected = false;

            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

    }

    public void stopConnection() throws Exception {
            mmSocket = null;
    }
}
