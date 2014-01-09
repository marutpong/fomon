package petHome;

import java.util.Random;

import preferenceSetting.PetUniqueDate;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class PetSprite {

	private int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 4;
	private int currentFrame = 0;
	private int Petwidth;
	private int Petheight;
	private int mX;
	private int mY;
	private int mSpeedX;
	private int mSpeedY;
	private Bitmap mBitmap;
	private static final Random rand = new Random();
	private boolean FirstPosSet = false;

	public PetSprite(Resources res, int x, int y) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inDither = false;
		o.inPurgeable = true;
		try {
			o.inSampleSize = 2;
			mBitmap = BitmapFactory.decodeResource(res,
					PetUniqueDate.getPetResource(), o);
		} catch (OutOfMemoryError E) {
			o.inSampleSize = 4;
			mBitmap = BitmapFactory.decodeResource(res,
					PetUniqueDate.getPetResource(), o);
		}
		Petwidth = mBitmap.getWidth() / BMP_COLUMNS;
		Petheight = mBitmap.getHeight() / BMP_ROWS;
		mX = x;
		mY = y;
		mSpeedX = randomFacetor() * (rand.nextInt(1) == 0 ? -1 : 1);
		mSpeedY = randomFacetor() * (rand.nextInt(1) == 0 ? -1 : 1);
	}

	public void setPetFirstRandomPosition(int width, int height) {
		if (!FirstPosSet) {
			mX = rand.nextInt(width);
			mY = rand.nextInt(height);
			FirstPosSet = true;
		}
	}

	public void animate() {
		mX += mSpeedX;
		mY += mSpeedY;
		checkBorders();
		currentFrame = ++currentFrame % BMP_COLUMNS;
	}

	private void checkBorders() {
		if (mX <= 0) {
			mSpeedX = -mSpeedX;
			mX = 0;
		} else if (mX + Petwidth >= PetPanel.PetFieldWidth) {
			mSpeedX = -mSpeedX;
			mX = PetPanel.PetFieldWidth - Petwidth;
		}
		if (mY <= 0) {
			mY = 0;
			mSpeedY = -mSpeedY;
		}
		if (mY + Petheight >= PetPanel.PetFieldHeight) {
			mSpeedY = -mSpeedY;
			mY = PetPanel.PetFieldHeight - Petheight;
		}
	}

	private int randomFacetor() {
		return rand.nextInt(5) + 3;
	}

	public void doDraw(Canvas canvas) {
		int srcX = currentFrame * Petwidth;
		int srcY = getAnimationRow() * Petheight;
		Rect src = new Rect(srcX, srcY, srcX + Petwidth, srcY + Petheight);
		Rect dst = new Rect(mX, mY, mX + Petwidth, mY + Petheight);
		canvas.drawBitmap(mBitmap, src, dst, null);
	}

	private int getAnimationRow() {
		double dirDouble = (Math.atan2(mSpeedX, mSpeedY) / (Math.PI / 2) + 2);
		int direction = (int) Math.round(dirDouble) % BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	}

	public boolean isCollition(float x2, float y2) {
		return x2 > mX && x2 < mX + Petwidth && y2 > mY && y2 < mY + Petheight;
	}

}
