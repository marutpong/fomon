package histogramDraw;

import java.util.ArrayList;

public class HistogramModule {

	private ArrayList<Float> CalWeek = new ArrayList<Float>();
	private ArrayList<Float> CalMonth = new ArrayList<Float>();
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

	public ArrayList<Float> getKCalWeek() {
		return CalWeek;
	}

	public ArrayList<Float> getKCalMonth() {
		return CalMonth;
	}

	public ArrayList<Float> getListFromStatus() {
		if (STATUS == 0)
			return CalWeek;
		else
			return CalMonth;
	}

	// DUMP NOW
	private void UpdateWeek() {
		int sM[] = { 14, 21, 28, 42, 35, 50, 61 };
		CalWeek.clear();
		for (int i = 0; i < sM.length; i++)
			CalWeek.add((float) sM[i]);
	}

	// DUMP NOW
	private void UpdateMonth() {
		int sM[] = { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
				24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39 ,40};
		CalMonth.clear();
		for (int i = 0; i < sM.length; i++)
			CalMonth.add((float) sM[i]);
	}

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
			if (STATUS == 0){
				UpdateWeek();
				this.onStateChangeListener.onStateChangeNotify(CalWeek);
			}
			else{
				UpdateMonth();
				this.onStateChangeListener.onStateChangeNotify(CalMonth);
			}
		}
	}

}
