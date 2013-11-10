package com.example.ox;

import com.example.ox.ModelOX.OnOXChangeListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OXActivity extends Activity implements OnOXChangeListener {

	private ModelOX modelOX;
	TextView[] OXText;
	Button Clear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ox);
		modelOX = new ModelOX();
		modelOX.setOnOXChangeListener(this);

		OXText = new TextView[9];
		OXText[0] = (TextView) findViewById(R.id.txtOX1);
		OXText[1] = (TextView) findViewById(R.id.txtOX2);
		OXText[2] = (TextView) findViewById(R.id.txtOX3);
		OXText[3] = (TextView) findViewById(R.id.txtOX4);
		OXText[4] = (TextView) findViewById(R.id.txtOX5);
		OXText[5] = (TextView) findViewById(R.id.txtOX6);
		OXText[6] = (TextView) findViewById(R.id.txtOX7);
		OXText[7] = (TextView) findViewById(R.id.txtOX8);
		OXText[8] = (TextView) findViewById(R.id.txtOX9);
		Clear = (Button) findViewById(R.id.button1);

		Clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				modelOX.clear();
			}
		});

		for (int i = 0; i < 9; i++) {
			final int j = i;
			OXText[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					modelOX.setOXTable(j);
				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ox, menu);
		return true;
	}

	@Override
	public void OnOXChange(ModelOX modelOX) {
		// TODO Auto-generated method stub
		int[] Temp = modelOX.getOXTable();
		for (int i = 0; i < 9; i++) {
			OXText[i].setText((Temp[i] == 1) ? "O" : ((Temp[i] == -1) ? "X"
					: ""));
		}
		CheckWinner();
		CheckFull();
	}

	private void CheckFull() {
		int chkfull =0;
		int[] Temp = modelOX.getOXTable();
		for(int i=0; i<9; i++){
			if(Temp[i]==0||Temp[i]==' ')
				break;
			else
				chkfull++;
		}
		if(chkfull==9)
			Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
	}

	private int CheckWinner() {
		int[] Temp = modelOX.getOXTable();
		int temp = 0;
		for(int i=0; i<3; i++){
			temp = 0;
			for(int j=0; j<3; j++){
				temp += Temp[i*3+j];
			}
			if(temp==3||temp==-3){
				WinnerIs(modelOX.getRealPlayer());
				return 0;
			}
		}// End Check Row Winner
		for(int i=0; i<3; i++){
			temp = 0;
			for(int j=0; j<3; j++){
				temp += Temp[i+j*3];
			}
			if(temp==3||temp==-3){
				WinnerIs(modelOX.getRealPlayer());
				return 0;
			}
		}// End Check Column Winner
		temp = 0;
		temp+=Temp[0]+Temp[4]+Temp[8];
		if(temp==3||temp==-3){
			WinnerIs(modelOX.getRealPlayer());
			return 0;
		}
		temp = 0;
		temp+=Temp[2]+Temp[4]+Temp[6];
		if(temp==3||temp==-3){
			WinnerIs(modelOX.getRealPlayer());
			return 0;
		}
		
		return 0;
	}

	private void WinnerIs(char realPlayer) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Winner is " + realPlayer, Toast.LENGTH_SHORT)
				.show();
		modelOX.setDisable();
	}

}
