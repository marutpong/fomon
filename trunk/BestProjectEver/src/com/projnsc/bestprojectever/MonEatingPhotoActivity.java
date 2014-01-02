package com.projnsc.bestprojectever;

import java.util.HashSet;
import java.util.Set;

import monsterEatPhoto.EatPanel;
import monsterEatPhoto.Pixel;
import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MonEatingPhotoActivity extends Activity {

	private static final int MENU_CLEAR_ID = 1000;
	private Set<Pixel> mPool = new HashSet<Pixel>();
	EatPanel mPanel;
	Button btnClear;
	Button btnCancel;
	Button btnFinish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mon_eat_pic);
		// mPanel = new EatPanel(this);
		mPanel = (EatPanel) findViewById(R.id.eatPanel1);
		mPanel.setPixelPool(mPool);
		// setContentView(mPanel);
		btnCancel = (Button) findViewById(R.id.btnEatCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnCancelPress();
			}
		});
		btnClear = (Button) findViewById(R.id.btnEatClear);
		btnClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPanel.clearMPool();
			}
		});
		btnFinish = (Button) findViewById(R.id.btnEatFinish);
		btnFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EataskFinish();
			}
		});

	}

	protected void EataskFinish() {
		new AlertDialog.Builder(this)
				.setTitle("Finish Eating!")
				.setMessage("Do you eat all food")
				.setPositiveButton("Yes",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								gotoCalculatePicEat();
							}
						}).setNegativeButton("No", null).show();
	}

	protected void gotoCalculatePicEat() {

	}

	protected void btnCancelPress() {
		finish();
	}

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case MENU_CLEAR_ID:
	// mPanel.clearMPool();
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_CLEAR_ID, Menu.NONE, "Clear");
		return true;
	}

}
