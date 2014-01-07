package petShowEmotion;

import com.projnsc.bestprojectever.PetBattleActivity;

import petSelection.PetSelectSprite;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class PetShowEmotionPanel extends SurfaceView implements Callback {

	private PetSelectSprite mPetShow;
	private EmotionSprite mEmotion;
	private PetShowEmotionThread mThread;
	public static int viewWidth;
	public static int viewHeight;
	private String emoKey;
	private PetStatGradualIncrease mPetStatModule;

	public interface OnPanelTouchListener {
		void OnPanelTouch();
	}

	private OnPanelTouchListener onPanelTouchListener;

	public void setOnPanelTouchListener(
			OnPanelTouchListener onPanelTouchListener) {
		this.onPanelTouchListener = onPanelTouchListener;
	}

	// private Bitmap BACKGROUND;
	public PetShowEmotionPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	// Comment init() for make view on layout
	public PetShowEmotionPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PetShowEmotionPanel(Context context) {
		super(context);
		init();
	}

	public PetShowEmotionPanel(Context context, String emoKey) {
		super(context);
		init(emoKey);
	}

	private void init() {
		getHolder().addCallback(this);
		mThread = new PetShowEmotionThread(this);
		mThread.setRunning(false);
		mPetShow = new PetSelectSprite(getResources(), 0, 0);
		mEmotion = new EmotionSprite(getResources(), 0, 0);
	}

	private void init(String emoKey) {
		getHolder().addCallback(this);
		mThread = new PetShowEmotionThread(this);
		mThread.setRunning(false);
		mPetShow = new PetSelectSprite(getResources(), 0, 0);
		mEmotion = new EmotionSprite(getResources(), 0, 0, emoKey);
	}

	public void setEmoKey(String emoKey) {
		if (mEmotion != null)
			mEmotion.setEmoKey(emoKey);
		else
			this.emoKey = emoKey;
	}

	private void SetEmotionShowSpritePosition() {
		float Width = viewWidth / 2;
		float Height = viewHeight / 2;
		mPetShow.setmPos(Width, (float) (Height * 1.25));
		mEmotion.setCenPos(Width, (float) (Height * 0.75));
	}

	public void RunAnimate() {
		mPetShow.animate();
		mEmotion.animate();
		if (mPetStatModule != null)
			mPetStatModule.runIncrease();
	}

	public void doDraw(Canvas canvas) {
		// canvas.drawBitmap(BACKGROUND, 0, 0, null);
		canvas.drawColor(Color.WHITE);
		mPetShow.doDraw(canvas);
		// if (mEmotion.getRunFrame() <= EmotionSprite.getRunFrameThreashole())
		mEmotion.doDraw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		viewHeight = height;
		viewWidth = width;
		SetEmotionShowSpritePosition();
		// BACKGROUND = Bitmap
		// .createScaledBitmap(BACKGROUND, width, height, false);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			if(onPanelTouchListener!=null){
				onPanelTouchListener.OnPanelTouch();
			}
			return true;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		startThread();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopThread();
	}

	public void setPetStatModule(PetStatGradualIncrease mPetStatModule) {
		this.mPetStatModule = mPetStatModule;
	}

	public void stopThread() {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

	public void startThread() {
		if (!mThread.isAlive()) {
			mThread = new PetShowEmotionThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}

}
