package com.projnsc.bestprojectever;

import gpsLocation.GPSLocation;
import gpsLocation.GPSLocation.OnGPSListener;

import com.projnsc.bestprojectever.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NavGoogleMAP extends Activity implements OnGPSListener {

	TextView txt1;
	GPSLocation gps;
	Button btnWh;
	Button btnHr;
	GoogleMap map;
	double laa;
	double loo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		
		txt1 = (TextView) findViewById(R.id.dummyPetInputNameLabel);
		gps = new GPSLocation(this);
		gps.setOnGPSListenern(this);
		btnWh = (Button) findViewById(R.id.btnTakePhoto);
		btnHr = (Button) findViewById(R.id.btnSearchFood);
		btnWh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goSydney();
			}
		});
		btnHr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				goSelf();
			}
		});
		 map  = ((MapFragment) getFragmentManager()
	                .findFragmentById(R.id.map)).getMap();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pet_main, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
        gps.setup();
    }
	
	@Override
	public void onStart() {
        super.onStart();
        gps.onStart(this);
    }

	@Override
	public void onStop() {
        super.onStop();
        gps.onStop();
    }

	@Override
	public void onGPSChange(GPSLocation gpsLocation) {
//		la.setText(gpsLocation.getLatitude() + "");
//		lo.setText(gpsLocation.getLongtitude() + "");
		laa = gpsLocation.getLatitude();
		loo = gpsLocation.getLongtitude();
		txt1.setText(laa + " : " + loo);
	}
	
	private void goSydney(){
		// Get a handle to the Map Fragment
	        LatLng sydney = new LatLng(-33.867, 151.206);

//	        map.setMyLocationEnabled(true);
	        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

	        map.addMarker(new MarkerOptions()
	                .title("Sydney")
	                .snippet("The most populous city in Australia.")
	                .position(sydney));
	}

	private void goSelf(){
		map.setMyLocationEnabled(true);
		//map.getMyLocation();
		
        LatLng sydney = new LatLng(laa,loo);

//        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Here")
                .snippet("You Are Here")
                .position(sydney));
	}
	
}
