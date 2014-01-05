package petBattle;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class PetBattlePanel extends SurfaceView implements Callback {

	private PetBattleThread mThread;
	public static int PetFieldWidth;
	public static int PetFieldHeight;
	private Handler msgHandler;
	
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
		if (!mThread.isAlive()) {
			mThread = new PetBattleThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mThread.isAlive()) {
			mThread.setRunning(false);
		}
	}

	public void setHandler(Handler msgHandler) {
		this.msgHandler = msgHandler;
	}

}
