package com.projnsc.bestprojectever;

import gpsLocation.GPSLocation;
import gpsLocation.GPSLocation.OnGPSListener;
import historyDatabase.HistoryType;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import preferenceSetting.PetUniqueDate;
import processImage.Hist_Phog;

import monsterEatPhoto.EatPanel;
import monsterEatPhoto.Pixel;
import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MonEatingPhotoActivity extends Activity implements OnGPSListener {
	Hist_Phog hist_phog = new Hist_Phog(this);
	private static final int MENU_CLEAR_ID = 1000;
	private Set<Pixel> mPool = new HashSet<Pixel>();
	EatPanel mPanel;
	Button btnClear;
	Button btnCancel;
	Button btnFinish;
	GPSLocation gps;
	double latitude = 0;
	double longtitude = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gps = new GPSLocation(this);
		gps.setOnGPSListener(this);
		
		Log.i("TEXT",
				getIntent().getExtras().getString(
						getString(R.string.intentkey_pathforfood)));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mon_eat_pic);
		// mPanel = new EatPanel(this);
		mPanel = (EatPanel) findViewById(R.id.eatPanel1);
		mPanel.setImagePicPath(getIntent().getExtras().getString(
				getString(R.string.intentkey_pathforfood)));
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

		// Bitmap mFood = BitmapFactory.decodeResource(getResources(),
		// R.drawable.dummyfoodeattest);
		Bitmap mFood = BitmapFactory.decodeFile(getIntent().getExtras()
				.getString(getString(R.string.intentkey_pathforfood)));
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
		File myDir = new File(root + "/" + HistoryType.FolderSavedName);
		myDir.mkdirs();
		File file = new File(myDir, HistoryType.TempFilePetEatSaveName);
		fullPath = root + "/" + HistoryType.FolderSavedName + "/"
				+ HistoryType.TempFilePetEatSaveName;
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

		int[] classFood = hist_phog.histImage(fullPath);

		Log.d("TEXT",
				String.valueOf(classFood[0]) + "  "
						+ String.valueOf(classFood[1]));

		PetUniqueDate.SetLatitude((float) latitude);
		PetUniqueDate.SetLongtitude((float) longtitude);
		Intent A = new Intent(this,TheTempActivity.class);
		A.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		A.putExtra(getString(R.string.intentkey_analysisfoodclass1), classFood[0]);
		A.putExtra(getString(R.string.intentkey_analysisfoodclass2), classFood[1]);
		A.putExtra(getString(R.string.intentkey_pathforfood) ,getIntent().getExtras().getString(getString(R.string.intentkey_pathforfood)));
		finish();
		startActivity(A);
	}

	protected void btnCancelPress() {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_CLEAR_ID, Menu.NONE, "Clear");
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
        gps.setup();
    }
	
	@Override
	public void onStart() {
        super.onStart();
        gps.onStart(this);
    }

	@Override
	public void onStop() {
        super.onStop();
        gps.onStop();
    }

	@Override
	public void onGPSChange(GPSLocation gpsLocation) {
//		la.setText(gpsLocation.getLatitude() + "");
//		lo.setText(gpsLocation.getLongtitude() + "");
		latitude = gpsLocation.getLatitude();
		longtitude = gpsLocation.getLongtitude();
	}

}
