package com.projnsc.bestprojectever;

import processImage.Hist_Phog;
import processImage.Hist_Phog.OnImageProcessListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;


public class PhotoProcessActivity extends Activity implements
		OnImageProcessListener {

	private ProgressDialog mPDialog = null;
	private Hist_Phog hist_phog;
	private Thread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		String fullPath = getIntent().getExtras().getString(
				getString(R.string.intentkey_pathforfood));
		
		setContentView(R.layout.thetemp_activity);
		hist_phog = new Hist_Phog(this);
		hist_phog.setOnImageProcessListener(this);
		hist_phog.setPath(fullPath);
		mThread = new Thread(hist_phog);
		mPDialog = ProgressDialog.show(this, "Waiting...",
				"Image process is running... Please wait", true, false, null);
		mThread.start();
		super.onCreate(savedInstanceState);
	}

	@Override
	public void OnImageProcessFinish(int[] classFood) {
		mPDialog.dismiss();
		
		mThread.interrupt();
		
		Intent A = new Intent(this, TheTempActivity.class);
//		A.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		A.putExtra(getString(R.string.intentkey_analysisfoodclass1),
				classFood[0]);
		A.putExtra(getString(R.string.intentkey_analysisfoodclass2),
				classFood[1]);
		A.putExtra(
				getString(R.string.intentkey_pathforfood),
				getIntent().getExtras().getString(
						getString(R.string.intentkey_pathforfood)));
		finish();
		startActivity(A);
	}

}
