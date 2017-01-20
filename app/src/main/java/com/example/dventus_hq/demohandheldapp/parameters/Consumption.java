package com.example.dventus_hq.demohandheldapp.parameters;

/**
 * Created by dVentus-hq on 20/1/2017.
 */
public class Consumption {
    private Long startTime;
    private Long endTime;
    private Double consumption;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }
}
