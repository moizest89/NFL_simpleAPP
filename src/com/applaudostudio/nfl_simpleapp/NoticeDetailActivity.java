package com.applaudostudio.nfl_simpleapp;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;

public class NoticeDetailActivity extends SherlockFragmentActivity{
	
	ImageView IVDescriptionImage;
	TextView TVDescriptionTitle,TVDescriptionNotice;
	String title,address,article_picture,ticket_link,phone;
	HashMap<String, Object> data = new HashMap<String,Object>();
	ArrayList<HashMap<String,Object>> schedules = new ArrayList<HashMap<String,Object>>(); 
	AQuery aq;
	LinearLayout LLSchedules;
	Bitmap preset;
	double latitude;
	double longitude;
	private static LayoutInflater inflater=null;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		
		setContentView(R.layout.item_notice_detail);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#082456")));
		LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		//Get elements in view
		IVDescriptionImage = (ImageView) findViewById(R.id.IVDescriptionImage);
		TVDescriptionTitle = (TextView) findViewById(R.id.TVDescriptionTitle);
		TVDescriptionNotice = (TextView) findViewById(R.id.TVDescriptionNotice);
		LLSchedules = (LinearLayout) findViewById(R.id.LLSchedules);
		
		//New Instance from AQ
		aq= new AQuery(NoticeDetailActivity.this);
		preset= aq.getCachedImage(R.drawable.place_holder_nfl);
		
		//Get bundle for data in previews activity
		Bundle extras = getIntent().getExtras();
		if(extras !=null){
			data = (HashMap<String, Object>) extras.get("data");
			title = (String) data.get("name");
			address = (String) data.get("address");
			article_picture = (String) data.get("image_url");
			ticket_link = (String) data.get("ticket_link");
			phone = (String) data.get("phone");
			latitude = (Double) data.get("latitude");
			longitude = (Double) data.get("longitude");
			schedules = (ArrayList<HashMap<String,Object>>) data.get("schedules");
			
			System.out.println("schedules: "+schedules);
		}
		
		
		//Set info in elements 
		TVDescriptionTitle.setText(title);
		TVDescriptionNotice.setText(address);
		aq.id((ImageView) IVDescriptionImage).image((article_picture), false, true,0, R.drawable.place_holder_nfl,preset,0,36.0f/80.0f);
		
		//Action Bar options
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setSubtitle(title);
		
		//Click for view image with Pinch
		
		IVDescriptionImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NoticeDetailActivity.this, PinchImageView.class);
				intent.putExtra("article_picture", article_picture);
				startActivity(intent);
			}
		});
		
		//add schedules
		if(schedules.size() > 0 ){
			for(int shed =0; shed < schedules.size(); shed++){
				//Inflate view
				//Title
				TextView rowTitle = new TextView(this);
				rowTitle.setText("Date # "+(shed + 1));
				rowTitle.setTextSize(18);
				rowTitle.setTypeface(rowTitle.getTypeface(), Typeface.BOLD);
				rowTitle.setLayoutParams(paramsText);
				
				//start_date
				inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View rowStart = inflater.inflate(R.layout.item_schedules, null);
				TextView titleStart = (TextView) rowStart.findViewById(R.id.title);
				TextView dateStart = (TextView) rowStart.findViewById(R.id.date);
				//Get Data
				String start_date = (String) schedules.get(shed).get("start_date");
				titleStart.setText("Start Date: ");
				
				dateStart.setText(formatDate(start_date));
				
				
				//end_date
				inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View rowEnd = inflater.inflate(R.layout.item_schedules, null);
				TextView titleEnd = (TextView) rowEnd.findViewById(R.id.title);
				TextView dateEnd = (TextView) rowEnd.findViewById(R.id.date);
				//Get Data
				String end_date = (String) schedules.get(shed).get("end_date");
				titleEnd.setText("End Date: ");
				dateEnd.setText(formatDate(end_date));
				LLSchedules.addView(rowTitle);
				LLSchedules.addView(rowStart);
				LLSchedules.addView(rowEnd);
			}
		}else{
			TextView noAvaliable = new TextView(this);
			noAvaliable.setText("Schedules not avaliable");
			noAvaliable.setLayoutParams(paramsText);
			LLSchedules.addView(noAvaliable);
		}
	}
	

	@Override
	public boolean onPrepareOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		menu.clear();
		getSupportMenuInflater().inflate(R.menu.menu_map, menu);
		getSupportMenuInflater().inflate(R.menu.menu_share, menu);
	    return super.onPrepareOptionsMenu(menu);
	    
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case android.R.id.home:
	        finish();
	        return(true);
	      case R.id.menu_map:
	    	Intent intent = new Intent(NoticeDetailActivity.this,LocationEventActivity.class);
	    	intent.putExtra("latitude", latitude);
	    	intent.putExtra("longitude", longitude);
	    	intent.putExtra("name", title);
	    	intent.putExtra("address", address);
	    	startActivity(intent);
	    	return(true);
	      case R.id.menu_share:
	    	Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = title+" in "+address +"\n Visit next link for buy your ticket: "+ticket_link+ "\n or call to: "+phone;
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "NFL");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent, "Share via"));
	    	return(true);	
	    }
	    // more code here for other cases
		return false;
	  }
	
	public String formatDate(String date_to_format){
		final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		Date simpleDate = null;
		try {
			simpleDate = FORMATTER.parse(date_to_format);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return simpleDate.toString();
	}
}
