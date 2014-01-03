package petShowEmotion;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.projnsc.bestprojectever.R;

public class EmotionSprite {

	private static int runFrameThreashole = 24;
	private static final int BMP_ROWS = 10;
	private static final int BMP_COLUMNS = 8;
	private int currentFrame = 0;
	private int Petwidth;
	private int Petheight;
	private int mX;
	private int mY;
	private int runFrame = 0;
	private Bitmap mBitmap;
	private int AnimationRow; // Always Down

	public EmotionSprite(Resources res, int x, int y, String emoKey) {
		mBitmap = BitmapFactory.decodeResource(res, R.drawable.rpgmakervxballoon);
		mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth()*3, mBitmap.getHeight()*3,
				false);
		Petwidth = mBitmap.getWidth() / BMP_COLUMNS;
		Petheight = mBitmap.getHeight() / BMP_ROWS;
		mX = x;
		mY = y;
		this.AnimationRow = KeytoInt(emoKey);
	}
	
	public void setEmoKey(String emoKey){
		this.AnimationRow = KeytoInt(emoKey);
		runFrame = 0;
	}
	
	private int KeytoInt(String emoKey) {
		if(emoKey=="LOVE")
			return 3;
		if(emoKey=="WIN")
			return 2;
		if(emoKey=="LOSE")
			return 6;
		return -1;
	}

	public EmotionSprite(Resources res, int x, int y) {
		mBitmap = BitmapFactory.decodeResource(res, R.drawable.rpgmakervxballoon);
		mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth()*3, mBitmap.getHeight()*3,
				false);
		Petwidth = mBitmap.getWidth() / BMP_COLUMNS;
		Petheight = mBitmap.getHeight() / BMP_ROWS;
		mX = x;
		mY = y;
		AnimationRow = 4;
	}

	public static int getRunFrameThreashole() {
		return runFrameThreashole;
	}
	
	public void animate() {
		currentFrame = ++currentFrame % BMP_COLUMNS;
		runFrame++;
	}
	
	public int getRunFrame() {
		return runFrame;
	}
	
	public void doDraw(Canvas canvas) {
		int srcX = currentFrame * Petwidth;
		int srcY = AnimationRow * Petheight;
		Rect src = new Rect(srcX, srcY, srcX + Petwidth, srcY + Petheight);
		Rect dst = new Rect(mX, mY, mX + Petwidth, mY + Petheight);
		canvas.drawBitmap(mBitmap, src, dst, null);
	}

	public void setCenPos(float f, float yCen) {
		mX = (int) Math.round(f) - (Petwidth / 2);
		mY = (int) Math.round(yCen) - (Petheight / 2);
	}

	//CheckForPetClick
	public boolean isCollition(float x2, float y2) {
		return x2 > mX && x2 < mX + Petwidth && y2 > mY && y2 < mY + Petheight;
	}
	
}
