package com.example.swipeexample;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public abstract class SwipeListener implements OnGestureListener{

	private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private static int LastState = 0;
    public static final int SWIPE_UP = 1;
    public static final int SWIPE_DOWN = 2;
    public static final int SWIPE_LEFT = 3;
    public static final int SWIPE_RIGHT = 4;
	
    public int getLastState(){
    	return LastState;
    }

	 @Override
     public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
         boolean result = false;
         try {
             float diffY = e2.getY() - e1.getY();
             float diffX = e2.getX() - e1.getX();
             if (Math.abs(diffX) > Math.abs(diffY)) {
                 if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                     if (diffX > 0) {
                    	  LastState = SWIPE_RIGHT;
                    	  result = true;
                     } else {
                    	 LastState = SWIPE_LEFT;
                    	 result = true;
                     }
                 }
             } else {
                 if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                     if (diffY > 0) {
                    	 LastState = SWIPE_DOWN;
                    	 result = true;
                     } else {
                    	 LastState = SWIPE_UP;
                    	 result = true;
                     }
                 }
             }
         } catch (Exception exception) {
             exception.printStackTrace();
         }
         return result;
     }
	
}