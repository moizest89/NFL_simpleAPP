package com.nfl_simpleapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;

import customClasses.TouchImageView;

public class PinchImageView extends SherlockActivity{

	TouchImageView TIVPinchImage;
	String article_picture;
	AQuery aq;
	Bitmap preset;
	ProgressBar PBPinch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Set activity in full screen
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activty_pinch_image_view);
		getSupportActionBar().hide();
		TIVPinchImage = (TouchImageView) findViewById(R.id.TIVPinchImage);
		PBPinch = (ProgressBar) findViewById(R.id.PBPinch);
		
		//New Instance from AQ
		aq= new AQuery(PinchImageView.this);
		preset= aq.getCachedImage(R.drawable.place_holder_nfl);
		
		
		Bundle extras = getIntent().getExtras();
		if(extras !=null){
			article_picture = (String) extras.getString("article_picture");
		}
		
		aq.id(TIVPinchImage).progress(PBPinch).image(article_picture, true, true,0, R.drawable.place_holder_nfl, new BitmapAjaxCallback(){
			@Override
			protected void callback(String url, ImageView iv, Bitmap bm,AjaxStatus status) {
				super.callback(url, iv, bm, status);
				if(status.getCode() == 200){
					iv.setImageBitmap(null);
					iv.setImageBitmap(bm);
				}
			}
			
		});
		
	}
	
}
