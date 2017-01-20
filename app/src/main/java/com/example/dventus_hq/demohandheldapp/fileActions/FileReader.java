package com.example.dventus_hq.demohandheldapp.fileActions;

import com.example.dventus_hq.demohandheldapp.parameters.ConsumptionData;
import com.example.dventus_hq.demohandheldapp.parameters.EventData;
import com.example.dventus_hq.demohandheldapp.parameters.MeterData;
import com.example.dventus_hq.demohandheldapp.parameters.ParameterType;
import com.example.dventus_hq.demohandheldapp.parameters.TamperType;

import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



public class FileReader {

	//private final static Logger logger = Logger.getLogger(FileReader.class);
	private ParameterType paraType;
	private TamperType tamperType;

	int counter=0;

	private HashMap<String, MeterData> cache = new HashMap<>();

	public FileReader(HashMap<String, MeterData> cache1) {
		super();
		this.cache = cache1;
	}
	public FileReader() {
		super();
	}

	public ParameterType getParaType() {
		return this.paraType;
	}

	public void setParaType(ParameterType paraType1) {
		this.paraType = paraType1;
	}

	public TamperType getTamperType() {
		return this.tamperType;
	}

	public void setTamperType(TamperType tamperType1) {
		this.tamperType = tamperType1;
	}

	final static int applicationLayerFrameStartIndex = 20;
	final static int minimumLengthForAllParametersResponse = 100;
	final static boolean allParameters = true;
	private static int count = 0;
	/*
	 * File in a .data format is sent by the hand-held device.
	 *
	 * the file is formed from byte streams of WMBUS packets.
	 *
	 * Record separator is a new line.
	 *
	 * Assumptions: It is assumed that each new line represents a full WMBUS
	 * packet.
	 *
	 */

