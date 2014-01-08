package com.projnsc.bestprojectever;

import historyDatabase.HistoryType;
import petSelection.SelectMonPanel;
import petSelection.SelectMonPanel.OnCharacterTouchListener;
import preferenceSetting.PetUniqueDate;
import preferenceSetting.PrefDataType;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class SelectPetFirst extends Activity implements
		OnCharacterTouchListener {

	private int MonSelectedID = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PetUniqueDate.setContext(this);

		if (PetUniqueDate.getMonName() == PrefDataType.NONE) {
			SelectMonPanel A = new SelectMonPanel(this);
			A.setOnCharacterTouchListener(this);
			setContentView(A);
		} else {
			goToHomePetActivity();
		}
	}

	private void goToHomePetActivity() {
		Intent mNext = new Intent(getApplicationContext(),
				MainPaggerNew.class);
		finish();
		startActivity(mNext);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pet_main, menu);
		return true;
	}

	@Override
	public void CharacterTouched(int ID) {
		MonSelectedID = ID;
		String name = (ID == 1 ? "Moragon" : "Mini-Knight");

		new AlertDialog.Builder(this).setTitle("Confirm Monster Selection")
				.setMessage("Do you want to select " + name)
				.setNegativeButton("Cancel", null)
				.setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setMonName();
					}
				}).show();

	}

	private void setMonName() {

		final View view = getLayoutInflater().inflate(R.layout.input_textfield,
				null);
		final EditText nameInput = (EditText) view
				.findViewById(R.id.intxtPetName);

		new AlertDialog.Builder(this).setTitle("Input your new pet name")
				.setView(view).setNegativeButton("Cancel", null)
				.setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.i("THIS", nameInput.getText().toString());
						if (nameInput.getText().toString().length() > 0) {
							success(nameInput.getText().toString());
						} else {
							fail();
						}
					}
				}).show();
	}

	protected void fail() {

		new AlertDialog.Builder(this).setTitle("Warning!")
				.setMessage("You can't leave your pet name as Blank")
				.setNegativeButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setMonName();
					}
				}).show();

	}

	protected void success(final String name) {

		new AlertDialog.Builder(this).setTitle("Success")
				.setMessage("Your new Pet name is " + name)
				.setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setFirstMonStatus(name);
						goToHomePetActivity();
					}
				}).show();

	}

	protected void setFirstMonStatus(String name) {
		PetUniqueDate.SetMonName(name);
		PetUniqueDate.SetMonTypeID(MonSelectedID);
		PetUniqueDate.SetMonHP(20);
		PetUniqueDate.SetMonATK(10);
		PetUniqueDate.SetMonDEF(5);
		PetUniqueDate.SetMonSPD(3);
		String android_id = Secure.getString(
    			getContentResolver(),
	            Secure.ANDROID_ID);
		PetUniqueDate.SetFacebookID(android_id);
		PetUniqueDate.SetMonsterBirthDay(HistoryType.getCurrentDate());
		resetPrefData();
	}
	
	private void resetPrefData(){
		PetUniqueDate.SetK_KNN(2);
		//Set La & Long for put data into SQLLite. It's unusual.
		PetUniqueDate.SetLatitude(0);
		PetUniqueDate.SetLongtitude(0);
		PetUniqueDate.SetMonWON(0);
		PetUniqueDate.SetMonLOSE(0);
	}

}
