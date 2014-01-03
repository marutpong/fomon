package textGetter;

import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;

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
	public static final String FolderSavedName = "FoMons";
	public static final String TempFilePetEatSaveName = "TempPicPetEat.jpg";
	private static final String TimeFormat = "dd-MM-yyyy HH:mm";
	public static final int DataSize = 9;
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(TimeFormat);
	public static final float RequireCalPerDay = 2000;
	public static final float RequireCalFactor = (float) 1.5;

	
}
