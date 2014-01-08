package tabFragment;

import foodDatabase.FoodDatabase;
import historyDatabase.HistoryDatabase;
import historyDatabase.HistoryType;

import com.projnsc.bestprojectever.R;

import connection.MyServer;
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
	private static int max_calcium = 900;
	private static int max_magnesium = 300;
	private static int max_potassium = 3500;
	private static int max_phosphorus = 800;
	private static int max_sodium = 2400;
	private View mView;
	public static ProgressBar progressCarbohydrate;
	public static ProgressBar progressProtien;
	public static ProgressBar progressFat;
	public static ProgressBar progressCalcium;
	public static ProgressBar progressMagnesium;
	public static ProgressBar progressPotassium;
	public static ProgressBar progressPhosphorus;
	public static ProgressBar progressSodium;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.quest_fragment, container,false);
		// TODO Auto-generated method stub
		
		//old = getIntent();
		//pet = PetDataGet.getDataBox(old.getExtras().getInt("index"));
		
		progressCarbohydrate = (ProgressBar) mView.findViewById(R.id.ProgressCarbohydrateQ);
		progressProtien = (ProgressBar) mView.findViewById(R.id.ProgressProtienQ);
		progressFat = (ProgressBar) mView.findViewById(R.id.ProgressFatQ);
		progressCalcium = (ProgressBar) mView.findViewById(R.id.ProgressCalciumQ);
		progressMagnesium = (ProgressBar) mView.findViewById(R.id.ProgressMagnesiumQ);
		progressPotassium = (ProgressBar) mView.findViewById(R.id.ProgressPotassiumQ);
		progressPhosphorus = (ProgressBar) mView.findViewById(R.id.ProgressPhosphorusQ);
		progressSodium = (ProgressBar) mView.findViewById(R.id.ProgressSodiumQ);
		
		progressCarbohydrate.setMax(max_carbohydrate);
		progressProtien.setMax(max_protien);
		progressFat.setMax(max_fat);
		progressCalcium.setMax(max_calcium);
		progressMagnesium.setMax(max_magnesium);
		progressPotassium.setMax(max_potassium);
		progressPhosphorus.setMax(max_phosphorus);
		progressSodium.setMax(max_sodium);
		
		updateValue();

		return mView;
	}
	public static void updateValue() {
		String currentDate = HistoryType.getCurrentDate();
		progressCarbohydrate.setProgress((int) HistoryDatabase.getSumNutritionOfDate(currentDate, FoodDatabase.Enum.carbohydrate.ordinal()));
		progressProtien.setProgress((int) HistoryDatabase.getSumNutritionOfDate(currentDate, FoodDatabase.Enum.protein.ordinal()));
		progressFat.setProgress((int) HistoryDatabase.getSumNutritionOfDate(currentDate, FoodDatabase.Enum.fat.ordinal()));
		progressCalcium.setProgress((int) HistoryDatabase.getSumNutritionOfDate(currentDate, FoodDatabase.Enum.calcium.ordinal()));
		progressMagnesium.setProgress((int) HistoryDatabase.getSumNutritionOfDate(currentDate, FoodDatabase.Enum.magnesium.ordinal()));
		progressPotassium.setProgress((int) HistoryDatabase.getSumNutritionOfDate(currentDate, FoodDatabase.Enum.potassium.ordinal()));
		progressPhosphorus.setProgress((int) HistoryDatabase.getSumNutritionOfDate(currentDate, FoodDatabase.Enum.phosphorus.ordinal()));
		progressSodium.setProgress((int) HistoryDatabase.getSumNutritionOfDate(currentDate, FoodDatabase.Enum.sodium.ordinal()));
	}
}
