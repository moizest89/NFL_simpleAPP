package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.nfl_simpleapp.R;

public class ListDescriptionNoticesFragment extends SherlockFragment{

	ImageView IVDescriptionImage;
	TextView TVDescriptionTitle,TVDescriptionNotice;
	Boolean is_load = false;
	String title_notice,description_notice;
	
	static ListDescriptionNoticesFragment newInstance(String ImageName,
			String title, String notice){
		ListDescriptionNoticesFragment newListDescriptionFragment = new ListDescriptionNoticesFragment();	
		Bundle args = new Bundle();
        args.putString("image_url", ImageName);
        args.putString("title", title);
        args.putString("notice", notice);
        args.putBoolean("is_load", false);
        newListDescriptionFragment.setArguments(args);
        return newListDescriptionFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle args = getArguments();
		if (args != null) {
			is_load = args.getBoolean("is_load");
			title_notice = args.getString("title");
			description_notice = args.getString("notice");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.item_notice_detail, container, false);
		IVDescriptionImage = (ImageView) v.findViewById(R.id.IVDescriptionImage);
		TVDescriptionTitle = (TextView) v.findViewById(R.id.TVDescriptionTitle);
		TVDescriptionNotice = (TextView) v.findViewById(R.id.TVDescriptionNotice);
		
		if(is_load){
			TVDescriptionTitle.setText("title_notice");
			TVDescriptionNotice.setText("description_notice");
		}
		
		return v;
	}
	
	
	
}
