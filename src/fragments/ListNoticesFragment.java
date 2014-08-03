package fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.ListNoticesAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nfl_simpleapp.NoticeDetailActivity;
import com.nfl_simpleapp.R;

public class ListNoticesFragment<DisplayFragment> extends SherlockFragment{
	
	private static final String TAG = ListNoticesFragment.class.getSimpleName();
	private String URL_CONNECTION = "http://glacial-earth-4981.herokuapp.com/v1/articles";
	ProgressBar PBList;
	ListView LVNoticesList;
	RequestQueue listNoticesRequest;
	JsonObjectRequest jsonListNoticesRequest;
	BaseAdapter ListNoticesAdapter;
	ArrayList<HashMap<String,Object>> jsonNotices = new ArrayList<HashMap<String,Object>>();
	
	

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
		
//		DisplayFragment displayFrag = (DisplayFragment) getFragmentManager()
//                .findFragmentById(R.id.display_frag);
		LVNoticesList.setOnItemClickListener(new OnItemClickListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				ViewGroup mContainerLayout = (ViewGroup) getActivity().findViewById(R.id.activity_main_description_container);
				if(mContainerLayout == null){
//					Toast.makeText(getActivity(), "Activity", Toast.LENGTH_LONG).show();
					
					HashMap<String, Object> data = new HashMap<String,Object>(); 
					data =  jsonNotices.get(position);
					
					String title = (String) data.get("title");
					String content = (String) data.get("content");
					
					ArrayList<HashMap<String,Object>> pictures = new ArrayList<HashMap<String,Object>>();
					pictures = (ArrayList<HashMap<String, Object>>) data.get("article_pictures");
					
					String article_picture = (String) pictures.get(0).get("image_name");
					
					
					Intent intent = new Intent(getActivity(),NoticeDetailActivity.class);
					intent.putExtra("title", title);
					intent.putExtra("content", content);
					intent.putExtra("article_picture",article_picture);
					getActivity().startActivity(intent);
					
				}else{
					Toast.makeText(getActivity(), "Fragment", Toast.LENGTH_LONG).show();
				}
			}
			
		});
		return v;
		
	}
	
	private void getNoticesData(String URL){
		listNoticesRequest = Volley.newRequestQueue(getActivity());
		jsonListNoticesRequest = new JsonObjectRequest(Request.Method.GET, URL, null, 
			new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					setNoticesData(response);
				}					
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					//If Request has error
					VolleyLog.d("Volley", error.getMessage());
				}
			});
		jsonListNoticesRequest.setShouldCache(true);
		listNoticesRequest.add(jsonListNoticesRequest);
	}
	
	private void setNoticesData(JSONObject data){
		try {
			jsonNotices.clear();
			JSONArray articles = data.getJSONArray("articles");
			for(int item_notice = 0; item_notice < articles.length() ; item_notice ++){
				JSONObject article = ((JSONObject) articles.get(item_notice)).getJSONObject("article");
				
				String title = article.getString("title");
				String content = article.getString("content");
				String created_at = article.getString("created_at");
				String article_excerpt = article.getString("article_excerpt");
				
				
				JSONArray article_pictures = article.getJSONArray("article_pictures");
				
				ArrayList<HashMap<String,Object>> pictures = new ArrayList<HashMap<String,Object>>();
				pictures.clear();
				for(int art_pic = 0; art_pic < article_pictures.length(); art_pic++){
					JSONObject article_picture = ((JSONObject) article_pictures.get(art_pic)).getJSONObject("article_picture");
					
					String image_name = article_picture.getString("picture_web_slide_non_retina_url");
					HashMap<String,Object> pictures_map = new HashMap<String,Object>();
					pictures_map.put("image_name", image_name);
					pictures.add(pictures_map);
				}
				
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("title", title);
				map.put("content", content);
				map.put("created_at", created_at);
				map.put("article_excerpt", article_excerpt);
				map.put("article_pictures", pictures);
				
				jsonNotices.add(map);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//Show Information
		ListNoticesAdapter.notifyDataSetChanged();
		PBList.setVisibility(View.INVISIBLE);
		LVNoticesList.setVisibility(View.VISIBLE);
	}
}
