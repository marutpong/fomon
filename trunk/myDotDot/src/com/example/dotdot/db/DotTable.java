package com.example.dotdot.db;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class DotTable {
	public static final String TABLE_NAME = "dot";

	public static class DotColumns implements BaseColumns {
		public static final String X = "x";
		public static final String Y = "y";
	}

	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + DotTable.TABLE_NAME + " (");
		sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
		sb.append(DotColumns.X + " INTEGER, ");
		sb.append(DotColumns.Y + " INTEGER");
		sb.append(");");
		db.execSQL(sb.toString());
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DotTable.TABLE_NAME);
		DotTable.onCreate(db);
	}

}