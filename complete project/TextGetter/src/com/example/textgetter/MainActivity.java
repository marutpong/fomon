package com.example.textgetter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button btnWrite;
	Button btnUpdate;
	EditText edtHead;
	EditText edtAttr;
	TextView showNum;
	PetDataGet a;
	Button btnClr;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnWrite = (Button) findViewById(R.id.btnWrite);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		edtHead = (EditText) findViewById(R.id.HeadGet);
		edtAttr = (EditText) findViewById(R.id.AttrGet);
		showNum = (TextView) findViewById(R.id.showNum);
		btnClr  = (Button) findViewById(R.id.btnClr);
		
		a = new PetDataGet(getApplicationContext());		
		
		btnWrite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				write();
			}
		});
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				update();
			}
		});
		btnClr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clear();
			}
		});
		
	}
	
	protected void update() {
			PetDataGet.Update();
			PetDBox i = PetDataGet.getDataBox(Integer.parseInt(edtHead.getText().toString()));
			String tmp = null;
			switch(Integer.parseInt(edtAttr.getText().toString())){
				case 0: tmp = i.getPicturePath(); break;
				case 1: tmp = i.getLatitude()+""; break;
				case 2: tmp = i.getLongtitude()+""; break;
				case 3: tmp = i.getFoodType()+""; break;
				case 4: tmp = i.getKCalories()+""; break;
				case 5: tmp = i.getProtien()+""; break;
				case 6: tmp = i.getCarbohydrate()+""; break;
				case 7: tmp = i.getFat()+""; break;
				case 8: tmp = i.getDay()+""; break;
				case 9: tmp = i.getMonth()+""; break;
				case 10: tmp = i.getYear()+""; break;
				case 11: tmp = i.getHour()+""; break;
				case 12: tmp = i.getMinuted()+""; break;
				default: tmp = "";
			}
			showNum.setText(tmp);
	}

	protected void write(){
		PetDataGet.Write("A B C D E F G H I J K L M");
	}

	protected void clear(){
		PetDataGet.Clear();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
