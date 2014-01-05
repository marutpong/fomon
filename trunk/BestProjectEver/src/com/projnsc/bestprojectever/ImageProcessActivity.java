package com.projnsc.bestprojectever;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Mat;

import com.projnsc.bestprojectever.R;

import processImage.ImageProcessView;
import processImage.ImageProcessView.OnImageProcess;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Point;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("NewApi")
public class ImageProcessActivity extends Activity implements
		CvCameraViewListener2, OnImageProcess {
	private static final String INTENT_EXTRA_KEY_PIC_SAVE_PATH = "Preview";
	private static final String TAG = "Activity";

	private ImageProcessView mOpenCvCameraView;
	private List<Size> mResolutionList;
	private MenuItem[] mEffectMenuItems;
	private SubMenu mColorEffectsMenu;
	private MenuItem[] mResolutionMenuItems;
	private SubMenu mResolutionMenu;
	private String PictureFileName;
	Button shootCamera;

	public static String getIntentExtraKeyPicSavePath() {
		return INTENT_EXTRA_KEY_PIC_SAVE_PATH;
	}
	
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		// Unknown by dumb people :3 yep
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				Log.i(TAG, "OpenCV loaded successfully");
				mOpenCvCameraView.enableView();
			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};

	public ImageProcessActivity() {
		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "called onCreate");
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.image_process);

		mOpenCvCameraView = (ImageProcessView) findViewById(R.id.image_activity_java_surface_view);

		mOpenCvCameraView.setOnImageProcess(this); // notify

		mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

		mOpenCvCameraView.setCvCameraViewListener(this);

		shootCamera = (Button) findViewById(R.id.camera1);

		final Intent intend = new Intent(getApplicationContext(),
				PreviewImage.class);

		shootCamera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd_HH-mm-ss");
				String currentDateandTime = sdf.format(new Date());

				File folder = new File(Environment
						.getExternalStorageDirectory() + "/FoMons");
				boolean success = true;
				if (!folder.exists()) {
					success = folder.mkdir();
					// Toast.makeText(this, "New Folder have create.",
					// Toast.LENGTH_LONG).show();
				} else {
					// Toast.makeText(this, "Folder is already create.",
					// Toast.LENGTH_LONG).show();
				}
				if (success) {
					String fileName = folder + "/Pic_FoMons"
							+ currentDateandTime + ".jpg";
					PictureFileName = fileName;
					
					/*Display display = getWindowManager().getDefaultDisplay();
					Point size = new Point();
					display.getSize(size);
					int width = size.x;
					int height = size.y;
					Log.d(TAG, "width "+String.valueOf(width)+" heigth "+String.valueOf(height));
					*/
					
					mOpenCvCameraView.takePicture(fileName);
					// Toast.makeText(this, "Picture is already created.",
					// Toast.LENGTH_LONG).show();
					Log.i(TAG, "After done");
					// onPause();
					// chagePage(fileName);

				} else {
					// Do something else on failure
				}

				
			}

//			private void chagePage(String fileName) {
//				intend.putExtra(INTENT_EXTRA_KEY_PIC_SAVE_PATH, fileName);
//				startActivityForResult(intend, 0);
//
//			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();
	}

	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this,
				mLoaderCallback);
	}

	public void onDestroy() {
		super.onDestroy();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();

	}

	public void onCameraViewStarted(int width, int height) {
	}

	public void onCameraViewStopped() {
	}

	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		return inputFrame.rgba();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		List<String> effects = mOpenCvCameraView.getEffectList();

		if (effects == null) {
			Log.e(TAG, "Color effects are not supported by device!");
			return true;
		}

		mColorEffectsMenu = menu.addSubMenu("Color Effect");
		mEffectMenuItems = new MenuItem[effects.size()];

		int idx = 0;
		ListIterator<String> effectItr = effects.listIterator();
		while (effectItr.hasNext()) {
			String element = effectItr.next();
			mEffectMenuItems[idx] = mColorEffectsMenu.add(1, idx, Menu.NONE,
					element);
			idx++;
		}

		mResolutionMenu = menu.addSubMenu("Resolution");
		mResolutionList = mOpenCvCameraView.getResolutionList();
		mResolutionMenuItems = new MenuItem[mResolutionList.size()];

		ListIterator<Size> resolutionItr = mResolutionList.listIterator();
		idx = 0;
		while (resolutionItr.hasNext()) {
			Size element = resolutionItr.next();
			mResolutionMenuItems[idx] = mResolutionMenu.add(2, idx, Menu.NONE,
					Integer.valueOf(element.width).toString() + "x"
							+ Integer.valueOf(element.height).toString());
			idx++;
		}

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
		if (item.getGroupId() == 1) {
			mOpenCvCameraView.setEffect((String) item.getTitle());
			Toast.makeText(this, mOpenCvCameraView.getEffect(),
					Toast.LENGTH_SHORT).show();
		} else if (item.getGroupId() == 2) {
			int id = item.getItemId();
			Size resolution = mResolutionList.get(id);
			mOpenCvCameraView.setResolution(resolution);
			resolution = mOpenCvCameraView.getResolution();
			String caption = Integer.valueOf(resolution.width).toString() + "x"
					+ Integer.valueOf(resolution.height).toString();
			Toast.makeText(this, caption, Toast.LENGTH_SHORT).show();
		}

		return true;
	}

	@SuppressLint("SimpleDateFormat")
	public void NotifyImageProcess(String path) {
		Intent A = new Intent(getApplicationContext(),MonEatingPhotoActivity.class);
		A.putExtra(INTENT_EXTRA_KEY_PIC_SAVE_PATH, path);
		startActivity(A);
	}

}
