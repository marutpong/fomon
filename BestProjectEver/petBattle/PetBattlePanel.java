package petBattle;

import java.util.ArrayList;

import com.projnsc.bestprojectever.PetBattleActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
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
	private int TURNDELAY = 0;

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
		if (TURNDELAY  <= 10)
			TURNDELAY++;
		else {
			BattleTurn++;
			if(BattleTurn%2==0)
			UpdateProgressBarFromArrayList();
		}
	}

	private void UpdateProgressBarFromArrayList() {
		try {
			ActionBox tmp = ActionBoxSet.get(BattleTurn/2);
			if (tmp.isLeftSideAction()) {
				setProgressBarATRight(tmp.getHPLeft(), tmp.getDamage());
			} else {
				setProgressBarATLeft(tmp.getHPLeft(), tmp.getDamage());
			}
		} catch (IndexOutOfBoundsException E) {

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

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		PetFieldHeight = height;
		PetFieldWidth = width;

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
