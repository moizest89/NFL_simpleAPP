package com.nfl_simpleapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationEventActivity extends SherlockFragmentActivity{
	
	GoogleMap map;
	MapView mapView;
	CameraUpdate cameraUpdate;
	LatLng myPosition;
	private ArrayList<Marker> mMarkerArray = new ArrayList<Marker>();
	ArrayList<HashMap<String, Object>> map_location = new ArrayList<HashMap<String, Object>>();
	double latitude;
	double longitude;
	String mapPlace="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_location_event);
		
		mapView = (MapView) findViewById(R.id.mapview);
	    mapView.onCreate(savedInstanceState);
	    mapView.onResume(); //without this, map showed but was empty 
	    
	    map = mapView.getMap(); 
	    map.getUiSettings().setMyLocationButtonEnabled(true);
	    map.setMyLocationEnabled(true);
	    /// get my position // 
	    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);
        if(location!=null){
        // Getting latitude of the current location
        	latitude = 39.744121;
        	// Getting longitude of the current location
        	longitude = -105.020049;
        	//Creating a LatLng object for the current location
        	//LatLng latLng = new LatLng(latitude, longitude);
        	myPosition = new LatLng(latitude, longitude);
        }
        //===========
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        		map_location= (ArrayList<HashMap<String, Object>>) extras.get("map");
        		mapPlace = (String) extras.getString("place");
        }
	    //for json
        //System.out.println(map_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0a2836")));
		getSupportActionBar().setTitle(mapPlace);
        MapsInitializer.initialize(LocationEventActivity.this);
        
        
    	final Marker marker =  map.addMarker(new MarkerOptions()
    		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
    		.anchor(1.0f, 1.0f)
    		.position(new LatLng(Double.parseDouble("39.744121"), Double.parseDouble("-105.020049")))
    		.draggable(true)
    		.title("Sports Authority Field @ Mile High")
    		.snippet("1701 Bryant Street #700"));
    	mMarkerArray.add(marker);
        
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
	    
	    //builder.include(map.getMyLocation());
	    
	    for(Marker  marker_item : mMarkerArray){
	    	builder.include(marker_item.getPosition());
	    }
	    builder.include(myPosition);
	    LatLngBounds bounds = builder.build();
	    
//	    
	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble("39.744121"), Double.parseDouble("-105.020049")), 15);
//	    cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,50,50, 6);
	    map.animateCamera(cameraUpdate);
	    
	}
	
}
