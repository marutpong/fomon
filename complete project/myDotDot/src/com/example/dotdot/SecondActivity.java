package com.example.dotdot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bignum);
		TextView CoorXTxt = (TextView) findViewById(R.id.txtBigX);
		TextView CoorYTxt = (TextView) findViewById(R.id.txtBigY);
		Intent intent = getIntent();
		if(intent != null && intent.getExtras() != null){
			int corX = intent.getExtras().getInt("coorX");
			int corY = intent.getExtras().getInt("coorY");
			CoorXTxt.setText(corX + "");
			CoorYTxt.setText(corY + "");
		}
		
		setResult(0, intent);	//
	}
	
}
