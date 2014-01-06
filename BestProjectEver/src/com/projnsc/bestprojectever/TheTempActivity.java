package com.projnsc.bestprojectever;

import com.projnsc.bestprojectever.R;

import foodDatabase.FoodDatabase;
import java.util.ArrayList;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TheTempActivity extends Activity {

	private int result = -1;
	AlertDialog selectDialog;
	AlertDialog foodListDialog = null;

	private ListView foodListView;
	private ArrayAdapter<String> listAdapter;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thetemp_activity);

		final Button btn_select = (Button) findViewById(R.id.btn_select);
		btn_select.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		this.showSelectDialog();

	}

	public void showSelectDialog() {

		View view = this.getLayoutInflater().inflate(R.layout.thetemp_3button,
				null);

		final Button btnClass1 = (Button) view.findViewById(R.id.btn_c1);
		final Button btnClass2 = (Button) view.findViewById(R.id.btn_c2);
		final Button btnOther = (Button) view.findViewById(R.id.btn_other);

		btnClass1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				askConfirmSelect(1);
				// finish();
			}
		});

		btnClass2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				askConfirmSelect(2);
				// finish();
			}
		});
		btnOther.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectOther();
			}
		});

		selectDialog = new AlertDialog.Builder(this).setTitle("Select Class")
				.setView(view)
				// .setPositiveButton("OK", null)
				.show();
	}

	protected void selectOther() {
		View foodview = this.getLayoutInflater().inflate(R.layout.thetemp_list,
				null);

		foodListView = (ListView) foodview.findViewById(R.id.foodlist);
		ArrayList<String> planetList = FoodDatabase.getArrayOfFoodname();
		// planetList.addAll( Arrays.asList(planets) );

		// Create ArrayAdapter using the planet list.
		listAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, planetList);
				 
		// Set the ArrayAdapter as the ListView's adapter.
		foodListView.setAdapter(listAdapter);

		// ListView Item Click Listener
		foodListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ListView Clicked item index
				int itemPosition = position;
				// ListView Clicked item value
				// String itemValue = (String)
				// foodListView.getItemAtPosition(position);
				askConfirmSelect(itemPosition);
				// Show Alert
			}

		});

		foodListDialog = new AlertDialog.Builder(this).setTitle("Select Class")
				.setView(foodview).show();
	}

	protected void askConfirmSelect(final int theInt) {
		new AlertDialog.Builder(this)
				.setTitle("Are you sure?")
				.setMessage("You seleted " + theInt)
				.setPositiveButton("Yes",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								result = theInt;
								selectDialog.dismiss();
								if (foodListDialog != null) {
									foodListDialog.dismiss();
								}
							}
						}).setNegativeButton("No", null).show();
	}

}
