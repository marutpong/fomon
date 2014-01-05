package com.projnsc.bestprojectever;

import java.util.Random;

import petShowEmotion.PetShowEmotionPanel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowPetVersusResultActivity extends Activity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		boolean BattleWON = true;
		
		BattleWON = (rand.nextInt(2)==1)? true : false;
		
		int WONScore = 0;
		int LOSEScore = 0;

		setContentView(R.layout.activity_show_versus_result);

		mEmoPanel = (PetShowEmotionPanel) findViewById(R.id.petShowEmotionPanel2);

		BattleLOSEScore = (TextView) findViewById(R.id.txtPETBattleLose);
		BattleWONScore = (TextView) findViewById(R.id.txtPETBattleWon);
		BattleResultShow = (TextView) findViewById(R.id.txtPetBattleResult);

		BattleLOSEScore.setText(LOSEScore + "");
		BattleWONScore.setText(WONScore + "");

		if (BattleWON) {
			mEmoPanel.setEmoKey("WIN");
			//Save to Database
			BattleWONScore.setText(LOSEScore + " ( +1)");
		} else {
			mEmoPanel.setEmoKey("LOSE");
			//Save to Database
			BattleLOSEScore.setText(LOSEScore + " ( +1)");
		}
		setBattleResultText(BattleResultShow, BattleWON);

		super.onCreate(savedInstanceState);
	}

	private void setBattleResultText(TextView src, boolean WIN) {

		if (WIN) {
			src.setText(WINText[rand.nextInt(WINText.length)]);
		} else {
			src.setText(LOSEText[rand.nextInt(LOSEText.length)]);
		}

	}

}
