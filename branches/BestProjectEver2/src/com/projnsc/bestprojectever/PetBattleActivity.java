package com.projnsc.bestprojectever;

import petBattle.PetBattlePanel;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class PetBattleActivity extends Activity {

	public static final String PetLeftHPUpdate = "PETLeftHPUpdate";
	public static final String PetRightHPUpdate = "PETRightHPUpdate";
	private final int LeftTextBox = R.id.txtBattleMonLHP;
	private final int RightTextBox = R.id.txtBattleMonRHP;

	private Handler msgHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int UpdatePos;
			Bundle bundle = msg.getData();
			String string = bundle.getString(PetLeftHPUpdate);
			if (string == null)
				string = bundle.getString(PetRightHPUpdate);
			UpdatePos = (string.equalsIgnoreCase(PetLeftHPUpdate)) ? LeftTextBox
					: RightTextBox;
			TextView text = (TextView) findViewById(UpdatePos);
			text.setText(string);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.pet_battle_activity);
		
		PetBattlePanel mPanel = (PetBattlePanel) findViewById(R.id.petBattlePanel1);
		mPanel.setHandler(msgHandler);
		
		super.onCreate(savedInstanceState);
	}

}
