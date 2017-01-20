package com.example.dventus_hq.demohandheldapp.parameters;

public class MeterData {

	private String meterID;
	
	private ConsumptionData consumption;
	private EventData events;
	
public String ParseTamper(String[]frame, int tamperIndex)
{
	
String	arr1 = frame[tamperIndex - 7 ] + frame[tamperIndex - 6 ]
			+ frame[tamperIndex - 5 ] + frame[tamperIndex - 4 ]
			+ frame[tamperIndex - 3 ] + frame[tamperIndex - 2 ]
			+ frame[tamperIndex - 1 ];
return arr1;
}

public String getMeterID() {
	return meterID;
}

public void setMeterID(String meterID) {
	this.meterID = meterID;
}

public ConsumptionData getConsumption() {
	return consumption;
}

public void setConsumption(ConsumptionData consumption) {
	this.consumption = consumption;
}

public EventData getEvents() {
	return events;
}

public void setEvents(EventData events) {
	this.events = events;
}
}
