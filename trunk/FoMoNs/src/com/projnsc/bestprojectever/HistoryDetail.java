package com.projnsc.bestprojectever;

import java.io.File;

import foodDatabase.FoodBox;
import foodDatabase.FoodDatabase;
import historyDatabase.HistoryBox;
import historyDatabase.HistoryDatabase;

//import android.R;
import com.projnsc.bestprojectever.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;

@SuppressLint("NewApi")
public class HistoryDetail extends Activity {

	HistoryBox his;
	Intent old;

	TextView txtFoodname;
	TextView txtDate;
	TextView txtCalories;
	TextView txtProtien;
	TextView txtCarbohydrate;
	TextView txtFat;
	TextView txtCalcium;
	TextView txtMagnesium;
	TextView txtPotassium;
	TextView txtSodium;
	TextView txtPhosphorus;

	ImageView imageView;
	Button show_btnBack;
	//Button show_btnTest;

	// Button show_btnGoNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_history_detail);

		old = getIntent();
		his = HistoryDatabase.getDataBox(old.getExtras().getInt("index"));

		txtFoodname = (TextView) findViewById(R.id.txtFoodname);
		txtDate = (TextView) findViewById(R.id.txtDate);
		txtCalories = (TextView) findViewById(R.id.txtCalories);
		imageView = (ImageView) findViewById(R.id.imageView1);
		txtProtien = (TextView) findViewById(R.id.txtProtien);
		txtCarbohydrate = (TextView) findViewById(R.id.txtCarbohydrate);
		txtFat = (TextView) findViewById(R.id.txtFat);
		txtCalcium = (TextView) findViewById(R.id.txtCalcium);
		txtMagnesium = (TextView) findViewById(R.id.txtMagnesium);
		txtPotassium = (TextView) findViewById(R.id.txtPotassium);
		txtSodium = (TextView) findViewById(R.id.txtSodium);
		txtPhosphorus = (TextView) findViewById(R.id.txtPhosphorus);

		// show_btnGoNext = (Button) findViewById(R.id.show_btnGonext);
		show_btnBack = (Button) findViewById(R.id.show_btnBack);
//		show_btnTest = (Button) findViewById(R.id.btb_test);

		FoodBox food = FoodDatabase.getFoodByID(his.getFoodType());

		String stringDate = his.getTimebyStringFormat();
		String collon = " : ";

		txtFoodname.setText(food.getName());
		txtDate.setText(stringDate);
		txtCalories.setText(collon + food.getCalories());
		txtProtien.setText(collon + food.getProtein());
		txtCarbohydrate.setText(collon + food.getCarbohydrate());
		txtFat.setText(collon + food.getFat());

		txtCalcium.setText(collon + food.getCalcium());
		txtMagnesium.setText(collon + food.getMagnesium());
		txtPotassium.setText(collon + food.getPotassium());
		txtSodium.setText(collon + food.getSodium());
		txtPhosphorus.setText(collon + food.getPhosphorus());

		setTitle(food.getName() + " " + stringDate);

		// String text = "";

		File imgFile = new File(old.getExtras().getString("imagePath") + "");
		if (imgFile.exists()) {
			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());
			imageView.setImageBitmap(myBitmap);
			// text = "Found :" + old.getExtras().getString("imagePath");
		} else {
			// text = "Not Found :" + old.getExtras().getString("imagePath");
		}

		// Toast.makeText(HistoryDetail.this,text ,Toast.LENGTH_LONG).show();

		show_btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}

		});

		/*show_btnTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				todayList();
			}
		});
*/		/*
		 * show_btnGoNext.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent inend = new Intent(getApplicationContext(),
		 * HistoryBarActivities.class); inend.putExtra("index",
		 * old.getExtras().getInt("index")); startActivity(inend); } });
		 */

	}

	public void todayList() {
//		String msg = "";
//		int type = FoodDatabase.Enum.calories.ordinal();
//		msg = String.valueOf(HistoryDatabase.getSumNutritionOfDate(
//				HistoryType.getCurrentDate(), type));
//		//Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
