package monsterEatPhoto;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.projnsc.bestprojectever.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class EatPanel extends SurfaceView implements Callback {

	private static final int CIRCLE_FACTOR = 20;
	private static boolean ClearView = false;
	private static int EatRadius;
	private Bitmap mFood, mTFood;
	private EatThread mThread;
	private static int PanelWidth;
	private static int PanelHight;
	private Set<Pixel> mPool;
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private EatingSprite mEating;
	private Canvas tempCanvas;
	private Queue<Pixel> QPixel = new LinkedList<Pixel>();

	public EatPanel(Context context) {
		super(context);
		init();
	}

	public void RunAnimate() {
		mEating.animate();
	}

	public void setPixelPool(Set<Pixel> mPool) {
		this.mPool = mPool;
	}

	@SuppressLint("NewApi")
	private void init() {
		getHolder().addCallback(this);
		mFood = BitmapFactory.decodeResource(getResources(), R.drawable.dummyfoodeattest);
		mFood.setHasAlpha(true);
		mTFood = mFood.copy(Config.ARGB_8888, true);
		mPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
		mThread = new EatThread(this);
		mEating = new EatingSprite(getResources(), 0, 0);
		tempCanvas = new Canvas(mTFood);
	}

	public void doDraw(Canvas canvas) {
		Log.i("SIZE",mPool.size()+"");
		canvas.drawBitmap(mFood, 0, 0, null);
		canvas.drawARGB(170, 0, 0, 0);
		if (ClearView) {
			tempCanvas.drawBitmap(mFood, 0, 0, null);
			ClearView = false;
		}
		
		synchronized (QPixel) {
			while(!QPixel.isEmpty()){
				Pixel tmp = QPixel.remove();
				tempCanvas.drawCircle(tmp.getX(), tmp.getY(), EatRadius, mPaint);
			}
		}
		
		canvas.drawBitmap(mTFood, 0, 0, null);
		mEating.doDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int XPos = (int) event.getX();
		int YPos = (int) event.getY();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			synchronized (mPool) {
				mPool.add(new Pixel(XPos, YPos));
				mEating.setPos(XPos, YPos);
				QPixel.add(new Pixel(XPos, YPos));
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			synchronized (mPool) {
				mPool.add(new Pixel(XPos, YPos));
				mEating.setPos(XPos, YPos);
				QPixel.add(new Pixel(XPos, YPos));
			}
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		PanelHight = height;
		PanelWidth = width;
		mFood = Bitmap.createScaledBitmap(mFood, PanelWidth, PanelHight, false);
		EatRadius = (int) (Math.min(width, height) / CIRCLE_FACTOR);
		clearMPool();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!mThread.isAlive()) {
			mThread = new EatThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}

	public void clearMPool() {
		synchronized (mPool) {
			mPool.clear();
			QPixel.clear();
		}
		ClearView = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		clearMPool();
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

}
