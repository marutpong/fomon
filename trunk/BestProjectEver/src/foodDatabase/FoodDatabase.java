package foodDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressLint("ShowToast")
public class FoodDatabase extends SQLiteOpenHelper {
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "mydatabase";
	// Table Name
	private static final String TABLE_FOOD = "food";

	public static enum Enum {
		id, name, calories, protein, fat, carbohydrate, calcium, magnesium, potassium, sodium, min_hp, max_hp, min_atk, max_atk, min_def, max_def
	}

	// Common column names
	private static final String KEY_id = "id";
	private static final String KEY_name = "name";
	private static final String KEY_calories = "calories";
	private static final String KEY_protein = "protein";
	private static final String KEY_fat = "fat";
	private static final String KEY_carbohydrate = "carbohydrate";
	private static final String KEY_calcium = "calcium";
	private static final String KEY_magnesium = "magnesium";
	private static final String KEY_potassium = "potassium";
	private static final String KEY_sodium = "sodium";
	private static final String KEY_phosphorus = "phosphorus";
	private static final String KEY_min_hp = "min_hp";
	private static final String KEY_max_hp = "max_hp";
	private static final String KEY_min_atk = "min_atk";
	private static final String KEY_max_atk = "max_atk";
	private static final String KEY_min_def = "min_def";
	private static final String KEY_max_def = "max_def";
	public static final String[] foodParam = { KEY_id, KEY_name, KEY_calories,
			KEY_protein, KEY_fat, KEY_carbohydrate, KEY_calcium, KEY_magnesium,
			KEY_potassium, KEY_sodium, KEY_phosphorus, KEY_min_hp, KEY_max_hp,
			KEY_min_atk, KEY_max_atk, KEY_min_def, KEY_max_def };

	public static FoodDatabase fd;
	public static List<FoodBox> FoodList;
	public Context mContext;

	public FoodDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String strSQL = "CREATE TABLE " + TABLE_FOOD + " ( "
				+ "id int(11) UNIQUE PRIMARY KEY," + "name varchar(120),"
				+ "calories double NOT NULL," + "protein double NOT NULL,"
				+ "fat double NOT NULL," + "carbohydrate double NOT NULL,"
				+ "calcium double NOT NULL," + "magnesium double NOT NULL,"
				+ "potassium double NOT NULL," + "sodium double NOT NULL,"
				+ "phosphorus double NOT NULL," + "min_hp int(11) NOT NULL,"
				+ "max_hp int(11) NOT NULL," + "min_atk int(11) NOT NULL,"
				+ "max_atk int(11) NOT NULL," + "min_def int(11) NOT NULL,"
				+ "max_def int(11) NOT NULL" + ") ";
		// Log.i("Create Table",strSQL);
		db.execSQL(strSQL);
		Log.i("SQLite", "Create Table:" + TABLE_FOOD + " Successfully.");

		String TABLE_NAME = "history";
		strSQL = "CREATE TABLE " + TABLE_NAME + " (" + "id INT PRIMARY KEY ,"
				+ "picpath TEXT NOT NULL ," + "latitude DOUBLE NOT NULL ,"
				+ "longitude DOUBLE NOT NULL ," + "foodid INT NOT NULL ,"
				+ "date DATE NOT NULL ," + "time TIME NOT NULL" + ")";
		// Log.i("Create Table",strSQL);
		db.execSQL(strSQL);
		Log.i("CREATE TABLE", "Create Table:" + TABLE_NAME + " Successfully.");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i("SQLite", "Upgread database version from version" + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
		onCreate(db);
	}

