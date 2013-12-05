package com.example.textgetter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

/*
 * <Include> PetDataType.java
 * How to Use this Database
 * - Create new object of this class
 * 	Example: PetDataGet tmp = new PetDataGet(this);
 * this --> for Activity (Context get)
 * then Database will generate database from file
 * - Get data from static method in any class you want
 * 	Example: PetDataGet.getData(0, PetDataType.PicturePath);
 * 		PetDataGet.getDataSize();
 * 
 * You must call this class when
 * 1) First Open this application
 * 2) use Write(String ) method when successfully take a photo
 */

public class PetDataGet {

	public static final String NEWLINE = "\r\n";
	public static final String SPACE = ",";
	public static final String filename = "MyFoMonData";

	private static ArrayList<PetDBox> SetData;
	private static Context context;
	private static String DataInFile = "";

	/*
	 * Initial Call when create object
	 * So, We might don't need to call "Update"
	 */
	public PetDataGet(Context context) {
		PetDataGet.context = context;
		if (!isFileExistance()) {
			InitialWrite();
		}
		Update();

	}

	/*
	 * Check if data that's split by SPACE(,) have 13 element
	 * Then, DataInFile = DataInFile + data + NEWLINE and write it in file
	 * (DataInFile is String)
	 */
	public static void Write(String data) {
		if (PetDBox.isCorrectPattern(data)) {
			Update();

			FileOutputStream outputStream;
			try {
				outputStream = context.openFileOutput(filename,
						Context.MODE_PRIVATE);

				DataInFile += data + NEWLINE;

				outputStream.write(DataInFile.getBytes());
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/*
	 * Use for update ArrayList
	 * This method called only take a photo
	 */
	public static void Update() {
		FileInputStream inputStream;

		try {

			inputStream = context.openFileInput(filename);
			String data = "";

			byte[] input = new byte[inputStream.available()];
			while (inputStream.read(input) > 0) {
				data += new String(input);
			}

			DataInFile = data;

			Log.i(PetDataGet.class.getName(), data);

			SetData = new ArrayList<PetDBox>();
			for(String tmp: data.split(NEWLINE)){
				SetData.add(new PetDBox(tmp));
			}
			
		} catch (IOException E) {
			E.printStackTrace();
		}
	}

	/* Check if file exist
	 * yes, do nothing
	 * no, write initial
	 */
	public static boolean isFileExistance() {
		File file = context.getFileStreamPath(filename);
		Log.i(PetDataGet.class.getName(), file.exists() + "");
		return file.exists();
	}

	/* Only Initial*/
	public void InitialWrite() {
		Log.i(this.getClass().getName(), "OK?");
		FileOutputStream outputStream;
		try {
			outputStream = context.openFileOutput(filename,
					Context.MODE_PRIVATE);

			String string = "PathPic,12.5,18.8,KaomunKai,30,10,15,20,11,12,2013,23,00" + NEWLINE + "PathPic2,12.55,18.88,KaomunKai2,300,100,150,200,10,10,2014,20,15"
					+ NEWLINE;

			outputStream.write(string.getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Check Size
	 */
	public static int getDataSize() {
		return SetData.size();
	}

	/*
	 * Remove all in FILE!!!
	 * We have to delete file, too.
	 */
	public static void Clear() {
		FileOutputStream outputStream;
		try {
			outputStream = context.openFileOutput(filename,
					Context.MODE_PRIVATE);

			DataInFile = "";

			outputStream.write(DataInFile.getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get it with OOP concept
	 * cc: Marupong -_-
	 */
	public static PetDBox getDataBox(int index) {
		try {
			return SetData.get(index);
		} catch (Exception E) {
			return null;
		}
	}

	/*
	 * Show What is in file at the Log (info)
	 */
	public static void FileLogShow() {
		Update();
		Log.i(PetDataGet.class.getName(), DataInFile);
	}

}
