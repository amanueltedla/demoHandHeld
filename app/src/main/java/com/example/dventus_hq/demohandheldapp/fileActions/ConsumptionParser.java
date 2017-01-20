package com.example.dventus_hq.demohandheldapp.fileActions;

import com.example.dventus_hq.demohandheldapp.parameters.ConsumptionData;
import com.example.dventus_hq.demohandheldapp.parameters.DailyCons;
import com.example.dventus_hq.demohandheldapp.parameters.MeterInfo;
import com.example.dventus_hq.demohandheldapp.parameters.ParameterType;
import com.example.dventus_hq.demohandheldapp.parameters.TamperType;





public class ConsumptionParser {

	private ParameterType paraType;
	private TamperType tamperType;

	//private final static Logger logger = Logger.getLogger(ConsumptionParser.class);
	public ConsumptionData ProcessFrames(String[] frame, int index) {

		final ConsumptionData mD = new ConsumptionData();




		MeterInfo meterInfo1 = new MeterInfo();
		DailyCons dailyCons1 = new DailyCons();



		final int pTypeIndex = index + 7;// ! to be changed later

		int type = 0;


		System.out.println("the ci file is "+frame[index]);

		// if (ConsumptionParser.SumAndValidate(frame) == true) {

	/*	final String meterID = frame[index - 5] + frame[index - 6] + frame[index - 7] + frame[index - 8]
				+ frame[index - 31] + frame[index - 4];
		ConsumptionParser.logger.info("The meter id is: " + meterID);*/

		//mD.setMeterID(meterID);

		if (FileReader.AllOrSingleParameters(frame) == FileReader.allParameters) {
			System.out.println("data has all parameters");





						final int dailyConsIndex = pTypeIndex;
			System.out.println("the daily index  is: " +frame[dailyConsIndex]);
						dailyCons1 = dailyCons1.ParseDailyData(frame, dailyConsIndex);


			System.out.println("the daily time  is: " + dailyCons1.getDailyConsStartTime());

			System.out.println("the daily end time is   is: " +dailyCons1.getDailyConsEndTime());
						final int mInfoIndex = dailyConsIndex + 17 + 4
								* com.example.dventus_hq.demohandheldapp.parameters.Common.getdays(dailyCons1.getDailyConsStartTime());

						meterInfo1 = meterInfo1.ParseMeterInfoData(frame, mInfoIndex, pTypeIndex);
			System.out.println("meter info index is: " +frame[mInfoIndex]);



						//ConsumptionParser.logger.info("the system time is: " + cumCons1.getCumConsEndTime());


			//mD.setCumCons(cumCons1);
			//mD.setSysTime(cumCons1.getCumConsEndTime());
			//mD.setMonthCons(monthCons1);
			mD.setDailyCons(dailyCons1);
			mD.setMeterInfo(meterInfo1);


		} else {
			type = Integer.valueOf(frame[pTypeIndex]);
		}
			switch (type) {



			case 9:
				this.setParaType(ParameterType.dailyCons);
				System.out.println("The daily is being processed.");

				final int dailyConsIndex = pTypeIndex;

				dailyCons1 = dailyCons1.ParseDailyData(frame, dailyConsIndex);

				mD.setDailyCons(dailyCons1);

				break;

			case 10:
				this.setParaType(ParameterType.meterInfo);
				System.out.println("The meter info is being processed.");
				meterInfo1 = meterInfo1.ParseMeterInfoData(frame, pTypeIndex, pTypeIndex);

				mD.setMeterInfo(meterInfo1);
				break;
			default:
				break;
			}
		return mD;
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


		// } else {
		// ConsumptionParser.logger.info("Invalid frame!!!");
		// do nothing
		// }

	}


