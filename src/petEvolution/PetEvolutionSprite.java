package petEvolution;

import preferenceSetting.PetUniqueDate;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.projnsc.bestprojectever.R;

public class PetEvolutionSprite {

	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 4;
	private static final int CHANGEFACTOR = 2;
	private static final int FPSThreadMinimum = 3;
	private static final int preTimeThreashole = 20;
	private static final int lessTimeThreshold = 20;
	private int currentFrame = 0;
	private int sPetwidth;
	private int sPetheight;
	private int fPetwidth;
	private int fPetheight;
	private int mX;
	private int mY;
	private int SetX;
	private int SetY;
	private int currentTotalFrame = 0;
	private Bitmap sBitmap;
	private Bitmap fBitmap;
	private int AnimationRow = 0; // Always Down
	private boolean ActionAble = true;
	private boolean finishEvolution = false;
	private boolean StartFirstEmotion = false;
	private boolean StartSecondEmotion = false;
	private boolean AllEmotionShow = false;
	private int lessTime = 0;
	private int preTime = 0;
	private Resources res;

	public PetEvolutionSprite(Resources res, int x, int y) {
		this.res = res;
		mX = x;
		mY = y;
	}

	public void setActionAble(boolean actionAble) {
		ActionAble = actionAble;
	}

	public boolean isFinishEvolution() {
		return finishEvolution;
	}

	public void animate() {
		currentFrame = ++currentFrame % BMP_COLUMNS;
		if (ActionAble) {
			if (preTime < preTimeThreashole) {
				StartFirstEmotion = true;
				preTime++;
			} else {
				currentTotalFrame++;
			}

			PetEvolutionThread.setFPS(PetEvolutionThread.getMAXFPS()
					- (currentTotalFrame / 10));
			if (PetEvolutionThread.getFPS() == FPSThreadMinimum) {
				ActionAble = false;
			}
		} else {
			PetEvolutionThread.setFPS(PetEvolutionThread.getMAXFPS());
			currentTotalFrame = 1;
			if (lessTime > lessTimeThreshold) {
				finishEvolution = true;
			} else {
				StartSecondEmotion = true;
				lessTime++;
			}
		}
	}

	public void doDraw(Canvas canvas) {
		setCenPos();
		int srcX = currentFrame * getCurrentPetwidth();
		int srcY = AnimationRow * getCurrentPetHeight();
		Rect src = new Rect(srcX, srcY, srcX + getCurrentPetwidth(), srcY
				+ getCurrentPetHeight());
		Rect dst = new Rect(mX, mY, mX + getCurrentPetwidth(), mY
				+ getCurrentPetHeight());
		try {
			canvas.drawBitmap(getCurrentBitmap(), src, dst, null);
		} catch (RuntimeException E) {

		}
	}

	private Bitmap getCurrentBitmap() {
		if (currentTotalFrame % CHANGEFACTOR < (CHANGEFACTOR / 2)) {
			return sBitmap;
		} else {
			return fBitmap;
		}
	}

	private int getCurrentPetHeight() {
		if (currentTotalFrame % CHANGEFACTOR < (CHANGEFACTOR / 2)) {
			return sPetheight;
		} else {
			return fPetheight;
		}
	}

	private int getCurrentPetwidth() {
		if (currentTotalFrame % CHANGEFACTOR < (CHANGEFACTOR / 2)) {
			return sPetwidth;
		} else {
			return fPetwidth;
		}
	}

	public void setCenPos(float f, float yCen) {
		SetX = (int) Math.round(f);
		SetY = (int) Math.round(yCen);
		setCenPos();
	}

	private void setCenPos() {
		mX = SetX - (getCurrentPetwidth() / 2);
		mY = SetY - (getCurrentPetHeight() / 2);
	}

	// CheckForPetClick
	public boolean isCollition(float x2, float y2) {
		return x2 > mX && x2 < mX + getCurrentPetwidth() && y2 > mY
				&& y2 < mY + getCurrentPetHeight();
	}

	public void setMonPIC(int monStartID, int monFinishID) {

		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inDither = false;
		o.inPurgeable = true;
		sBitmap = BitmapFactory.decodeResource(res,
				PetUniqueDate.getPetResource(monStartID), o);

		sPetwidth = sBitmap.getWidth() / BMP_COLUMNS;
		sPetheight = sBitmap.getHeight() / BMP_ROWS;
		fBitmap = BitmapFactory.decodeResource(res,
				PetUniqueDate.getPetResource(monFinishID), o);

		fPetwidth = fBitmap.getWidth() / BMP_COLUMNS;
		fPetheight = fBitmap.getHeight() / BMP_ROWS;
	}

	public boolean canStartFirstEmotion() {
		return StartFirstEmotion;
	}

	public boolean canStartSecondEmotion() {
		return StartSecondEmotion;
	}

	public void setAllEmotionShow(boolean allEmotionShow) {
		AllEmotionShow = allEmotionShow;
	}

	public boolean isAllEmotionShow() {
		return AllEmotionShow;
	}

	public void forceRecycle() {
		if (sBitmap != null)
			sBitmap.recycle();
		if (fBitmap != null)
			fBitmap.recycle();
	}

}
