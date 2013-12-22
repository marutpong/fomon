package com.projnsc.bestprojectever;

import java.util.HashSet;
import java.util.Set;

import monsterEatPhoto.EatPanel;
import monsterEatPhoto.Pixel;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MonEatingPhotoActivity extends Activity {

	private static final int MENU_CLEAR_ID = 1000;
	private Set<Pixel> mPool = new HashSet<Pixel>();
	EatPanel mPanel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mPanel = new EatPanel(this);
		mPanel.setPixelPool(mPool);
		setContentView(mPanel);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_CLEAR_ID:
			mPanel.clearMPool();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_CLEAR_ID, Menu.NONE, "Clear");
		return true;
	}

}
