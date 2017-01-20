package com.example.dventus_hq.demohandheldapp.parameters;

public class ConsumptionData {

	private String meterID;
	
	private DailyCons dailyCons;
	
	private MeterInfo meterInfo;


	public MeterInfo getMeterInfo() {
		return this.meterInfo;
	}

	public void setMeterInfo(MeterInfo meterInfo1) {
		this.meterInfo = meterInfo1;
	}



	public String getMeterID() {
		return this.meterID;
	}

	public void setMeterID(String meterID1) {
		this.meterID = meterID1;
	}

	public DailyCons getDailyCons() {
		return dailyCons;
	}

	public void setDailyCons(DailyCons dailyCons) {
		this.dailyCons = dailyCons;
	}

public String ParseTamper(String[]frame, int tamperIndex)
{
	
String	arr1 = frame[tamperIndex - 7 ] + frame[tamperIndex - 6 ]
			+ frame[tamperIndex - 5 ] + frame[tamperIndex - 4 ]
			+ frame[tamperIndex - 3 ] + frame[tamperIndex - 2 ]
			+ frame[tamperIndex - 1 ];
return arr1;
}
}
