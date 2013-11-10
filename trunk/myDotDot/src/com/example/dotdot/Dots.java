package com.example.dotdot;

import java.util.ArrayList;
import java.util.List;

import com.example.dotdot.db.DotOpenHelper;
import com.example.dotdot.db.DotTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dots {

	private List<Dot> DotList = new ArrayList<Dot>();
	private DotOpenHelper openHelper;
	private SQLiteDatabase db;
	
	public interface OnDotChangeListener{
		void onDotChange(Dots dots);
	}
	
	private OnDotChangeListener onDotChangeListener;
	
	Context mContext;
	
	public Dots(Context context){
		mContext = context;
		openHelper = new DotOpenHelper(mContext);
		db = openHelper.getWritableDatabase();
	}
	
	public void setOnDotChangeListener(OnDotChangeListener onDotChangeListener) {
		this.onDotChangeListener = onDotChangeListener;
	}

	public void addDot(Dot dot){
		DotList.add(dot);
		ContentValues Values = new ContentValues();
		Values.put(DotTable.DotColumns.X, dot.getCorX());
		Values.put(DotTable.DotColumns.Y, dot.getCorY());
		db.insert(DotTable.TABLE_NAME, null, Values);
		NotifyDotChange();
	}
	
	public void delete(int index){
		DotList.remove(index);
		NotifyDotChange();
	}
	
	public void clearDot(){
		DotList.clear();
		db.delete(DotTable.TABLE_NAME, null, null);
		NotifyDotChange();
	}
	
	public int getSize(){
		//return DotList.size();
		//db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
		Cursor cursor = db.query(DotTable.TABLE_NAME, null, null, null, null, null, null);
		int Size = cursor.getCount();
		cursor.close();
		return Size;
	}
	
	public Dot getPosition(int index){
		//return this.DotList.get(index);
		Cursor cursor = db.query(DotTable.TABLE_NAME, null, null, null, null, null, null);
		cursor.moveToPosition(index);
		int corX = cursor.getInt(cursor.getColumnIndex(DotTable.DotColumns.X));
		int corY = cursor.getInt(cursor.getColumnIndex(DotTable.DotColumns.Y));
		return new Dot(corX,corY);
	}
	
	private void NotifyDotChange(){
		if(this.onDotChangeListener!=null){
			this.onDotChangeListener.onDotChange(this);
		}
	}

	public void edit(int position, int corX, int corY) {
		Dot dump = DotList.get(position);
		dump.setCorX(corX);
		dump.setCorY(corY);
		NotifyDotChange();
	}
	
}