	@SuppressWarnings("null")
	public List<String[]> ReadFile(File file) {

		final List<String[]> wmbusFrames = new ArrayList<>();

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNext()) {

				String s = scanner.nextLine();
				s = s.replaceAll(" ", "");
				//s=s.replace("-","*");
				if(s!=null || s.length()!=0)
				{/*
				 final String sampleString = s;
			      final String[] items = sampleString.split("-");

			      if(items.length==2)
			      {    for (final String item : items) {
			       s=item;
			      }
*/

				//FileReader.logger.info("The  frame is: " +splited[0]);



			//	FileReader.logger.info("The  item size  is: " +items.length);

				final String[] frame = new String[s.length() / 2];
				System.out.println("The size of this frame is: " + frame.length);
				for (int i = 0, j = 0; i < s.length(); i += 2, j++) {

					if (s.substring(i, i + 2) == null) {
						continue;
					}

					frame[j] = s.substring(i, i + 2);

				}

					System.out.println("Adding the individual arrays to the list");
				wmbusFrames.add(frame);
			     // }
				}
			}
		}

		 catch (final Exception e) {
			e.printStackTrace();
		}

		return wmbusFrames;
	}

	/*
	 * Assumptions : It is assumed that the format of the packet received from
	 * the meter is
	 *
	 * fixed and previously known.
	 *
	 */

	@SuppressWarnings("null")
	public MeterData ProcessFrames(String[] frame) {

		final MeterData mD = new MeterData();

		final int index = FileReader.applicationLayerFrameStartIndex;


		System.out.println("the ci file is "+frame[index]);

		System.out.println("index is "+ frame[index +7]);


		// if (FileReader.SumAndValidate(frame) == true) {

		final String meterID = Integer.valueOf(frame[index - 5],16)+"" + Integer.valueOf(frame[index - 6],16) +""
		+Integer.valueOf(frame[index - 7],16)+"" + Integer.valueOf(frame[index - 8],16)+""
				+Integer.valueOf(frame[index - 3],16)+"" +Integer.valueOf(frame[index - 4],16);

		// final String meterID = frame[index-5]+frame[index-6]+frame[index-7]+frame[index-8]+frame[index-3]+frame[index-4];


/*		final Integer meterID = (Integer.valueOf(Eventframe[index - 3], 16) << 40
				| Integer.valueOf( Eventframe[index - 4], 16)) << 32
				| Integer.valueOf(Eventframe[index - 5], 16) << 24
				| Integer.valueOf(Eventframe[index - 6], 16) << 16
				| Integer.valueOf(Eventframe[index - 1], 16) << 8
				| Integer.valueOf(Eventframe[index - 2], 16)  ;*/


		System.out.println("The meter id is: " + meterID);


		mD.setMeterID(""+meterID);


		EventData event=null;
		ConsumptionData data=null;
		if(frame[index+7].equalsIgnoreCase("0B"))
		{System.out.println("parsing event data ......");
		 data=new ConsumptionParser().ProcessFrames(frame,index);

		}

		else

		{
			System.out.println("parsing daily data ......");
			event=new EventParser().ProcessFrames(frame,index);
		}



if(data!=null) {
	mD.setConsumption(data);
}
if(event!=null) {
	mD.setEvents(event);
}


		System.out.println("Number of single para packes:..." + FileReader.count);
		if(mD!=null) {
			//this.addToCache(mD.getMeterID(), mD);
		}
		this.counter++;
		System.out.println("Frame successfully added to cache.");
		// } else {
		// FileReader.logger.info("Invalid frame!!!");
		// do nothing
		// }
return mD;
	}

	public List<MeterData> GetXMLFileData() {

		System.out.println("Retrieving the stored data from the cache.");

		final Set<String> key = this.getCache().keySet();

		System.out.println("This is the key size: " + key.size());

		final List<MeterData> mData = new ArrayList<>();

		for (final String iD : key) {
			final MeterData d = this.Fetch(iD);
			//if (d.getBatLevel() != null && d.getCumCons() != null && d.getMonthCons() != null && d.getSysTime() != null
				//	&& d.getMeterInfo() != null) {
				mData.add(d);
			//} else {
			System.out.println("This is the bat size: " + d.getEvents().getBatLevel());
			System.out.println("This is the cumulative cons size: " + d.getEvents().getCumCons());
			System.out.println("This is the month size: " + d.getEvents().getMonthCons());
			System.out.println("This is the system time size: " + d.getEvents().getSysTime());
			System.out.println("This is the meterInfo size: " + d.getConsumption().getMeterInfo());
				continue;
			//}
		}
		System.out.println("Retrieved data size is: " + mData.size());

		return mData;
	}

	public static boolean SumAndValidate(String... frame) {
		int result = 0;

		final int frameLength = frame.length;
		System.out.println("The size of the array is: " + frameLength);
		System.out.println("The checksum is: " + frame[frameLength - 1]);

		final int checksum = Integer.valueOf(frame[frameLength - 1], 16) & 0xff;

		for (int i = 0; i < frameLength - 1; i++) {
			result += Integer.valueOf(frame[i], 16);
		}
		System.out.println("The calculated size is: " + result);
		System.out.println("The CHECKSUM size is: " + checksum);
		System.out.println("The CHECKSUM size is: " + (result & 0xff));
		return (result & 0xff) == checksum ? true : false;
	}
	public static DateTime getTimeStamp(String s) {
		final int Year = Integer.parseInt(s.substring(2, 4) + s.substring(0, 2), 16);
		final int Month = Integer.parseInt(s.substring(4, 6), 16);
		final int Date = Integer.parseInt(s.substring(6, 8), 16);
		final int Hour = Integer.parseInt(s.substring(8, 10), 16);
		final int Minute = Integer.parseInt(s.substring(10, 12), 16);
		final int Second = Integer.parseInt(s.substring(12, 14), 16);
		final int MSecond = 0;
		DateTime time = new DateTime(Year, Month, Date, Hour, Minute, Second);
		final GregorianCalendar c = new GregorianCalendar(Year, Month, Date);
		c.set(Calendar.MILLISECOND, MSecond);
		//return c.getTimeInMillis();
		return time;

	}

	public static boolean AllOrSingleParameters(String... frame) {

		final int size = frame.length;
		final int valueInformationField = Integer.valueOf(frame[27], 16);

		if (valueInformationField == 1 && size <= 0x6E) {
			return false;
		} else if (valueInformationField != 1) {
			return size >= FileReader.minimumLengthForAllParametersResponse ? FileReader.allParameters : false;
		} else {
			return FileReader.allParameters;
		}
	}

//	public void addToCache(@NonNull String iD, MeterData mData) {
//
//		synchronized (this.getCache()) {
//
//		final MeterData mD = this.getCache().get(iD);
//
//
//
//
//				FileReader.logger.info("Adding new entry to the cache...");
//				if (mD != null)
//				{final MeterData m=new MeterData();
//				m.setMeterID(iD);
//					if(mData.getConsumption()!=null) {
//						m.setConsumption(mData.getConsumption());
//					} else {
//						m.setConsumption(mD.getConsumption());
//					}
//
//					if(mData.getEvents()!=null) {
//						m.setEvents(mData.getEvents());
//					} else {
//						m.setEvents(mD.getEvents());
//					}
//					this.getCache().replace(iD,m);
//
//					FileReader.logger.info("data already exist for meter "+iD);
//				} else {
//					this.getCache().put(iD, mData);
//				}
//		}
//		//}
//	}

	public HashMap<String, MeterData> getCache() {
		return this.cache;
	}

	public MeterData Fetch(String key) {

		return this.getCache().get(key);
	}

	public void freeCache() {
		synchronized (this.getCache()) {
			this.getCache().clear();
		}
	}

}
