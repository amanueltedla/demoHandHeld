package com.example.dventus_hq.demohandheldapp.parameters;


public class LeakEvent {
	private String leakStartTime;
	private String leakEventEndTime;
	private int leakCons;
	//private final static Logger logger = Logger.getLogger(LeakEvent.class);
	public String getLeakStartTime() {
		return this.leakStartTime;
	}
	public void setLeakStartTime(String leakStartTime1) {
		this.leakStartTime = leakStartTime1;
	}
	public String getLeakEventEndTime() {
		return this.leakEventEndTime;
	}
	public void setLeakEventEndTime(String leakEventEndTime1) {
		this.leakEventEndTime = leakEventEndTime1;
	}
	public int getLeakCons() {
		return this.leakCons;
	}
	public void setLeakCons(int leakCons1) {
		this.leakCons = leakCons1;
	}
	public LeakEvent ParseLeakEventyData(String[] frame, int leakIndex){
		System.out.println(" Tamper event plus reverse flow plus leak");
		/*leakCons = Integer.valueOf(frame[leakIndex + 15], 16) << 8
				| Integer.valueOf(frame[leakIndex + 16], 16);*/
		this.leakCons = (Integer.valueOf(frame[leakIndex + 18], 16) << 24
				| Integer.valueOf(frame[leakIndex + 17], 16)) << 16
				| Integer.valueOf(frame[leakIndex + 16], 16) << 8
				| Integer.valueOf(frame[leakIndex + 15], 16);


		final LeakEvent leakEvent1=new LeakEvent();
		leakEvent1.setLeakStartTime(Common.getByteAsString(frame, leakIndex));

		leakEvent1.setLeakEventEndTime(Common.getByteAsString(frame, leakIndex + 7));

		leakEvent1.setLeakCons(this.leakCons);
		return leakEvent1;
	}
}
