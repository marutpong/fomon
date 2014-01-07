package com.projnsc.bestprojectever;

import petEvolution.PetEvolutionPanel;
import petEvolution.PetEvolutionPanel.OnEvolutionFinishListener;
import preferenceSetting.PetUniqueDate;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

public class PetEvolutionActivity extends Activity implements OnEvolutionFinishListener{

	private PetEvolutionPanel mEvolPanel;
	private Button btnBackHome;
	private int MonFinishID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		MonFinishID = 11;
		
		mEvolPanel = new PetEvolutionPanel(this);
		mEvolPanel.setOnEvolutionFinishListener(this);
		setContentView(mEvolPanel);
		mEvolPanel.setMonStart(PetUniqueDate.getMonTypeID(),MonFinishID);
		
		showDialog();
		super.onCreate(savedInstanceState);
	}

	private void showDialog() {
		new AlertDialog.Builder(this).setTitle("What wrong with your pet?")
		.setMessage("Something happen with your pet!!!")
		.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mEvolPanel.startThread();
			}
		})
		.show();
	}

	@Override
	public void OnEvolutionFinish() {
		PetUniqueDate.SetMonTypeID(MonFinishID);
		mEvolPanel.stopAndRecycle();
		Intent mNext = new Intent(getApplicationContext(),
				MainPaggerNew.class);
//		Intent mNext = new Intent(getApplicationContext(),
//				PetMainActivity.class);
		mNext.putExtra(getString(R.string.intentkey_isfromevolution), true);
		finish();
		startActivity(mNext);	
	}

	protected void goBack() {

	}
	
}
