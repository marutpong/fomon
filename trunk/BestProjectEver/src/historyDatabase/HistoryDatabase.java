package historyDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import tabFragment.QuestFragment;

import foodDatabase.FoodDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

@SuppressLint("ShowToast")
public class HistoryDatabase extends SQLiteOpenHelper {

	private static Context myComtext;
	// Caller
	public static HistoryDatabase hd;
	public static ArrayList<HistoryBox> historyList;

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "mydatabase";
	// Table Name
	private static final String TABLE_NAME = "history";

	// Common column names
	private static final String KEY_hid = "id";
	private static final String KEY_picpath = "picpath";
	private static final String KEY_latitude = "latitude";
	private static final String KEY_longitude = "longitude";
	private static final String KEY_foodid = "foodid";
	private static final String KEY_date = "date";
	private static final String KEY_time = "time";

	private static final String[] paramHistory = { KEY_hid, KEY_picpath,
			KEY_latitude, KEY_longitude, KEY_foodid, KEY_date, KEY_time };

	public enum HistoryEnum {
		id, picpath, latitude, longitude, foodid, date, time;
	}

	/*
	 * Initial Call when create object So, We might don't need to call "Update"
	 */
	public HistoryDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		myComtext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String strSQL = "CREATE TABLE " + TABLE_NAME + " ("
				+ "id INT PRIMARY KEY ," + "picpath TEXT NOT NULL ,"
				+ "latitude DOUBLE NOT NULL ," + "longitude DOUBLE NOT NULL ,"
				+ "foodid INT NOT NULL ," + "date DATE NOT NULL ,"
				+ "time TIME NOT NULL" + ")";
		// Log.i("Create Table",strSQL);
		db.execSQL(strSQL);
		Log.i("CREATE TABLE", "Create Table:" + TABLE_NAME + " Successfully.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("SQLite", "Upgread database version from version" + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);

	}

	/*
	 * Remove all record in Database By Drop Table history And Create mew Table
	 */
	public static void Clear() {
		try {
			SQLiteDatabase db;
			db = hd.getWritableDatabase(); // Write Data
			String strSQL = "DROP TABLE " + TABLE_NAME;
			db.execSQL(strSQL);
			hd.onCreate(db);
			Log.i("SQLite", "Clear Table:" + TABLE_NAME + " Successfully.");
		} catch (Exception e) {
			Log.i("SQLite", "Create Table:" + TABLE_NAME + " Successfully.");
		}
	}

	/*
	 * Check if data that's split by SPACE(,) have 13 element Then, DataInFile =
	 * DataInFile + data + NEWLINE and write it in file (DataInFile is String)
	 */

	public static long insertHistory(String picpath, double latitude,
			double longitude, int foodid, String date, String time) {

		if (FoodDatabase.getFoodByID(foodid) == null) {
			Log.i(HistoryDatabase.class.getName(), "Food ID: " + foodid
					+ " Not Found");
			return -1;
		} else {
			try {
				SQLiteDatabase db;
				db = hd.getWritableDatabase(); // Write Data

				/**
				 * for API 11 and above SQLiteStatement insertCmd; String strSQL
				 * = "INSERT INTO " + TABLE_MEMBER +
				 * "(MemberID,Name,Tel) VALUES (?,?,?)";
				 * 
				 * insertCmd = db.compileStatement(strSQL);
				 * insertCmd.bindString(1, strMemberID); insertCmd.bindString(2,
				 * strName); insertCmd.bindString(3, strTel); return
				 * insertCmd.executeInsert();
				 */
				ContentValues Val = new ContentValues();
				Val.put(KEY_picpath, picpath);
				Val.put(KEY_latitude, latitude);
				Val.put(KEY_longitude, longitude);
				Val.put(KEY_foodid, foodid);
				Val.put(KEY_date, date);
				Val.put(KEY_time, time);

				long rows = db.insert(TABLE_NAME, null, Val);
				db.close();
				Log.i("SQLite", "Insert " + TABLE_NAME + ":" + date + " "
						+ time);
				HistoryDatabase.SelectAllData();
				QuestFragment.updateValue();
				return rows; // return rows inserted.

			} catch (Exception e) {
				return -1;
			}
		}
	}

	public interface OnNumberBoxChange {
		void OnNumberChange(HistoryDatabase dual_Number);
	}

	private OnNumberBoxChange onNumberBoxChange;

	public void setOnNumberBoxChange(OnNumberBoxChange onNumberBoxChange) {
		this.onNumberBoxChange = onNumberBoxChange;
	}

	public HistoryBox getNumber(int index) {
		return historyList.get(index);
	}

	public void addNumberBox(HistoryBox temp) {
		historyList.add(temp);
		NorifyOnNumChange();
	}

	private void NorifyOnNumChange() {
		if (onNumberBoxChange != null)
			onNumberBoxChange.OnNumberChange(this);
	}

	/*
	 * Check Size
	 */
	public static int getDataSize() {
		return historyList.size();
	}

	/*
	 * Get it with OOP concept cc: Marupong -_-
	 */
	public static HistoryBox getDataBox(int index) {
		try {
			return historyList.get(index);
		} catch (Exception E) {
			return null;
		}
	}

	public static ArrayList<HistoryBox> getSetData() {
		HistoryDatabase.SelectAllData();
		return historyList;
	}

