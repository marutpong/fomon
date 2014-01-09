package histogramDraw;

import histogramDraw.HistogramModule.OnStateChangeListener;
import historyDatabase.HistoryType;

import java.util.ArrayList;
import petSelection.PetSelectSprite;
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
	private static final double LineWidthFactor = 1.6;
	private static int BaseLineStrokeWidth = 8;
	private static final float BaseLineStrokeFactor = (float) 0.02;
	public static final float HeightMarginFactor = (float) 0.75;
	private static final float heightBaseFactor = (float) 0.90; // 90%
	private static int[] ColorList = { Color.RED, Color.YELLOW,
		Color.argb(255, 255, 0, 255), Color.GREEN,
		Color.argb(255, 255, 128, 0), Color.BLUE,
		Color.argb(255, 128, 0, 128) };
	public static long TextRealSize = 0;
	private static String[] TextDayList = { "SU", "MO", "TU", "WE", "TH", "FR", "SA",
	 };

	private Paint mpaint;
	private HistogramThread mThread;
	private ArrayList<HistogramLine> LineSet = new ArrayList<HistogramLine>();
	private ArrayList<Float> CalData = new ArrayList<Float>();
	private static int viewHeight;
	private int viewWidth;
	private PetSelectSprite mPet;
	private long startTime = 0;
	private long mSleepTime = 0;

	public static int getViewHeight() {
		return viewHeight;
	}

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
		if (divFactor == 1)
			divFactor = 2;

		float Max = -1;
		if (divFactor != 2) {
			for (Float a : CalData) {
				if (a > Max)
					Max = a;
			}
		} else {
//			if (Max < HistoryType.RequireCalPerDay
//					* HistoryType.RequireCalFactor)
//				Max = (HistoryType.RequireCalPerDay * HistoryType.RequireCalFactor);
			Max = CalData.get(0);
		}
		HistogramLine.setMaxValue(Max);

		if (divFactor != 0) {
			float Next = (((float) viewWidth) / ((float) divFactor));
			float init = ((float) (Next)) * ((float) (0.5));

			LineStrokeWidth = (int) Math
					.round((viewWidth / (divFactor * LineWidthFactor)));

			synchronized (LineSet) {
				LineSet = new ArrayList<HistogramLine>();
			}
			synchronized (LineSet) {
				if (CalData.size() == 7) {
					mPet = null;
					for (int i = 0; i < CalData.size(); i++) {
						LineSet.add(new HistogramLine(Math.round(init
								+ (Next * i)), CalData.get(i), mpaint,
								ColorList[i % 7], TextDayList[i % 7]));
					}
				} else {
					mPet = new PetSelectSprite(getResources(),
							Math.round(init), (int) (((float) viewHeight) * ((float) 0.75)));
					mPet.setmPos(Math.round(init), ((float) viewHeight) * ((float) 0.75));
					LineSet.add(new HistogramLine(Math.round(init + Next),
							CalData.get(0), mpaint, Color.WHITE,
							"Today's Calories"));
				}
				// else {
				// LineSet.add(new HistogramLine(Math.round(init
				// + (Next * i)), CalData.get(i).intValue(),
				// mpaint, ColorList[i % 7], (i + 1) + ""));
				// }
			}
		}
	}

	public void drawBaseLine(Canvas canvas) {
		/*
		 * Set brush style before draw base line
		 */
		mpaint.setColor(Color.WHITE);
		mpaint.setStrokeWidth(BaseLineStrokeWidth);
		if (CalData.size() <= 1) {
			canvas.drawLine(viewWidth / 2, viewHeight
					* heightBaseFactor, viewWidth, viewHeight
					* heightBaseFactor, mpaint);
		} else {
			canvas.drawLine(0, viewHeight * heightBaseFactor, viewWidth,
					viewHeight * heightBaseFactor, mpaint);
		}
	}

	public void RunAnimate() {

		if (mPet != null)
			synchronized (mPet) {
				if(System.currentTimeMillis()-startTime >= mSleepTime){
					mPet.animate();
					startTime = System.currentTimeMillis();
					mSleepTime = (100)
							- (System.currentTimeMillis() - startTime);
				}
			}

		synchronized (LineSet) {
			for (HistogramLine L : LineSet) {
				L.animate();
			}
		}
	}

	public void doDraw(Canvas canvas) {
		canvas.drawColor(Color.parseColor("#ffdfde"));
		
		
		if(mPet!=null){
			mPet.doDraw(canvas);
		}
		
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
		defaultBasePosY = Math.round(height * heightBaseFactor);
		BaseLineStrokeWidth = (int) (viewHeight * BaseLineStrokeFactor);
		TextRealSize = Math.round((viewHeight * 0.025)); 
		if (LineSet.size() >= 1)
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
		// ClearLineSet();
		// StopThread();
		synchronized (CalData) {
			CalData = CalSet;
		}
		AddLinetoThread();
		// StartThread();
	}

}
