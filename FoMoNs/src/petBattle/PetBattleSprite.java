package petBattle;

import java.util.Random;

import preferenceSetting.PetUniqueDate;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class PetBattleSprite {

	// private int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
	private static final float SFFactor = (float) 0.05;
	private static final float BWFactor = (float) 0.075;
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 4;
	private static int SpeedForward;
	private static int SpeedBackward;
	private int currentFrame = 0;
	private int currentAnimationRow = 0;
	private int Petwidth;
	private int Petheight;
	private int showX;
	private int showY;
	private int realX;
	private int realY;
	private int baseShowX = -1;
	private int baseShowY = -1;
	private Bitmap mBitmap;
	private Random rand = new Random();
	private int mSpeed;
	private boolean isLeft;
	private boolean isFinish = false;
	private boolean isInitialSpeed = false;
	private boolean isActionOnBar = false;
	private boolean isCleared = false;

	public PetBattleSprite(Resources res, int PetKey, boolean isLeft) {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inDither = false;
		o.inPurgeable = true;
		try {
			mBitmap = BitmapFactory.decodeResource(res,
					PetUniqueDate.getPetResource(PetKey), o);
		} catch (OutOfMemoryError E) {
			o.inSampleSize = 2;
			mBitmap = BitmapFactory.decodeResource(res,
					PetUniqueDate.getPetResource(PetKey), o);
		}

		Log.i(this.getClass().getName(), PetUniqueDate.getPetResource(PetKey)
				+ " " + PetKey);

		Petwidth = mBitmap.getWidth() / BMP_COLUMNS;
		Petheight = mBitmap.getHeight() / BMP_ROWS;
		// setCenPos( x, y);
		this.isLeft = isLeft;
		if (isLeft)
			currentAnimationRow = 2;
		else
			currentAnimationRow = 1;
	}

	public void setSpeedFromView() {
		SpeedForward = Math.round(SFFactor * PetBattlePanel.PetFieldWidth);
		SpeedBackward = Math.round(BWFactor * PetBattlePanel.PetFieldWidth);

		// mSpeed = SpeedForward;
		// if (!isLeft)
		// mSpeed *= -1;
	}

	public void setCenPos(int x, int y) {
		realX = x;
		realY = y;
		showX = (int) Math.round(x) - (Petwidth / 2);
		showY = (int) Math.round(y) - (Petheight);
		if (baseShowX == -1 && baseShowY == -1) {
			baseShowX = showX;
			baseShowY = showY;
		}
	}

	public void animate() {
		currentFrame = ++currentFrame % BMP_COLUMNS;
	}

	public void animate(PetBattleSprite mPet) {
		// Log.i("Speed " + isLeft ,isFinish + " " + isInitialSpeed + " " +
		// isActionOnBar);
		if (isFinish()) {
			setCenPos(realX, realY);
		} else {
			setCenPos(realX + mSpeed, realY);
			if (mPet.isPetCollition(this) && !isWillBack()) {

				if (mSpeed < 0) {
					mSpeed = SpeedBackward;
				} else {
					mSpeed = SpeedBackward * -1;
				}

			}
			if (isWillBack()) {
				if (isWillPassBase() && !isCleared) {
					mSpeed = 0;
					setCenPos(realX, realY);
					Log.i("Finish " + isLeft, "FINISH");
					isFinish = true;
					setCenPos(realX, realY);
				}
			}
		}
		currentFrame = ++currentFrame % BMP_COLUMNS;
	}

	public boolean isWillBack() {
		return Math.abs(mSpeed) == Math.abs(SpeedBackward);
	}

	private boolean isWillPassBase() {
		if (mSpeed > 0) {
			if (((int) Math.round(realX + mSpeed) - (Petwidth / 2)) > baseShowX) {
				return true;
			}
		} else {
			if (((int) Math.round(realX + mSpeed) - (Petwidth / 2)) < baseShowX) {
				return true;
			}
		}
		return false;
	}

	public boolean isPetCollition(PetBattleSprite mPet) {
		if (mPet.mSpeed < 0) {
			if ((int) Math.round(mPet.realX + mPet.mSpeed)
					- (mPet.Petwidth / 2) < baseShowX) {
				return true;
			}
		} else {
			if ((int) Math.round(mPet.realX + mPet.mSpeed)
					- (mPet.Petwidth / 2) > baseShowX) {
				return true;
			}
		}
		return false;
	}

	public void doDraw(Canvas canvas) {
		int srcX = currentFrame * Petwidth;
		int srcY = currentAnimationRow * Petheight;
		Rect src = new Rect(srcX, srcY, srcX + Petwidth, srcY + Petheight);
		Rect dst = new Rect(showX, showY, showX + Petwidth, showY + Petheight);
		canvas.drawBitmap(mBitmap, src, dst, null);
	}

	public boolean isFinish() {
		return isFinish;
	}

	public boolean isInitialSpeed() {
		return isInitialSpeed;
	}

	public void InitialSpeed() {
		Log.i("Initial Speed " + isLeft, "INITIALITED " + isLeft);
		mSpeed = SpeedForward;
		Log.i("XXX", isLeft + " " + mSpeed + " " + SpeedForward);
		if (!isLeft)
			mSpeed *= -1;
		isInitialSpeed = true;
		isCleared = false;
	}

	public boolean isActionOnBar() {
		return isActionOnBar;
	}

	public void setActionOnBar(boolean isActionOnBar) {
		this.isActionOnBar = isActionOnBar;
	}

	public void clearSpeed() {
		Log.i("ClearSpeed " + isLeft, "CLEARED FOR" + isLeft);
		isFinish = false;
		isInitialSpeed = false;
		isActionOnBar = false;
		isCleared = true;
	}

	// public void setInitialSpeed(boolean isInitialSpeed) {
	// this.isInitialSpeed = isInitialSpeed;
	// }

}
