package com.example.textgetter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

	private static final String NEWLINE = "\r\n";
	private static final String SPACE = " ";
	private static final String filename = "MyFoMonData";

	private static String[] SetData;
	private static Context context;
	private static String DataInFile = "";

	public PetDataGet(Context context) {
		PetDataGet.context = context;
		if (!isFileExistance()) {
			InitialWrite();
		}
		Update();

	}

	public static void Write(String data) {
		if (data.split(SPACE).length == 13) {
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

			SetData = data.split(NEWLINE);
		} catch (IOException E) {
			E.printStackTrace();
		}
	}

	public static boolean isFileExistance() {
		File file = context.getFileStreamPath(filename);
		Log.i(PetDataGet.class.getName(), file.exists() + "");
		return file.exists();
	}

	public void InitialWrite() {
		Log.i(this.getClass().getName(), "OK?");
		FileOutputStream outputStream;
		try {
			outputStream = context.openFileOutput(filename,
					Context.MODE_PRIVATE);

			String string = "Head1 A E I O U" + NEWLINE + "Head2 a e i o u"
					+ NEWLINE;

			outputStream.write(string.getBytes());
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getDataSize() {
		return SetData.length;
	}

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

	public static String getDataSet(int index) {
		try {
			return SetData[index];
		} catch (ArrayIndexOutOfBoundsException E) {
			return null;
		}
	}

	public static String getData(int index, int type) {
		String[] temp = null;
		try {
			temp = SetData[index].split(SPACE);
			return temp[type];
		} catch (ArrayIndexOutOfBoundsException E) {
			return null;
		}
	}

	public static Integer[] getNutritions(int index) {
		String[] temp = SetData[index].split(SPACE);
		Integer[] data = { Integer.parseInt(temp[PetDataType.KCalories]),
				Integer.parseInt(temp[PetDataType.Protien]),
				Integer.parseInt(temp[PetDataType.Carbohydrate]),
				Integer.parseInt(temp[PetDataType.Fat]) };
		return data;
	}

	public static Integer[] getDate(int index) {
		String[] temp = SetData[index].split(SPACE);
		Integer[] data = { Integer.parseInt(temp[PetDataType.Day]),
				Integer.parseInt(temp[PetDataType.Month]),
				Integer.parseInt(temp[PetDataType.Year]) };
		return data;
	}

	public static Integer[] getTime(int index) {
		String[] temp = SetData[index].split(SPACE);
		Integer[] data = { Integer.parseInt(temp[PetDataType.Hour]),
				Integer.parseInt(temp[PetDataType.Minuted]) };
		return data;
	}

	public static Integer[] getDateTime(int index) {
		Integer[] temp1 = getDate(index);
		Integer[] temp2 = getTime(index);
		Integer[] data = { temp1[0], temp1[1], temp1[2], temp2[0], temp2[1] };
		return data;
	}

	public static void FileLogShow() {
		Update();
		Log.i(PetDataGet.class.getName(), DataInFile);
	}

}
