package processImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvKNearest;
import org.opencv.core.Size;

import com.projnsc.bestprojectever.R;

import android.content.Context;
import android.util.Log;

public class Hist_Phog {

	private static final String TAG = "Hist_Phog";
	//private int bin = 8;
	//private int angle = 360;
	private static Context context;
	float y[], z[];
	static int foodnum = 599;
	//int foodnumtest = 248;
	static int featurenum = 41;
	static int featurepc = 15;
	
	public interface OnImageProcessListener{
		void OnImageProcessFinish(int[] classFood); 
	}
	
	private OnImageProcessListener onImageProcessListener;
	
	public void setOnImageProcessListener(
			OnImageProcessListener onImageProcessListener) {
		this.onImageProcessListener = onImageProcessListener;
	}
	
	public int[] histImage(String path) {
		
		Log.d(TAG, path);
		List<Mat> channel = new ArrayList<Mat>();
//		File folder = new File(Environment.getExternalStorageDirectory()
//				+ "/FoMons");
//		path = folder + "/14_xxx1.jpg";
		Mat im = Highgui.imread(path);
		/*
		 * Mat imOriginal = im;
		 * 
		 * Core.split(imOriginal, channel);
		 * 
		 * Mat b = channel.get(0); Mat g = channel.get(1); Mat r =
		 * channel.get(2);
		 * 
		 * double[] check = b.get(0, 0); Log.d(TAG, "b "
		 * +String.valueOf(check[0])); check = g.get(0, 0); Log.d(TAG, "g "
		 * +String.valueOf(check[0])); check = r.get(0, 0); Log.d(TAG, "r "
		 * +String.valueOf(check[0]));
		 */
		// Mat im = Highgui.imread(pathFile);
		// Size tempSize = new Size(im.cols() / 4, im.rows() / 4);
		// Imgproc.resize(im, im, tempSize);
		Imgproc.cvtColor(im, im, Imgproc.COLOR_BGR2HSV);
		Core.split(im, channel);

		Mat h = channel.get(0);
		Mat s = channel.get(1);
		Mat v = channel.get(2);
		// double[] tmptest = tmp.get(1, 1);
		// double[] tmptest2 = tmp.get(1, 1);
		// Log.d(TAG, tmptest[0] + " " + tmptest2[0]);

		Mat tmpH = FindHSV(h, "H");
		Mat tmpS = FindHSV(s, "S");
		Mat tmpV = FindHSV(v, "V");
		Mat tmpEd = FindEd(path);
		Mat feature = tmpH.clone();
		feature.push_back(tmpS);
		feature.push_back(tmpV);
		feature.push_back(tmpEd);
		Core.transpose(feature, feature);

		Log.d(TAG, String.valueOf(feature.size()));
		Log.d(TAG, String.valueOf(feature.cols()));
		for (int i = 0; i < feature.cols(); i++) {
			double[] xx2 = feature.get(0, i);
			Log.d(TAG, (i+1) + "  " + String.valueOf(xx2[0]));
		}

		int[] classFood = KNN(feature);
		
		onImageProcessListener.OnImageProcessFinish(classFood);
		
		return classFood;
	}
	public Hist_Phog(Context context) {
		setContext(context);
	}
	public Context getContext() {
		return context;
	}
	public static void setContext(Context context) {
		Hist_Phog.context = context;
	}
	