	public static long insertFood(int id, String name, double Cal, double Pro,
			double Fat, double Carb, double Calc, double Mag, double Pot,
			double So, double Phos, int randHP1, int randHP2, int randATK1,
			int randATK2, int randDEF1, int randDEF2) {

		if (getFoodString(id) != null) {
			Log.i("Insert", name + "already insert.");
			return -1;
		} else {
			try {
				SQLiteDatabase db;
				db = fd.getWritableDatabase(); // Write Data

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
				Val.put(KEY_id, id);
				Val.put(KEY_name, name);
				Val.put(KEY_calories, Cal);
				Val.put(KEY_protein, Pro);
				Val.put(KEY_fat, Fat);
				Val.put(KEY_carbohydrate, Carb);
				Val.put(KEY_calcium, Calc);
				Val.put(KEY_magnesium, Mag);
				Val.put(KEY_potassium, Pot);
				Val.put(KEY_sodium, So);
				Val.put(KEY_phosphorus, Phos);
				Val.put(KEY_min_hp, randHP1);
				Val.put(KEY_max_hp, randHP2);
				Val.put(KEY_min_atk, randATK1);
				Val.put(KEY_max_atk, randATK2);
				Val.put(KEY_min_def, randDEF1);
				Val.put(KEY_max_def, randDEF2);

				long rows = db.insert(TABLE_FOOD, null, Val);

				db.close();
				Log.i("SQLite", "Insert " + TABLE_FOOD + ":" + name);
				return rows; // return rows inserted.

			} catch (Exception e) {
				return -1;
			}
		}
	}

