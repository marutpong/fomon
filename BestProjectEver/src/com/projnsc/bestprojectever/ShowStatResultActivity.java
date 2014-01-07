package com.projnsc.bestprojectever;

import historyDatabase.HistoryDatabase;
import historyDatabase.HistoryType;

import java.util.Random;

import foodDatabase.FoodBox;
import foodDatabase.FoodDatabase;
import petShowEmotion.PetShowEmotionPanel;
import petShowEmotion.PetStatGradualIncrease;
import petShowEmotion.PetStatGradualIncrease.OnMonsterStatChange;
import preferenceSetting.PetUniqueDate;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShowStatResultActivity extends Activity implements
		OnMonsterStatChange {

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_pet_stat_up);

		int FoodClass = getIntent().getExtras().getInt(
				this.getString(R.string.intentkey_setfoodclass));
		HPUp = 0;
		ATKUp = 0;
		DEFUp = 0;
		if (FoodClass != -1) {
			FoodResult = FoodDatabase.getFoodByID(FoodClass);
			HPUp = FoodResult.getHPRandomValue();
			ATKUp = FoodResult.getATKRamdomValue();
			DEFUp = FoodResult.getDEFRandomValue();
		}
		SPDUp = getIntent().getExtras().getInt(
				getString(R.string.intentkey_setspeedstatup));

		// Save to Database

		mPetStatModule = new PetStatGradualIncrease(HPUp, ATKUp, DEFUp, SPDUp);
		mPetStatModule.setOnMonsterStatChange(this);
		mEmoPanel = (PetShowEmotionPanel) findViewById(R.id.petShowEmotionPanel1);
		mEmoPanel.setEmoKey("LOVE");
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
		Intent A = new Intent(getApplicationContext(),MainPaggerNew.class);
		A.putExtra(getString(R.string.intentkey_isfromstatup), true);
		A.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		startActivity(A);
		PetUniqueDate.SetMonHP(PetUniqueDate.getMonHP() + HPUp);
		PetUniqueDate.SetMonATK(PetUniqueDate.getMonATK() + ATKUp);
		PetUniqueDate.SetMonDEF(PetUniqueDate.getMonDEF() + DEFUp);
		PetUniqueDate.SetMonSPD(PetUniqueDate.getMonSPD() + SPDUp);
		HistoryDatabase.insertHistory(getIntent().getExtras().getString(getString(R.string.intentkey_pathforfood)), 11.1212, 21.1212, FoodResult.getID(), HistoryType.getCurrentDate(), HistoryType.getCurrentTime());
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
	}
	
}
