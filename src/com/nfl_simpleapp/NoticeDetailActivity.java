package com.nfl_simpleapp;

import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;

public class NoticeDetailActivity extends SherlockFragmentActivity{
	
	ImageView IVDescriptionImage;
	TextView TVDescriptionTitle,TVDescriptionNotice;
	String title,content,article_picture;
	HashMap<String, Object> data = new HashMap<String,Object>();
	AQuery aq;
	Bitmap preset;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle saveInstance) {
		super.onCreate(saveInstance);
		
		setContentView(R.layout.item_notice_detail);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#082456")));
		
		//Get elements in view
		IVDescriptionImage = (ImageView) findViewById(R.id.IVDescriptionImage);
		TVDescriptionTitle = (TextView) findViewById(R.id.TVDescriptionTitle);
		TVDescriptionNotice = (TextView) findViewById(R.id.TVDescriptionNotice);
		
		
		//New Instance from AQ
		aq= new AQuery(NoticeDetailActivity.this);
		preset= aq.getCachedImage(R.drawable.place_holder_nfl);
		
		//Get bundle for data in previews activity
		Bundle extras = getIntent().getExtras();
		if(extras !=null){
			data = (HashMap<String, Object>) extras.get("data");
			title = (String) data.get("name");
			content = (String) data.get("address");
			article_picture = (String) data.get("image_url");
		}
		
		
		//Set info in elements 
		TVDescriptionTitle.setText(title);
		TVDescriptionNotice.setText(content);
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
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}
	
	@Override
	public void finish() {
		super.finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	      case android.R.id.home:
	        finish();
	        return(true);
	    }
	    // more code here for other cases
		return false;
	  }
}
