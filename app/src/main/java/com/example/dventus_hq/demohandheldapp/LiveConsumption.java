package com.example.dventus_hq.demohandheldapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dventus_hq.demohandheldapp.Database_and_File_Managment.HandheldDatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiveConsumption extends Fragment implements MainActivity.LiveEventInterface, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private TextView flowLS;
    //private TextView flowGpm;
    private TextView consumptionLiter;
    //private TextView consumptionGallon;
    private LineChart lineChart;
    private SQLiteDatabase db;
    private HandheldDatabaseHelper dbHandler;
    private LineDataSet dataSet;
    private LineData lineData;
    private FrameLayout reverseFrame;
    private FrameLayout leakFrame;
    private FrameLayout contaminationFrame;
    private FrameLayout magTamperFrame;
    private FrameLayout coverFrame;
    private FrameLayout lowPowerFrame;
    private Spinner meterChooser;
    private RadioGroup meterChoice;
    private RadioButton literUnit;
    private RadioButton gallonUnit;
    private TextView flowRateUnitText;
    private TextView consumptionUnitText;
    private TextView consumptionGraphUnitText;
    private String Unit;

    public String getSelectedMeter() {
        return selectedMeter;
    }

    public void setSelectedMeter(String selectedMeter) {
        this.selectedMeter = selectedMeter;
    }

    private String selectedMeter;

    public LiveConsumption() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_check, container, false);
        meterChoice = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        literUnit = (RadioButton) rootView.findViewById(R.id.radioButton1);
        flowRateUnitText = (TextView) rootView.findViewById(R.id.literPerMinuteUnitText);
        consumptionUnitText = (TextView) rootView.findViewById(R.id.literUnitText);
        consumptionGraphUnitText = (TextView) rootView.findViewById(R.id.consumptionGraphUnit);
        literUnit.setSelected(true);
        Unit = "liter";
        literUnit.setOnClickListener(this);
        gallonUnit = (RadioButton) rootView.findViewById(R.id.radioButton2);
        gallonUnit.setOnClickListener(this);
        meterChooser = (Spinner) rootView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.MeterIDArray, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meterChooser.setOnItemSelectedListener(this);
