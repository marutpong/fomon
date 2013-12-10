package tabFragment;

import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {
	
	View mView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.pet_home_fragment, container, false);
//		mView = new PetPanel(getActivity());
		return mView;
	}

}
