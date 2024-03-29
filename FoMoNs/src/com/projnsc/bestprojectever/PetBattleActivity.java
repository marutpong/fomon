package com.projnsc.bestprojectever;

import java.util.ArrayList;
import java.util.Random;

import petBattle.ActionBox;
import petBattle.PetBattlePanel;
import petBattle.PetBattlePanel.OnFinishBattleListener;
import preferenceSetting.PetUniqueDate;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PetBattleActivity extends Activity implements
		OnFinishBattleListener {

	public static final String PetHPUpdate = "PETHPUpdate";
	public static final String ISUpdateLeftProgress = "isUPDATELEFT";
	public static final String PetHPLoss = "PETHPLoss";
	private ProgressBar LeftProgressBar;
	private ProgressBar RightProgressBar;
	private TextView LeftName;
	private TextView RightName;
	private TextView LeftPetHP;
	private TextView RightPetHP;
	private String LeftPetName;
	private String RightPetName;
	private int[] LeftPetAttribute = new int[4];
	private int[] RightPetAttribute = new int[4];
	private boolean LeftWIN = false;
	private PetBattlePanel mPanel;
	// private ArrayList<ActionBox> AcionBoxSet;

	private Handler msgHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			Bundle bundle = msg.getData();
			int updateValue = bundle.getInt(PetHPUpdate);
			int decValue = bundle.getInt(PetHPLoss);
			ProgressBar mBar = (bundle.getBoolean(ISUpdateLeftProgress)) ? LeftProgressBar
					: RightProgressBar;
			TextView mText = (bundle.getBoolean(ISUpdateLeftProgress)) ? LeftPetHP
					: RightPetHP;
			mBar.setProgress(updateValue);
			int backValue = (bundle.getBoolean(ISUpdateLeftProgress)) ? LeftPetAttribute[0]
					: RightPetAttribute[0];
			mText.setText(updateValue + " ( -" + decValue + ") / " + backValue);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.pet_battle_activity);

		LeftPetName = PetUniqueDate.getMonName();
		RightPetName = getIntent().getExtras().getString(
				getString(R.string.intentkey_getenemyname));
		LeftPetAttribute[0] = PetUniqueDate.getMonHP();
		LeftPetAttribute[1] = PetUniqueDate.getMonATK();
		LeftPetAttribute[2] = PetUniqueDate.getMonDEF();
		LeftPetAttribute[3] = PetUniqueDate.getMonSPD();
		RightPetAttribute[0] = getIntent().getExtras().getInt(
				getString(R.string.intentkey_getenemyhp));
		RightPetAttribute[1] = getIntent().getExtras().getInt(
				getString(R.string.intentkey_getenemyatk));
		RightPetAttribute[2] = getIntent().getExtras().getInt(
				getString(R.string.intentkey_getenemydef));
		RightPetAttribute[3] = getIntent().getExtras().getInt(
				getString(R.string.intentkey_getenemyspd));

		LeftName = (TextView) findViewById(R.id.txtBattleMonsterNameL);
		RightName = (TextView) findViewById(R.id.txtBattleMonsterNameR);
		LeftPetHP = (TextView) findViewById(R.id.txtMonBattleHPLEFT);
		RightPetHP = (TextView) findViewById(R.id.txtMonBattleHPRIGHT);
		LeftProgressBar = (ProgressBar) findViewById(R.id.BattleLeftProgressBar);
		RightProgressBar = (ProgressBar) findViewById(R.id.BattleRightProgressBar);
		LeftProgressBar.setMax(LeftPetAttribute[0]);
		LeftProgressBar.setProgress(LeftPetAttribute[0]);
		RightProgressBar.setMax(RightPetAttribute[0]);
		RightProgressBar.setProgress(RightPetAttribute[0]);
		RightProgressBar.setRotation(180);
		LeftName.setText(LeftPetName);
		RightName.setText(RightPetName);
		LeftPetHP.setText(LeftPetAttribute[0] + " / " + LeftPetAttribute[0]);
		RightPetHP.setText(RightPetAttribute[0] + " / " + RightPetAttribute[0]);
		mPanel = (PetBattlePanel) findViewById(R.id.petBattlePanel1);
		mPanel.setHandler(msgHandler);
		ArrayList<ActionBox> tmp = ActionSeriesGenerate();
		LeftWIN = tmp.get(tmp.size() - 1).isLeftSideAction();
		mPanel.setActionSeries(tmp);
		mPanel.setEachMonID(PetUniqueDate.getMonTypeID(), getIntent()
				.getExtras().getInt(getString(R.string.intentkey_getenemytype)));
		mPanel.setOnFinishBattleListener(this);
		mPanel.startThread();
		super.onCreate(savedInstanceState);
	}

	private ArrayList<ActionBox> ActionSeriesGenerate() {
		ArrayList<ActionBox> tmpActionSet = new ArrayList<ActionBox>();
		int Damage;
		Random randGEN = new Random();
		float randomFactor = (float) 0.2;
		int HP1 = LeftPetAttribute[0];
		int HP2 = RightPetAttribute[0];
		int ATK1 = LeftPetAttribute[1];
		int ATK2 = RightPetAttribute[1];
		int DEF1 = LeftPetAttribute[2];
		int DEF2 = RightPetAttribute[2];
		int SPD1 = LeftPetAttribute[3];
		int SPD2 = RightPetAttribute[3];
		int rATK1 = (int) (randomFactor * ATK1);
		int rATK2 = (int) (randomFactor * ATK2);
		int rDEF1 = (int) (randomFactor * DEF1);
		int rDEF2 = (int) (randomFactor * DEF2);
		if (SPD1 == SPD2)
			if (randGEN.nextInt(1) == 0)
				--SPD1;
			else
				--SPD2;
		int tSPD1 = SPD1;
		int tSPD2 = SPD2;

		while (!(HP1 == 0 || HP2 == 0)) {

			if (tSPD1 > tSPD2) {
				tSPD1 -= SPD2;
				Damage = (ATK1 - randGEN.nextInt(rATK1))
						- (DEF2 - randGEN.nextInt(rDEF2));
				if (Damage <= 0)
					Damage = 1;
				HP2 -= Damage;
				if (HP2 < 0)
					HP2 = 0;
				tmpActionSet.add(new ActionBox(true, Damage, HP2));
			} else {
				tSPD2 -= SPD1;
				Damage = (ATK2 - randGEN.nextInt(rATK2))
						- (DEF1 - randGEN.nextInt(rDEF1));
				if (Damage <= 0)
					Damage = 1;
				HP1 -= Damage;
				if (HP1 < 0)
					HP1 = 0;
				tmpActionSet.add(new ActionBox(false, Damage, HP1));
			}

			if (tSPD1 == tSPD2) {
				if (SPD1 > SPD2) {
					tSPD1 -= SPD2;
				} else {
					tSPD2 -= SPD1;
				}
			}

		}
		return tmpActionSet;
	}

	private void goToResultPAGE() {
		Intent A = new Intent(this, ShowPetVersusResultActivity.class);
		A.putExtra(getString(R.string.intentkey_battleresult), LeftWIN);
		A.putExtra(getString(R.string.intentkey_getenemyid), getIntent().getExtras().getString(getString(R.string.intentkey_getenemyid)));
		finish();
		startActivity(A);
	}

	@Override
	public void OnFinishBattle() {
		goToResultPAGE();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		mPanel.stopThread();
		goToResultPAGE();
//		super.onBackPressed();
	}
	
}
