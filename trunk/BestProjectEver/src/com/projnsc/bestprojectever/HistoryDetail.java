package com.projnsc.bestprojectever;

import java.io.File;

import textGetter.PetDBox;
import textGetter.PetDataGet;


//import android.R;
import com.projnsc.bestprojectever.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi") public class HistoryDetail extends Activity {
	
	PetDBox pet;
	Intent old;
	
	TextView txtFoodname;
	TextView txtDate;
	TextView txtCalories;
	TextView txtProtien;
	TextView txtCarbohydrate;
	TextView txtFat;
	ImageView imageView;
	Button show_btnBack;
	//Button show_btnGoNext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_detail);
		
		old = getIntent();
		pet = PetDataGet.getDataBox(old.getExtras().getInt("index"));
		
		txtFoodname = (TextView) findViewById(R.id.txtFoodname);
		txtDate = (TextView) findViewById(R.id.txtDate);
		txtCalories = (TextView) findViewById(R.id.txtCalories);
		imageView = (ImageView) findViewById(R.id.imageView1);
		txtProtien = (TextView) findViewById(R.id.txtProtien);
		txtCarbohydrate = (TextView) findViewById(R.id.txtCarbohydrate);
		txtFat = (TextView) findViewById(R.id.txtFat);
		//show_btnGoNext = (Button) findViewById(R.id.show_btnGonext);
		show_btnBack = (Button) findViewById(R.id.show_btnBack);
		
//		String stringDate = pet.getDay() + "/" + pet.getMonthString() + "/" + pet.getYear() + " " + pet.getHourString()+ ":" + pet.getMinutedString();
		String stringDate = pet.getTimebyStringFormat();
		txtFoodname.setText(pet.getFoodType());
		txtDate.setText(stringDate);
		txtCalories.setText(" : " + pet.getKCalories());
		txtProtien.setText(" : " + pet.getProtien());
		txtCarbohydrate.setText(" : " + pet.getCarbohydrate());
		txtFat.setText(" : " + pet.getFat());
		
		setTitle(pet.getFoodType() + " " + stringDate);
		
		String text = "";
		
		File imgFile = new  File(old.getExtras().getString("imagePath")+"");
		if(imgFile.exists()){
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		    imageView.setImageBitmap(myBitmap);
		    text = "Found :" + old.getExtras().getString("imagePath");
		} else {
			text = "Not Found :" + old.getExtras().getString("imagePath");
		}
		
		//Toast.makeText(HistoryDetail.this,text ,Toast.LENGTH_LONG).show();
			
		show_btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}

		});
		
		/*show_btnGoNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inend = new Intent(getApplicationContext(), HistoryBarActivities.class);
				inend.putExtra("index", old.getExtras().getInt("index"));
				startActivity(inend);
			}
		});*/
		
	}
	
	
	
}
