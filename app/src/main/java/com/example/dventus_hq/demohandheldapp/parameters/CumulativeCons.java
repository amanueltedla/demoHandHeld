package com.example.dventus_hq.demohandheldapp.parameters;

public class CumulativeCons {
	private String cumConsStartTime;
	private String cumConsEndTime;
	private long cumCons;

	public String getCumConsStartTime() {
		return this.cumConsStartTime;
	}
	public void setCumConsStartTime(String cumConsStartTime1) {
		this.cumConsStartTime = cumConsStartTime1;
	}
	public String getCumConsEndTime() {
		return this.cumConsEndTime;
	}
	public void setCumConsEndTime(String cumConsEndTime1) {
		this.cumConsEndTime = cumConsEndTime1;
	}
	public long getCumCons() {
		return this.cumCons;
	}
	public void setCumCons(long cumCons1) {
		this.cumCons = cumCons1;
	}
	public CumulativeCons ParseCumulativeData(String[] frame, int cumConsIndex)
	{

		final long totalCons = (Integer.valueOf(frame[cumConsIndex + 18], 16) << 24
				| Integer.valueOf(frame[cumConsIndex + 17], 16)) << 16
				| Integer.valueOf(frame[cumConsIndex + 16], 16) << 8
				| Integer.valueOf(frame[cumConsIndex + 15], 16);
		final CumulativeCons cumCons1=new CumulativeCons();
		cumCons1.setCumConsStartTime(Common.getByteAsString(frame, cumConsIndex));

		cumCons1.setCumConsEndTime(Common.getByteAsString(frame, cumConsIndex + 7));

		cumCons1.setCumCons(totalCons);
		return cumCons1;
	}

}
