package com.projnsc.bestprojectever;

import petShowEmotion.PetShowEmotionPanel;
import petShowEmotion.PetStatGradualIncrease;
import petShowEmotion.PetStatGradualIncrease.OnMonsterStatChange;
import preferenceSetting.PetUniqueDate;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.pet_stat_up_activity);

		int HPUp = 10;
		int ATKUp = 5;
		int DEFUp = 3;
		int SPDUp = 2;
		
		mPetStatModule = new PetStatGradualIncrease(HPUp, ATKUp, DEFUp, SPDUp);
		mPetStatModule.setOnMonsterStatChange(this);
		mEmoPanel = (PetShowEmotionPanel) findViewById(R.id.petShowEmotionPanel1);
		mEmoPanel.setEmoKey("LOVE");
		mEmoPanel.setPetStatModule(mPetStatModule);

		txtHpShow = (TextView) findViewById(R.id.txtHPemo);
		txtATKShow = (TextView) findViewById(R.id.txtATKemo);
		txtDEFShow = (TextView) findViewById(R.id.txtDEFemo);
		txtSPDShow = (TextView) findViewById(R.id.txtSPDemo);

		txtHpShow.setText((PetUniqueDate.getMonHP()+HPUp) + " ( +" +HPUp + " )" + " / "
				+ PetStatGradualIncrease.MaxPETHP);
		txtATKShow.setText((PetUniqueDate.getMonATK()+ATKUp) + " ( +" +ATKUp + " )" + " / "
				+ PetStatGradualIncrease.MaxPETATK);
		txtDEFShow.setText((PetUniqueDate.getMonDEF()+DEFUp) + " ( +" +DEFUp + " )" + " / "
				+ PetStatGradualIncrease.MaxPETDEF);
		txtSPDShow.setText((PetUniqueDate.getMonSPD()+SPDUp) + " ( +" +SPDUp + " )" + " / "
				+ PetStatGradualIncrease.MaxPETSPD);

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
		
	}

}
