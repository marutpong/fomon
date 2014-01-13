package com.projnsc.bestprojectever;

import com.projnsc.bestprojectever.R;

import foodDatabase.FoodDatabase;
import java.util.ArrayList;

import petShowEmotion.PetShowEmotionPanel;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TheTempActivity extends Activity {

	// private int result = -1;
	AlertDialog selectDialog;
	AlertDialog foodListDialog = null;
	private int ClassFood1;
	private int ClassFood2;

	private ListView foodListView;
	private ArrayAdapter<String> listAdapter;
	private String NameFood1;
	private String NameFood2;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.thetemp_activity);

		ClassFood1 = getIntent().getIntExtra(
				getString(R.string.intentkey_analysisfoodclass1), -1);
		ClassFood2 = getIntent().getIntExtra(
				getString(R.string.intentkey_analysisfoodclass2), -1);

		if (ClassFood1 == 0)
			ClassFood1 = -1;
		if (ClassFood2 == 0)
			ClassFood2 = -1;

		if (ClassFood1 == ClassFood2 && ClassFood1 != -1) {
			// goToStatUpResult(ClassFood1);
			this.showSelectDialog();
			askConfirmSelect(ClassFood1);

		} // else {
		NameFood1 = FoodDatabase.getFoodByID(ClassFood1).getName();
		NameFood2 = FoodDatabase.getFoodByID(ClassFood2).getName();
		// }

		final Button btn_select = (Button) findViewById(R.id.btn_select);
		btn_select.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		if(ClassFood1 != ClassFood2)
			this.showSelectDialog();

	}

	private void goToStatUpResult(int classFood) {
		Intent A = new Intent(this, ShowStatResultActivity.class);
		A.putExtra(getString(R.string.intentkey_setfoodclass), classFood);
		A.putExtra(
				getString(R.string.intentkey_pathforfood),
				getIntent().getExtras().getString(
						getString(R.string.intentkey_pathforfood)));
		finish();
		startActivity(A);
	}

	public void showSelectDialog() {

		View view = this.getLayoutInflater().inflate(R.layout.thetemp_3button,
				null);

		final Button btnClass1 = (Button) view.findViewById(R.id.btn_c1);
		final Button btnClass2 = (Button) view.findViewById(R.id.btn_c2);
		final Button btnOther = (Button) view.findViewById(R.id.btn_other);
		final PetShowEmotionPanel ePanel = (PetShowEmotionPanel) view
				.findViewById(R.id.petShowEmotionPanelEx);
		ePanel.setEmoKey("QUESTION");
		btnClass1.setText(NameFood1);
		btnClass2.setText(NameFood2);

		btnClass1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				askConfirmSelect(ClassFood1);
				// finish();
			}
		});

		btnClass2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				askConfirmSelect(ClassFood2);
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
				.setView(view).setCancelable(false)
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
				askConfirmSelect(itemPosition + 1);
				// Show Alert
			}

		});

		foodListDialog = new AlertDialog.Builder(this).setTitle("Select Class")
				.setView(foodview).show();
	}

	protected void askConfirmSelect(final int theInt) {
		String FoodName = FoodDatabase.getFoodByID(theInt).getName();
		new AlertDialog.Builder(this)
				.setTitle("Are you sure?")
				.setMessage("You seleted " + FoodName)
				.setPositiveButton("Yes",
						new android.content.DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								// result = theInt;
								selectDialog.dismiss();
								goToStatUpResult(theInt);
								if (foodListDialog != null) {
									foodListDialog.dismiss();
								}
							}
						}).setNegativeButton("No", null).show();
	}

}
