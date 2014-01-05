package historyDatabase;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

import android.annotation.SuppressLint;
import android.net.ParseException;

public class HistoryType {
    
	public static final int PicturePath = 0 ;
	public static final int Latitude = 1 ;
	public static final int Longtitude = 2 ;
	public static final int FoodType = 3 ;
	/*public static final int KCalories = 4 ;
	public static final int Protien = 5 ;
	public static final int Carbohydrate = 6 ;
	public static final int Fat = 7 ;
	*/
	public static final int TimeStamp = 8;
	public static final String FolderSavedName = "FoMons";
	public static final String TempFilePetEatSaveName = "TempPicPetEat.jpg";
	public static final String DateFormat = "dd-MM-yyyy";
	public static final String TimeFormat = "HH:mm";	
	public static final String DateTimeFormat = DateFormat+" "+TimeFormat;
	//public static final int DataSize = 9;
	@SuppressLint("SimpleDateFormat")
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeFormat);
	public static final float RequireCalPerDay = 2000;
	public static final float RequireCalFactor = (float) 1.5;
	
	
	@SuppressLint("SimpleDateFormat")
	public static Date dateStringToDate(String date, String time){
		Date returnDate = null;
		String dtStart = date+" "+time;  
			try {
				returnDate = dateFormat.parse(dtStart);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		return returnDate;
	}
	@SuppressLint("SimpleDateFormat")
	public static String dateToStringDate(Date theDate, String stringDatFormat){
		String returnDateString = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(stringDatFormat);
		try {  
		 returnDateString = dateFormat.format(theDate);
		} catch (ParseException e) {  
		    // TODO Auto-generated catch block  
		    e.printStackTrace();  
		}
		return returnDateString;
	}
	public static String getCurrentDateTime() {
		Calendar c = Calendar.getInstance();
		return HistoryType.dateFormat.format(c.getTime());
	}
	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();		
		return dateToStringDate(c.getTime(), DateFormat);
	}
	public static String getCurrentTime() {
		Calendar c = Calendar.getInstance();
		return dateToStringDate(c.getTime(), TimeFormat);
	}
}
