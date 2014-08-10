package fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.ListNoticesAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.nfl_simpleapp.MainActivity;
import com.nfl_simpleapp.NoticeDetailActivity;
import com.nfl_simpleapp.R;

public class ListNoticesFragment<DisplayFragment> extends SherlockFragment{
	
	private static final String TAG = ListNoticesFragment.class.getSimpleName();
	private String URL_CONNECTION = "https://s3.amazonaws.com/jon-hancock-phunware/nflapi-static.json";
	ProgressBar PBList;
	ListView LVNoticesList;
	RequestQueue listNoticesRequest;
	BaseAdapter ListNoticesAdapter;
	ArrayList<HashMap<String,Object>> jsonNotices = new ArrayList<HashMap<String,Object>>();
	ViewGroup mContainerLayout;
	Boolean loadData = false,isMenuTouch = false;
	private MenuItem menuItem;
	
	public static ListNoticesFragment newInstance(){
		ListNoticesFragment newListFragment = new ListNoticesFragment();
		
		return newListFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate: savedInstanceState " + (savedInstanceState == null ? "==" : "!=") + " null");
		
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v(TAG, "onCreateView: savedInstanceState " + (savedInstanceState == null ? "==" : "!=") + " null");
		
		View v = inflater.inflate(R.layout.fragment_list, container, false);
		
		PBList = (ProgressBar) v.findViewById(R.id.PBList);
		PBList.setVisibility(View.VISIBLE);
		LVNoticesList = (ListView) v.findViewById(R.id.LVNoticesList);
		LVNoticesList.setVisibility(View.INVISIBLE);
		
		ListNoticesAdapter = new ListNoticesAdapter(getActivity(),jsonNotices);
		LVNoticesList.setAdapter(ListNoticesAdapter);
		getNoticesData(URL_CONNECTION);
		
		LVNoticesList.setOnItemClickListener(new OnItemClickListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				mContainerLayout = (ViewGroup) getActivity().findViewById(R.id.activity_main_description_container);
//				if(mContainerLayout == null){
					HashMap<String, Object> data = new HashMap<String,Object>(); 
					data =  jsonNotices.get(position);
					Intent intent = new Intent(getActivity(),NoticeDetailActivity.class);
					intent.putExtra("data", data);
					
					getActivity().startActivity(intent);
					
//				}else{
//					try{
//						MainActivity.setDataInPaneTablet(getChildFragmentManager());
//					}catch(Exception e){
//						Log.e(TAG, e.getMessage());
//					}
//				}
			}
			
		});
		return v;
		
	}
	
	private void getNoticesData(String URL){
		RequestQueue listNoticesRequest = Volley.newRequestQueue(getActivity());
		
		JsonArrayRequest jsonListNoticesRequest = new JsonArrayRequest(URL,
			new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					
					setNoticesData(response);
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					VolleyLog.d(TAG, "Error: " + error.getMessage());
					loadData = true;
					setHasOptionsMenu(true);
				}
			});
		listNoticesRequest.add(jsonListNoticesRequest);
	}
	
	
	private void setNoticesData(JSONArray response){
		try {
			jsonNotices.clear();
			for(int items = 0; items < response.length() ; items ++){
				JSONObject dataItem = response.getJSONObject(items);
				
				String zip = (String) dataItem.get("zip");
				String phone = (String)dataItem.get("phone");
				String ticket_link = (String) dataItem.get("ticket_link");
				String state = (String) dataItem.get("state");
				Integer pcode = (Integer) dataItem.get("pcode");
				String city = (String) dataItem.get("city");
				Integer id = (Integer) dataItem.get("id");
				String tollfreephone = (String) dataItem.get("tollfreephone");
				String address = (String) dataItem.get("address");
				String image_url = (String) dataItem.get("image_url");
				String description = (String) dataItem.get("description");
				String name = (String) dataItem.get("name");
				Double longitude = (Double) dataItem.get("longitude");
				Double latitude = (Double) dataItem.get("latitude");
				
				
				JSONArray schedule = dataItem.getJSONArray("schedule");
//				
				ArrayList<HashMap<String,Object>> schedules = new ArrayList<HashMap<String,Object>>();
				schedules.clear();
				for(int sch = 0; sch < schedule.length(); sch++){
					JSONObject OBJDate = (JSONObject) schedule.get(sch);
					String end_date = OBJDate.getString("end_date");
					String start_date = OBJDate.getString("start_date");
					
					HashMap<String,Object> date_map = new HashMap<String,Object>();
					date_map.put("end_date", end_date);
					date_map.put("start_date", start_date);
					schedules.add(date_map);
				}
				
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("zip",zip);
				map.put("phone",phone);
				map.put("ticket_link",ticket_link);
				map.put("state",state);
				map.put("pcode",pcode);
				map.put("city",city);
				map.put("id",id);
				map.put("tollfreephone",tollfreephone);
				map.put("address",address);
				map.put("image_url",image_url);
				map.put("description",description);
				map.put("name",name);
				map.put("longitude",longitude);
				map.put("latitude",latitude);
				map.put("schedules", schedules);
				
				jsonNotices.add(map);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//Show Information
		ListNoticesAdapter.notifyDataSetChanged();
		PBList.setVisibility(View.INVISIBLE);
		LVNoticesList.setVisibility(View.VISIBLE);
		if(mContainerLayout != null){
			ListDescriptionNoticesFragment descriptionFragment = new ListDescriptionNoticesFragment();
			FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
			fragmentTransaction.replace(mContainerLayout.getId(), descriptionFragment,
			ListDescriptionNoticesFragment.class.getName());
			// Commit the transaction
			fragmentTransaction.commit();
		}
		if(isMenuTouch){
			menuItem.collapseActionView();
            menuItem.setActionView(null);
            isMenuTouch = false;
		}
		loadData = true;
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onPrepareOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.clear();
		if(loadData){
			getSherlockActivity().getSupportMenuInflater().inflate(R.menu.menu_reload_data, menu);
		}
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		menuItem = item;
		switch (item.getItemId()) {
	    case R.id.menu_reload: 
	    	menuItem.setActionView(R.layout.progress_menu);
  			menuItem.expandActionView();
  			isMenuTouch = true;
  			getNoticesData(URL_CONNECTION);
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
		}
	}
}