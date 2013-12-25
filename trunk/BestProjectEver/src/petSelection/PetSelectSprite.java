package petSelection;

import com.projnsc.bestprojectever.R;

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
	private static int SpriteAnimate[] = { R.drawable.pet_s7, R.drawable.pet_s6 };
	private int Key;

	public PetSelectSprite(Resources res, int x, int y, int Key) {
		mBitmap = BitmapFactory.decodeResource(res, SpriteAnimate[Key - 1]);
		Petwidth = mBitmap.getWidth() / BMP_COLUMNS;
		Petheight = mBitmap.getHeight() / BMP_ROWS;
		mX = x;
		mY = y;
		this.Key = Key;
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

	public boolean isCollition(float x2, float y2) {
		return x2 > mX && x2 < mX + Petwidth && y2 > mY && y2 < mY + Petheight;
	}

}
