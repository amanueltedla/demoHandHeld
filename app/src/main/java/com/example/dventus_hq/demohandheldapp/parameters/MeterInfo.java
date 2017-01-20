package com.example.dventus_hq.demohandheldapp.parameters;

public class MeterInfo {
	private String serialNum;
	private int operatingChannel;
	private int commMode;
	private int baudRate;
	private int outputPower;

	public String getSerialNum() {
		return this.serialNum;
	}
	public void setSerialNum(String serialNum1) {
		this.serialNum = serialNum1;
	}
	public int getOperatingChannel() {
		return this.operatingChannel;
	}
	public void setOperatingChannel(int operatingChannel1) {
		this.operatingChannel = operatingChannel1;
	}
	public int getCommMode() {
		return this.commMode;
	}
	public void setCommMode(int commMode1) {
		this.commMode = commMode1;
	}
	public int getBaudRate() {
		return this.baudRate;
	}
	public void setBaudRate(int baudRate1) {
		this.baudRate = baudRate1;
	}
	public int getOutputPower() {
		return this.outputPower;
	}
	public void setOutputPower(int outputPower) {
		this.outputPower = outputPower;
	}
	public MeterInfo ParseMeterInfoData(String[] frame, int mInfoIndex,int pTypeIndex){
	MeterInfo meterInfo1=new MeterInfo();
	meterInfo1.setSerialNum(frame[mInfoIndex + 1] + frame[mInfoIndex + 2] + frame[mInfoIndex + 3]
			+ frame[mInfoIndex + 4] + frame[mInfoIndex + 5] + frame[mInfoIndex + 6]
			+ frame[mInfoIndex + 7]);
	meterInfo1.setCommMode(Integer.valueOf(frame[mInfoIndex + 8], 16));
	meterInfo1.setOperatingChannel(Integer.valueOf(frame[mInfoIndex + 9], 16));
	meterInfo1.setBaudRate(Integer.valueOf(frame[mInfoIndex + 10], 16));
	meterInfo1.setOutputPower(Integer.valueOf(frame[pTypeIndex + 11], 16));
	return meterInfo1;}
}
