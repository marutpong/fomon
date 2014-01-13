package processImage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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
import org.opencv.core.MatOfDouble;
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
import android.os.Environment;
import android.util.Log;

public class Hist_Phog implements Runnable {

	private static final String TAG = "Hist_Phog";
	// private int bin = 8;
	// private int angle = 360;
	private static Context context;
	float y[], z[];
	static int foodnum = 0; // = readNum();
	// int foodnumtest = 248;
	static int featurenum = 41;
	static int featurepc = 15;

	public interface OnImageProcessListener {
		void OnImageProcessFinish(int[] classFood);
	}

	private OnImageProcessListener onImageProcessListener;
	private String path;

	public void setOnImageProcessListener(
			OnImageProcessListener onImageProcessListener) {
		this.onImageProcessListener = onImageProcessListener;
	}

	@Override
	public void run() {
		int[] Class = histImage(this.path);
		onImageProcessListener.OnImageProcessFinish(Class);
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int[] histImage(String path) {

		Log.d(TAG, path);
		Log.d(TAG, String.valueOf(foodnum));
		List<Mat> channel = new ArrayList<Mat>();
		// File folder = new File(Environment.getExternalStorageDirectory()
		// + "/FoMons");
		// path = folder + "/use1.jpg";
		Mat im = Highgui.imread(path);

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
			Log.d(TAG, (i + 1) + "  " + String.valueOf(xx2[0]));
		}

		int[] classFood = KNN(feature);

		// onImageProcessListener.OnImageProcessFinish(classFood);

		return classFood;
	}

	public Hist_Phog(Context context) {
		setContext(context);
		foodnum = readNum();
	}

