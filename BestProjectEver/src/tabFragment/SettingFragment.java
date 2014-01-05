package tabFragment;

import preferenceSetting.PetUniqueDate;
import preferenceSetting.PrefDataType;
import textGetter.PetDataGet;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.projnsc.bestprojectever.PetEvolutionActivity;
import com.projnsc.bestprojectever.R;
import com.projnsc.bestprojectever.SelectPetFirst;
import com.projnsc.bestprojectever.ShowPetVersusResultActivity;
import com.projnsc.bestprojectever.ShowStatResultActivity;

public class SettingFragment extends Fragment {

	View mView;
	Button btnRename;
	Button btnClear;
	TextView txtPetNameSet;
	TextView txtHPSet;
	TextView txtATKSet;
	TextView txtDEFSet;
	TextView txtSPDSet;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.setting_fragment, container, false);

		btnRename = (Button) mView.findViewById(R.id.btnChangePetNameSet);
		btnClear = (Button) mView.findViewById(R.id.btnClearData);
		txtPetNameSet = (TextView) mView.findViewById(R.id.txtPetNameSet);
		txtHPSet = (TextView) mView.findViewById(R.id.txtPetHPSet);
		txtATKSet = (TextView) mView.findViewById(R.id.txtPetATKSet);
		txtDEFSet = (TextView) mView.findViewById(R.id.txtPetDEFSet);
		txtSPDSet = (TextView) mView.findViewById(R.id.txtPetSPDSet);

		txtPetNameSet.setText(" : " + PetUniqueDate.getMonName());
		txtHPSet.setText(" : " + PetUniqueDate.getMonHP());
		txtATKSet.setText(" : " + PetUniqueDate.getMonATK());
		txtDEFSet.setText(" : " + PetUniqueDate.getMonDEF());
		txtSPDSet.setText(" : " + PetUniqueDate.getMonSPD());

		btnClear.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				askForClearData();
			}
		});

		btnRename.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				showRenameDialog();
				addRandomText();
				
			}
		});

		return mView;
	}

	protected void addRandomText() {
//		Intent A = new Intent(getActivity(),ShowStatResultActivity.class);
//		Intent A = new Intent(getActivity(),ShowPetVersusResultActivity.class);
		Intent A = new Intent(getActivity(),PetEvolutionActivity.class);
		startActivity(A);
//		PetDataGet.setContext(getActivity());
//		PetDataGet.Write("AAAA,1.2,1.3,RICE,4,5,6,7,11,12,2013,23,07");
	}

	protected void askForClearData() {
		new AlertDialog.Builder(getActivity())
				.setTitle("Warning!")
				.setMessage(
						"All data will lose, You have to replay the game. Do you want to continue?")
				.setPositiveButton("Yes", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						clearALLdataANDReGame();
					}
				}).setNegativeButton("No", null).show();
	}

	protected void clearALLdataANDReGame() {
		PetDataGet.setContext(getActivity());
		PetDataGet.Clear();
		PetUniqueDate.SetMonName(PrefDataType.NONE);
		PetUniqueDate.SetMonTypeID(PrefDataType.NONEINT);
		Intent next = new Intent(getActivity(), SelectPetFirst.class);
		getActivity().finish();
		startActivity(next);
	}

	protected void showRenameDialog() {

		final View view = getActivity().getLayoutInflater().inflate(
				R.layout.input_textfield, null);
		final EditText nameInput = (EditText) view
				.findViewById(R.id.intxtPetName);

		new AlertDialog.Builder(getActivity()).setTitle("Change Pet Name")
				.setView(view).setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.i("THIS", nameInput.getText().toString());
						if (nameInput.getText().toString().length() > 0) {
							success(nameInput.getText().toString());
						} else {
							fail();
						}
					}
				}).setNegativeButton("Cancel", null).show();

	}

	protected void fail() {
		new AlertDialog.Builder(getActivity()).setTitle("Warning!")
				.setMessage("You can't leave your pet name as Blank")
				.setNegativeButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						showRenameDialog();
					}
				}).show();
	}

	protected void success(String nameChange) {
		PetUniqueDate.SetMonName(nameChange);
		txtPetNameSet.setText(PetUniqueDate.getMonName());
	}

}
