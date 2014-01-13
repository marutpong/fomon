package com.projnsc.bestprojectever;

import java.util.Random;

import connection.MyServer;

import petShowEmotion.PetShowEmotionPanel;
import petShowEmotion.PetShowEmotionPanel.OnPanelTouchListener;
import preferenceSetting.PetUniqueDate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.widget.TextView;

public class ShowPetVersusResultActivity extends Activity implements
		OnPanelTouchListener {

	private static final String[] WINText = {
			"Thank you, Master. I do my BEST",
			"Ha ha ha, No one can beat me cause of my healthy" };
	private static final String[] LOSEText = { "Sorry Master, I'm too weak",
			"I think I can do better, Please give me a food" };

	TextView BattleWONScore;
	TextView BattleLOSEScore;
	TextView BattleResultShow;
	private PetShowEmotionPanel mEmoPanel;
	private static Random rand = new Random();
	private boolean BattleWON = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		BattleWON = getIntent().getBooleanExtra(
				getString(R.string.intentkey_battleresult), false);
		int WONScore = 0;
		int LOSEScore = 0;

		setContentView(R.layout.activity_show_versus_result);

		mEmoPanel = (PetShowEmotionPanel) findViewById(R.id.petShowEmotionPanel2);
		mEmoPanel.setOnPanelTouchListener(this);

		BattleLOSEScore = (TextView) findViewById(R.id.txtPETBattleLose);
		BattleWONScore = (TextView) findViewById(R.id.txtPETBattleWon);
		BattleResultShow = (TextView) findViewById(R.id.txtPetBattleResult);

		BattleLOSEScore.setText(LOSEScore + "");
		BattleWONScore.setText(WONScore + "");

		if (BattleWON) {
			mEmoPanel.setEmoKey("WIN");
			// Save to Database
			BattleWONScore.setText(LOSEScore + " ( +1)");
		} else {
			mEmoPanel.setEmoKey("LOSE");
			// Save to Database
			BattleLOSEScore.setText(LOSEScore + " ( +1)");
		}
		setBattleResultText(BattleResultShow, BattleWON);

		super.onCreate(savedInstanceState);
	}

	private void setBattleResultText(TextView src, boolean WIN) {
		String enemyID = getIntent().getExtras().getString(
				getString(R.string.intentkey_getenemyid));
		MyServer.addFightHistory(enemyID, WIN);

		int sWIN = MyServer.GetWIN();
		int sLOSE = MyServer.GetLOSE();
//		Log.i(this.getClass().getName(), sWIN + " " + sLOSE);
		if (WIN) {
			src.setText(WINText[rand.nextInt(WINText.length)]);
		} else {
			src.setText(LOSEText[rand.nextInt(LOSEText.length)]);
		}
		PetUniqueDate.SetMonWON(sWIN);
		PetUniqueDate.SetMonLOSE(sLOSE);
//		Log.i(this.getClass().getName() + " HTML", sWIN + " " + sLOSE);
		if (BattleWON) {
			BattleWONScore.setText(sWIN + " ( +1)");
			BattleLOSEScore.setText(sLOSE + "");
		} else {
			BattleLOSEScore.setText(sLOSE + " ( +1)");
			BattleWONScore.setText(sWIN + "");
		}

	}

	private void goToHOME() {
		Intent A = new Intent(getApplicationContext(), MainPaggerNew.class);
		A.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
		startActivity(A);
	}

	@Override
	public void OnPanelTouch() {
		mEmoPanel.stopThread();
		goToHOME();
	}

}