	public Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		Hist_Phog.context = context;
	}

	private static int[] KNN(Mat test) {
		String tag = "TEXT";

		Mat fvtrain = new Mat(foodnum, featurenum, CvType.CV_32F);
		Mat fvtrainpc = new Mat(foodnum, featurepc, CvType.CV_32F);
		Mat gt = new Mat(foodnum, 1, CvType.CV_32F);
		Mat meantrain = new Mat(1, featurenum, CvType.CV_32F);
		Mat stdtrain = new Mat(1, featurenum, CvType.CV_32F);
		Mat pc = new Mat(featurenum, featurenum, CvType.CV_32F);

		fvtrain = readText(fvtrain, 1);
		gt = readText(gt, 3);

		// // find mean std
		Log.d(tag, "rowww = " + fvtrain.rows());
		Mat tmpmeanstd = new Mat(foodnum, 1, CvType.CV_32F);
		for (int i = 0; i < featurenum; i++) {
			for (int j = 0; j < foodnum; j++) {
				double[] tmpval = fvtrain.get(j, i);
				tmpmeanstd.put(j, 0, tmpval);
			}
			MatOfDouble mean = new MatOfDouble();
			MatOfDouble stddev = new MatOfDouble();
			Core.meanStdDev(tmpmeanstd, mean, stddev);
			double[] val = mean.get(0, 0);
			meantrain.put(0, i, val[0]);
			val = stddev.get(0, 0);
			stdtrain.put(0, i, val[0]);
		}

		for (int i = 0; i < featurenum; i++) {
			double[] val1 = meantrain.get(0, i);
			double[] val2 = stdtrain.get(0, i);
			// Log.d(tag,"chk mean std "+String.valueOf(val1[0])+"   "+String.valueOf(val2[0]));
		}
		// Log.d(tag,"chksize "+
		// String.valueOf(mean.size())+" "+String.valueOf(stddev.size()));

		// Core.PCACompute(fvtrain, mean, eigenvectors, maxComponents)
		Mat fvtrainnor = new Mat(foodnum, featurenum, CvType.CV_32F);
		for (int i = 0; i < foodnum; i++) {
			for (int j = 0; j < featurenum; j++) {
				double[] x = fvtrain.get(i, j);
				double[] xmean = meantrain.get(0, j);
				double[] sd = stdtrain.get(0, j);
				double cal = (x[0] - xmean[0]) / sd[0];
				// Log.d("Hist_Phog","train nor "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(cal));
				fvtrainnor.put(i, j, cal);
			}
		}

		Mat fvtestpc = new Mat(1, featurenum, CvType.CV_32F);
		for (int j = 0; j < featurenum; j++) {
			double[] x = test.get(0, j);
			double[] xmean = meantrain.get(0, j);
			double[] sd = stdtrain.get(0, j);
			double cal = (x[0] - xmean[0]) / sd[0];
			// Log.d("Hist_Phog","sd "+(j+1)+" "+String.valueOf(cal));
			fvtestpc.put(0, j, cal);
		}

		Mat mean = new Mat();

		Core.PCACompute(fvtrainnor, mean, pc);

		Log.d("TEXT", "PC " + String.valueOf(pc.size()));
		for (int i = 0; i < featurenum; i++) {
			for (int j = 0; j < featurenum; j++) {

				double[] val1 = pc.get(i, j);
				Log.d("Hist_Phog",
						"chk PC " + i + " " + j + "  "
								+ String.valueOf(val1[0]));

			}
		}

		for (int k = 0; k < foodnum; k++) {
			for (int i = 0; i < featurenum; i++) {
				double sumfrommul = 0;
				for (int j = 0; j < featurenum; j++) {
					double[] num1 = fvtrainnor.get(k, j);
					double[] num2 = pc.get(j, i);
					double mul = num1[0] * num2[0];
					// Log.d(tag,"mul "+String.valueOf(num1[0])+" "+String.valueOf(num2[0])+" "+String.valueOf(mul));
					sumfrommul += mul;
				}
				fvtrainpc.put(k, i, sumfrommul);

			}
			// double[] val1 = fvtrainpc.get(k, 0);
			// Log.d("mul",String.valueOf(k)+" 0 "+String.valueOf(val1[0]));
		}

		Mat mulmattmp = fvtestpc.clone();
		for (int i = 0; i < featurenum; i++) {
			double sumfrommul = 0;

			for (int j = 0; j < featurenum; j++) {
				double[] num1 = mulmattmp.get(0, j);
				double[] num2 = pc.get(j, i);
				double mul = num1[0] * num2[0];
				// Log.d(tag,"mul "+String.valueOf(num1[0])+" "+String.valueOf(num2[0])+" "+String.valueOf(mul));
				sumfrommul += mul;
			}
			fvtestpc.put(0, i, sumfrommul);
		}

		Mat results1 = new Mat();
		Mat results2 = new Mat();
		Mat neighborResponses = new Mat();
		Mat dists = new Mat();
		CvKNearest knn = new CvKNearest();
		int[] classFood = { 0, 0 };
		knn.train(fvtrain, gt);
		knn.find_nearest(test, 1, results1, neighborResponses, dists);
		double[] tmpclassfood = results1.get(0, 0);
		int classFoodchk1 = (int) tmpclassfood[0];
		Log.d(tag, "first " + String.valueOf(classFoodchk1));

		Mat fvtrainpcCut = new Mat(foodnum, featurepc, CvType.CV_32F);
		for (int j = 0; j < featurepc; j++) {
			double[] cuttmp = fvtestpc.get(0, j);
			fvtrainpcCut.put(0, j, cuttmp[0]);
			// Log.d("Hist_Phog","fv "+(j+1)+" "+String.valueOf(cuttmp[0]));
		}

		Mat fvtestpcCut = new Mat(1, featurepc, CvType.CV_32F);
		for (int j = 0; j < featurepc; j++) {
			double[] cuttmp = fvtestpc.get(0, j);
			fvtestpcCut.put(0, j, cuttmp[0]);
			// Log.d("Hist_Phog","fv "+(j+1)+" "+String.valueOf(cuttmp[0]));
		}

		Log.d("TEXT", String.valueOf(fvtestpcCut.size()));

		knn.train(fvtrainpc, gt);
		knn.find_nearest(fvtrainpcCut, 18, results2, neighborResponses, dists);
		double[] tmpclassfood1 = results2.get(0, 0);
		int classFoodchk2 = (int) tmpclassfood1[0];
		Log.d(tag, "second " + String.valueOf(classFoodchk2));

		// classFood = groupClass(classFoodchk);
		// Log.d(tag, "result "+
		// String.valueOf(classFood[0])+"  "+String.valueOf(classFood[1]));
		classFood[0] = classFoodchk1;
		classFood[1] = classFoodchk2;

		writeText(test, 1);

		return classFood;
	}

	public static void writeNew(int classfood) {
		int num = readNum();
		Log.d("Add", "all num = " + String.valueOf(num));
		// Mat tmp = new Mat(num-1,featurenum,CvType.CV_32F);
		Mat all = new Mat(num, featurenum, CvType.CV_32F);
		Mat testtmp = new Mat(1, featurenum, CvType.CV_32F);
		all = readText(all, 1);
		testtmp = readText(testtmp, 2);
		for (int i = 0; i < featurenum; i++) {
			double[] val = testtmp.get(0, i);
			all.put(num - 1, i, val[0]);
		}
		writeText(all, 2);

		Log.d("Add", "all new = " + String.valueOf(all.size()));

		Mat allgt = new Mat(num, 1, CvType.CV_32F);
		allgt = readText(allgt, 3);
		Log.d("Add", " class = " + String.valueOf(classfood));

		allgt.put(num - 1, 0, classfood);

		double[] xx = allgt.get(num - 1, 0);
		Log.d("Add", " last train = " + xx[0]);

		writeText(allgt, 3);

		Log.d("Add", "allgt new = " + String.valueOf(allgt.size()));

	}

	private static void writeText(Mat keepfile, int name) {
		// FileWriter fw = null;
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/FoMons");
		File file = null;
		switch (name) {
		case 1:
			file = new File(folder, "keeptrain.txt");
			break;
		case 2:
			file = new File(folder, "fvtrain.txt");

			break;
		case 3:
			file = new File(folder, "gt.txt");
			break;
		default:
			break;
		}

		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			// While the BufferedReader readLine is not null
			if (name == 3) {

				for (int i = 0; i < keepfile.rows(); i++) {
					double[] d = keepfile.get(i, 0);
					String dtmp = String.valueOf(d[0]);
					// Log.d("Add", "Check write keepfile = "+dtmp);
					bw.write(dtmp + "\n");
				}
			} else {

				for (int i = 0; i < keepfile.rows(); i++) {
					for (int j = 0; j < keepfile.cols(); j++) {
						double[] d = keepfile.get(i, j);
						String tmpd = String.valueOf(d[0]);
						bw.write(tmpd + "\t");

					}
					bw.write("\n");
				}
				double[] xx = keepfile.get(keepfile.rows() - 1,
						keepfile.cols() - 1);
				Log.d("Add", "all name chk = " + name + "  " + xx[0]);

			}

			// Close the InputStream and BufferedReader
			// fw.close();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeNum() {
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/FoMons");
		File file = new File(folder, "foodnum.txt");
		;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			// While the BufferedReader readLine is not null
			String foodnumber = String.valueOf(foodnum + 1);
			bw.write(foodnumber);

			// Close the InputStream and BufferedReader
			// fw.close();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int readNum() {
		int num = 0;
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/FoMons");
		File file = new File(folder, "foodnum.txt");
		if (!file.exists()) {
			
			Log.i("mul",context + " ");
			
			InputStream ins = context.getResources().openRawResource(R.raw.foodnum);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			int size = 0;
			// Read the entire resource into a local byte buffer.
			byte[] buffer = new byte[1024];
			try {
				while ((size = ins.read(buffer, 0, 1024)) >= 0) {
					outputStream.write(buffer, 0, size);
				}
				ins.close();

				buffer = outputStream.toByteArray();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(buffer);
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String readLine = null;
		try {
			// While the BufferedReader readLine is not null
			int j = 0;

			while ((readLine = br.readLine()) != null) {
				Scanner s = new Scanner(readLine).useDelimiter("\\t");
				int i = 0;
				while (s.hasNext()) {

					num = Integer.parseInt(s.next());
					i++;
					// Log.d("TEXT", String.valueOf(d));
				}
				j++;
				// Log.d("TEXT", readLine);
				// Log.d("TEXT", String.valueOf(d));
			}

			// Close the InputStream and BufferedReader
			// is.close();
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return num;
	}

	private static Mat readText(Mat keepfile, int name) {

		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/FoMons");
		File file = null;

		switch (name) {
		case 1:

			file = new File(folder, "fvtrain.txt");

			break;
		case 2:
			file = new File(folder, "keeptrain.txt");

			break;
		case 3:
			file = new File(folder, "gt.txt");

			break;
		case 4:
			// is = context.getResources().openRawResource(R.raw.meantrain);
			break;
		case 5:
			// is = context.getResources().openRawResource(R.raw.stdtrain);
			break;
		case 6:
			// is = context.getResources().openRawResource(R.raw.pc);
		default:
			break;
		}
		if (!file.exists()) {
			InputStream ins = null;
			if (name == 1) {
				ins = context.getResources().openRawResource(
						R.raw.fvtrain);
			} else if (name == 3) {
				ins = context.getResources().openRawResource(
						R.raw.gt);
			}
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			int size = 0;
			// Read the entire resource into a local byte buffer.
			byte[] buffer = new byte[1024];
			try {
				while ((size = ins.read(buffer, 0, 1024)) >= 0) {
					outputStream.write(buffer, 0, size);
				}
				ins.close();

				buffer = outputStream.toByteArray();
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(buffer);
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String readLine = null;
		try {
			// While the BufferedReader readLine is not null
			if (name == 3) {
				int j = 0;

				while ((readLine = br.readLine()) != null) {
					Scanner s = new Scanner(readLine).useDelimiter("\\t");
					int i = 0;
					while (s.hasNext()) {

						double d = Double.parseDouble(s.next());
						keepfile.put(j, i, d);
						i++;
						// Log.d("TEXT", String.valueOf(d));
					}
					j++;
					// Log.d("TEXT", readLine);
					// Log.d("TEXT", String.valueOf(d));
				}
			} else {
				int j = 0;

				while ((readLine = br.readLine()) != null) {
					Scanner s = new Scanner(readLine).useDelimiter("\\t");
					int i = 0;
					while (s.hasNext()) {

						double d = Double.parseDouble(s.next());
						keepfile.put(j, i, d);
						i++;
						// Log.d("TEXT", String.valueOf(d));
					}
					j++;
					// Log.d("TEXT", readLine);
					// Log.d("TEXT", String.valueOf(d));
				}

				// Close the InputStream and BufferedReader
				// is.close();
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return keepfile;

	}

	private static int[] groupClass(int first) {
		int tmp[] = { 0, 0 };

		switch (first) {
		case 1:
			tmp[0] = 1;
			tmp[1] = 11;
			break;
		case 2:
			tmp[0] = 2;
			tmp[1] = 9;
			break;
		case 3:
			tmp[0] = 3;
			tmp[1] = 0;
			break;
		case 4:
			tmp[0] = 4;
			tmp[1] = 12;
			break;
		case 5:
			tmp[0] = 14;
			tmp[1] = 5;
			break;
		case 6:
			tmp[0] = 6;
			break;
		case 7:
			tmp[0] = 7;
			tmp[1] = 17;
			break;
		case 8:
			tmp[0] = 8;
			tmp[1] = 10;
			break;
		case 9:
			tmp[0] = 2;
			tmp[1] = 9;
			break;
		case 10:
			tmp[0] = 10;
			tmp[1] = 12;
			break;
		case 11:
			tmp[0] = 11;
			tmp[1] = 13;
			break;
		case 12:
			tmp[0] = 12;
			break;
		case 13:
			tmp[0] = 13;
			break;
		case 14:
			tmp[0] = 14;
			break;
		case 15:
			tmp[0] = 15;
			tmp[1] = 5;
			break;
		case 16:
			tmp[0] = 16;
			break;
		case 17:
			tmp[0] = 17;
			tmp[1] = 15;
			break;
		case 18:
			tmp[0] = 18;
			tmp[1] = 0;
			break;
		case 19:
			tmp[0] = 19;
			tmp[1] = 13;
			break;
		case 20:
			tmp[0] = 20;
			tmp[1] = 0;
			break;
		case 21:
			tmp[0] = 21;
			tmp[1] = 7;
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
