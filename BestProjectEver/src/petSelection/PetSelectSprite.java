package petSelection;

import preferenceSetting.PetUniqueDate;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class PetSelectSprite {

	private int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 4;
	private int currentFrame = 0;
	private int Petwidth;
	private int Petheight;
	private int mX;
	private int mY;
	private Bitmap mBitmap;
	private int AnimationRow = DIRECTION_TO_ANIMATION_MAP[2]; // Always Down
	private int Key;

	public PetSelectSprite(Resources res, int x, int y, int Key) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inDither = false;
		o.inPurgeable = true;
		o.inSampleSize = 2;
		try {
			mBitmap = BitmapFactory.decodeResource(res,
					PetUniqueDate.getPetResource(Key), o);
		} catch (OutOfMemoryError E) {
			o.inSampleSize = 4;
			mBitmap = BitmapFactory.decodeResource(res,
					PetUniqueDate.getPetResource(Key), o);
		}
		Petwidth = mBitmap.getWidth() / BMP_COLUMNS;
		Petheight = mBitmap.getHeight() / BMP_ROWS;
		mX = x;
		mY = y;
		this.Key = Key;
	}

	public PetSelectSprite(Resources res, int x, int y) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inDither = false;
		o.inPurgeable = true;
		try {
			o.inSampleSize = 2;
			mBitmap = BitmapFactory.decodeResource(res,
					PetUniqueDate.getPetResource(), o);
		} catch (OutOfMemoryError E) {
			o.inSampleSize = 2;
			mBitmap = BitmapFactory.decodeResource(res,
					PetUniqueDate.getPetResource(), o);
		}
		Petwidth = mBitmap.getWidth() / BMP_COLUMNS;
		Petheight = mBitmap.getHeight() / BMP_ROWS;
		mX = x;
		mY = y;
	}

	public int getKey() {
		return Key;
	}

	public void animate() {
		currentFrame = ++currentFrame % BMP_COLUMNS;
	}

	public void doDraw(Canvas canvas) {
		int srcX = currentFrame * Petwidth;
		int srcY = AnimationRow * Petheight;
		Rect src = new Rect(srcX, srcY, srcX + Petwidth, srcY + Petheight);
		Rect dst = new Rect(mX, mY, mX + Petwidth, mY + Petheight);
		canvas.drawBitmap(mBitmap, src, dst, null);
	}

	public void setmPos(float f, float yCen) {
		mX = (int) Math.round(f) - (Petwidth / 2);
		mY = (int) Math.round(yCen) - (Petheight / 2);
	}

	// CheckForPetClick
	public boolean isCollition(float x2, float y2) {
		return x2 > mX && x2 < mX + Petwidth && y2 > mY && y2 < mY + Petheight;
	}

	public void forceRecycle() {
		if (mBitmap != null)
			mBitmap.recycle();
	}

}
