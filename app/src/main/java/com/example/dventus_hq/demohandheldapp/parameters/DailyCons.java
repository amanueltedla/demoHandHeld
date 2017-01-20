package com.example.dventus_hq.demohandheldapp.parameters;

import java.util.ArrayList;
import java.util.logging.Logger;


public class DailyCons {

	//private final static Logger logger = Logger.getLogger(ConsumptionParser.class);

private String DailyConsStartTime;
	private String DailyConsEndTime;
	private ArrayList<Integer> Dailycons;
	public String getDailyConsStartTime() {
		return this.DailyConsStartTime;
	}
	public void setDailyConsStartTime(String dailyConsStartTime) {
		this.DailyConsStartTime = dailyConsStartTime;
	}
	public String getDailyConsEndTime() {
		return this.DailyConsEndTime;
	}
	public void setDailyConsEndTime(String dailyConsEndTime) {
		this.DailyConsEndTime = dailyConsEndTime;
	}
	public ArrayList<Integer> getDailyCons() {
		return this.Dailycons;
	}
	public void setDailyCons(ArrayList<Integer> dailyCons) {
		this.Dailycons = dailyCons;
	}


	public DailyCons ParseDailyData(String[] frame, int dailyConsIndex)
	{
		final DailyCons dailyCons1=new DailyCons();
		dailyCons1.setDailyConsStartTime(Common.getByteAsString(frame, dailyConsIndex));

		//int NumOfDays = Common.getdays(dailyCons1.getDailyConsStartTime());

		final int NumOfDays=31;


		System.out.println("number of days: " + NumOfDays);
		// int NumOfDays=31;
		final ArrayList<Integer> dailycons = new ArrayList<Integer>();

		System.out.println("the daily cons start time is: " + dailyCons1.getDailyConsStartTime());

		for (int i = 0; i < NumOfDays * 4; i = i + 4) {
			System.out.println(i);
			/*dailycons.add(Integer.valueOf(frame[dailyConsIndex + 8 + i], 16) << 8
					| Integer.valueOf(frame[dailyConsIndex + 9 + i], 16));*/

			final Integer value = (Integer.valueOf(frame[dailyConsIndex + 11 + i], 16) << 24
					| Integer.valueOf(frame[dailyConsIndex + 10 + i], 16)) << 16
					| Integer.valueOf(frame[dailyConsIndex + 9 + i], 16) << 8
					| Integer.valueOf(frame[dailyConsIndex + 8 + i], 16);

			dailycons.add(value);
		}



		;


		dailyCons1.setDailyCons(dailycons);

		dailyCons1.setDailyConsEndTime(
				Common.getByteAsString(frame, dailyConsIndex + 7 + 4 * NumOfDays ));
		return dailyCons1;

	}
}
