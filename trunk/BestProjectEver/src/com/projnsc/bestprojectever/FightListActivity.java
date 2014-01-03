package com.projnsc.bestprojectever;

import com.projnsc.bestprojectever.R;

import connection.MyServer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FightListActivity extends Activity {
	
    
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight_list);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        SearchData();
        final Button btn1 = (Button) findViewById(R.id.button1);
        final Button btn_select = (Button) findViewById(R.id.btn_select);
        // Perform action on click
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	SearchData();
            }
        });
        
        btn_select.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
    }
    
    public void SearchData()
    {
    	 // listView1
        final ListView lisView1 = (ListView)findViewById(R.id.listView1); 	
        
   	 	// editText1
        final EditText inputText = (EditText)findViewById(R.id.editText1); 
        
		/**
		 * [{"CustomerID":"C001","Name":"Win Weerachai","Email":"win.weerachai@thaicreate.com" ,"CountryCode":"TH","Budget":"1000000","Used":"600000"},
		 * {"CustomerID":"C002","Name":"John Smith","Email":"john.smith@thaicreate.com" ,"CountryCode":"EN","Budget":"2000000","Used":"800000"},
		 * {"CustomerID":"C003","Name":"Jame Born","Email":"jame.born@thaicreate.com" ,"CountryCode":"US","Budget":"3000000","Used":"600000"},
		 * {"CustomerID":"C004","Name":"Chalee Angel","Email":"chalee.angel@thaicreate.com" ,"CountryCode":"US","Budget":"4000000","Used":"100000"}]
		 */
		
		String url = MyServer.server_url+"getUserList.php";
		
		// Paste Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("txtKeyword", inputText.getText().toString()));

		try {
			JSONArray data = new JSONArray(getJSONUrl(url,params));
			
			final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;
			
			for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);
                
    			map = new HashMap<String, String>();
    			map.put("id", c.getString("id"));
    			map.put("name", c.getString("name"));
    			map.put("type", c.getString("type"));
    			map.put("age", c.getString("age"));
    			map.put("hp", c.getString("hp"));
    			map.put("atk", c.getString("atk"));
    			map.put("def", c.getString("def"));
    			map.put("spd", c.getString("spd"));
    			map.put("win", c.getString("win"));
    			map.put("lose", c.getString("lose"));
    			
    			MyArrList.add(map);
    			
			}

	        SimpleAdapter sAdap;
	        sAdap = new SimpleAdapter(FightListActivity.this, MyArrList, R.layout.fight_list_column,
	                new String[] {"type", "name", "hp"}, new int[] {R.id.ColCustomerID, R.id.ColName, R.id.ColEmail});      
	        lisView1.setAdapter(sAdap);
	        
	        final AlertDialog.Builder viewDetail = new AlertDialog.Builder(this);
			// OnClick Item
			lisView1.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> myAdapter, View myView,
						int position, long mylng) {
	
					String strID = MyArrList.get(position).get("id")
							.toString();
					String sName = MyArrList.get(position).get("name")
							.toString();
					String strType = MyArrList.get(position).get("type")
							.toString();
					String strAge = MyArrList.get(position).get("age")
							.toString();
					String strHP = MyArrList.get(position).get("hp")
							.toString();
					String strATK = MyArrList.get(position).get("atk")
							.toString();
					String strDEF = MyArrList.get(position).get("def")
							.toString();
					String strSPD = MyArrList.get(position).get("spd")
							.toString();
					String strWin = MyArrList.get(position).get("win")
							.toString();
					String strLose = MyArrList.get(position).get("lose")
							.toString();

					viewDetail.setIcon(android.R.drawable.btn_star_big_on);
					viewDetail.setTitle(sName);
					viewDetail.setMessage("MemberID : " + strID + "\n"
							+ "Name : " + sName + "\n" 
							+ "Type : " + strType + "\n" 
							+ "Age : " + strAge + "\n" 
							+ "HP : " + strHP + "\n"
							+ "ATK : " + strATK + "\n"
							+ "DEF : " + strDEF + "\n"
							+ "SPD : " + strSPD + "\n"
							+ "WIN : " + strWin + "\n"
							+ "Lose : " + strLose);
					viewDetail.setPositiveButton("Cancel",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
					viewDetail.setNeutralButton("Fight",
					            new DialogInterface.OnClickListener() {
					                public void onClick(DialogInterface dialog, int id) {
					                	dialog.dismiss();
					                    //context.startActivity(new Intent(context, Setup.class));
					                    //dialog.cancel();

					                }
					            });
					viewDetail.show();

				}
			});
			
	        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

	public String getJSONUrl(String url,List<NameValuePair> params) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(httpPost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { // Download OK
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download file..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pet_main, menu);
        return true;
    }
    
}
