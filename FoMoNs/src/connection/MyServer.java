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
import org.apache.http.client.methods.HttpGet;
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
	public static boolean isTodayFight(String vsMonID) {
		if (!isConnectServer()){
			return false;
		}
		String url = PetUniqueDate.getServerURL() + "is_today_fight.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mon", PetUniqueDate.getFacebookID() ));
		params.add(new BasicNameValuePair("vsmon", vsMonID.toString()));
		String strResult = getHttpPost(url, params);
		return strResult.equalsIgnoreCase("true");
	}
	public static String addFightHistory(String vsMonID, boolean isWin) {
		Log.i("My server", "Add result");
		String strResult = null;
		String url = PetUniqueDate.getServerURL() + "fight.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mon", PetUniqueDate.getFacebookID() ));
		params.add(new BasicNameValuePair("vsmon", vsMonID.toString()));
		params.add(new BasicNameValuePair("iswin", String.valueOf(isWin)));
		strResult = getHttpPost(url, params);
		Log.i("VS Mode", "My ID :"+PetUniqueDate.getFacebookID() ); 
		Log.i("VS Mode", "Vs Mon :"+vsMonID.toString());
		Log.i("VS Mode", "iswin :"+String.valueOf(isWin));
		return strResult;
	}
	public static String saveToServer() {
		String strResult = null;

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uid", PetUniqueDate.getFacebookID()
				.toString()));
		params.add(new BasicNameValuePair("uname", PetUniqueDate.getMonName()
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
		params.add(new BasicNameValuePair("win", String.valueOf(PetUniqueDate
				.getMonWON())));
		params.add(new BasicNameValuePair("lose", String.valueOf(PetUniqueDate
				.getMonLOSE())));
		Log.i("MyServer",PetUniqueDate.getMonName()+" "+PetUniqueDate.getMonTypeID());
		
		if (!isConnectServer()){
			return strResult;
		}

		String url = PetUniqueDate.getServerURL() + "enter.php";
		strResult = getHttpPost(url, params);

		return strResult;
	}
	
	public static boolean isConnectServer() {
		String url = PetUniqueDate.getServerURL();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("uid", PetUniqueDate.getFacebookID()
				.toString()));
		String testFirstServer = getHttpPost(url, params);
		if (testFirstServer == null) {
			String secondUrl = "http://myweb.cmu.ac.th/marutpong_c/fomon/getserverip.php";
			String newServerIP = getHttpPost(secondUrl, params);
			if (newServerIP!=null)
				PetUniqueDate.SetServerIP(newServerIP);
		}
		return true;
	}
	public static String getHttpGet(String url) {
		Log.i("getHttpGet", url);
		// Permission StrictMode
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		InputStream inputStream = null;
		String result = "";
		try {

			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert inputstream to string
			if (inputStream != null)
				result = inputStream.toString();
			else
				Log.i("getHttpGet", url + " Fail");
				result = "Did not work!";
				return null;

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		Log.i("getHttpGet result", result);
		return result;
	}

	public static String getHttpPost(String url, List<NameValuePair> params) {
		Log.i("getHttpPost", url);
		if (PetUniqueDate.isServerIPEmpty()){
			return null;
		}
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost =  new HttpPost(url);

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
				return null;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Log.i("getHttpPost", "result:"+str.toString());
		return str.toString();
	}


	public static int GetWIN() {
		int result = -1;
		String url = PetUniqueDate.getServerURL() + "getWinLost.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mon", PetUniqueDate.getFacebookID() ));
		params.add(new BasicNameValuePair("type", "win"));
		String strResult = getHttpPost(url, params);
		try {
			result = Integer.parseInt(strResult);
		} catch (Exception e){
			return -1;
		}
		return result;
	}

	public static int GetLOSE() {
		int result = -1;
		String url = PetUniqueDate.getServerURL() + "getWinLost.php";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("mon", PetUniqueDate.getFacebookID() ));
		params.add(new BasicNameValuePair("type", "lose"));
		String strResult = getHttpPost(url, params);
		try {
			result = Integer.parseInt(strResult);
		} catch (Exception e){
			return -1;
		}		return result;
	}

	public static void UpdataUserWINLOSE() {
		int upWON = PetUniqueDate.getMonWON();
		int upLOSE = PetUniqueDate.getMonLOSE();
		
		//UPDATE HERE
		
	}

}