package connection;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import preferenceSetting.PetUniqueDate;
import android.content.Context;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

import com.projnsc.bestprojectever.R;
import com.projnsc.bestprojectever.SelectPetFirst;

public class MyServer// extends Application
{
	private static Context mContext;
	private static MyServer mServer = null;

	// public static String server_ip = "10.10.188.72";
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	public MyServer(Context context) {
		setContext(context);
	}

	public Context getmContext() {
		return mContext;
	}

	public static void setContext(Context mContext) {
		MyServer.mContext = mContext;
	}

	/*
	 * public static String sever_url() { //String server_ip =
	 * Resources.getSystem
	 * ().getString(R.string.server_ip);//mContext.getString(R
	 * .string.server_ip);
	 * //mContext.getResources().getString(R.string.server_ip); //String get_ip
	 * = MyServer.getContext().getResources().getString(R.string.server_ip);
	 * String url = "http://"+server_ip+"/fomon/"; return url; }
	 */
	public static String saveToServer() {
		String strResult = null;
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		HttpURLConnection conn = null;

		String url = PetUniqueDate.getServerURL() + "enter.php";

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uid", PetUniqueDate.getFacebookID()
				.toString()));
		params.add(new BasicNameValuePair("name", PetUniqueDate.getMonName()
				.toString()));
		params.add(new BasicNameValuePair("type", String.valueOf(PetUniqueDate
				.getMonTypeID())));
		params.add(new BasicNameValuePair("hp", String.valueOf(PetUniqueDate
				.getMonHP())));
		params.add(new BasicNameValuePair("atk", String.valueOf(PetUniqueDate
				.getMonATK())));
		params.add(new BasicNameValuePair("def", String.valueOf(PetUniqueDate
				.getMonDEF())));
		params.add(new BasicNameValuePair("spd", String.valueOf(PetUniqueDate
				.getMonSPD())));
		strResult = getHttpPost(url, params);

		return strResult;
	}

	public static String getHttpPost(String url, List<NameValuePair> params) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Status OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download result..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	public static void IncreaseWIN() {
		
	}

	public static void IncreaseLOSE() {
		
	}

	public static int GetWIN(){
		return 0;
	}
	
	public static int GetLOSE(){
		return 0;
	}
	
	public static void IncreaseRANK() {
		
	}
}