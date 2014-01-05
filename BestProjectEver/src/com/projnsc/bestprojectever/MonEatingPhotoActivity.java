package com.projnsc.bestprojectever;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import processImage.Hist_Phog;

import textGetter.PetDataType;

import monsterEatPhoto.EatPanel;
import monsterEatPhoto.Pixel;
import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MonEatingPhotoActivity extends Activity {
	Hist_Phog hist_phog = new Hist_Phog(this);
	private static final int MENU_CLEAR_ID = 1000;
	private Set<Pixel> mPool = new HashSet<Pixel>();
	EatPanel mPanel;
	Button btnClear;
	Button btnCancel;
	Button btnFinish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("TEXT",
				getIntent().getExtras().getString(
						ImageProcessActivity.getIntentExtraKeyPicSavePath()));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mon_eat_pic);
		// mPanel = new EatPanel(this);
		mPanel = (EatPanel) findViewById(R.id.eatPanel1);
		mPanel.setImagePicPath(getIntent().getExtras().getString(
				ImageProcessActivity.getIntentExtraKeyPicSavePath()));
		mPanel.setPixelPool(mPool);
		// setContentView(mPanel);
		btnCancel = (Button) findViewById(R.id.btnEatCancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnCancelPress();
			}
		});
		btnClear = (Button) findViewById(R.id.btnEatClear);
		btnClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPanel.clearMPool();
			}
		});
		btnFinish = (Button) findViewById(R.id.btnEatFinish);
		btnFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EataskFinish();
			}
		});

	}

	protected void EataskFinish() {
		new AlertDialog.Builder(this)
				.setTitle("Finish Eating!")
				.setMessage("Do you eat all food")
				.setPositiveButton("Yes",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								gotoCalculatePicEat();
							}
						}).setNegativeButton("No", null).show();
	}

	protected void gotoCalculatePicEat() {
		String fullPath;
		Pixel[] PixelPool = mPool.toArray(new Pixel[0]);
		int minX, minY, maxX, maxY;
		Arrays.sort(PixelPool, new Comparator<Pixel>() {

			@Override
			public int compare(Pixel lhs, Pixel rhs) {
				return lhs.getX() - rhs.getX();
			}
		});
		minX = PixelPool[0].getX();
		maxX = PixelPool[PixelPool.length - 1].getX();
		Arrays.sort(PixelPool, new Comparator<Pixel>() {

			@Override
			public int compare(Pixel lhs, Pixel rhs) {
				return lhs.getY() - rhs.getY();
			}
		});
		minY = PixelPool[0].getY();
		maxY = PixelPool[PixelPool.length - 1].getY();
		int Heigh = EatPanel.PanelHight;
		int Width = EatPanel.PanelWidth;

//		Bitmap mFood = BitmapFactory.decodeResource(getResources(),
//				R.drawable.dummyfoodeattest);
		Bitmap mFood = BitmapFactory.decodeFile(getIntent().getExtras().getString(
				ImageProcessActivity.getIntentExtraKeyPicSavePath()));
		mFood = Bitmap.createScaledBitmap(mFood, Width, Heigh, false);
		Log.i(this.getClass().getName(), "Width Real: " + mFood.getWidth()
				+ " Height Real: " + mFood.getHeight());
		Log.i(this.getClass().getName(), "miX: " + minX + " mxX: " + maxX
				+ " abs: " + Math.abs(maxX - minX));
		Log.i(this.getClass().getName(), "miY: " + minY + " mxY: " + maxY
				+ " abs: " + Math.abs(maxY - minY));
		// for(int i=0; i<PixelPool.length; i++){
		// Log.i(this.getClass().getName()+" OK", PixelPool[i].getX() + " " +
		// PixelPool[i].getY());
		// }
		mFood = Bitmap.createBitmap(mFood, minX, minY, Math.abs(maxX - minX),
				Math.abs(maxY - minY));

		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/" + PetDataType.FolderSavedName);
		myDir.mkdirs();
		File file = new File(myDir, PetDataType.TempFilePetEatSaveName);
		fullPath = root + "/" + PetDataType.FolderSavedName + "/" + PetDataType.TempFilePetEatSaveName;
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			mFood.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			Log.i(this.getClass().getName(), "Success!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int[] classFood  = hist_phog.histImage(fullPath);
		
		Log.d("TEXT", String.valueOf(classFood[0])+"  "+String.valueOf(classFood[1]));

		// /

	}

	protected void btnCancelPress() {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_CLEAR_ID, Menu.NONE, "Clear");
		return true;
	}

}
