package com.example.dventus_hq.demohandheldapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dventus_hq.demohandheldapp.Bluetooth.ConnectThread;
import com.example.dventus_hq.demohandheldapp.Bluetooth.ConnectedThread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    private DrawerLayout drawerLayout;
    private String[] titles;
    private ListView drawerList;
    private RelativeLayout drawerRelativeLayout;
    private ListView connectionList;
    ArrayAdapter<String> listAdapter;
    public Dialog dialog;
    private ProgressDialog statusDialog;
    BluetoothDevice selectedDevice;
    ArrayList<BluetoothDevice> bluetoothDevices;
    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> pairedBD;
    ArrayList<String> listBD;
    BroadcastReceiver mReceiver;
    private static LiveEventInterface listener;

    public interface LiveEventInterface {
        void Update();
    }

    public static void setListener(LiveEventInterface listener) {
        MainActivity.listener = listener;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//Code to run when an item in the navigation drawer gets clicked
            selectItem(position);
        }
    }

    public void blueFunction() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {

            Toast.makeText(getApplicationContext(), "Your mobile don't support bluetooth", Toast.LENGTH_SHORT).show();

        } else {

            if (mBluetoothAdapter.isEnabled()) {
                pairedDevices();

            }

            if (!mBluetoothAdapter.isEnabled()) {

                //Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }
        }
    }

    public void pairedDevices() {

        pairedBD = mBluetoothAdapter.getBondedDevices();

        if (pairedBD.size() > 0) {

            listBD = new ArrayList<>();

            for (BluetoothDevice BD : pairedBD) {
                bluetoothDevices.add(BD);
                listBD.add(BD.getName() + "\n" + BD.getAddress());
            }

            listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listBD);
            connectionList.setAdapter(listAdapter);
        }

        scanning();
    }

    private void startDiscovery() {

        mBluetoothAdapter.cancelDiscovery();
        mBluetoothAdapter.startDiscovery();

    }

    public void scanning() {
        startDiscovery();

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                    BluetoothDevice bluetoothDeviceFound = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (!listBD.contains(bluetoothDeviceFound.getName() + "\n" + bluetoothDeviceFound.getAddress())) {

                        listBD.add(bluetoothDeviceFound.getName() + "\n" + bluetoothDeviceFound.getAddress());
                        listAdapter.notifyDataSetChanged();
                        bluetoothDevices.add(bluetoothDeviceFound);
                    }
                } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                    //Device is now connected
                    Log.d("", "Device is now connected");


                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    //Done searching
                    Log.d("", "Done searching");

                } else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {
                    //Device is about to disconnect
                    Log.d("", "Device is about to disconnect");
                } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                    //Device has disconnected
                    Log.d("", "Device has disconnected");
                    statusDialog.dismiss();
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(mReceiver, filter1);
        registerReceiver(mReceiver, filter2);
        registerReceiver(mReceiver, filter3);
        registerReceiver(mReceiver, filter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        selectedDevice = bluetoothDevices.get(position);
        if (selectedDevice != null) ;
        {
            dialog.dismiss();
            statusDialog.setMessage("Connecting with " + selectedDevice.getName());
            statusDialog.setIndeterminate(false);
            statusDialog.setCancelable(true);
            statusDialog.show();
            ConnectThread connect2 = new ConnectThread(selectedDevice, mBluetoothAdapter, mHandler, getApplicationContext());
            connect2.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerRelativeLayout = (RelativeLayout) findViewById(R.id.left_drawer);
//Populate the ListView
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState == null) {
            selectItem(0);
        }
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(android.R.color.transparent);
        bluetoothDevices = new ArrayList<BluetoothDevice>();
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.devicelist);
        dialog.setTitle("List of Devices");
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //do whatever you want the back key to do
                //eblueFunction();
            }
        });
        statusDialog = new ProgressDialog(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    private void selectItem(int position) {
// update the main content by replacing fragments
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new LiveConsumption();
                setListener((LiveEventInterface) fragment);
                break;
            case 2:
                fragment = new Readings();
                break;
            case 3:
                fragment = new Setting();
                break;
            default:
                fragment = new Home();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
//Set the action bar title
        setActionBarTitle(position);
//Close drawer
        drawerLayout.closeDrawer(drawerRelativeLayout);
    }

    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }


    public void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.blueOn) {
            connectionList = (ListView) dialog.findViewById(R.id.deviceList);
            ProgressBar bar = (ProgressBar) dialog.findViewById(R.id.bar);
            try {
                dialog.show();
            } catch (Exception e) {

            }
            try {
                blueFunction();
            } catch (Exception e) {

            }

            connectionList.setOnItemClickListener(this);


        }
        return super.onOptionsItemSelected(item);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Context context = getApplicationContext();
            switch (msg.what) {

                case 0:
                    if (null != context) {
                        statusDialog.dismiss();
                        ConnectedThread receiveMessage = new ConnectedThread(getApplicationContext(), mHandler, "newData" + ".txt");
                        receiveMessage.start();
                    }
                    break;
                case 1: {
//                    Toast.makeText(getApplicationContext(), msg.getData().getString("Data"),
//                            Toast.LENGTH_SHORT).show();
                    if (getFragmentManager().findFragmentById(R.id.content_frame).getTag() != null && getFragmentManager().findFragmentById(R.id.content_frame).getTag().equals("LiveConsumption")) {
                        listener.Update();
                    }
                    break;
                }

            }
        }
    };
}
