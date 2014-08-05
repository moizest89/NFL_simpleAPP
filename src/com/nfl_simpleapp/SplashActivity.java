package com.nfl_simpleapp;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

public class SplashActivity extends SherlockActivity{
	
	
	private long splashDelay = 4000; //6 segundos
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getSupportActionBar().hide();
		
		TimerTask task = new TimerTask() {
		      @Override
		      public void run() {
		        Intent mainIntent;
		        mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
		        startActivity(mainIntent);
		        finish();
		      }
			};
		Timer timer = new Timer();
		timer.schedule(task, splashDelay);
	}
	
}
