package histogramDraw;

import histogramDraw.HistogramModule.OnStateChangeListener;

import java.util.ArrayList;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class HistogramPanel extends SurfaceView implements Callback,
		OnStateChangeListener {

	public static int defaultBasePosY;
	public static int LineStrokeWidth = 0;
	public static final int TextMarginBaseLine = 20;
	public static final int TextMarginTopLine = 10;
	private static final double LineWidthFactor = 1.6;
	private static final int BaseLineStrokeWidth = 8;
	private static final float heightFactor = (float) 0.90; //90%
	private Paint mpaint;
	private HistogramThread mThread;
	private ArrayList<HistogramLine> LineSet = new ArrayList<HistogramLine>();
	private ArrayList<Float> CalData = new ArrayList<Float>();
	private int viewHeight;
	private int viewWidth;
	private static int[] ColorList = {Color.YELLOW, Color.argb(255, 255, 0, 255), Color.GREEN, Color.argb(255, 255, 128, 0) , Color.BLUE, Color.argb(255, 128, 0, 128), Color.RED};
	private static String[] TextDayList = {"MO","TU","WE","TH","FR","SA","SU"};
	
	public HistogramPanel(Context context) {
		super(context);
		init();
	}

	public HistogramPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public HistogramPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		getHolder().addCallback(this);
		mThread = new HistogramThread(this);
		mThread.setRunning(false);

		mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mpaint.setStyle(Paint.Style.STROKE);
	}

	private void AddLinetoThread() {

		int divFactor = CalData.size();
		if (divFactor != 0) {
			float Next = (((float) viewWidth )/( (float) divFactor));
			float init = ((float) (Next)) * ((float) (0.5));

			LineStrokeWidth = (int) Math.round((viewWidth / (CalData.size() * LineWidthFactor)));
			
			synchronized (LineSet) {
				LineSet = new ArrayList<HistogramLine>();
			}
			synchronized (LineSet) {
				for (int i = 0; i < CalData.size(); i++) {
					if(CalData.size()<=7){
					LineSet.add(new HistogramLine(Math.round(init + (Next * i)), CalData
							.get(i).intValue(), mpaint,ColorList[i%7],TextDayList[i%7]));
					}else{
						LineSet.add(new HistogramLine(Math.round(init + (Next * i)), CalData
								.get(i).intValue(), mpaint,ColorList[i%7],(i+1)+""));
					}
				}
			}
		}
	}

	public void drawBaseLine(Canvas canvas) {
		/*
		 * Set brush style before draw base line
		 */
		mpaint.setColor(Color.WHITE);
		mpaint.setStrokeWidth(BaseLineStrokeWidth);
		canvas.drawLine(0, viewHeight * heightFactor, viewWidth, viewHeight
				* heightFactor, mpaint);
	}

	public void RunAnimate() {
		synchronized (LineSet) {
			for (HistogramLine L : LineSet) {
				L.animate();
			}
		}
	}

	public void doDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		
		drawBaseLine(canvas);
		synchronized (LineSet) {
			for (HistogramLine L : LineSet) {
				L.doDraw(canvas);
			}
		}
		// canvas.drawLine(0, 0, 100, 100, mpaint);
	}

	public void StartThread() {
		if (!mThread.isAlive()) {
			mThread = new HistogramThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}

	public void StopThread() {
		if (mThread.isAlive())
			mThread.setRunning(false);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		viewHeight = height;
		viewWidth = width;
		defaultBasePosY = Math.round(height * heightFactor);
		AddLinetoThread();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		StartThread();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		StopThread();
	}

	@Override
	public void onStateChangeNotify(ArrayList<Float> CalSet) {
//		 ClearLineSet();
//		 StopThread();
		synchronized (CalData) {
			CalData = CalSet;
		}
		AddLinetoThread();
//		 StartThread();
	}

}
