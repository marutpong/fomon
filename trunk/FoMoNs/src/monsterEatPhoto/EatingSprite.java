package monsterEatPhoto;

import java.util.Random;

import com.projnsc.bestprojectever.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class EatingSprite {

	private static final int MaxLifeTime = 20;
	private int FACE_DIRECTION;
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 3;
	private Bitmap mEat;
	private int mX;
	private int mY;
	private int sWidth;
	private int sHeight;
	private Random rand = new Random();
	private int currentFrame = 0;
	private int LifeTime = 0;
	
	public EatingSprite(Resources res, int x, int y){
		mEat = BitmapFactory.decodeResource(res, R.drawable.eating_spite);
		sWidth = mEat.getWidth() / BMP_COLUMNS;
		sHeight = mEat.getHeight() / BMP_ROWS;
		mX = x;
		mY = y;
		FACE_DIRECTION = rand.nextInt(3) + 1;
		LifeTime = MaxLifeTime + 1;
	}
	
	public void animate() {
		currentFrame = ++currentFrame % BMP_COLUMNS;
		LifeTime++;
	}
	
	public void setPos(int x, int y){
		mX = x - sWidth/2;
		mY = y - sHeight/2;
		LifeTime = 0;
		FACE_DIRECTION = rand.nextInt(3) + 1;
	}
	
	public void doDraw(Canvas canvas) {
		if(LifeTime < MaxLifeTime){
			int srcX = currentFrame * sWidth;
			int srcY = FACE_DIRECTION * sHeight;
			Rect src = new Rect(srcX, srcY, srcX + sWidth, srcY + sHeight);
			Rect dst = new Rect(mX, mY, mX + sWidth, mY + sHeight);
			canvas.drawBitmap(mEat, src, dst, null);
		}
	}
	
}
