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
		
		a = new PetDataGet(this);

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
			showNum.setText(PetDataGet.getData(Integer.parseInt(edtHead.getText().toString()),Integer.parseInt(edtAttr.getText().toString())));
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
