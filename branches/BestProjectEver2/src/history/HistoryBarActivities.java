package history;

import textGetter.PetDBox;
import textGetter.PetDataGet;
import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;

public class HistoryBarActivities extends Activity {
	PetDBox pet;
	Intent old;
	
	ProgressBar progressCarbohydrate;
	ProgressBar progressProtien;
	ProgressBar progressFat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history_bar);
		
		old = getIntent();
		pet = PetDataGet.getDataBox(old.getExtras().getInt("index"));
		
		progressCarbohydrate = (ProgressBar) findViewById(R.id.progressATK);
		progressProtien = (ProgressBar) findViewById(R.id.progressHP);
		progressFat = (ProgressBar) findViewById(R.id.atkValue);
		
		
		progressCarbohydrate.setProgress(0);
		progressCarbohydrate.setMax(100);
		progressProtien.setProgress(0);
		progressProtien.setMax(100);
		progressFat.setProgress(0);
		progressFat.setMax(100);
		
		progressCarbohydrate.setProgress(50);
		progressProtien.setProgress(80);
		progressFat.setProgress(20);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bar_activities, menu);
		return true;
	}

}
