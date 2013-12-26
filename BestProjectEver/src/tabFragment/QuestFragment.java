package tabFragment;

import textGetter.PetDBox;
import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class QuestFragment extends Fragment {
	View mView;
	PetDBox pet;
	Intent old;
	
	ProgressBar progressCarbohydrate;
	ProgressBar progressProtien;
	ProgressBar progressFat;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.quest_fragment, container,false);
		// TODO Auto-generated method stub
		
		//old = getIntent();
		//pet = PetDataGet.getDataBox(old.getExtras().getInt("index"));
		
		progressCarbohydrate = (ProgressBar) mView.findViewById(R.id.progressCarbohydrate);
		progressProtien = (ProgressBar) mView.findViewById(R.id.progressProtien);
		progressFat = (ProgressBar) mView.findViewById(R.id.progressFat);
		
		
		progressCarbohydrate.setProgress(0);
		progressCarbohydrate.setMax(100);
		progressProtien.setProgress(0);
		progressProtien.setMax(100);
		progressFat.setProgress(0);
		progressFat.setMax(100);
		
		progressCarbohydrate.setProgress(50);
		progressProtien.setProgress(80);
		progressFat.setProgress(20);
		return mView;
	}

}
