package com.example.dventus_hq.demohandheldapp.parameters;



public class RevFlowEvent {
	private String revStartTime;
	private String revEventEndTime;
	private int revCons;
	//private final static Logger logger = Logger.getLogger(RevFlowEvent.class);
	public String getRevStartTime() {
		return this.revStartTime;
	}
	public void setRevStartTime(String revStartTime1) {
		this.revStartTime = revStartTime1;
	}
	public String getRevEventEndTime() {
		return this.revEventEndTime;
	}
	public void setRevEventEndTime(String revEventEndTime1) {
		this.revEventEndTime = revEventEndTime1;
	}
	public int getRevCons() {
		return this.revCons;
	}
	public void setRevCons(int revCons1) {
		this.revCons = revCons1;
	}

	public RevFlowEvent ParseRevFlowEventlData(String[] frame, int revIndex){
System.out.println(" Tamper event plus reverse flow " + frame[revIndex+15]);

	 this.revCons = (Integer.valueOf(frame[revIndex + 18], 16) << 24
			| Integer.valueOf(frame[revIndex + 17], 16)) << 16
			| Integer.valueOf(frame[revIndex + 16], 16) << 8
			| Integer.valueOf(frame[revIndex + 15], 16);

	final RevFlowEvent revFlowEvent1=new RevFlowEvent();
	revFlowEvent1.setRevStartTime(Common.getByteAsString(frame, revIndex));

	revFlowEvent1.setRevEventEndTime(Common.getByteAsString(frame, revIndex + 7));
	revFlowEvent1.setRevCons(this.revCons);


		System.out.println("rev consumption in litres is : " + this.revCons);

		System.out.println("rev consumption start time is : " + frame[revIndex + 1]
			+ frame[revIndex + 2] + frame[revIndex + 3] + frame[revIndex + 4] + frame[revIndex + 5]
			+ frame[revIndex + 6] + frame[revIndex + 7]);

		System.out.println("rev consumption end time is : " + frame[revIndex + 8] + frame[revIndex + 9]
			+ frame[revIndex + 10] + frame[revIndex + 11] + frame[revIndex + 12] + frame[revIndex + 13]
			+ frame[revIndex + 14]);
return revFlowEvent1;
	}
}
