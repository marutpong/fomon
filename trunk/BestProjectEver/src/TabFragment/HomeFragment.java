package tabFragment;

import com.projnsc.bestprojectever.MonEatingPhotoActivity;
import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
	
	View mView;
	Button btnTakePhoto;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.pet_home_fragment, container, false);
//		mView = new PetPanel(getActivity());
		
		btnTakePhoto = (Button) mView.findViewById(R.id.btnTakePhoto);
		btnTakePhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent tIntent = new Intent(getActivity(), MonEatingPhotoActivity.class);
				startActivity(tIntent);
			}
		});
		
		return mView;
	}

}
