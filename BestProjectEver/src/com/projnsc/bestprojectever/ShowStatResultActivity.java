package com.projnsc.bestprojectever;

import historyDatabase.HistoryDatabase;
import historyDatabase.HistoryType;

import foodDatabase.FoodBox;
import foodDatabase.FoodDatabase;
import petShowEmotion.PetShowEmotionPanel;
import petShowEmotion.PetShowEmotionPanel.OnPanelTouchListener;
import petShowEmotion.PetStatGradualIncrease;
import petShowEmotion.PetStatGradualIncrease.OnMonsterStatChange;
import preferenceSetting.PetUniqueDate;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShowStatResultActivity extends Activity implements
		OnMonsterStatChange, OnPanelTouchListener {

	private PetShowEmotionPanel mEmoPanel;
	private ProgressBar barHP;
	private ProgressBar barATK;
	private ProgressBar barDEF;
	private ProgressBar barSPD;
	private TextView txtHpShow;
	private TextView txtATKShow;
	private TextView txtDEFShow;
	private TextView txtSPDShow;
	private PetStatGradualIncrease mPetStatModule;
	private FoodBox FoodResult;
	private int HPUp = 0;
	private int ATKUp = 0;
	private int DEFUp = 0;
	private int SPDUp = 0;
	private boolean isFULLCALORIES = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_pet_stat_up);

		int FoodClass = getIntent().getExtras().getInt(
				this.getString(R.string.intentkey_setfoodclass));

		if (HistoryDatabase.getSumNutritionOfDate(HistoryType.getCurrentDate(),
				FoodDatabase.Enum.calories.ordinal()) <= PetUniqueDate.MAXIMUM) {
			if (FoodClass != -1) {
				FoodResult = FoodDatabase.getFoodByID(FoodClass);
				HPUp = HPUPCAL(FoodResult);
				ATKUp = ATKUPCAL(FoodResult);
				DEFUp = DEFUPCAL(FoodResult);
				SPDUp = SPDUPCAL(FoodResult);
			}
			// SPDUp = getIntent().getExtras().getInt(
			// getString(R.string.intentkey_setspeedstatup));
		} else {
			isFULLCALORIES = true;
			if (FoodClass != -1) {
				FoodResult = FoodDatabase.getFoodByID(FoodClass);
			}
		}
		// Save to Database

		mPetStatModule = new PetStatGradualIncrease(HPUp, ATKUp, DEFUp, SPDUp);
		mPetStatModule.setOnMonsterStatChange(this);
		mEmoPanel = (PetShowEmotionPanel) findViewById(R.id.petShowEmotionPanelEx);
		if (!isFULLCALORIES)
			mEmoPanel.setEmoKey("LOVE");
		else
			mEmoPanel.setEmoKey("EXHAUSTED");
		mEmoPanel.setOnPanelTouchListener(this);
		mEmoPanel.setPetStatModule(mPetStatModule);

		txtHpShow = (TextView) findViewById(R.id.txtHPemo);
		txtATKShow = (TextView) findViewById(R.id.txtATKemo);
		txtDEFShow = (TextView) findViewById(R.id.txtDEFemo);
		txtSPDShow = (TextView) findViewById(R.id.txtSPDemo);

		txtHpShow.setText((PetUniqueDate.getMonHP() + HPUp) + " ( +" + HPUp
				+ " )" + " / " + PetStatGradualIncrease.MaxPETHP);
		txtATKShow.setText((PetUniqueDate.getMonATK() + ATKUp) + " ( +" + ATKUp
				+ " )" + " / " + PetStatGradualIncrease.MaxPETATK);
		txtDEFShow.setText((PetUniqueDate.getMonDEF() + DEFUp) + " ( +" + DEFUp
				+ " )" + " / " + PetStatGradualIncrease.MaxPETDEF);
		txtSPDShow.setText((PetUniqueDate.getMonSPD() + SPDUp) + " ( +" + SPDUp
				+ " )" + " / " + PetStatGradualIncrease.MaxPETSPD);

		barHP = (ProgressBar) findViewById(R.id.progressHP);
		barHP.setMax(PetStatGradualIncrease.MaxPETHP);
		barHP.setProgress(PetUniqueDate.getMonHP());
		barATK = (ProgressBar) findViewById(R.id.progressATK);
		barATK.setMax(PetStatGradualIncrease.MaxPETATK);
		barATK.setProgress(PetUniqueDate.getMonATK());
		barDEF = (ProgressBar) findViewById(R.id.progressDEF);
		barDEF.setMax(PetStatGradualIncrease.MaxPETDEF);
		barDEF.setProgress(PetUniqueDate.getMonDEF());
		barSPD = (ProgressBar) findViewById(R.id.progressSPD);
		barSPD.setMax(PetStatGradualIncrease.MaxPETSPD);
		barSPD.setProgress(PetUniqueDate.getMonSPD());

		super.onCreate(savedInstanceState);
	}

	private int SPDUPCAL(FoodBox src) {
		float Phos = (float) src.getPhosphorus();
		float Sodi = (float) src.getSodium();
		float Potas = (float) src.getPotassium();
		int PhosCal = Math.round(Phos * 3 / 800);
		int SodiCal = Math.round(Sodi * 3 / 2400);
		int PotasCal = Math.round(Potas * 5 / 3500);
		return PhosCal + SodiCal + PotasCal;
	}

	private int DEFUPCAL(FoodBox src) {
		float Fat = (float) src.getFat();
		float Carb = (float) src.getCarbohydrate();
		float Calc = (float) src.getCalcium();
		float Sodi = (float) src.getSodium();
		int FatCal = Math.round(Fat * 9 / 65);
		int CarbCal = Math.round(Carb * 3 / 300);
		int CalcCal = Math.round(Calc * 5 / 900);
		int SodiCal = Math.round(Sodi * 5 / 2400);
		return FatCal + CarbCal + CalcCal + SodiCal;
	}

	private int ATKUPCAL(FoodBox src) {
		float Prot = (float) src.getProtein();
		float Mag = (float) src.getMagnesium();
		float Potas = (float) src.getPotassium();
		int ProtCal = Math.round(Prot * 21 / 50);
		int MagCal = Math.round(Mag * 5 / 300);
		int PotasCal = Math.round(Potas * 9 / 3500);
		return ProtCal + MagCal + PotasCal;
	}

	private int HPUPCAL(FoodBox src) {
		float Call = (float) src.getCalories();
		float Carb = (float) src.getCarbohydrate();
		float Calc = (float) src.getCalcium();
		float Mag = (float) src.getMagnesium();
		float Phos = (float) src.getPhosphorus();
		int HPCal = Math.round(Call * 20 / 2000);
		int CarbCal = Math.round(Carb * 9 / 300);
		int CalcCal = Math.round(Calc * 5 / 900);
		int MagCal = Math.round(Mag * 9 / 300);
		int PhosCal = Math.round(Phos * 9 / 800);
		return HPCal + CarbCal + CalcCal + MagCal + PhosCal;
	}

	@Override
	public void OnMonsterStatHasChange(PetStatGradualIncrease obj) {
		barHP.setProgress(PetUniqueDate.getMonHP() + obj.getCurHP());
		barATK.setProgress(PetUniqueDate.getMonATK() + obj.getCurATK());
		barDEF.setProgress(PetUniqueDate.getMonDEF() + obj.getCurDEF());
		barSPD.setProgress(PetUniqueDate.getMonSPD() + obj.getCurSPD());
	}

	@Override
	public void OnMonsterStatChangeComplete() {
		mEmoPanel.stopThread();
		Intent A = new Intent(getApplicationContext(), MainPaggerNew.class);
		A.putExtra(getString(R.string.intentkey_isfromstatup), true);
		if (isFULLCALORIES) {
			A.putExtra(getString(R.string.intentkey_ismaxcalories), true);
		} else {
			A.putExtra(getString(R.string.intentkey_ismaxcalories), false);
		}
		A.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		startActivity(A);
		PetUniqueDate.SetMonHP(PetUniqueDate.getMonHP() + HPUp);
		PetUniqueDate.SetMonATK(PetUniqueDate.getMonATK() + ATKUp);
		PetUniqueDate.SetMonDEF(PetUniqueDate.getMonDEF() + DEFUp);
		PetUniqueDate.SetMonSPD(PetUniqueDate.getMonSPD() + SPDUp);
		HistoryDatabase.insertHistory(
				getIntent().getExtras().getString(
						getString(R.string.intentkey_pathforfood)), PetUniqueDate.getLatitude(),
				PetUniqueDate.getLongtitude(), FoodResult.getID(), HistoryType.getCurrentDate(),
				HistoryType.getCurrentTime());
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}

	@Override
	public void OnPanelTouch() {
		OnMonsterStatChangeComplete();
	}

}
