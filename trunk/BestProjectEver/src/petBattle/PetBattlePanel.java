package petBattle;

import java.util.ArrayList;

import com.projnsc.bestprojectever.PetBattleActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class PetBattlePanel extends SurfaceView implements Callback {

	private PetBattleThread mThread;
	public static int PetFieldWidth;
	public static int PetFieldHeight;
	private Handler msgHandler;
	private ArrayList<ActionBox> ActionBoxSet;
	private int BattleTurn = 0;
	private PetBattleSprite LeftBitmap;
	private PetBattleSprite RightBitmap;

	public interface OnFinishBattleListener {
		void OnFinishBattle();
	}

	private OnFinishBattleListener onFinishBattleListener;

	public void setOnFinishBattleListener(
			OnFinishBattleListener onFinishBattleListener) {
		this.onFinishBattleListener = onFinishBattleListener;
	}

	public PetBattlePanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PetBattlePanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PetBattlePanel(Context context) {
		super(context);
		init();
	}

	private void init() {
		getHolder().addCallback(this);
		mThread = new PetBattleThread(this);
	}

	public void RunAnimate() {
		try {
			ActionBox tmp = ActionBoxSet.get(BattleTurn);
			// Log.i(this.getClass().getName(),BattleTurn + " " +
			// tmp.isLeftSideAction());
			if (tmp.isLeftSideAction()) {
				if (!LeftBitmap.isInitialSpeed() && !LeftBitmap.isFinish()) {
					LeftBitmap.InitialSpeed();
				}
				if (LeftBitmap.isWillBack() && !LeftBitmap.isActionOnBar()) {
					UpdateProgressBarFromArrayList();
					LeftBitmap.setActionOnBar(true);
				}
				if (LeftBitmap.isFinish()) {
					BattleTurn++;
					LeftBitmap.clearSpeed();
				}
				LeftBitmap.animate(RightBitmap);
				RightBitmap.animate();
			} else {
				// Log.i("Right Bitmap",RightBitmap.isInitialSpeed() + " " +
				// RightBitmap.isFinish());
				if (!RightBitmap.isInitialSpeed() && !RightBitmap.isFinish()) {
					RightBitmap.InitialSpeed();
				}
				if (RightBitmap.isWillBack() && !RightBitmap.isActionOnBar()) {
					UpdateProgressBarFromArrayList();
					RightBitmap.setActionOnBar(true);
				}
				if (RightBitmap.isFinish()) {
					BattleTurn++;
					RightBitmap.clearSpeed();
				}
				RightBitmap.animate(LeftBitmap);
				LeftBitmap.animate();
			}
		} catch (IndexOutOfBoundsException E) {
			if (onFinishBattleListener != null) {
				onFinishBattleListener.OnFinishBattle();
			}
		}
		// UpdateProgressBarFromArrayList();
	}

	public void setEachMonID(int Left, int Right) {
		LeftBitmap = new PetBattleSprite(getResources(), Left, true);
		RightBitmap = new PetBattleSprite(getResources(), Right, false);
	}

	private void UpdateProgressBarFromArrayList() {
		try {
			ActionBox tmp = ActionBoxSet.get(BattleTurn);
			if (tmp.isLeftSideAction()) {
				setProgressBarATRight(tmp.getHPLeft(), tmp.getDamage());
			} else {
				setProgressBarATLeft(tmp.getHPLeft(), tmp.getDamage());
			}
		} catch (IndexOutOfBoundsException E) {
			if (onFinishBattleListener != null) {
				onFinishBattleListener.OnFinishBattle();
			}
		}
	}

	private void setProgressBarATRight(int value, int dmg) {
		Message msg = msgHandler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putBoolean(PetBattleActivity.ISUpdateLeftProgress, false);
		bundle.putInt(PetBattleActivity.PetHPUpdate, value);
		bundle.putInt(PetBattleActivity.PetHPLoss, dmg);
		msg.setData(bundle);
		msgHandler.sendMessage(msg);
	}

	private void setProgressBarATLeft(int value, int dmg) {
		Message msg = msgHandler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putBoolean(PetBattleActivity.ISUpdateLeftProgress, true);
		bundle.putInt(PetBattleActivity.PetHPUpdate, value);
		bundle.putInt(PetBattleActivity.PetHPLoss, dmg);
		msg.setData(bundle);
		msgHandler.sendMessage(msg);
	}

	public void doDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		LeftBitmap.doDraw(canvas);
		RightBitmap.doDraw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		PetFieldHeight = height;
		PetFieldWidth = width;
		LeftBitmap.setCenPos((int) Math.round(width * 0.2),
				(int) Math.round(height));
		RightBitmap.setCenPos((int) Math.round(width * 0.8),
				(int) Math.round(height));
		LeftBitmap.setSpeedFromView();
		RightBitmap.setSpeedFromView();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// startThread();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

	public void startThread() {
		if (!mThread.isAlive()) {
			mThread = new PetBattleThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}

	public void setHandler(Handler msgHandler) {
		this.msgHandler = msgHandler;
	}

	public void setActionSeries(ArrayList<ActionBox> actionSeriesGenerate) {
		ActionBoxSet = actionSeriesGenerate;
	}

}
