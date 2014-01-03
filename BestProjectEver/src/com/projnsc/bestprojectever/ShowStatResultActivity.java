package com.projnsc.bestprojectever;

import petShowEmotion.PetShowEmotionPanel;
import petShowEmotion.PetStatGradualIncrease;
import petShowEmotion.PetStatGradualIncrease.OnMonsterStatChange;
import preferenceSetting.PetUniqueDate;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class ShowStatResultActivity extends Activity implements OnMonsterStatChange{

	private PetShowEmotionPanel mEmoPanel;
	private ProgressBar barHP;
	private ProgressBar barATK;
	private ProgressBar barDEF;
	private ProgressBar barSPD;
	private PetStatGradualIncrease mPetStatModule;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.pet_stat_up_activity);
		
		mPetStatModule = new PetStatGradualIncrease(10,5,3,2);
		mPetStatModule.setOnMonsterStatChange(this);
		mEmoPanel = (PetShowEmotionPanel) findViewById(R.id.petShowEmotionPanel1);
		mEmoPanel.setEmoKey("LOVE");
		mEmoPanel.setPetStatModule(mPetStatModule);
		
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
	
	
	
}
