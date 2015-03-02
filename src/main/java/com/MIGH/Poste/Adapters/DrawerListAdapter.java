package com.MIGH.Poste.Adapters;



import com.MIGH.Poste.R;
import com.nhaarman.listviewanimations.ArrayAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerListAdapter extends ArrayAdapter<String>{
	
	private LayoutInflater inflater=null;
	String [] DrawerOptions;
	Context from;
	Resources res;

	public DrawerListAdapter(Context context, int resource) {
		super();
		// TODO Auto-generated constructor stub
		from = context;
		res = from.getResources();
		DrawerOptions = res.getStringArray(R.array.DrawerOptions);
		inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override 
	public int getCount(){
		return DrawerOptions.length;
	}
	
	@Override
	public String getItem(int position){
		
		return DrawerOptions[position];
	}
	
	@Override
	public long getItemId(int position){
		return position;
	}	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if(v==null){
			v=inflater.inflate(R.layout.drawer_list_row, parent, false); 
		}
		
		String dOption = DrawerOptions[position];
		
		ImageView listIcon =(ImageView) v.findViewById(R.id.drawerlist_row_image);
		TextView optiontoDisplay=(TextView) v.findViewById(R.id.drawerlist_row_text);

		Typeface poiretOne = Typeface.createFromAsset(from.getAssets(), "fonts/PoiretOne-Regular.ttf");
		Typeface quicksandRegular = Typeface.createFromAsset(from.getAssets(), "fonts/Quicksand-Regular.otf");
		Typeface quicksandLight = Typeface.createFromAsset(from.getAssets(), "fonts/Quicksand-Light.otf");


        //change the icon depending on the type drawer option
        if(dOption.contains("Feeds")){
            listIcon.setImageResource(R.drawable.ic_feeds_icon);
        }
        else if(dOption.contains("Home")){
            listIcon.setImageResource(R.drawable.ic_home_icon);
        }
        else  if(dOption.contains("Events")) {
            listIcon.setImageResource(R.drawable.ic_events_icon);
        }
		listIcon.setPadding(3, 1, 15, 1);

		optiontoDisplay.setText(dOption);
		
		optiontoDisplay.setTypeface(quicksandRegular);
		optiontoDisplay.setTextSize(23);
		
		return v;
	}
	
}