	private static int[] KNN(Mat test) {
		String tag = "TEXT";
		
		Mat fvtrain = new Mat(foodnum,featurenum,CvType.CV_32F);
		Mat fvtrainpc = new Mat(foodnum,featurepc,CvType.CV_32F);
		Mat gt = new Mat(foodnum,1,CvType.CV_32F);
		Mat meantrain = new Mat(1,featurenum,CvType.CV_32F);
		Mat stdtrain = new Mat(1,featurenum,CvType.CV_32F);
		Mat pc = new Mat(featurenum,featurenum,CvType.CV_32F);
		
		fvtrain=readText(fvtrain,1);
		fvtrainpc=readText(fvtrainpc, 2);
		gt=readText(gt, 3);
		meantrain=readText(meantrain, 4);
		stdtrain=readText(stdtrain, 5);
		pc=readText(pc, 6);
		
		
		Mat fvtestpc = new Mat(1,featurenum,CvType.CV_32F);
		for(int j=0;j<featurenum;j++){
				double[] x = test.get(0, j);
				double[] xmean = meantrain.get(0, j);
				double[] sd = stdtrain.get(0, j);
				double cal = (x[0]-xmean[0])/sd[0];
				Log.d("Hist_Phog","sd "+(j+1)+" "+String.valueOf(cal));
				fvtestpc.put(0, j, cal);
			}		
		
		
		Mat mulmattmp = fvtestpc.clone();
		for(int i=0;i<featurenum;i++){
			double sumfrommul = 0;
			int count=0;
			for(int j=0;j<featurenum;j++){
				double[] num1 = mulmattmp.get(0,j );
				double[] num2 = pc.get(j, i);
				double mul = num1[0]*num2[0];
				Log.d(tag,"mul "+String.valueOf(num1[0])+" "+String.valueOf(num2[0])+" "+String.valueOf(mul));
				sumfrommul+=mul;
			}
			fvtestpc.put(0, count, sumfrommul);
			count++;
		}

		Mat results1 = new Mat();
		Mat results2 = new Mat();
		Mat neighborResponses = new Mat();
		Mat dists = new Mat();
		CvKNearest knn = new CvKNearest();
		int[] classFood = {0,0} ;
		knn.train(fvtrain, gt);
		knn.find_nearest(test, 1, results1, neighborResponses, dists);
		double[] tmpclassfood = results1.get(0, 0);
		int classFoodchk1 = (int)tmpclassfood[0];
		Log.d(tag,"first "+String.valueOf(classFoodchk1));
		
		Mat fvtestpcCut = new Mat(1,featurepc,CvType.CV_32F);
		for(int j=0;j<featurepc;j++){
			double[] cuttmp = fvtestpc.get(0, j);
			fvtestpcCut.put(0, j, cuttmp[0]);
			Log.d("Hist_Phog","fv "+(j+1)+" "+String.valueOf(cuttmp[0]));
		}
		
		Log.d("TEXT",String.valueOf(fvtestpcCut.size()));
		
		knn.train(fvtrainpc, gt);
		knn.find_nearest(fvtestpcCut, 18, results2, neighborResponses, dists);
		double[] tmpclassfood1 = results2.get(0, 0);
		int classFoodchk2 = (int)tmpclassfood1[0];
		Log.d(tag,"second "+ String.valueOf(classFoodchk2));
		
		//classFood = groupClass(classFoodchk);
		//Log.d(tag, "result "+ String.valueOf(classFood[0])+"  "+String.valueOf(classFood[1]));
		classFood[0] = classFoodchk1;
		classFood[1] = classFoodchk2;
		return classFood;
	}
	
