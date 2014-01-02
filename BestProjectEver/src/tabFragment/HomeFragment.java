package tabFragment;

import petHome.PetPanel;
import petHome.PetPanel.OnPetTouchListener;
import preferenceSetting.PetUniqueDate;

import com.projnsc.bestprojectever.MonEatingPhotoActivity;
import com.projnsc.bestprojectever.R;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment implements OnPetTouchListener {
	
	View mView;
	Button btnTakePhoto;
	PetPanel petPanel;
	TextView txtPetName;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.pet_home_fragment, container, false);
//		mView = new PetPanel(getActivity());
		
		txtPetName = (TextView) mView.findViewById(R.id.PetNameLabel);
		txtPetName.setText(PetUniqueDate.getMonName());
		petPanel = (PetPanel) mView.findViewById(R.id.petPanel1);
		petPanel.setOnPetTouchListener(this);
		
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

	@Override
	public void OnPetTouch() {
		View view = getActivity().getLayoutInflater().inflate(R.layout.pet_status_show, null);
		
		final TextView txtPetName = (TextView) view.findViewById(R.id.txtPetNameST);
		final TextView txtPetHP = (TextView) view.findViewById(R.id.txtPetHPST);
		final TextView txtPetATK = (TextView) view.findViewById(R.id.txtPetATKST);
		final TextView txtPetDEF = (TextView) view.findViewById(R.id.txtPetDEFST);
		final TextView txtPetSPD = (TextView) view.findViewById(R.id.txtPetSPDST);
		final TextView txtPetBD = (TextView) view.findViewById(R.id.txtPetBirthday);
		
		txtPetName.setText(" : " + PetUniqueDate.getMonName());
		txtPetHP.setText(" : " + PetUniqueDate.getMonHP());
		txtPetATK.setText(" : " + PetUniqueDate.getMonATK());
		txtPetDEF.setText(" : " + PetUniqueDate.getMonDEF());
		txtPetSPD.setText(" : " + PetUniqueDate.getMonSPD());
		txtPetBD.setText("Birthday : " + PetUniqueDate.getMonsterBirthday());
		
		new AlertDialog.Builder(getActivity()).setTitle("Pet Status")
		.setView(view)
		.setPositiveButton("OK", null)
		.show();
		
	}

}
