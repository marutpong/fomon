package petEvolution;

import petShowEmotion.EmotionSprite;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class PetEvolutionPanel extends SurfaceView implements Callback {

	private PetEvolutionSprite mPetEvol;
	private PetEvolutionThread mThread;
	private EmotionSprite mEmotion;
	public static int viewWidth;
	public static int viewHeight;
	private int emoShowOnViewX;
	private int emoShowOnViewY;

	public interface OnEvolutionFinishListener {
		void OnEvolutionFinish();
	}

	private OnEvolutionFinishListener onEvolutionFinishListener;

	public void setOnEvolutionFinishListener(
			OnEvolutionFinishListener onEvolutionFinishListener) {
		this.onEvolutionFinishListener = onEvolutionFinishListener;
	}

	public PetEvolutionPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	// Comment init() for make view on layout
	public PetEvolutionPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PetEvolutionPanel(Context context) {
		super(context);
		init();
	}

	private void init() {
		getHolder().addCallback(this);
		mThread = new PetEvolutionThread(this);
		mThread.setRunning(false);
		mPetEvol = new PetEvolutionSprite(getResources(), 0, 0);
	}

	public void RunAnimate() {
		mPetEvol.animate();
		if (mEmotion != null) {
			mEmotion.animate();
		}

		if (mPetEvol.canStartSecondEmotion() && !mPetEvol.isAllEmotionShow()) {
			Log.i("ISOK", "VICTORY");
			mEmotion = new EmotionSprite(getResources(), 0, 0, "QUESTION");
			mEmotion.setAllowRunFrameThreshole(true);
			mPetEvol.setAllEmotionShow(true);
			mEmotion.setCenPos(emoShowOnViewX, emoShowOnViewY);
			mPetEvol.setAllEmotionShow(true);
		} else if (mPetEvol.canStartFirstEmotion() && mEmotion == null) {
			mEmotion = new EmotionSprite(getResources(), 0, 0, "EXAMINATION");
			mEmotion.setAllowRunFrameThreshole(true);
			mEmotion.setCenPos(emoShowOnViewX, emoShowOnViewY);
		}

		if (mPetEvol.isFinishEvolution()) {
			if (onEvolutionFinishListener != null) {
				onEvolutionFinishListener.OnEvolutionFinish();
			}
		}
	}

	public void doDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		mPetEvol.doDraw(canvas);
		if (mEmotion != null)
			mEmotion.doDraw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		viewHeight = height;
		viewWidth = width;
		mPetEvol.setCenPos(viewWidth / 2, viewHeight / 2);
		emoShowOnViewX = (int) (viewWidth / 2);
		emoShowOnViewY = (int) (viewWidth / 2 * 0.75);
		// BACKGROUND = Bitmap
		// .createScaledBitmap(BACKGROUND, width, height, false);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// startThread();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopThread();
	}

	public void stopThread() {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

	public void startThread() {
		if (!mThread.isAlive()) {
			mThread = new PetEvolutionThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}

	public void setMonStart(int monStartID, int monFinishID) {
		mPetEvol.setMonPIC(monStartID, monFinishID);
	}

	public void stopAndRecycle() {
		stopThread();
		mPetEvol.forceRecycle();
		mEmotion.forceRecycle();
	}

}
