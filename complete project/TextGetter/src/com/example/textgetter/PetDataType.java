package com.example.textgetter;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;

public class PetDataType {

	public static final int PicturePath = 0 ;
	public static final int Latitude = 1 ;
	public static final int Longtitude = 2 ;
	public static final int FoodType = 3 ;
	public static final int KCalories = 4 ;
	public static final int Protien = 5 ;
	public static final int Carbohydrate = 6 ;
	public static final int Fat = 7 ;
	public static final int TimeStamp = 8;
	private static final String TimeFormat = "dd-MM-yyyy HH:mm";
	public static final int DataSize = 9;
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(TimeFormat);
	

}
