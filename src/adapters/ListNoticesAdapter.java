package adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.nfl_simpleapp.R;

public class ListNoticesAdapter extends BaseAdapter{
	
	Context context;
	ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
	AQuery aq;
	Bitmap preset;
	private static LayoutInflater inflater=null;
	
	
	
	public ListNoticesAdapter(Context context, ArrayList<HashMap<String,Object>> data){
		this.context = context;
		this.data = data;
		
		aq= new AQuery(this.context);
		preset= aq.getCachedImage(R.drawable.place_holder_nfl);
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return this.data.size();
		
	}

	@Override
	public Object getItem(int position) {
		return position;
		
	}

	@Override
	public long getItemId(int position) {
		return position;
		
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		
		if (convertView == null) {
			vi = inflater.inflate(R.layout.item_notice_list, null);
			//Get items view
			holder = new ViewHolder();
			holder.IVImageNotice = (ImageView) vi.findViewById(R.id.IVImageNotice);
			holder.TVTitleNotice = (TextView) vi.findViewById(R.id.TVTitleNotice);
			holder.TVDescriptionNotice = (TextView) vi.findViewById(R.id.TVDescriptionNotice);
			vi.setTag(holder);
		}else{
			holder = (ViewHolder) vi.getTag();
		}
		//Get items data
		String title = (String) data.get(position).get("name");
		String address = (String) data.get(position).get("address");
		
		String article_picture = (String) data.get(position).get("image_url");
		//Set data in element view
		holder.TVTitleNotice.setText(title);
		holder.TVDescriptionNotice.setText(address);
		aq.id((ImageView) holder.IVImageNotice).image((article_picture), true, true,0, R.drawable.place_holder_nfl,preset,0,3.0f/4.0f);
		return vi;
	}
	
	public class ViewHolder {
		TextView TVTitleNotice;
		TextView TVDescriptionNotice;
		ImageView IVImageNotice;
	}

}
