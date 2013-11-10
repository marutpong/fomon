package com.example.dotdot.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DotOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dot.db";
    private static final int DATABASE_VERSION = 1;

    public DotOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DotTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DotTable.onUpgrade(db, oldVersion, newVersion);
    }


}