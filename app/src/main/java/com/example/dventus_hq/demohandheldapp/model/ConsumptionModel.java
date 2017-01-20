package com.example.dventus_hq.demohandheldapp.model;

/**
 * Created by dVentus-hq on 10/1/2017.
 */
public class ConsumptionModel {
    private String consumption;
    private String time;
    public static final ConsumptionModel[] consumptions = {
           new ConsumptionModel("Consumption: 93.0","12/01/01 09:30 - 12/01/01 09:31"),
            new ConsumptionModel("Consumption: 75.0","12/01/01 09:40 - 12/01/01 09:41"),
            new ConsumptionModel("Consumption: 330.0","12/01/01 10:01 - 12/01/01 10:02"),
            new ConsumptionModel("Consumption: 114.0","12/01/01 10:23 - 12/01/01 10:30"),
            new ConsumptionModel("Consumption: 78.0","12/01/01 11:43 - 12/01/01 11:45"),
            new ConsumptionModel("Consumption: 130.0","12/01/01 11:50 - 12/01/01 11:52"),

    };
    public static final ConsumptionModel[] events = {
            new ConsumptionModel("Remove Cover","12/01/01 09:30"),
            new ConsumptionModel("Remove Cover","12/01/01 09:33"),
            new ConsumptionModel("Remove Cover","12/01/01 10:01"),
            new ConsumptionModel("Leak","12/01/01 10:23 - 12/01/01 10:30"),
            new ConsumptionModel("Magnetic Tampering","12/01/01 11:43 - 12/01/01 11:45"),
            new ConsumptionModel("Magnetic Tampering","12/01/01 11:50 - 12/01/01 11:52"),

    };

    public String getConsumption() {
        return consumption;
    }

    public String getTime() {
        return time;
    }

    public ConsumptionModel(String consumption, String time) {
        this.consumption = consumption;
        this.time = time;
    }
}
