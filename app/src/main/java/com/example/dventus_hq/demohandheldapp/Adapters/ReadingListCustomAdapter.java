package com.example.dventus_hq.demohandheldapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dventus_hq.demohandheldapp.R;

/**
 * Created by dVentus-hq on 9/1/2017.
 */
public class ReadingListCustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] dates;
    private final boolean[] viewStatus;
    public ReadingListCustomAdapter(Context contextParam,String [] datesParam,boolean[] viewStatusParam){
        super(contextParam, R.layout.reading_list_custom_view,datesParam);
        this.context = contextParam;
        this.dates = datesParam;
        this.viewStatus = viewStatusParam;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.reading_list_custom_view,parent,false);
        TextView dateText = (TextView) rootView.findViewById(R.id.readingListdate);
        TextView statusText = (TextView) rootView.findViewById(R.id.viewStatusText);
        dateText.setText(dates[position]);
        if(viewStatus[position]){
            statusText.setVisibility(View.GONE);
        }
        else{
            statusText.setVisibility(View.VISIBLE);
        }
        return rootView;
    }
}
