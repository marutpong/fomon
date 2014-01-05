package tabFragment;

import foodDatabase.FoodDatabase;
import historyDatabase.HistoryDatabase;
import historyDatabase.HistoryType;

import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class QuestFragment extends Fragment {
	private static int max_carbohydrate = 300;
	private static int max_protien = 50;
	private static int max_fat = 65;
	private View mView;
	public static ProgressBar progressCarbohydrate;
	public static ProgressBar progressProtien;
	public static ProgressBar progressFat;
	static int today_carbo = 0;
	static int today_prot = 0; 
	static int today_fat = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.quest_fragment, container,false);
		// TODO Auto-generated method stub
		
		//old = getIntent();
		//pet = PetDataGet.getDataBox(old.getExtras().getInt("index"));
		
		progressCarbohydrate = (ProgressBar) mView.findViewById(R.id.progressATK);
		progressProtien = (ProgressBar) mView.findViewById(R.id.progressHP);
		progressFat = (ProgressBar) mView.findViewById(R.id.atkValue);
		
		progressCarbohydrate.setProgress(0);
		progressCarbohydrate.setMax(max_carbohydrate);
		progressProtien.setProgress(0);
		progressProtien.setMax(max_protien);
		progressFat.setProgress(0);
		progressFat.setMax(max_fat);
		
		updateValue();

		return mView;
	}
	public static void updateValue() {
		String curentDate = HistoryType.getCurrentDate();
		today_carbo = (int) HistoryDatabase.getSumNutritionOfDate(curentDate, FoodDatabase.Enum.carbohydrate.ordinal());
		today_prot = (int) HistoryDatabase.getSumNutritionOfDate(curentDate, FoodDatabase.Enum.protein.ordinal());
		today_fat = (int) HistoryDatabase.getSumNutritionOfDate(curentDate, FoodDatabase.Enum.fat.ordinal());
		progressCarbohydrate.setProgress(today_carbo);
		progressProtien.setProgress(today_prot);
		progressFat.setProgress(today_fat);
	}
}
