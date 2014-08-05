package com.nfl_simpleapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.WindowManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
	String mapPlace="",title,address;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Set activity in full screen
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				
		setContentView(R.layout.activity_location_event);
		
		getSupportActionBar().hide();
		//
		
		Bundle extras = getIntent().getExtras();
		if(extras !=null){
			title = (String) extras.get("name");
			address = (String) extras.get("address");
			latitude = (Double) extras.get("latitude");
			longitude = (Double) extras.get("longitude");
		}
		
		mapView = (MapView) findViewById(R.id.mapview);
	    mapView.onCreate(savedInstanceState);
	    mapView.onResume(); //without this, map showed but was empty 
	    
	    map = mapView.getMap(); 
	    map.getUiSettings().setMyLocationButtonEnabled(true);
	    map.setMyLocationEnabled(true);
	    
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0a2836")));
		getSupportActionBar().setTitle(mapPlace);
        MapsInitializer.initialize(LocationEventActivity.this);
        
        
    	final Marker marker =  map.addMarker(new MarkerOptions()
    		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
    		.anchor(1.0f, 1.0f)
    		.position(new LatLng(latitude,longitude))
    		.draggable(true)
    		.title(title
    				)
    		.snippet(address));
    	mMarkerArray.add(marker);
        

	    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 15);
	    map.animateCamera(cameraUpdate);
	    
	}
	
}