// Apply the adapter to the spinner
        meterChooser.setAdapter(adapter);
        reverseFrame = (FrameLayout) rootView.findViewById(R.id.reverseFrame);
        leakFrame = (FrameLayout) rootView.findViewById(R.id.leakFrame);
        contaminationFrame = (FrameLayout) rootView.findViewById(R.id.contaminationFrame);
        coverFrame = (FrameLayout) rootView.findViewById(R.id.coverFrame);
        lowPowerFrame = (FrameLayout) rootView.findViewById(R.id.lowPowerFrame);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/digital-7.ttf");
        try {
            dbHandler = new HandheldDatabaseHelper(getActivity());

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        setSelectedMeter("0000000000000001");
        // Get the input and output streams, using temp objects because
        db = dbHandler.getWritableDatabase();
        flowLS = (TextView) rootView.findViewById(R.id.flowLS);
        flowLS.setTextSize(40);
        flowLS.setTypeface(tf);
        //flowGpm = (TextView) rootView.findViewById(R.id.flowGpm);
        //flowGpm.setTextSize(40);
        //flowGpm.setTypeface(tf);
        consumptionLiter = (TextView) rootView.findViewById(R.id.consumptionLiters);
        consumptionLiter.setTextSize(40);
        consumptionLiter.setTypeface(tf);
        //consumptionGallon = (TextView) rootView.findViewById(R.id.consumptionGallons);
        //consumptionGallon.setTextSize(40);
        //consumptionGallon.setTypeface(tf);
        lineChart = (LineChart) rootView.findViewById(R.id.chart);
        lineChart.animateY(2000);
        // lineChart.animateX(3000);
        //lineChart.animateXY(2000,3000);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(13f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setTextSize(13f); // set the text size
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawGridLines(false);
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0));
        dataSet = new LineDataSet(entries, "Consumption(Liter)");
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#29B6F6"));
        dataSet.setFillAlpha(100);
        dataSet.setColor(Color.parseColor("#01579B"));
        dataSet.setValueTextColor(Color.BLACK);
        Update();
        return rootView;
    }

    @Override
    public void Update() {
        Cursor cursor = dbHandler.loadConsumption(db, getSelectedMeter());
        List<Integer> consumptionList = new ArrayList();
        if (cursor.moveToFirst()) {
            for (int k = 0; k < cursor.getCount(); k++) {
                consumptionList.add(cursor.getInt(0));
                if (k == cursor.getCount() - 1) {
                    setConsumptionValue(cursor.getInt(0));
                    setFlowRateValue(cursor.getInt(1));
                    setEventValue(cursor.getString(2));
                }
                cursor.moveToNext();
            }
        } else {
            setConsumptionValue(0);
            setFlowRateValue(0);
            setEventValue("0000");
        }
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0));
        for (int i = 0; i < consumptionList.size(); i++) {
            if(Unit.equals("liter")) {
                entries.add(new Entry(i + 2, (consumptionList.get(i))));
            }
            else{
                entries.add(new Entry(i + 2,(float)convertFromLiterToGallon(consumptionList.get(i))));
            }

        }
        dataSet.setValues(entries);
        lineChart.invalidate();
        lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    @Override
    public void onClick(View view) {
        if(literUnit.isChecked()){
            Unit = "liter";
        }
        else
        {
            Unit = "gallon";
        }
        changeUnit();
        Update();
    }
  private void changeUnit(){
      if(Unit.equals("liter")){
          flowRateUnitText.setText("LPM");
          consumptionUnitText.setText("Liters");
          consumptionGraphUnitText.setText("Consumption(Liter)");
      }
      else
      {
          flowRateUnitText.setText("GPM");
          consumptionUnitText.setText("Gallons");
          consumptionGraphUnitText.setText("Consumption(Gallon)");
      }
  }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        if (pos == 0) {
            setSelectedMeter("0000000000000001");
        } else if (pos == 1) {
            setSelectedMeter("0000000000000102");
        }
        Update();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void setConsumptionValue(long anInt) {
        if(Unit.equals("liter")) {
            consumptionLiter.setText("" + anInt);
        }
        else {
            double consGallon = convertFromLiterToGallon(anInt);
            DecimalFormat decimalFormat = new DecimalFormat("#.###");
            decimalFormat.setRoundingMode(RoundingMode.CEILING);
            consumptionLiter.setText(decimalFormat.format(consGallon));
        }

    }

    private void setFlowRateValue(long flowRateValue) {
        if(Unit.equals("liter")) {
            flowLS.setText("" + (flowRateValue / 100));
        }
        else {
            double flowGallon = convertFromLiterToGallon(flowRateValue / 100);
            DecimalFormat decimalFormat = new DecimalFormat("#.###");
            decimalFormat.setRoundingMode(RoundingMode.CEILING);
            flowLS.setText(decimalFormat.format(flowGallon));
        }
    }

    private void setEventValue(String eventValue) {
        if (eventValue.equals("0001")) {
            reverseFrame.setBackgroundColor(Color.parseColor("#F44336"));
            contaminationFrame.setBackgroundColor(Color.parseColor("#BDBDBD"));
        } else if (eventValue.equals("0002")) {
            contaminationFrame.setBackgroundColor(Color.parseColor("#F44336"));
            reverseFrame.setBackgroundColor(Color.parseColor("#BDBDBD"));
        } else if (eventValue.equals("0003")) {
            contaminationFrame.setBackgroundColor(Color.parseColor("#F44336"));
            reverseFrame.setBackgroundColor(Color.parseColor("#F44336"));
        } else {
            contaminationFrame.setBackgroundColor(Color.parseColor("#BDBDBD"));
            reverseFrame.setBackgroundColor(Color.parseColor("#BDBDBD"));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setTitle("");
    }

    private double convertFromLiterToGallon(double liter) {
        return liter * 3.7853;
    }
}
