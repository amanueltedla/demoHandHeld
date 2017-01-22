package com.example.dventus_hq.demohandheldapp;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiveConsumption extends Fragment implements MainActivity.LiveEventInterface {
    private TextView flowLS;
    private TextView flowGpm;
    private TextView consumptionLiter;
    private TextView consumptionGallon;
    private LineChart lineChart;

    public LiveConsumption() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_live_consumption, container, false);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/digital-7.ttf");
         flowLS = (TextView) rootView.findViewById(R.id.flowLS);
        flowLS.setText("0.573");
        flowLS.setTextSize(40);
        flowLS.setTypeface(tf);
        flowGpm = (TextView)rootView.findViewById(R.id.flowGpm);
        flowGpm.setTextSize(40);
        flowGpm.setTypeface(tf);
        flowGpm.setText("9.082");
        consumptionLiter = (TextView) rootView.findViewById(R.id.consumptionLiters);
        consumptionLiter.setTextSize(40);
        consumptionLiter.setTypeface(tf);
        consumptionLiter.setText("300");
        consumptionGallon = (TextView) rootView.findViewById(R.id.consumptionGallons);
        consumptionGallon.setTextSize(40);
        consumptionGallon.setTypeface(tf);
        consumptionGallon.setText("79.251");
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
        entries.add(new Entry(1, 2));
        entries.add(new Entry(2, 4));
        entries.add(new Entry(3, 100));
        LineDataSet dataSet = new LineDataSet(entries, "Consumption(Liter) vs Time");
        dataSet.setDrawCircles(true);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.GREEN);
        dataSet.setFillAlpha(100);
        dataSet.setColor(Color.parseColor("#388E3C"));
        dataSet.setValueTextColor(Color.BLACK);
        LineData lineData = new LineData(dataSet);
        lineChart.setPinchZoom(false);
        lineChart.setData(lineData);
        // and many more...
        lineChart.invalidate(); // refresh
        return rootView;
    }
    @Override
    public void Update() {
        consumptionGallon.setText("100");
    }

}
