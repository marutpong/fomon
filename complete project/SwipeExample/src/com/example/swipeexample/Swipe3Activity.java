package com.example.swipeexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

public class Swipe3Activity extends Activity{
	
	GestureDetector detector;
	StateCheck swipeCheck;
	TextView txtView2;
	TextView txtView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        swipeCheck = new StateCheck();
        detector = new GestureDetector(this, swipeCheck);
        txtView2 = (TextView) findViewById(R.id.textView1);
        txtView2.setText("You are in the Third Page");
        txtView = (TextView) findViewById(R.id.textView);
        txtView.setText("Please swipe Left or Right to Change Page");
    }   
    
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (detector.onTouchEvent(event)){
			switch(swipeCheck.getLastState()){
			case StateCheck.SWIPE_UP:
				txtView.setText("You have swipe up, But I want you to swipe left or right");
				break;
			case StateCheck.SWIPE_DOWN:
				txtView.setText("You have swipe down, But I want you to swipe left or right");
				break;
			case StateCheck.SWIPE_LEFT:
				NextActivity(2);
				break;
			case StateCheck.SWIPE_RIGHT:
				NextActivity(1);
				break;
			}
			return true;
		}
		else
			return false;
	}

	private void NextActivity(int i) {
		Intent intend = null;
		switch(i){
		case 1: 
			intend = new Intent(this, SwipeActivity.class);
			break;
		case 2: 
			intend = new Intent(this, Swipe2Activity.class);
			break;
		case 3: 
			intend = new Intent(this, Swipe3Activity.class);
			break;
		}
		startActivity(intend);
	}

}
