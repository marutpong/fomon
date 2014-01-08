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
import android.widget.TextView;

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
	public static TextView txtCarbohydrate;
	public static TextView txtProtien;
	public static TextView txtFat;
	public static TextView txtCalcium;
	public static TextView txtMagnesium;
	public static TextView txtPotassium;
	public static TextView txtPhosphorus;
	public static TextView txtSodium;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.quest_fragment, container, false);
		// TODO Auto-generated method stub

		// old = getIntent();
		// pet = PetDataGet.getDataBox(old.getExtras().getInt("index"));

		progressCarbohydrate = (ProgressBar) mView
				.findViewById(R.id.ProgressCarbohydrateQ);
		progressProtien = (ProgressBar) mView
				.findViewById(R.id.ProgressProtienQ);
		progressFat = (ProgressBar) mView.findViewById(R.id.ProgressFatQ);
		progressCalcium = (ProgressBar) mView
				.findViewById(R.id.ProgressCalciumQ);
		progressMagnesium = (ProgressBar) mView
				.findViewById(R.id.ProgressMagnesiumQ);
		progressPotassium = (ProgressBar) mView
				.findViewById(R.id.ProgressPotassiumQ);
		progressPhosphorus = (ProgressBar) mView
				.findViewById(R.id.ProgressPhosphorusQ);
		progressSodium = (ProgressBar) mView.findViewById(R.id.ProgressSodiumQ);
		txtCarbohydrate = (TextView) mView.findViewById(R.id.txtCarbohydrateQ);
		txtProtien = (TextView) mView.findViewById(R.id.txtProtienQ);
		txtFat = (TextView) mView.findViewById(R.id.txtFatQ);
		txtCalcium = (TextView) mView.findViewById(R.id.txtCalciumQ);
		txtMagnesium = (TextView) mView.findViewById(R.id.txtMagnesiumQ);
		txtPotassium = (TextView) mView.findViewById(R.id.txtPotassiumQ);
		txtPhosphorus = (TextView) mView.findViewById(R.id.txtPhosphorusQ);
		txtSodium = (TextView) mView.findViewById(R.id.txtSodiumQ);

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
		int Carb = (int) HistoryDatabase.getSumNutritionOfDate(currentDate,
				FoodDatabase.Enum.carbohydrate.ordinal());
		int Prot = (int) HistoryDatabase.getSumNutritionOfDate(currentDate,
				FoodDatabase.Enum.protein.ordinal());
		int Fat = (int) HistoryDatabase.getSumNutritionOfDate(currentDate,
				FoodDatabase.Enum.fat.ordinal());
		int Calc = (int) HistoryDatabase.getSumNutritionOfDate(currentDate,
				FoodDatabase.Enum.calcium.ordinal());
		int Magn = (int) HistoryDatabase.getSumNutritionOfDate(currentDate,
				FoodDatabase.Enum.magnesium.ordinal());
		int Potas = (int) HistoryDatabase.getSumNutritionOfDate(currentDate,
				FoodDatabase.Enum.potassium.ordinal());
		int Phos = (int) HistoryDatabase.getSumNutritionOfDate(currentDate,
				FoodDatabase.Enum.phosphorus.ordinal());
		int Sodium = (int) HistoryDatabase.getSumNutritionOfDate(currentDate,
				FoodDatabase.Enum.sodium.ordinal());
		progressCarbohydrate.setProgress(Carb);
		progressProtien.setProgress(Prot);
		progressFat.setProgress(Fat);
		progressCalcium.setProgress(Calc);
		progressMagnesium.setProgress(Magn);
		progressPotassium.setProgress(Potas);
		progressPhosphorus.setProgress(Phos);
		progressSodium.setProgress(Sodium);
		txtCarbohydrate.setText(Carb + " /" + max_carbohydrate + " (g)");
		txtProtien.setText(Prot + " /" + max_protien + " (g)");
		txtFat.setText(Fat + " /" + max_fat+ " (g)");
		txtCalcium.setText(Calc + " /" + max_calcium + " (mg)");
		txtMagnesium.setText(Magn + " /" + max_magnesium + " (mg)");
		txtPotassium.setText(Potas + " /" + max_potassium + " (mg)");
		txtPhosphorus.setText(Phos + " /" + max_phosphorus + " (mg)");
		txtSodium.setText(Sodium + " /" + max_sodium + " (mg)");
	}
}
