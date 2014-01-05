package processImage;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.opencv.android.JavaCameraView;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import android.content.Context;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


public class ImageProcessView extends JavaCameraView implements PictureCallback, AutoFocusCallback {

	public ImageProcessView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public interface OnImageProcess{
		void NotifyImageProcess(String path);
	}
	
	private OnImageProcess onImageProcess;

	public void setOnImageProcess(OnImageProcess onImageProcess) {
		this.onImageProcess = onImageProcess;
	}
	
	private static final String TAG = "Image View";
	//Hist_Phog hist_phog = new Hist_Phog(getContext());
	
	private String mPictureFileName;
	
	
	public List<String> getEffectList() {
		return mCamera.getParameters().getSupportedColorEffects();
	}

	public boolean isEffectSupported() {
		return (mCamera.getParameters().getColorEffect() != null);
	}

	public String getEffect() {
		return mCamera.getParameters().getColorEffect();
	}

	public void setEffect(String effect) {
		Camera.Parameters params = mCamera.getParameters();
		params.setColorEffect(effect);
		mCamera.setParameters(params);
	}

	public List<Size> getResolutionList() {
		return mCamera.getParameters().getSupportedPreviewSizes();
	}

	public void setResolution(Size resolution) {
		disconnectCamera();
		mMaxHeight = resolution.height;
		mMaxWidth = resolution.width;
		Log.i(TAG, String.valueOf(mMaxWidth));
		Log.i(TAG, String.valueOf(mMaxHeight));
		
		connectCamera(getWidth(), getHeight());
	}

	public Size getResolution() {
		return mCamera.getParameters().getPreviewSize();
	}

	public  void takePicture(final String fileName) {
		Log.i(TAG, "Taking picture");
		this.mPictureFileName = fileName;
		// Postview and jpeg are sent in the same buffers if the queue is not
		// empty when performing a capture.
		// Clear up buffers to avoid mCamera.takePicture to be stuck because of
		// a memory issue
		mCamera.setPreviewCallback(null);

		// PictureCallback is implemented by the current class
		mCamera.takePicture(null, null, this);

	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		Log.i(TAG, "Saving a bitmap to file");
		// The camera preview was automatically stopped. Start it again.
		mCamera.startPreview();
		mCamera.setPreviewCallback(this);
		
		// Write the image in a file (in jpeg format)
		
		try {
			
			
			FileOutputStream fos = new FileOutputStream(mPictureFileName);
			fos.write(data);
			fos.close();
			
			ProcessImage(mPictureFileName);		
			
		} catch (java.io.IOException e) {
			Log.e("PictureDemo", "Exception in photoCallback", e);
		}
		
	}

	private void ProcessImage(String path) {
		
		Log.d(TAG, "height "+String.valueOf(getHeight()));
		Log.d(TAG, "width "+String.valueOf(getWidth()));
		
		int heightTmp=getHeight();
		int widthTmp=getWidth();

		Mat im = Highgui.imread(path);
		org.opencv.core.Size tempSize ;
		tempSize = new org.opencv.core.Size((double)widthTmp,(double)heightTmp);
		Imgproc.resize(im, im, tempSize);
		
		Log.d(TAG, "height "+String.valueOf(im.height()));
		Log.d(TAG, "width "+String.valueOf(im.width()));
		
		
		
		Log.i(TAG, "Process Image");
		
		//int[] classFood  = hist_phog.histImage(mPictureFileName);
		
		//String tmp = mPictureFileName.substring(0,mPictureFileName.length()-4);
	 	//tmp += "1.jpg";
	 
		Highgui.imwrite(path, im);
		 	
		if(onImageProcess!=null){
			onImageProcess.NotifyImageProcess(path);
		}
	}

	public void FourceCameraAutoFocus(){
		Log.i("CameraSystem","Focus");
		mCamera.autoFocus(this);
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		Log.i("CameraSystem","onAutoFocus2");
	}
	

}
