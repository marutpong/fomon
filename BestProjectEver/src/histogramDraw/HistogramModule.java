package histogramDraw;

import foodDatabase.FoodDatabase;
import historyDatabase.HistoryDatabase;
import historyDatabase.HistoryType;

import java.util.ArrayList;

public class HistogramModule {

	private ArrayList<Float> CalToday = new ArrayList<Float>();
	private ArrayList<Float> CalWeek = new ArrayList<Float>();
	
	private int STATUS = 0;

	interface OnStateChangeListener {
		void onStateChangeNotify(ArrayList<Float> KCalSet);
	}

	private OnStateChangeListener onStateChangeListener;

	public void setOnStateChangeListener(
			OnStateChangeListener onStateChangeListener) {
		this.onStateChangeListener = onStateChangeListener;
		NotifyListener();
	}

	public ArrayList<Float> getCalWeek() {
		return CalWeek;
	}
	
	public ArrayList<Float> getCalToday() {
		return CalToday;
	}

	public ArrayList<Float> getListFromStatus() {
		if (STATUS == 0)
			return CalToday;
		else
			return CalWeek;
	}

	// DUMP NOW
	private void UpdateWeek() {
		CalWeek.clear();
		CalWeek = HistoryDatabase.getHistoryListByWeek();
	}

	//DUMP NOW
	private void UpdataToday(){
		CalToday.clear();
		//CalToday.add((float) HistoryDatabase.getCaloriesOfDate(HistoryType.getCurrentDate()));
		CalToday.add((float) (HistoryDatabase.getSumNutritionOfDate(HistoryType.getCurrentDate(),FoodDatabase.Enum.calories.ordinal())));

	}
	
	// DUMP NOW
//	private void UpdateMonth() {
//		int sM[] = { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
//				24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39 ,40};
//		CalMonth.clear();
//		for (int i = 0; i < sM.length; i++)
//			CalMonth.add((float) sM[i]);
//	}

	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
		NotifyListener();
	}

	public int getSTATUS() {
		return STATUS;
	}

	public void toggleSTATUS() {
		if (STATUS == 0)
			STATUS = 1;
		else
			STATUS = 0;
		NotifyListener();
	}

	private void NotifyListener() {
		if (onStateChangeListener != null) {
			if (STATUS == 1){
				UpdateWeek();
				this.onStateChangeListener.onStateChangeNotify(CalWeek);
			}
			else{
				UpdataToday();
				this.onStateChangeListener.onStateChangeNotify(CalToday);
			}
		}
	}

}
