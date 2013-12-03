package com.example.tabcreate;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Tab tab = ab
				.newTab()
				.setText("Frag1")
				.setTabListener(
						new MyTabListener(this, FragmentA.class.getName()));
		ab.addTab(tab);

		tab = ab.newTab()
				.setText("Frag2")
				.setTabListener(
						new MyTabListener(this, FragmentB.class.getName()));
		ab.addTab(tab);
		
		tab = ab.newTab()
				.setText("Frag2")
				.setTabListener(
						new MyTabListener(this, FragmentB.class.getName()));
		ab.addTab(tab);
		
		tab = ab.newTab()
				.setText("Frag2")
				.setTabListener(
						new MyTabListener(this, FragmentB.class.getName()));
		ab.addTab(tab);
		
		tab = ab.newTab()
				.setText("Frag2")
				.setTabListener(
						new MyTabListener(this, FragmentB.class.getName()));
		ab.addTab(tab);

//		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.action_settings) {
			// Handle Settings
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
