package com.example.dventus_hq.demohandheldapp.parameters;

public class EventData {

	private String meterID;
	private String covertTamperTime[];
	private String magTamperTime[];
	private String sysTime;
	private BatLevel batLevel;
	private LeakEvent leakEvent;
	private RevFlowEvent revFlowEvent;
	private CumulativeCons cumCons;

	private MonthlyCons monthCons;

	private BatLowEvent batLowEvent;



	public CumulativeCons getCumCons() {
		return this.cumCons;
	}

	public void setCumCons(CumulativeCons cumCons1) {
		this.cumCons = cumCons1;
	}

	public MonthlyCons getMonthCons() {
		return this.monthCons;
	}

	public void setMonthCons(MonthlyCons monthCons1) {
		this.monthCons = monthCons1;
	}

	public String getMeterID() {
		return this.meterID;
	}

	public void setMeterID(String meterID1) {
		this.meterID = meterID1;
	}

	public String[] getCovertTamperTime() {
		return this.covertTamperTime;
	}

	public void setCovertTamperTime(String[] covertTamperTime1) {
		this.covertTamperTime = covertTamperTime1;
	}

	public String[] getMagTamperTime() {
		return this.magTamperTime;
	}

	public void setMagTamperTime(String[] magTamperTime1) {
		this.magTamperTime = magTamperTime1;
	}

	public String getSysTime() {
		return this.sysTime;
	}

	public void setSysTime(String sysTime1) {
		this.sysTime = sysTime1;
	}

	public BatLevel getBatLevel() {
		return this.batLevel;
	}

	public void setBatLevel(BatLevel batLevel1) {
		this.batLevel = batLevel1;
	}

	public LeakEvent getLeakEvent() {
		return this.leakEvent;
	}

	public void setLeakEvent(LeakEvent leakEvent1) {
		this.leakEvent = leakEvent1;
	}

	public RevFlowEvent getRevFlowEvent() {
		return this.revFlowEvent;
	}

	public void setRevFlowEvent(RevFlowEvent revFlowEvent1) {
		this.revFlowEvent = revFlowEvent1;
	}

	public BatLowEvent getBatLowEvent() {
		return this.batLowEvent;
	}

	public void setBatLowEvent(BatLowEvent batLowEvent1) {
		this.batLowEvent = batLowEvent1;
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