	private static Mat readText(Mat keepfile,int name){
		InputStream is = null;
		Mat fvtrain = null;
		switch (name) {
		case 1:
			is = context.getResources().openRawResource(
					R.raw.fvtrain);
			break;
		case 2:
			is = context.getResources().openRawResource(
					R.raw.fvtrainpc);
			break;
		case 3:
			is = context.getResources().openRawResource(
					R.raw.gt);
			break;
		case 4:
			is = context.getResources().openRawResource(
					R.raw.meantrain);
			break;
		case 5:
			is = context.getResources().openRawResource(
					R.raw.stdtrain);
			break;
		case 6:
			is = context.getResources().openRawResource(
					R.raw.pc);
		default:
			break;
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String readLine = null;
		try {
			// While the BufferedReader readLine is not null
			int j=0;
			
			while ((readLine = br.readLine()) != null) {
				Scanner s = new Scanner(readLine).useDelimiter("\\t");
				int i=0;
				while(s.hasNext()){
					
					double d = Double.valueOf(s.next());
					keepfile.put(j, i, d);
					i++;
					//Log.d("TEXT", String.valueOf(d));
				}
				j++;
				//Log.d("TEXT", readLine);
				//Log.d("TEXT", String.valueOf(d));
			}
//			double[] tmpclassfood1 = fvtrain.get(0, 0);
//			int classFoodchk2 = (int)tmpclassfood1[0];
//			Log.d("TEXT","chhbe "+ String.valueOf(classFoodchk2));
//			tmpclassfood1 = fvtrain.get(0, 1);
//			classFoodchk2 = (int)tmpclassfood1[0];
//			Log.d("TEXT","chhbe "+ String.valueOf(classFoodchk2));
//			tmpclassfood1 = fvtrain.get(1, 0);
//			classFoodchk2 = (int)tmpclassfood1[0];
//			Log.d("TEXT","chhbe "+ String.valueOf(classFoodchk2));
//			Log.d("TEXT",String.valueOf(name)+"  "+String.valueOf(keepfile.size()));
			
			
			// Close the InputStream and BufferedReader
			is.close();
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return keepfile;
		
	}
	
	private static int[] groupClass(int first){
		int tmp[] ={0,0};
		
		switch (first) {
		case 1:
			tmp[0]=1;
			tmp[1]=11;
			break;
		case 2:
			tmp[0]=2;
			tmp[1]=9;
			break;
		case 3:
			tmp[0]=3;
			tmp[1]=0;
			break;
		case 4:
			tmp[0]=4;
			tmp[1]=12;
			break;
		case 5:
			tmp[0]=14;
			tmp[1]=5;
			break;
		case 6:
			tmp[0]=6;
			break;
		case 7:
			tmp[0]=7;
			tmp[1]=17;
			break;
		case 8:
			tmp[0]=8;
			tmp[1]=10;
			break;
		case 9:
			tmp[0]=2;
			tmp[1]=9;
			break;
		case 10:
			tmp[0]=10;
			tmp[1]=12;
			break;
		case 11:
			tmp[0]=11;
			tmp[1]=13;
			break;
		case 12:
			tmp[0]=12;
			break;
		case 13:
			tmp[0]=13;
			break;
		case 14:
			tmp[0]=14;
			break;
		case 15:
			tmp[0]=15;
			tmp[1]=5;
			break;
		case 16:
			tmp[0]=16;
			break;
		case 17:
			tmp[0]=17;
			tmp[1]=15;
			break;
		case 18:
			tmp[0]=18;
			tmp[1]=0;
			break;
		case 19:
			tmp[0]=19;
			tmp[1]=13;
			break;
		case 20:
			tmp[0]=20;
			tmp[1]=0;
			break;
		case 21:
			tmp[0]=21;
			tmp[1]=7;
			break;
		default:
			break;
		}
		return tmp;		
	}
	
	public Mat FindHSV(Mat tmp, String type) {
		Log.d(TAG, String.valueOf(Core.minMaxLoc(tmp).maxVal));
		Log.d(TAG, String.valueOf(Core.minMaxLoc(tmp).minVal));
		List<Mat> images = Arrays.asList(tmp);
		Mat hist = new Mat();
		MatOfInt channels = new MatOfInt(0);

		if (type.equalsIgnoreCase("H")) {
			Mat hist1 = new Mat();
			Mat hist2 = new Mat();
			Mat hist3 = new Mat();
			Mat hist4 = new Mat();
			Mat hist5 = new Mat();
			Mat hist6 = new Mat();

			MatOfInt histSize1 = new MatOfInt(1);
			MatOfFloat ranges1 = new MatOfFloat(0f, 2.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist1, histSize1,
					ranges1);
			MatOfInt histSize2 = new MatOfInt(17);
			MatOfFloat ranges2 = new MatOfFloat(2.5f, 87.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist2, histSize2,
					ranges2);
			MatOfInt histSize3 = new MatOfInt(1);
			MatOfFloat ranges3 = new MatOfFloat(87.5f, 105.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist3, histSize3,
					ranges3);
			MatOfInt histSize4 = new MatOfInt(1);
			MatOfFloat ranges4 = new MatOfFloat(105.5f, 127.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist4, histSize4,
					ranges4);
			MatOfInt histSize5 = new MatOfInt(1);
			MatOfFloat ranges5 = new MatOfFloat(127.5f, 137.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist5, histSize5,
					ranges5);
			MatOfInt histSize6 = new MatOfInt(8);
			MatOfFloat ranges6 = new MatOfFloat(137.5f, 180.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist6, histSize6,
					ranges6);
			// for(int i=0;i<29;i++){
			// double[] xx2 = matH.get(i, 0);
			// Log.d(TAG, String.valueOf(xx2[0]));
			// }
			hist = hist1.clone();
			hist.push_back(hist2);
			hist.push_back(hist3);
			hist.push_back(hist4);
			hist.push_back(hist5);
			hist.push_back(hist6);

		} else if (type.equalsIgnoreCase("S")) {
			Mat hist1 = new Mat();
			Mat hist2 = new Mat();
			Mat hist3 = new Mat();

			MatOfInt histSize1 = new MatOfInt(1);
			MatOfFloat ranges1 = new MatOfFloat(0f, 25.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist1, histSize1,
					ranges1);
			MatOfInt histSize2 = new MatOfInt(4);
			MatOfFloat ranges2 = new MatOfFloat(25.5f, 229.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist2, histSize2,
					ranges2);
			MatOfInt histSize3 = new MatOfInt(1);
			MatOfFloat ranges3 = new MatOfFloat(229.5f, 256f);
			Imgproc.calcHist(images, channels, new Mat(), hist3, histSize3,
					ranges3);
			hist = hist1.clone();
			hist.push_back(hist2);
			hist.push_back(hist3);

		} else if (type.equalsIgnoreCase("V")) {
			Mat hist1 = new Mat();
			Mat hist2 = new Mat();
			Mat hist3 = new Mat();

			MatOfInt histSize1 = new MatOfInt(1);
			MatOfFloat ranges1 = new MatOfFloat(0f, 95.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist1, histSize1,
					ranges1);
			MatOfInt histSize2 = new MatOfInt(1);
			MatOfFloat ranges2 = new MatOfFloat(95.5f, 159.5f);
			Imgproc.calcHist(images, channels, new Mat(), hist2, histSize2,
					ranges2);
			MatOfInt histSize3 = new MatOfInt(1);
			MatOfFloat ranges3 = new MatOfFloat(159.5f, 256f);
			Imgproc.calcHist(images, channels, new Mat(), hist3, histSize3,
					ranges3);

			hist = hist1.clone();
			hist.push_back(hist2);
			hist.push_back(hist3);
		}

		Log.d(TAG, String.valueOf(hist.size()));
		double sum = 0;
		for (int i = 0; i < hist.rows(); i++) {
			for (int j = 0; j < hist.cols(); j++) {
				double[] value = hist.get(i, j);
				// value[0]/tmp.total()
				hist.put(i, j, value[0] / tmp.total());
				sum += value[0] / tmp.total(); // check prob
			}

		}
		Log.d(TAG, String.valueOf(sum));

		return hist;

	}

	public Mat FindEd(String path) {
		List<Mat> channel = new ArrayList<Mat>();
		Mat imtmp = Highgui.imread(path);
		Mat im = new Mat();
		Mat im_fil = new Mat();

		/*
		 * Core.split(im, channel); Mat b = channel.get(0); Mat g =
		 * channel.get(1); Mat r = channel.get(2); double[] check = b.get(0, 0);
		 * Log.d(TAG, "b " +String.valueOf(check[0])); check = g.get(0, 0);
		 * Log.d(TAG, "g " +String.valueOf(check[0])); check = r.get(0, 0);
		 * Log.d(TAG, "r " +String.valueOf(check[0]));
		 * 
		 * Log.d(TAG,"Size "+String.valueOf(im.size()));
		 * Log.d(TAG,"row "+String.valueOf(im.rows())); // width = col , higth =
		 * row
		 */
		Imgproc.cvtColor(imtmp, im, Imgproc.COLOR_BGR2GRAY);
		im.convertTo(im_fil, CvType.CV_64FC1);

		double size = 500;
		Size tempSize;
		if (im.rows() > im.cols()) {
			tempSize = new Size(((double) im.cols() / (double) im.rows())
					* size, ((double) im.rows() / (double) im.rows()) * size);
		} else {
			// double tmpCalSize =(im_fil.cols()/ im_fil.cols())*size;
			// double tmpCalSize1 =((double)im_fil.rows()/
			// (double)im_fil.cols());
			// Log.d(TAG,tmpCalSize+"  "+tmpCalSize1);
			tempSize = new Size(((double) im.cols() / (double) im.cols())
					* size, ((double) im.rows() / (double) im.cols()) * size);
		}

		Imgproc.resize(im_fil, im_fil, tempSize);

		float maxValImg = (float) Core.minMaxLoc(im_fil).maxVal;
		float minValImg = (float) Core.minMaxLoc(im_fil).minVal;
		Log.d(TAG, "channel after resize " + String.valueOf(im.channels()));
		Log.d(TAG, "max after resize " + String.valueOf(maxValImg) + " "
				+ String.valueOf(minValImg));
		Log.d(TAG, "height after resize " + String.valueOf(tempSize.height));
		Log.d(TAG, "width after resize " + String.valueOf(tempSize.width));

		Mat kernel = new Mat(3, 3, CvType.CV_64FC1);
		kernel.put(0, 0, 0);
		kernel.put(0, 1, -0.25);
		kernel.put(0, 2, 0);
		kernel.put(1, 0, -0.25);
		kernel.put(1, 1, 1);
		kernel.put(1, 2, -0.25);
		kernel.put(2, 0, 0);
		kernel.put(2, 1, -0.25);
		kernel.put(2, 2, 0);

		Point anchor = new Point(-1, -1);
		double delta = 0;
		int ddepth = -1;

		double[] chkpix = im_fil.get(0, 0);
		double[] chkpix1 = im_fil.get(0, 1);
		double[] chkpix2 = im_fil.get(0, 2);
		Log.d(TAG,
				"chk after gray " + String.valueOf(chkpix[0]) + " "
						+ String.valueOf(chkpix1[0]) + " "
						+ String.valueOf(chkpix2[0]));
		chkpix = im_fil.get(1, 0);
		chkpix1 = im_fil.get(1, 1);
		chkpix2 = im_fil.get(1, 2);
		Log.d(TAG,
				"chk after gray " + String.valueOf(chkpix[0]) + " "
						+ String.valueOf(chkpix1[0]) + " "
						+ String.valueOf(chkpix2[0]));
		chkpix = im_fil.get(2, 0);
		chkpix1 = im_fil.get(2, 1);
		chkpix2 = im_fil.get(2, 2);
		Log.d(TAG,
				"chk after gray " + String.valueOf(chkpix[0]) + " "
						+ String.valueOf(chkpix1[0]) + " "
						+ String.valueOf(chkpix2[0]));

		maxValImg = (float) Core.minMaxLoc(im_fil).maxVal;
		minValImg = (float) Core.minMaxLoc(im_fil).minVal;
		Log.d(TAG,
				"max after gray " + String.valueOf(maxValImg) + " "
						+ String.valueOf(minValImg));

		Imgproc.filter2D(im_fil, im_fil, ddepth, kernel, anchor, delta,
				Imgproc.BORDER_REPLICATE);
		chkpix = im_fil.get(4, 0);
		chkpix1 = im_fil.get(4, 1);
		chkpix2 = im_fil.get(4, 2);

		Log.d(TAG,
				"chk after fil " + String.valueOf(chkpix[0]) + " "
						+ String.valueOf(chkpix1[0]) + " "
						+ String.valueOf(chkpix2[0]));

		maxValImg = (float) Core.minMaxLoc(im_fil).maxVal;
		minValImg = (float) Core.minMaxLoc(im_fil).minVal;
		Log.d(TAG,
				"max after fil " + String.valueOf(maxValImg) + " "
						+ String.valueOf(minValImg));

		Log.d(TAG, im_fil.size() + " " + im_fil.channels());

		/*
		 * for(int i=0;i<im_fil.rows();i++){ for (int j = 0; j < im_fil.cols();
		 * j++) { double[] xx2 = im_fil.get(i, j); Log.d(TAG,i+" "+j+" "+
		 * String.valueOf(xx2[0])); } }
		 */
		// Mat zero = new Mat(im_fil.rows(), im_fil.cols(), CvType.CV_64FC1);
		// zero.zeros((int)im_fil.rows() , (int)im_fil.cols(),CvType.CV_64FC1);
		// for(int i=0;i<100;i++){
		// double[] ze =zero.get(0, i);
		// Log.d(TAG,"zero "+String.valueOf(ze[0]));
		// }
		// Log.d(TAG,"chk abs zero "+String.valueOf(zero.channels())+" "+String.valueOf(zero.size()));
		// Log.d(TAG,"chk abs im_fil "+String.valueOf(im_fil.channels())+" "+String.valueOf(im_fil.size()));
		Core.absdiff(im_fil, Scalar.all(0f), im_fil);
		maxValImg = (float) Core.minMaxLoc(im_fil).maxVal;
		minValImg = (float) Core.minMaxLoc(im_fil).minVal;
		Log.d(TAG,
				"max after abs " + String.valueOf(maxValImg) + " "
						+ String.valueOf(minValImg));
		/*
		 * for(int i=0;i<im_fil.rows();i++){ for (int j = 0; j < im_fil.cols();
		 * j++) { double[] xx2 = im_fil.get(i, j); Log.d(TAG,"abb "+i+" "+j+" "+
		 * String.valueOf(xx2[0])); } }
		 */
		Mat hist = new Mat();
		Mat hist1 = new Mat();
		Mat hist2 = new Mat();
		Mat hist3 = new Mat();
		MatOfInt channels = new MatOfInt(0);
		im_fil.convertTo(im_fil, CvType.CV_32F);

		List<Mat> images = Arrays.asList(im_fil);

		MatOfInt histSize1 = new MatOfInt(1);
		MatOfFloat ranges1 = new MatOfFloat(0f, 3.125f);
		Imgproc.calcHist(images, channels, new Mat(), hist1, histSize1, ranges1);

		MatOfInt histSize2 = new MatOfInt(1);
		MatOfFloat ranges2 = new MatOfFloat(3.125f, 9.375f);
		Imgproc.calcHist(images, channels, new Mat(), hist2, histSize2, ranges2);

		MatOfInt histSize3 = new MatOfInt(1);
		MatOfFloat ranges3 = new MatOfFloat(9.375f, 1000f);
		Imgproc.calcHist(images, channels, new Mat(), hist3, histSize3, ranges3);
		hist = hist1.clone();
		hist.push_back(hist2);
		hist.push_back(hist3);

		double sum = 0;
		for (int i = 0; i < hist.rows(); i++) {
			for (int j = 0; j < hist.cols(); j++) {
				double[] value = hist.get(i, j);
				// value[0]/tmp.total()
				hist.put(i, j, value[0] / im_fil.total());
				sum += value[0] / im_fil.total(); // check prob
			}
		}
		Log.d(TAG, String.valueOf(sum));

		return hist;
	}

}
