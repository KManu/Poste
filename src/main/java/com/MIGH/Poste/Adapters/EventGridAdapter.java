package com.MIGH.Poste.Adapters;

import java.util.ArrayList;
import java.util.Random;

import com.MIGH.Poste.R;
import com.MIGH.Poste.DataModels.EventNoticeModel;
import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ScaleImageView;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;




public class EventGridAdapter extends BaseAdapter {
	ArrayList<EventNoticeModel> events;
	Context context;
	LayoutInflater inflater;
	
	public EventGridAdapter(Context context, ArrayList<EventNoticeModel> events){
		this.events= events;
		this.context= context;
	}

	@Override
	public int getCount() {
		
		return events.size();
	}

	@Override
	public Object getItem(int position) {
		
		return events.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
        final ImageView eventDispImage;
        TextView eventName;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			v = new View(context);
			v = inflater.inflate(R.layout.event_notice_grid_item,null);
			//v.setLayoutParams(new GridView.LayoutParams(300,300));
			v.setPadding(2, 2, 2, 2);
		}
		else{
			v = (View) convertView;
		}


        //Initialize views
        ImageView eventTypeLogo =(ImageView) v.findViewById(R.id.EventNoticeGrid_EventType);
        eventDispImage =(ImageView) v.findViewById(R.id.EventNoticeGrid_DispImage);
        eventName=(TextView) v.findViewById(R.id.EventNoticeGrid_EventName);

        EventNoticeModel EventToDisplay = events.get(position);
        String eventType= EventToDisplay.getEventType();

        //Set the event type logo to the appropriate one based on what type it is
        //Switch, or if on the eventType string
        eventTypeLogo.setImageResource(R.drawable.ic_drawerlist_placeholder);

        //set the rest of the views
        eventName.setText(events.get(position).getEventName());
        //Set the type display image based on the type fo event
        String eventTypeString = events.get(position).getEventType();


        //set the image view to the event logo

        String urls[]={
                "http://lorempixel.com/640/500/cats",
                "http://lorempixel.com/840/500/",
                "http://lorempixel.com/340/500/",
                "http://lorempixel.com/700/700/"
        };
        Random rand = new Random();
       String url =  urls[ rand.nextInt(urls.length-1)];
        //eventDispImage.setImageAsset(url);

        //String url = "http://lorempixel.com/640/500/cats";

           Glide.with(this.context.getApplicationContext())
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.image_loading_spinner_gif)
                .thumbnail(0.3f)
                .fitCenter()
                .error(R.drawable.img_super_meat)
                .crossFade()
                .into(eventDispImage);

		return v;
	}

}
