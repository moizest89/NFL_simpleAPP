package com.applaudostudio.nfl_simpleapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.applaudostudio.nfl_simpleapp.fragments.ListDescriptionNoticesFragment;
import com.applaudostudio.nfl_simpleapp.fragments.ListNoticesFragment;


public class MainActivity extends SherlockFragmentActivity{
	//
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private ViewGroup mListLayout;

	private static ViewGroup mContainerLayout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.v(TAG, "onCreate: savedInstanceState " + (savedInstanceState == null ? "==" : "!=") + " null");
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#082456")));

		setContentView(R.layout.activity_main);
		
		if (savedInstanceState != null) {
			
		}else{
			mListLayout = (ViewGroup) findViewById(R.id.activity_main_list_container);
			if(mListLayout != null){
				
				@SuppressWarnings("rawtypes")
				ListNoticesFragment listFragment = new ListNoticesFragment();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(mListLayout.getId(), listFragment,ListNoticesFragment.class.getName());
				
				// Commit the transaction
				fragmentTransaction.commit();
			}
			mContainerLayout = (ViewGroup) findViewById(R.id.activity_main_description_container);
			if(mContainerLayout !=null){
				ListDescriptionNoticesFragment descriptionFragment = new ListDescriptionNoticesFragment();
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				fragmentTransaction.replace(
						mContainerLayout.getId(), 
						descriptionFragment,
				ListDescriptionNoticesFragment.class.getName());
				
				// Commit the transaction
				fragmentTransaction.commit();
				
			}
		}
		

	}
	public static void setDataInPaneTablet(FragmentManager frg){
		ListDescriptionNoticesFragment descriptionFragment = new ListDescriptionNoticesFragment();
		FragmentTransaction fragmentTransaction = frg.beginTransaction();
		fragmentTransaction.replace(
				mContainerLayout.getId(), 
				descriptionFragment,
		ListDescriptionNoticesFragment.class.getName());
		
		// Commit the transaction
		fragmentTransaction.commit();
	}
	
	
}
