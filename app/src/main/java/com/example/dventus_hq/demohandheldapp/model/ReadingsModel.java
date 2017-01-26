package com.example.dventus_hq.demohandheldapp.model;

/**
 * Created by dVentus-hq on 9/1/2017.
 */
public class ReadingsModel {
    private String Date;
    private boolean viewed;
    public static final ReadingsModel[] readings = {
            new ReadingsModel("02/01/17",true)
    };
    public ReadingsModel(String dateParam, boolean viewedParam) {
        Date = dateParam;
        this.viewed = viewedParam;
    }

    public String getDate() {
        return this.Date;
    }

    public boolean isViewed() {
        return this.viewed;
    }
}