	/*
	 * For get date by week
	 */
	public static ArrayList<Float> getHistoryListByWeek() {
		ArrayList<Float> returnList = new ArrayList<Float>();
		Calendar c = Calendar.getInstance();
		// String msg = "";
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		String theDate = "";
		for (int i = 0; i < 7; i++) {
			theDate = HistoryType.dateToStringDate(c.getTime(),
					HistoryType.DateFormat);
			// Set Sum of Calories each date
			returnList.add((float) (HistoryDatabase.getSumNutritionOfDate(
					theDate, FoodDatabase.Enum.calories.ordinal())));
			c.add(Calendar.DATE, 1);
			// msg += "\n"+theDate;
		}
		/*
		 * for (Float l : returnList){ //msg += "\n"+String.valueOf(l); }
		 * Toast.makeText(myComtext, msg, Toast.LENGTH_LONG).show();
		 */
		return returnList;
	}

	/*
	 * public static double getCaloriesOfDate(String theDate) { double
	 * returnValue = 0; ArrayList<HistoryBox> tmpHistoryList =
	 * HistoryDatabase.getListOFDate(theDate); for (HistoryBox his :
	 * tmpHistoryList){ // Get food Detail from foodID FoodBox food =
	 * FoodDatabase.getFoodByID(his.getFoodType()); // Increase Calories
	 * returnValue += food.getCalories(); } return returnValue; }
	 */

	public static double getSumNutritionOfDate(String theDate, int type) {
		double returnValue = 0;
		ArrayList<HistoryBox> tmpHistoryList = HistoryDatabase
				.getListOFDate(theDate);

		for (HistoryBox his : tmpHistoryList) {
			// Get food Detail from foodID
			String[] food = FoodDatabase.getFoodString(his.getFoodType());
			// Increase Calories
			returnValue += Double.parseDouble(food[type]);
		}
		return returnValue;
	}

	public ArrayList<String[]> getListOfDateString(String theDate) {
		// TODO Auto-generated method stub
		ArrayList<String[]> returnList = new ArrayList<String[]>();
		String arrData[] = null;

		try {
			SQLiteDatabase db;
			db = hd.getReadableDatabase(); // Read Data

			String strSQL = "SELECT  * FROM " + TABLE_NAME + " WHERE DATE = '"
					+ theDate + "'";
			Cursor cursor = db.rawQuery(strSQL, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						arrData = new String[cursor.getColumnCount()];
						for (int i = 0; i < cursor.getColumnCount(); i++) {
							arrData[i] = cursor.getString(i);
						}
						returnList.add(arrData);
					} while (cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();

			return returnList;

		} catch (Exception e) {
			return null;
		}

	}

	/*
	 * For get date by month
	 */
	/*
	 * public static ArrayList<HistoryBox> getSetPetDBoxByMonth(){
	 * ArrayList<HistoryBox> tmp = new ArrayList<HistoryBox>();
	 * 
	 * Calendar c = Calendar.getInstance(); c.set(Calendar.DAY_OF_MONTH,
	 * Calendar.MONDAY); c.set(Calendar.HOUR_OF_DAY, 0); c.set(Calendar.MINUTE,
	 * 0); c.set(Calendar.SECOND, 0); c.set(Calendar.MILLISECOND, 0);
	 * 
	 * Date dateWeek = null; try { dateWeek =
	 * HistoryType.dateFormat.parse(HistoryType.dateFormat.format(c.getTime()));
	 * } catch (ParseException e) { e.printStackTrace(); }
	 * 
	 * for(int i=0; i<SetData.size(); i++){
	 * if(SetData.get(i).getTimeStamp().after(dateWeek))
	 * SetData.add(SetData.get(i)); else break; }
	 * 
	 * return tmp; }
	 */

	// Show All Data
	public ArrayList<HashMap<String, String>> SelectAllDataHash() {
		// TODO Auto-generated method stub
		try {
			ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			String strSQL = "SELECT  * FROM " + TABLE_NAME;
			Cursor cursor = db.rawQuery(strSQL, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						map = new HashMap<String, String>();

						// Get all data with Key(param) and Value
						for (int i = 0; i < paramHistory.length; i++) {
							map.put(paramHistory[i], cursor.getString(i));
						}

						MyArrList.add(map);
					} while (cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();
			return MyArrList;

		} catch (Exception e) {
			return null;
		}

	}

	public static boolean SelectAllData() {
		// TODO Auto-generated method stub

		try {
			historyList = new ArrayList<HistoryBox>();

			SQLiteDatabase db;
			db = hd.getReadableDatabase(); // Read Data

			String strSQL = "SELECT  * FROM " + TABLE_NAME;
			Cursor cursor = db.rawQuery(strSQL, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						HistoryBox historybox = new HistoryBox(
								cursor.getInt(0), cursor.getString(1),
								cursor.getDouble(2), cursor.getDouble(3),
								cursor.getInt(4), cursor.getString(5),
								cursor.getString(6));
						historyList.add(historybox);
					} while (cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public static ArrayList<HistoryBox> getListOFDate(String theDate) {
		// TODO Auto-generated method stub
		ArrayList<HistoryBox> returnList = new ArrayList<HistoryBox>();

		try {
			SQLiteDatabase db;
			db = hd.getReadableDatabase(); // Read Data

			String strSQL = "SELECT  * FROM " + TABLE_NAME + " WHERE DATE = '"
					+ theDate + "'";
			Cursor cursor = db.rawQuery(strSQL, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						HistoryBox historybox = new HistoryBox(
								cursor.getInt(0), cursor.getString(1),
								cursor.getDouble(2), cursor.getDouble(3),
								cursor.getInt(4), cursor.getString(5),
								cursor.getString(6));
						returnList.add(historybox);
					} while (cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();

			return returnList;

		} catch (Exception e) {
			return null;
		}
	}
}
