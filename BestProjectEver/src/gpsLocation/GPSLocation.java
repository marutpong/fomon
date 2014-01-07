package gpsLocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

public class GPSLocation {

	private LocationManager lm;
	private Criteria criteria;
	private final LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location location) {
        	Latitude = location.getLatitude();
        	Longtitude = location.getLongitude();
        	NotifyGPSListener();
        }

        public void onProviderDisabled(String provider) { }

        public void onProviderEnabled(String provider) { }

        public void onStatusChanged(String provider, int status, Bundle extras) { }
    };
    private boolean GPSupdate;
    private double Latitude;
    private double Longtitude;
    
    public interface OnGPSListener{
    	void onGPSChange(GPSLocation gpsLocation);
    	void onResume();
    	void onStart();
    	void onStop();
    }
    
    private OnGPSListener onGPSListenern;
    
	public void setOnGPSListener(OnGPSListener onGPSListenern) {
		this.onGPSListenern = onGPSListenern;
	}

	public GPSLocation(Activity src) {
		 lm = (LocationManager) src.getSystemService(Context.LOCATION_SERVICE);
		 
		 criteria = new Criteria();
		 criteria.setAccuracy(Criteria.ACCURACY_FINE);
		 criteria.setAltitudeRequired(false);
		 criteria.setBearingRequired(false);
	}
    
	public boolean isGPSupdate() {
		return GPSupdate;
	}

	public double getLatitude() {
		return Latitude;
	}

	public double getLongtitude() {
		return Longtitude;
	}
	
	//Call this method at "onResume"
	public void setup() {
        lm.removeUpdates(listener);
        boolean Update = false;
        
        Location networkLocation = requestUpdatesFromProvider(
                LocationManager.NETWORK_PROVIDER, "Network not supported");
        if(networkLocation != null) {
        	Latitude = networkLocation.getLatitude();
        	Longtitude = networkLocation.getLongitude();
        	Update = true;
        }
        
        Location gpsLocation = requestUpdatesFromProvider(
                LocationManager.GPS_PROVIDER, "GPS not supported");
        
        if(gpsLocation != null) {
        	Latitude = gpsLocation.getLatitude();
        	Longtitude = gpsLocation.getLongitude();
        	Update = true;
        }
     
        if(Update)
        	NotifyGPSListener();
    }
	
	//Call this on "onStart"
	public void onStart(Activity src){
	boolean gpsEnabled, networkEnabled;
	gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

    if(!gpsEnabled) {
	    	networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	    	if(!networkEnabled) {
	    		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	    		src.startActivity(intent);
	    	}
	    }
	}
	
	//Call this on "onStop"
	public void onStop(){
		lm.removeUpdates(listener);
	}
	
	private Location requestUpdatesFromProvider(final String provider, String error) {
        Location location = null;
        if (lm.isProviderEnabled(provider)) {
        	lm.requestLocationUpdates(provider, 1000, 10, listener);
            location = lm.getLastKnownLocation(provider);
            GPSupdate = true;
        } else {
        	GPSupdate = false;
        }
        return location;
    }

	private void NotifyGPSListener(){
		if(onGPSListenern != null){
			onGPSListenern.onGPSChange(this);
		}
	}
	
}
