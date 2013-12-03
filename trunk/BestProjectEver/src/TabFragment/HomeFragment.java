package TabFragment;

import PetHome.PetPanel;
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
//		View v = inflater.inflate(R.layout.frag1, container, false);
		mView = new PetPanel(getActivity());
		return mView;
	}

}
