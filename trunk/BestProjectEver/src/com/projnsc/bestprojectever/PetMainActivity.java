package com.projnsc.bestprojectever;

import TabFragment.HomeFragment;
import TabPlayer.MyTabListener;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.view.Menu;
import android.view.WindowManager;

public class PetMainActivity extends Activity {

	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Tab tempTab;
		mActionBar = getActionBar();
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		tempTab = mActionBar
				.newTab()
				.setText("Home")
				.setTabListener(
						new MyTabListener(this, HomeFragment.class.getName()));
		mActionBar.addTab(tempTab);
		
//		Intent a = new Intent(this, NavGoogleMAP.class);
//		startActivity(a);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pet_main, menu);
		return true;
	}

}
