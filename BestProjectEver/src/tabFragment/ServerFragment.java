package tabFragment;

import preferenceSetting.PetUniqueDate;

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

	View mView;
	Button btnClear;
	Button btnConnect;
	Button btnTest;

	TextView txtMessage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.server_fragment, container, false);

		btnClear = (Button) mView.findViewById(R.id.btnClearData);
		btnConnect = (Button) mView.findViewById(R.id.btn_conserver);
		btnTest = (Button) mView.findViewById(R.id.btn_test);

		txtMessage = (TextView) mView.findViewById(R.id.txtPetNameSet);

		txtMessage.setText(" : " + PetUniqueDate.getMonName());

		btnClear.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * String arrData[] = FoodDatabase.fb.SelectData(1); if(arrData
				 * == null) { Toast.makeText(getActivity(),"Not found Data!",
				 * Toast.LENGTH_LONG).show(); } else {
				 * Toast.makeText(getActivity(),"ID = " + arrData[0] + "," +
				 * arrData[1] + "," + arrData[2], Toast.LENGTH_LONG).show(); }
				 */
				FoodBox fBox = FoodDatabase.fd.getFoodByName("ÊéÁµÓ");
				if (fBox == null) {
					Toast.makeText(getActivity(), "Not found Data!",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(
							getActivity(),
							"ID = " + fBox.getID() + "," + fBox.getName() + ","
									+ fBox.getCalcium(), Toast.LENGTH_LONG)
							.show();
				}

				// Permission StrictMode
				if (android.os.Build.VERSION.SDK_INT > 9) {
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
							.permitAll().build();
					StrictMode.setThreadPolicy(policy);
				}
				Log.i("TEST", "Click");
				HttpURLConnection conn = null;
				try {
					String url = PetUniqueDate.getServerURL();
					/*Toast.makeText(getActivity(), url, Toast.LENGTH_LONG)
							.show();*/
					conn = (HttpURLConnection) new URL(url).openConnection();
					conn.setRequestMethod("GET");
					conn.setInstanceFollowRedirects(true);
					conn.setUseCaches(false);

					int resultServer = conn.getResponseCode();
					if (resultServer == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						ByteArrayOutputStream bos = new ByteArrayOutputStream();

						int read = 0;
						while ((read = is.read()) != -1) {
							bos.write(read);
						}
						byte[] result = bos.toByteArray();
						bos.close();

						String data = new String(result);

						txtMessage.setText(data);
					} else {
						txtMessage.setText("Error Code : " + resultServer);
					}

					conn.disconnect();

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String result = MyServer.saveToServer();
				if (result.equalsIgnoreCase("true")) {
					Intent mNext = new Intent(getActivity(),
							FightListActivity.class);
					startActivity(mNext);
				} else {
					Toast.makeText(getActivity(), "Please connect WIFI.",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		
		btnTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent inend = new Intent(getActivity(), TheTempActivity.class);
				startActivity(inend);
			}
		});
		return mView;
	}

}
