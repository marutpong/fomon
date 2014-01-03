package tabFragment;

import preferenceSetting.PetUniqueDate;
import preferenceSetting.PrefDataType;
import textGetter.PetDataGet;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings.Secure;
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
import com.projnsc.bestprojectever.MainPaggerNew;
import com.projnsc.bestprojectever.R;
import com.projnsc.bestprojectever.SelectPetFirst;

import connection.MyServer;

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
	TextView txtMessage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.server_fragment, container, false);

		btnClear = (Button) mView.findViewById(R.id.btnClearData);
		btnConnect = (Button) mView.findViewById(R.id.btn_conserver);
		txtMessage = (TextView) mView.findViewById(R.id.txtPetNameSet);

		txtMessage.setText(" : " + PetUniqueDate.getMonName());
	
		btnClear.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
		        // Permission StrictMode
		        if (android.os.Build.VERSION.SDK_INT > 9) {
		            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		            StrictMode.setThreadPolicy(policy);
		        }
				Log.i("TEST","Click");
				HttpURLConnection conn = null;
            	try {
            		String url = MyServer.server_url;
					conn = (HttpURLConnection)new URL(url).openConnection();
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
                    }
                    else {
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
				if (result.equalsIgnoreCase("true")){
					Intent mNext = new Intent(getActivity(),FightListActivity.class);
					startActivity(mNext);
				} else {
					Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
				}
			}
		});
		
		return mView;
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


}