	// Select Data
	public static String[] getFoodString(int id) {
		// TODO Auto-generated method stub

		try {
			String arrData[] = null;

			SQLiteDatabase db;
			db = fd.getReadableDatabase(); // Read Data

			Cursor cursor = db
					.query(TABLE_FOOD, new String[] { "*" }, "id=?",
							new String[] { String.valueOf(id) }, null, null,
							null, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					arrData = new String[cursor.getColumnCount()];
					/***
					 * 0 = MemberID 1 = Name 2 = Tel
					 */
					for (int i = 0; i < cursor.getColumnCount(); i++) {
						arrData[i] = cursor.getString(i);
					}
				}
			}
			cursor.close();
			db.close();
			return arrData;

		} catch (Exception e) {
			return null;
		}

	}

	public FoodBox getFoodByName(String name) {
		FoodBox foodbox = null;
		try {

			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			Cursor cursor = db.query(TABLE_FOOD, new String[] { "*" },
					"name=?", new String[] { String.valueOf(name) }, null,
					null, null, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					foodbox = new FoodBox(cursor.getInt(0),
							cursor.getString(1), cursor.getDouble(2),
							cursor.getDouble(3), cursor.getDouble(4),
							cursor.getDouble(5), cursor.getDouble(6),
							cursor.getDouble(7), cursor.getDouble(8),
							cursor.getDouble(9), cursor.getDouble(10),
							cursor.getInt(11), cursor.getInt(12),
							cursor.getInt(13), cursor.getInt(14),
							cursor.getInt(15), cursor.getInt(16));
				}
			}
			cursor.close();
			db.close();
			return foodbox;

		} catch (Exception e) {
			return null;
		}
	}

	public static FoodBox getFoodByID(int id) {
		FoodBox foodbox = null;
		try {

			SQLiteDatabase db;
			db = fd.getReadableDatabase(); // Read Data

			Cursor cursor = db
					.query(TABLE_FOOD, new String[] { "*" }, "id=?",
							new String[] { String.valueOf(id) }, null, null,
							null, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					foodbox = new FoodBox(cursor.getInt(0),
							cursor.getString(1), cursor.getDouble(2),
							cursor.getDouble(3), cursor.getDouble(4),
							cursor.getDouble(5), cursor.getDouble(6),
							cursor.getDouble(7), cursor.getDouble(8),
							cursor.getDouble(9), cursor.getDouble(10),
							cursor.getInt(11), cursor.getInt(12),
							cursor.getInt(13), cursor.getInt(14),
							cursor.getInt(15), cursor.getInt(16));
				}
			}
			cursor.close();
			db.close();
			return foodbox;

		} catch (Exception e) {
			return null;
		}

	}

	// Select All Data
	/*
	 * public class sFood { int _id; String _name; Double _calories, _protein,
	 * _fat, _carbohydrate, _calcium, _magnesium, _potassium, _sodium,
	 * _phosphorus; int _min_hp,_max_hp, _min_atk, _max_atk, _min_def,_max_def;
	 * 
	 * public int get_id() { return _id; } public void set_id(int _id) {
	 * this._id = _id; } public Double get_calories() { return _calories; }
	 * public void set_calories(Double _calories) { this._calories = _calories;
	 * } public Double get_protein() { return _protein; } public void
	 * set_protein(Double _protein) { this._protein = _protein; } public Double
	 * get_fat() { return _fat; } public void set_fat(Double _fat) { this._fat =
	 * _fat; } public Double get_carbohydrate() { return _carbohydrate; } public
	 * void set_carbohydrate(Double _carbohydrate) { this._carbohydrate =
	 * _carbohydrate; } public Double get_calcium() { return _calcium; } public
	 * void set_calcium(Double _calcium) { this._calcium = _calcium; } public
	 * Double get_magnesium() { return _magnesium; } public void
	 * set_magnesium(Double _magnesium) { this._magnesium = _magnesium; } public
	 * Double get_potassium() { return _potassium; } public void
	 * set_potassium(Double _potassium) { this._potassium = _potassium; } public
	 * Double get_sodium() { return _sodium; } public void set_sodium(Double
	 * _sodium) { this._sodium = _sodium; } public int get_min_hp() { return
	 * _min_hp; } public void set_min_hp(int _min_hp) { this._min_hp = _min_hp;
	 * } public int get_max_hp() { return _max_hp; } public void set_max_hp(int
	 * _max_hp) { this._max_hp = _max_hp; } public int get_min_atk() { return
	 * _min_atk; } public void set_min_atk(int _min_atk) { this._min_atk =
	 * _min_atk; } public int get_max_atk() { return _max_atk; } public void
	 * set_max_atk(int _max_atk) { this._max_atk = _max_atk; } public int
	 * get_min_def() { return _min_def; } public void set_min_def(int _min_def)
	 * { this._min_def = _min_def; } public int get_max_def() { return _max_def;
	 * } public void set_max_def(int _max_def) { this._max_def = _max_def; }
	 * public String get_name() { return _name; } public void set_name(String
	 * _name) { this._name = _name; } public Double get_phosphorus() { return
	 * _phosphorus; } public void set_phosphorus(Double _phosphorus) {
	 * this._phosphorus = _phosphorus; }
	 * 
	 * }
	 */
	public boolean SelectAllData() {
		// TODO Auto-generated method stub

		try {
			FoodList = new ArrayList<FoodBox>();

			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			String strSQL = "SELECT  * FROM " + TABLE_FOOD;
			Cursor cursor = db.rawQuery(strSQL, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						FoodBox foodbox = new FoodBox(cursor.getInt(0),
								cursor.getString(1), cursor.getDouble(2),
								cursor.getDouble(3), cursor.getDouble(4),
								cursor.getDouble(5), cursor.getDouble(6),
								cursor.getDouble(7), cursor.getDouble(8),
								cursor.getDouble(9), cursor.getDouble(10),
								cursor.getInt(11), cursor.getInt(12),
								cursor.getInt(13), cursor.getInt(14),
								cursor.getInt(15), cursor.getInt(16));
						/*
						 * sFood cFood = new sFood();
						 * 
						 * cFood.set_id(cursor.getInt(0));
						 * cFood.set_name(cursor.getString(1));
						 * cFood.set_calories(cursor.getDouble(2));
						 * cFood.set_protein(cursor.getDouble(3));
						 * cFood.set_fat(cursor.getDouble(4));
						 * cFood.set_carbohydrate(cursor.getDouble(5));
						 * cFood.set_calcium(cursor.getDouble(6));
						 * cFood.set_magnesium(cursor.getDouble(7));
						 * cFood.set_potassium(cursor.getDouble(8));
						 * cFood.set_sodium(cursor.getDouble(9));
						 * cFood.set_set_phosphorus(cursor.getDouble(10));
						 * cFood.set_min_hp(cursor.getInt(11));
						 * cFood.set_max_hp(cursor.getInt(12));
						 * cFood.set_min_atk(cursor.getInt(13));
						 * cFood.set_max_atk(cursor.getInt(14));
						 * cFood.set_min_def(cursor.getInt(15));
						 * cFood.set_max_def(cursor.getInt(16));
						 * FoodList.add(cFood);
						 */
						FoodList.add(foodbox);
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

	// Show All Data
	public ArrayList<HashMap<String, String>> SelectAllDataHash() {
		// TODO Auto-generated method stub

		try {

			ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data

			String strSQL = "SELECT  * FROM " + TABLE_FOOD;
			Cursor cursor = db.rawQuery(strSQL, null);

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						map = new HashMap<String, String>();

						for (int i = 0; i < foodParam.length; i++) {
							map.put(foodParam[i], cursor.getString(i));
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
}
