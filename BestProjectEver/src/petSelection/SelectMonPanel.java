package petSelection;

import java.util.ArrayList;

import com.projnsc.bestprojectever.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class SelectMonPanel extends SurfaceView implements Callback{

//	private PetSprite mPet;
	private ArrayList<PetSelectSprite> mPetSet;
	private PetFieldThread mThread;
	public static int viewWidth;
	public static int viewHeight;
	private Bitmap BACKGROUND;
	
	public interface OnCharacterTouchListener{
		void CharacterTouched(int ID);
	}
	
	private OnCharacterTouchListener onCharacterTouchListener;
	
	public void setOnCharacterTouchListener(
			OnCharacterTouchListener onCharacterTouchListener) {
		this.onCharacterTouchListener = onCharacterTouchListener;
	}
	
	public SelectMonPanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SelectMonPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SelectMonPanel(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		mPetSet = new ArrayList<PetSelectSprite>();
		getHolder().addCallback(this);
		mThread = new PetFieldThread(this);
		mPetSet.add(new PetSelectSprite(getResources(), 0, 0, 1));
		mPetSet.add(new PetSelectSprite(getResources(), 0, 0, 11));
		BACKGROUND = BitmapFactory.decodeResource(getResources(), R.drawable.bg_forest);
	}

	private void mPetSetFixPosition() {
		float Size = (float) mPetSet.size();
		float Next = ((float) viewWidth) / ((float) Size);
		float init = Next * ((float) 0.5);
		float YCen = (float) (viewHeight / 2);
		for(int i=0 ;i<Size; i++){
			mPetSet.get(i).setmPos(init + (Next*i),YCen);
		}
		
	}
	
	@Override
		public boolean onTouchEvent(MotionEvent event) {

			int Key = -1;
			for(PetSelectSprite mPet: mPetSet){
				if(mPet.isCollition(event.getX(), event.getY())){
					Key = mPet.getKey();
				}
			}
		
			if(Key != -1 && onCharacterTouchListener != null){
				onCharacterTouchListener.CharacterTouched(Key);
			}
			
			return super.onTouchEvent(event);
		}
	
	public void RunAnimate() {
//		mPet.animate();
		for(PetSelectSprite mPet: mPetSet){
			mPet.animate();
		}
	}
	
	public void doDraw(Canvas canvas){
//		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(BACKGROUND, 0, 0, null);
//		mPet.doDraw(canvas);
		for(PetSelectSprite mPet: mPetSet){
			mPet.doDraw(canvas);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		viewHeight = height;
		viewWidth = width;
		mPetSetFixPosition();
//		mPet.setPetFirstRandomPosition(width, height);
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
