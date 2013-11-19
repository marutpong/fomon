package com.example.dotdot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DotsView extends View {

	public DotsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public DotsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DotsView(Context context) {
		super(context);
		init();
	}

	private void init() {
		paint = new Paint();
	}

	public interface DotsViewDataSource{
		Dot getItem(int Position);
		int getCount();
	}
	
	private DotsViewDataSource dataSource;
	private Paint paint;
	
	public void setDataSource(DotsViewDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
			if(this.onDotsTouchListener!=null){
				this.onDotsTouchListener.onDotsTouch(this, event.getX(), event.getY());
			}
		}
		return super.onTouchEvent(event);
	}
	
	public interface DotsTouchListener{
		void onDotsTouch(DotsView dotsView, float coorX, float coorY);
	}
	
	DotsTouchListener onDotsTouchListener;
	
	public void setOnDotsTouchListener(DotsTouchListener onDotsTouchListener) {
		this.onDotsTouchListener = onDotsTouchListener;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		if(dataSource != null){
			for(int position=0; position<dataSource.getCount(); position++){
				Dot dot = dataSource.getItem(position);
				paint.setColor(Color.BLUE);
				canvas.drawCircle(dot.getCorX(), dot.getCorY(), 10, paint);
			}
		}
	}
	
}
