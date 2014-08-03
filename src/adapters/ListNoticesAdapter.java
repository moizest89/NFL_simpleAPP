package adapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.androidquery.AQuery;
import com.nfl_simpleapp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		View vi=convertView;
		
		System.out.println(data.get(position));
		
		vi = inflater.inflate(R.layout.item_notice_list, null);
		
		//Get items view
		ImageView IVImageNotice = (ImageView) vi.findViewById(R.id.IVImageNotice);
		TextView TVTitleNotice = (TextView) vi.findViewById(R.id.TVTitleNotice);
		TextView TVDescriptionNotice = (TextView) vi.findViewById(R.id.TVDescriptionNotice);
 		
		//Get items data
		String title = (String) data.get(position).get("title");
		String article_excerpt = (String) data.get(position).get("article_excerpt");
		
		//Set data in element view
		TVTitleNotice.setText(title);
		TVDescriptionNotice.setText(article_excerpt);
		
		return vi;
	}

}
