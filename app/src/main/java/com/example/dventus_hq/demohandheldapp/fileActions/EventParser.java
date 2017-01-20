package com.example.dventus_hq.demohandheldapp.fileActions;

import com.example.dventus_hq.demohandheldapp.parameters.BatLevel;
import com.example.dventus_hq.demohandheldapp.parameters.BatLowEvent;
import com.example.dventus_hq.demohandheldapp.parameters.CumulativeCons;
import com.example.dventus_hq.demohandheldapp.parameters.EventData;
import com.example.dventus_hq.demohandheldapp.parameters.LeakEvent;
import com.example.dventus_hq.demohandheldapp.parameters.MonthlyCons;
import com.example.dventus_hq.demohandheldapp.parameters.ParameterType;
import com.example.dventus_hq.demohandheldapp.parameters.RevFlowEvent;
import com.example.dventus_hq.demohandheldapp.parameters.TamperType;

import java.util.Arrays;





public class EventParser {

	//private final static Logger logger = Logger.getLogger(EventParser.class);
	private ParameterType paraType;
	private TamperType tamperType;

	public EventData ProcessFrames(String[] frame, int index) {

		final EventData mD = new EventData();
		BatLevel batLevel1 = new BatLevel();
		final BatLowEvent batLowEvent = new BatLowEvent();
		String[] covertTamperTime1 = null;
		String[] magTamperTime1 = null;

CumulativeCons cumCons1 = new CumulativeCons();


		MonthlyCons monthCons1 = new MonthlyCons();


		LeakEvent leakEvent1 = new LeakEvent();

		RevFlowEvent revFlowEvent1 = new RevFlowEvent();

		final int pTypeIndex = index + 7;// ! to be changed later
		final int tamperCount;
		final int type;
		final int leakCons = 0;

		System.out.println("the ci file is "+frame[index]);

		// if (EventParser.SumAndValidate(frame) == true) {

	/*	final String meterID = frame[index - 3] + frame[index - 4] + frame[index - 5] + frame[index - 6]
				+ frame[index - 1] + frame[index - 2];
		EventParser.logger.info("The meter id is: " + meterID);*/

		//mD.setMeterID(meterID);

		if (FileReader.AllOrSingleParameters(frame) == FileReader.allParameters) {
			System.out.println("data has all parameters");
			this.setParaType(ParameterType.all);
			batLevel1 = batLevel1.ParseBatLevelData(frame, pTypeIndex);
			System.out.println("the powerlevel index is "+frame[pTypeIndex]);
			System.out.println("level is  "+batLevel1.getLevel());
			System.out.println("the time is  "+batLevel1.getMeasureTime());

			if (Integer.valueOf(frame[pTypeIndex + 9], 16) == 1) {
				System.out.println("There is a low battery event.");
				batLowEvent.setMeasureTime(batLevel1.getMeasureTime());
				batLowEvent.setLevel(batLevel1.getLevel());
				mD.setBatLowEvent(batLowEvent);
			} else {
				System.out.println("No battery low event.");
				mD.setBatLowEvent(null);
			}

			mD.setBatLevel(batLevel1);

			final int tamperIndex = pTypeIndex + 13;
			System.out.println("the tamper index is: " + frame[tamperIndex-1]);
			tamperCount = Integer.valueOf(frame[tamperIndex], 16);
			System.out.println("The tamper count is: " + tamperCount);
			final String[] arr1 = new String[10];
			final String[] arr2 = new String[10];
			if (tamperCount >= 1) {
				int j = 1;
				int c = 0;
				int k = 0;
				int l = 0;
				int m = 0;
				for (; j <= tamperCount; j++) {
					if (Integer.valueOf(frame[tamperIndex + 8 * j], 16) == 1) {
						this.setTamperType(TamperType.coverTamper);

						arr1[k] = mD.ParseTamper(frame, tamperIndex + 8 * j);
						c++;
						k++;
					} else if (Integer.valueOf(frame[tamperIndex + 8 * j], 16) == 2) {
						this.setTamperType(TamperType.magneticTamper);
						arr2[l] = mD.ParseTamper(frame, tamperIndex + 8 * j);
						;
						m++;
						l++;

					} else {
						System.out.println("Null tamper");
						arr1[k] = null;
						arr2[l] = null;
					}
				}
				covertTamperTime1 = Arrays.copyOf(arr1, c);
				magTamperTime1 = Arrays.copyOf(arr2, m);
				System.out.println("There are: " + c + " cover tampers and " + m + " mag tampers");
				for (final String string : arr2) {
					//System.out.println(string);
				}

				for (final String string : arr1) {
					//System.out.println(string);
				}


				System.out.println("j is: " + j);
				final int revIndex = tamperIndex+ 4 + 8 * tamperCount;
				System.out.println("the rev index is: " + revIndex);
				System.out.println("the rev value is: " + frame[revIndex]);
				final int revInd = Integer.valueOf(frame[revIndex ], 16);

				if (revInd == 1) {
					System.out.println(" Tamper event plus reverse flow ");
					revFlowEvent1 = revFlowEvent1.ParseRevFlowEventlData(frame, revIndex);
					final int leakIndex = revIndex + 22;
					System.out.println("the leak index is: " + frame[leakIndex]);
					final int leakInd = Integer.valueOf(frame[leakIndex], 16);
					System.out.println("rev leak in litres is : " + leakInd);
					if (leakInd == 1) {
						leakEvent1 = leakEvent1.ParseLeakEventyData(frame, leakIndex);
						final int cumConsIndex = leakIndex + 22;
						System.out.println("the total cons index is: " + cumConsIndex);
						System.out.println("the total cons value is: " + frame[cumConsIndex]);
						cumCons1 = cumCons1.ParseCumulativeData(frame, cumConsIndex);
						System.out.println("the total cons time is: " + cumCons1.getCumConsStartTime());
						System.out.println("the total cons time is: " + cumCons1.getCumConsEndTime());
						final int monConsIndex = cumConsIndex + 21;
						System.out.println("the monthly cons index is: " + monConsIndex);
						System.out.println("the monthly cons value is: " + frame[monConsIndex]);
						monthCons1 = monthCons1.ParseMontlyData(frame, monConsIndex);
						System.out.println("the monthly value start is: " + monthCons1.getMonthConsStartTime());
						System.out.println("the monthly value end is: " + monthCons1.getMonthConsEndTime());
						System.out.println("the monthly value  is: " + monthCons1.getMonthCons());


						mD.setLeakEvent(leakEvent1);

						System.out.println("the system time is: " + cumCons1.getCumConsEndTime());

					} else {
						System.out.println(" Tamper event plus reverse flow with no leak");
						final int cumConsIndex = leakIndex + 3;

						System.out.println("Started parsing consumption");

						cumCons1 = cumCons1.ParseCumulativeData(frame, cumConsIndex);

						final int monConsIndex = cumConsIndex + 21;
						monthCons1 = monthCons1.ParseMontlyData(frame, monConsIndex);

						System.out.println("the monthly value start is: " + monthCons1.getMonthConsStartTime());
						System.out.println("the monthly value end is: " + monthCons1.getMonthConsEndTime());
						System.out.println("the monthly value  is: " + monthCons1.getMonthCons());

						mD.setLeakEvent(null);

					}

					mD.setRevFlowEvent(revFlowEvent1);

				} else {
					System.out.println(" Tamper event with no reverse flow ");
					final int leakIndex = revIndex + 4;
					final int leakInd = Integer.valueOf(frame[leakIndex], 16);
					if (leakInd == 1) {
						System.out.println(" Tamper event with no reverse flow but with leak");
						leakEvent1 = leakEvent1.ParseLeakEventyData(frame, leakIndex);

						final int cumConsIndex = leakIndex + 22;
						cumCons1 = cumCons1.ParseCumulativeData(frame, cumConsIndex);
						final int monConsIndex = cumConsIndex + 21;
						monthCons1 = monthCons1.ParseMontlyData(frame, monConsIndex);
						System.out.println("the monthly value start is: " + monthCons1.getMonthConsStartTime());
						System.out.println("the monthly value end is: " + monthCons1.getMonthConsEndTime());
						System.out.println("the monthly value  is: " + monthCons1.getMonthCons());
						mD.setLeakEvent(leakEvent1);

					} else {
						System.out.println(" Tamper event with no reverse flow and no leak");
						final int cumConsIndex = leakIndex + 3;
						cumCons1 = cumCons1.ParseCumulativeData(frame, cumConsIndex);
						final int monConsIndex = cumConsIndex + 21;
						monthCons1 = monthCons1.ParseMontlyData(frame, monConsIndex);
						System.out.println("the monthly value start is: " + monthCons1.getMonthConsStartTime());
						System.out.println("the monthly value end is: " + monthCons1.getMonthConsEndTime());
						System.out.println("the monthly value  is: " + monthCons1.getMonthCons());

						mD.setLeakEvent(null);

					}
					mD.setRevFlowEvent(null);
				}
				mD.setCovertTamperTime(covertTamperTime1);

				mD.setMagTamperTime(magTamperTime1);

			} else {
				System.out.println("No tamper event.");
				final int revIndex = tamperIndex + 4;
				final int revInd = Integer.valueOf(frame[revIndex], 16);
				if (revInd == 1) {
					System.out.println("No tamper event with reverse flow");

					revFlowEvent1 = revFlowEvent1.ParseRevFlowEventlData(frame, revIndex);

					final int leakIndex = revIndex + 20;
					final int leakInd = Integer.valueOf(frame[leakIndex], 16);
					if (leakInd == 1) {
						System.out.println("No tamper event with reverse flow and leak");

						leakEvent1 = leakEvent1.ParseLeakEventyData(frame, leakIndex);
						final int cumConsIndex = leakIndex + 22;
						cumCons1 = cumCons1.ParseCumulativeData(frame, cumConsIndex);
						final int monConsIndex = cumConsIndex + 21;
						monthCons1 = monthCons1.ParseMontlyData(frame, monConsIndex);
						System.out.println("the monthly value start is: " + monthCons1.getMonthConsStartTime());
						System.out.println("the monthly value end is: " + monthCons1.getMonthConsEndTime());
						System.out.println("the monthly value  is: " + monthCons1.getMonthCons());
						mD.setLeakEvent(null);

					}

					else {
						System.out.println("No tamper event,no reverse flow and no leak");
						final int cumConsIndex = leakIndex + 3;
						cumCons1 = cumCons1.ParseCumulativeData(frame, cumConsIndex);
						final int monConsIndex = cumConsIndex + 21;
						monthCons1 = monthCons1.ParseMontlyData(frame, monConsIndex);
						System.out.println("the monthly value start is: " + monthCons1.getMonthConsStartTime());
						System.out.println("the monthly value  is: " + monthCons1.getMonthCons());
						mD.setLeakEvent(null);

					}

					mD.setRevFlowEvent(revFlowEvent1);
				} else {
					System.out.println("No tamper event and no reverse flow");
					final int leakIndex = revIndex + 4;
					final int leakInd = Integer.valueOf(frame[leakIndex], 16);
					if (leakInd == 1) {
						System.out.println("No tamper event and no reverse flow with leak");
						leakEvent1 = leakEvent1.ParseLeakEventyData(frame, leakIndex);
						final int cumConsIndex = leakIndex + 22;
						cumCons1 = cumCons1.ParseCumulativeData(frame, cumConsIndex);
						final int monConsIndex = cumConsIndex + 21;
						monthCons1 = monthCons1.ParseMontlyData(frame, monConsIndex);
						System.out.println("the monthly value start is: " + monthCons1.getMonthConsStartTime());
						System.out.println("the monthly value end is: " + monthCons1.getMonthConsEndTime());
						System.out.println("the monthly value  is: " + monthCons1.getMonthCons());
						mD.setLeakEvent(leakEvent1);

					} else {
						System.out.println("No tamper event,no reverse flow and no leak");
						final int cumConsIndex = leakIndex + 3;
						cumCons1 = cumCons1.ParseCumulativeData(frame, cumConsIndex);
						final int monConsIndex = cumConsIndex + 21;
						monthCons1 = monthCons1.ParseMontlyData(frame, monConsIndex);
						System.out.println("the monthly value start is: " + monthCons1.getMonthConsStartTime());
						System.out.println("the monthly value end is: " + monthCons1.getMonthConsEndTime());
						System.out.println("the monthly value  is: " + monthCons1.getMonthCons());
						mD.setLeakEvent(null);

					}
					mD.setRevFlowEvent(null);

				}
				mD.setCovertTamperTime(null);
				mD.setMagTamperTime(null);
			}
			mD.setCumCons(cumCons1);
			mD.setSysTime(cumCons1.getCumConsEndTime());
			mD.setMonthCons(monthCons1);

		} else {
			type = Integer.valueOf(frame[pTypeIndex]);
			final String[] arr1 = new String[10];
			final String[] arr2 = new String[10];

			switch (type) {
			case 0:
			this.setParaType(ParameterType.batLevel);
				System.out.println("The bat level is being processed.");
				batLevel1 = batLevel1.ParseBatLevelData(frame, pTypeIndex);

				if (Integer.valueOf(frame[pTypeIndex + 9], 16) == 1) {
					System.out.println("There is a low battery event.");
					batLowEvent.setMeasureTime(batLevel1.getMeasureTime());
					batLowEvent.setLevel(batLevel1.getLevel());
					mD.setBatLowEvent(batLowEvent);
				} else {
					System.out.println("No battery low event.");
					mD.setBatLowEvent(null);
				}

				mD.setBatLevel(batLevel1);
				break;
			case 1:
				this.setParaType(ParameterType.tamper);
				System.out.println("The tamper is being processed.");
				final int tamperIndex = pTypeIndex + 1;
				final int noOfTampers = Integer.valueOf(frame[tamperIndex], 16);
				System.out.println("The number of tampers is.." + noOfTampers);
				covertTamperTime1 = new String[noOfTampers];
				magTamperTime1 = new String[noOfTampers];
				int k = 0;
				int l = 0;
				if (noOfTampers >= 1) {
					for (int i = 1; i <= noOfTampers; i++) {
						if (Integer.valueOf(frame[tamperIndex + 8 * i], 16) == 1) {
							//this.setTamperType(TamperType.coverTamper);
							arr1[k] = mD.ParseTamper(frame, tamperIndex + 8 * i);
							k++;
						} else if (Integer.valueOf(frame[tamperIndex + 8 * i], 16) == 2) {
							//this.setTamperType(TamperType.magneticTamper);
							arr2[l] = mD.ParseTamper(frame, tamperIndex + 8 * i);
							l++;
						}
					}
					covertTamperTime1 = Arrays.copyOf(arr1, k);
					magTamperTime1 = Arrays.copyOf(arr2, l);

					mD.setCovertTamperTime(covertTamperTime1);
					mD.setMagTamperTime(magTamperTime1);
				}
				break;
			case 2:
				this.setParaType(ParameterType.revFlow);
				System.out.println("The revFlow is being processed.");
				final int revFlowInd = Integer.valueOf(frame[pTypeIndex + 1], 16); // !
				// to
				// be
				// changed
				// later
				if (revFlowInd == 1) {

					final int revIndex = pTypeIndex + 1;
					revFlowEvent1 = revFlowEvent1.ParseRevFlowEventlData(frame, revIndex);
					mD.setRevFlowEvent(revFlowEvent1);
				} else {
					mD.setRevFlowEvent(null);
				}
				break;
			case 3:
				this.setParaType(ParameterType.leak);
				System.out.println("The leak is being processed.");
				final int leakInd = Integer.valueOf(frame[pTypeIndex + 1], 16); // !
				// to
				// be
				// changed
				// later
				if (leakInd == 1) {

					final int leakIndex = pTypeIndex + 1;
					leakEvent1 = leakEvent1.ParseLeakEventyData(frame, leakIndex);
					leakEvent1.setLeakCons(leakCons);

					mD.setLeakEvent(leakEvent1);

				} else {
					mD.setLeakEvent(null);
				}
				break;
			case 7:
				this.setParaType(ParameterType.totalCons);
				System.out.println("The cumcons is being processed.");

				final int cumConsIndex = pTypeIndex;
				cumCons1 = cumCons1.ParseCumulativeData(frame, cumConsIndex);
				mD.setCumCons(cumCons1);
				mD.setSysTime(cumCons1.getCumConsEndTime());
				System.out.println("The system time is being..." + mD.getSysTime());
				break;
			case 8:
				this.setParaType(ParameterType.monthlyCons);
				System.out.println("The monthly is being processed.");

				final int monConsIndex = pTypeIndex;
				monthCons1 = monthCons1.ParseMontlyData(frame, monConsIndex);
				mD.setMonthCons(monthCons1);
				break;


			default:
				break;
			}
		}
		//EventParser.logger.info("Number of single para packes:..." + this.count);
		//this.addToCache(meterID, mD);
		System.out.println("Frame successfully added to cache.");
		// } else {
		// EventParser.logger.info("Invalid frame!!!");
		// do nothing
		// }
		return mD;

	}

	public ParameterType getParaType() {
		return this.paraType;
	}

	public void setParaType(ParameterType paraType) {
		this.paraType = paraType;
	}

	public TamperType getTamperType() {
		return this.tamperType;
	}

	public void setTamperType(TamperType tamperType) {
		this.tamperType = tamperType;
	}

}
