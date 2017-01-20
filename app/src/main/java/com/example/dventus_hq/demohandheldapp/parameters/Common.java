package com.example.dventus_hq.demohandheldapp.parameters;

import java.util.Calendar;
import java.util.GregorianCalendar;

//import org.apache.log4j.Logger;

public class Common {
	//private final static Logger logger = Logger.getLogger(Common.class);
	public static int getdays(String s) {

	System.out.println("year value " + s.substring(2, 4) + s.substring(0, 2));
		final int Year = Integer.parseInt(s.substring(2, 4) + s.substring(0, 2), 16);

		final int Month = Integer.parseInt(s.substring(4, 6), 16);
		final int Date = Integer.parseInt(s.substring(6, 8), 16);
		final int Hour = Integer.parseInt(s.substring(8, 10), 16);
		final int Minute = Integer.parseInt(s.substring(10, 12), 16);
		final int Second = Integer.parseInt(s.substring(12, 14), 16);
		final int MSecond = 0;

		final GregorianCalendar c = new GregorianCalendar(Year, Month, Date, Hour, Minute, Second);
		c.set(Calendar.MILLISECOND, MSecond);

		int daysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		return daysInMonth;

	}

	public static String getByteAsString(String[] st, int index) {
		String s = "";
		for (int i = 1; i <= 7; i++)
			s = s + st[index + i];
		return s;
	}
	

}
