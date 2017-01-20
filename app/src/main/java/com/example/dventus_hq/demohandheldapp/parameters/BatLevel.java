package com.example.dventus_hq.demohandheldapp.parameters;

public class BatLevel {
	private String measureTime;
	private int level;

	public String getMeasureTime() {
		return this.measureTime;
	}
	public void setMeasureTime(String measureTime1) {
		this.measureTime = measureTime1;
	}
	public int getLevel() {
		return this.level;
	}
	public void setLevel(int level1) {
		this.level = level1;
	}

	public BatLevel ParseBatLevelData(String[] frame, int pTypeIndex)
	{
		BatLevel batLevel1=new BatLevel();
		batLevel1.setMeasureTime(Common.getByteAsString(frame, pTypeIndex));
		batLevel1.setLevel(Integer.valueOf(frame[pTypeIndex + 8], 16));
		return batLevel1;
		
	}
	
}
