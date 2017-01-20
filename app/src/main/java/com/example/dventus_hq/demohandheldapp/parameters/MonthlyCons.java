package com.example.dventus_hq.demohandheldapp.parameters;


public class MonthlyCons {
	//private final static Logger logger = Logger.getLogger(MonthlyCons.class);
	private String monthConsStartTime;
	private String monthConsEndTime;
	private int monthCons;

	public String getMonthConsStartTime() {
		return this.monthConsStartTime;
	}
	public void setMonthConsStartTime(String monthConsStartTime1) {
		this.monthConsStartTime = monthConsStartTime1;
	}
	public String getMonthConsEndTime() {
		return this.monthConsEndTime;
	}
	public void setMonthConsEndTime(String monthConsEndTime1) {
		this.monthConsEndTime = monthConsEndTime1;
	}
	public int getMonthCons() {
		return this.monthCons;
	}
	public void setMonthCons(int monthCons1) {
		this.monthCons = monthCons1;
	}

public MonthlyCons ParseMontlyData(String[] frame, int monConsIndex)
{	System.out.println("the monthly cons index is: " + monConsIndex);
/*final int monCons = Integer.valueOf(frame[monConsIndex + 15], 16) << 8
| Integer.valueOf(frame[monConsIndex + 16], 16);*/


final Integer monCons = (Integer.valueOf(frame[monConsIndex + 18], 16) << 24
		| Integer.valueOf(frame[monConsIndex + 17], 16)) << 16
		| Integer.valueOf(frame[monConsIndex + 16], 16) << 8
		| Integer.valueOf(frame[monConsIndex + 15], 16);


final MonthlyCons monthCons1=new MonthlyCons();
monthCons1.setMonthConsStartTime(Common.getByteAsString(frame, monConsIndex));

monthCons1.setMonthConsEndTime(Common.getByteAsString(frame, monConsIndex + 7));

monthCons1.setMonthCons(monCons);


	return monthCons1;


}

}
