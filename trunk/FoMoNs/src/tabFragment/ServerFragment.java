package tabFragment;

import preferenceSetting.PetUniqueDate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.projnsc.bestprojectever.FightListActivity;
import com.projnsc.bestprojectever.R;
import com.projnsc.bestprojectever.TheTempActivity;

import connection.MyServer;
import foodDatabase.FoodBox;
import foodDatabase.FoodDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServerFragment extends Fragment {

	private View mView;
	//Button btnClear;
	private Button btnConnect;
	//Button btnTest;

	private TextView txtPETName;
	private TextView txtWON;
	private TextView txtLOSE;

	private TextView txtHPSet;
	private TextView txtATKSet;
	private TextView txtDEFSet;
	private TextView txtSPDSet;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.server_fragment, container, false);

		//btnClear = (Button) mView.findViewById(R.id.btnClearData);
		btnConnect = (Button) mView.findViewById(R.id.btn_conserver);
		//btnTest = (Button) mView.findViewById(R.id.btn_test);

		txtPETName = (TextView) mView.findViewById(R.id.txtPetNamePK);
		txtWON = (TextView) mView.findViewById(R.id.txtPetWONPK);
		txtLOSE = (TextView) mView.findViewById(R.id.txtPetLOSEPK);

		txtPETName.setText(" : " + PetUniqueDate.getMonName());
		txtWON.setText(" : " + PetUniqueDate.getMonWON());
		txtLOSE.setText(" : " + PetUniqueDate.getMonLOSE());
		txtHPSet = (TextView) mView.findViewById(R.id.txtPetHPSet);
		txtATKSet = (TextView) mView.findViewById(R.id.txtPetATKSet);
		txtDEFSet = (TextView) mView.findViewById(R.id.txtPetDEFSet);
		txtSPDSet = (TextView) mView.findViewById(R.id.txtPetSPDSet);
		txtHPSet.setText(" : " + PetUniqueDate.getMonHP());
		txtATKSet.setText(" : " + PetUniqueDate.getMonATK());
		txtDEFSet.setText(" : " + PetUniqueDate.getMonDEF());
		txtSPDSet.setText(" : " + PetUniqueDate.getMonSPD());

		
		btnConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String result = MyServer.saveToServer();
				if (result != null && result.equalsIgnoreCase("true")) {
					MyServer.UpdataUserWINLOSE();
					
					Intent mNext = new Intent(getActivity(),
							FightListActivity.class);
					startActivity(mNext);
				} else {
					Toast.makeText(getActivity(), "Please connect WIFI",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	
		return mView;
	}

}
