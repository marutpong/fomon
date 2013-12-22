package petHome;

import com.projnsc.bestprojectever.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class PetPanel extends SurfaceView implements Callback{

	private PetSprite mPet;
	private PetFieldThread mThread;
	public static int PetFieldWidth;
	public static int PetFieldHeight;
	private Bitmap BACKGROUND;
	
	public PetPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PetPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PetPanel(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		getHolder().addCallback(this);
		mThread = new PetFieldThread(this);
		mPet = new PetSprite(getResources(), 0, 0);
		BACKGROUND = BitmapFactory.decodeResource(getResources(), R.drawable.bg_forest);
	}

	public void RunAnimate() {
		mPet.animate();
	}
	
	public void doDraw(Canvas canvas){
//		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(BACKGROUND, 0, 0, null);
		mPet.doDraw(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		PetFieldHeight = height;
		PetFieldWidth = width;
		mPet.setPetFirstRandomPosition(width, height);
		BACKGROUND = Bitmap.createScaledBitmap(BACKGROUND, width, height, false);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(!mThread.isAlive()){
			mThread = new PetFieldThread(this);
			mThread.setRunning(true);
			mThread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(mThread.isAlive()){
			mThread.setRunning(false);
		}
	}

}
