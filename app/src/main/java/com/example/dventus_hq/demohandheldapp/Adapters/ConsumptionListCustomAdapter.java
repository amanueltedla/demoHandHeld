package com.example.dventus_hq.demohandheldapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dventus_hq.demohandheldapp.R;

/**
 * Created by dVentus-hq on 10/1/2017.
 */
public class ConsumptionListCustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] consumptions;
    private final String[] consumptionTimes;
    public ConsumptionListCustomAdapter(Context contextParam,String[] consumptionsParam,String[] consumptionTimesParam){
        super(contextParam, R.layout.conusmption_list_custom_view,consumptionsParam);
        this.context = contextParam;
        this.consumptions = consumptionsParam;
        this.consumptionTimes = consumptionTimesParam;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.conusmption_list_custom_view,parent,false);
        TextView consumption = (TextView) rootView.findViewById(R.id.consumptionText);
        consumption.setText(consumptions[position]);
        TextView consumptionTimeView = (TextView) rootView.findViewById(R.id.consumptionTimeText);
        consumptionTimeView.setText(consumptionTimes[position]);
        return rootView;
    }
}
