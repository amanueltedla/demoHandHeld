package com.example.dventus_hq.demohandheldapp.Bluetooth;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dventus_hq.demohandheldapp.Database_and_File_Managment.HandheldDatabaseHelper;
import com.example.dventus_hq.demohandheldapp.Database_and_File_Managment.MeterFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by AManuel on 7/23/2016.
 */
public class ConnectedThread extends Thread {

    private static final int MESSAGE_READ = 1;
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private HandheldDatabaseHelper dbHandler;
    private Handler mHandler;
    private String mFilename;
    MeterFile mfile;
    Context mContext;
    SQLiteDatabase db;
    TextView valueFrom;


    public ConnectedThread(Context context, String filename) {
        mmSocket = ConnectThread.mmSocket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        mContext = context;
        mfile = new MeterFile(mContext);
        // mHandler = handler;
        mFilename = filename;

        //this.mContext = mContext;
        //this.valueFrom = valueFrom;

        try {
            dbHandler = new HandheldDatabaseHelper(mContext);

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(mContext, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        // Get the input and output streams, using temp objects because
        db = dbHandler.getWritableDatabase();
        // member streams are final
        try {

            tmpIn = mmSocket.getInputStream();
            tmpOut = mmSocket.getOutputStream();

        } catch (Exception e) {
        }
        mmInStream = tmpIn;
        mmOutStream = tmpOut;
        //this.run();
    }

    @Override
    public void run() {
        //byte[] buffer = new byte[9000];  // buffer store for the stream
        //int bytes; // bytes returned from read()
        // Keep listening to the InputStream until an exception occurs
        int ifavailable;
        boolean filerecieved = true;
        while (!Thread.interrupted()) {
            try {
                //String checkup2 = mmDevice.;
                //Log.d("",checkup2);
                filerecieved = true;
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                mFilename = df.format(Calendar.getInstance().getTime());
                ifavailable = mmSocket.getInputStream().available();
                // Read from the InputStream
                //byte[] packet = {1,2,3,4,4};
                //this.write(packet);
                Parser parser = new Parser();
                if (ifavailable > 0) {
                    byte[] paketByte = new byte[ifavailable];
                    mmInStream.read(paketByte);
                    StringBuilder sb = new StringBuilder();
                    parser.setConsumption(paketByte.length);
                    parser.setFlowRate(3);
                    parser.setEvent("dfdfd");
                    //parser.parseFrame(paketByte);
                    Thread.sleep(500);
                    dbHandler.insertMeterConsumption(db, parser.getConsumption(), parser.getFlowRate(),parser.getEvent());
                    for (byte b : paketByte) {
                        sb.append(String.format("%02X ", b));
                    }
                    String sbb = sb.toString();
                    // for(int i=0;i <=10000;i++);
                    mfile.addStreamData(sbb, mFilename);// generate file with file name mFilename
                    if (filerecieved) {
                        dbHandler.insertMeterFile(db, mFilename, "Unsent", "Generated");
                        filerecieved = false;
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        Log.d("", "Interupted");
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (Exception e) {
        }
    }


//    Handler mHandler = new Handler(){
//
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            switch (msg.what){
//
//                case MESSAGE_READ:
//
//                    byte[] readBuf = (byte[]) msg.obj;
//                   // String readMessage = new String(readBuf, 0, msg.arg1);
//                    //Toast.makeText(getApplicationContext(), readMessage , Toast.LENGTH_LONG).show();
//                    //String string = new String(readBuf);
//                   char afe = (char) readBuf[0];
//                    // Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
//                   // valueFrom.setText("" + afe);
//                    //Toast.makeText(mContext,readMessage,Toast.LENGTH_LONG).show();
//                    break;
//            }
//        }
//    };

}
