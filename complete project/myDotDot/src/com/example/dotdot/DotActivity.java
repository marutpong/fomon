package com.example.dotdot;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
//import android.widget.Toast;

import java.util.Random;

import com.example.dotdot.Dots.OnDotChangeListener;
import com.example.dotdot.DotsView.DotsTouchListener;
import com.example.dotdot.DotsView.DotsViewDataSource;

public class DotActivity extends Activity implements OnDotChangeListener, OnItemClickListener, DotsTouchListener {

	private static final int MENU_CLEAR_ITEM = 1001;
	private static final int MENU_DELETE_ITEM = 1002;
	private static final int MENU_EDIT_ITEM = 1003;
	//private static final Const CONST = new Const();
	private Random RandomGen = new Random();
	private Dots mDots;
	private ListView mListView;
	private DotsListAdapter mAdapter;
	private DotsView mDotsView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dot);
		mDots = new Dots(this);
		mDots.setOnDotChangeListener(this);
		mListView = (ListView) findViewById(R.id.listView1);
		mAdapter = new DotsListAdapter(this) {
			
			@Override
			public Object getItem(int position) {
				return mDots.getPosition(position);
			}
			
			@Override
			public int getCount() {
				return mDots.getSize();
			}
		};
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		registerForContextMenu(mListView);
		mDotsView = (DotsView) findViewById(R.id.dotsView1);
		mDotsView.setDataSource(new DotsViewDataSource() {
			
			@Override
			public Dot getItem(int Position) {
				// TODO Auto-generated method stub
				return mDots.getPosition(Position);
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mDots.getSize();
			}
		});
		mDotsView.setOnDotsTouchListener(this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case MENU_CLEAR_ITEM: 
			mDots.clearDot();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo mInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId()){
		case MENU_DELETE_ITEM:
			confirmDelete(mInfo.position);
			return true;
		case MENU_EDIT_ITEM:
			EditBox(mInfo.position);
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	private void confirmDelete(final int position) {
		
		new AlertDialog.Builder(this).setTitle("Confirm Delete?")
		.setMessage("Are you sure to delete?")
		.setPositiveButton("Yes", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				mDots.delete(position);	
			}
		})
		.setNegativeButton("No", null).show();
	}

	private void EditBox(final int position){
		View view = getLayoutInflater().inflate(R.layout.editdialog, null);
		final EditText nullcoorX = (EditText) view.findViewById(R.id.editX);
		final EditText nullcoorY = (EditText) view.findViewById(R.id.editY);
		nullcoorX.setText(mDots.getPosition(position).getCorX() + "");
		nullcoorY.setText(mDots.getPosition(position).getCorY() + "");
		new AlertDialog.Builder(this).setTitle("Confirm Edit")
		.setView(view)
		.setPositiveButton("Yes", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int corX = Integer.parseInt(nullcoorX.getText().toString());
				int corY = Integer.parseInt(nullcoorY.getText().toString());
				mDots.edit(position,corX,corY);
			}
		})
		.setNegativeButton("No", null).show();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE,MENU_EDIT_ITEM, Menu.NONE, R.string.edit);
		menu.add(Menu.NONE, MENU_DELETE_ITEM, Menu.NONE, R.string.delete);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_CLEAR_ITEM, Menu.NONE, getString(R.string.clear));
		return true;
	}
	
	public void Random(View view){
		int corX = RandomGen.nextInt(mDotsView.getWidth());
		int corY = RandomGen.nextInt(mDotsView.getHeight());
	//	Toast.makeText(this,corX + " " + corY, 0).show();
		Dot dot = new Dot(corX, corY);
		mDots.addDot(dot);
	}
	
	public void Clear(View view){
		mDots.clearDot();
	}

	@Override
	public void onDotChange(Dots dots) {
		// TODO Auto-generated method stub
		mAdapter.notifyDataSetChanged();
		mDotsView.invalidate();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		// start new activity
		Intent intend = new Intent(this, SecondActivity.class);
		intend.putExtra(Const.CONSTX, mDots.getPosition(position).getCorX());
		intend.putExtra(Const.CONSTY, mDots.getPosition(position).getCorY());
		//startActivity(intend);
		startActivityForResult(intend, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0 && resultCode==0){
			Toast.makeText(this,data.getExtras().getInt(Const.CONSTX) + " " + data.getExtras().getInt(Const.CONSTY), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDotsTouch(DotsView dotsView, float coorX, float coorY) {
		mDots.addDot(new Dot((int) coorX,(int) coorY));
	}
	
}
