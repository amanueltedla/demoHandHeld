package com.example.dventus_hq.demohandheldapp;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dventus_hq.demohandheldapp.Database_and_File_Managment.HandheldDatabaseHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class lineChartView extends Fragment {
    private LineChart lineChart;
    private SQLiteDatabase db;
    private HandheldDatabaseHelper dbHandler;

    public lineChartView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_line_chart_view, container, false);
        try {
            dbHandler = new HandheldDatabaseHelper(getActivity());

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        // Get the input and output streams, using temp objects because
        db = dbHandler.getWritableDatabase();
        getConsumptionData();
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
        entries.add(new Entry(3, 6));
        LineDataSet dataSet = new LineDataSet(entries, "Consumption(Liter) vs Time");
        dataSet.setDrawCircles(true);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.GREEN);
        dataSet.setFillAlpha(100);
        dataSet.setColor(Color.parseColor("#388E3C"));
        dataSet.setValueTextColor(Color.BLACK);
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        // and many more...
        lineChart.invalidate(); // refresh
        return rootView;
    }

    public void updateChart() {
        lineChart.invalidate();
    }

    private void getConsumptionData() {
        Cursor cursor = dbHandler.loadConsumption(db);
        List<Integer> consumptionList = new ArrayList();
        if (cursor.moveToFirst()) {
            consumptionList.add(cursor.getInt(0));
        }
        while (cursor.moveToNext()) ;
    }
}


