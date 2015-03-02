package com.MIGH.Poste.Adapters;
/*
 * This has been changed into the feeds adapter, which would take in feed objects,
 * and display them in a grid. 
 */


import java.util.ArrayList;
import java.util.Random;

import com.MIGH.Poste.R;
import com.MIGH.Poste.DataModels.FeedsModel;
import com.bumptech.glide.Glide;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FeedsGridAdaptor extends BaseAdapter {
	Context context;
	ArrayList<FeedsModel> feeds;
	LayoutInflater inflater;
	public FeedsGridAdaptor(Context context, ArrayList<FeedsModel> feeds){
		this.context = context;
		Resources  r =context.getResources();
		this.feeds = feeds;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return feeds.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return feeds.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			v = new View(context);
			v = inflater.inflate(R.layout.feeds_grid_item,null);
			//v.setLayoutParams(new GridView.LayoutParams(350,350));
			v.setPadding(2, 2, 2, 2);
		}
		else{
			v = (View) convertView;
		}
		// Grid label/ Feed name
		TextView label = (TextView) v.findViewById(R.id.FeedGrid_FeedName) ;
		ImageView image = (ImageView) v.findViewById(R.id.FeedGrid_FeedLogo);
        //Image loading done by Glide dB-/ ...like a bause

        String urls[]={
                "http://lorempixel.com/640/500/food",
                "http://lorempixel.com/840/500/cats",
                "http://lorempixel.com/340/500/abstract",
                "http://lorempixel.com/700/800/technics",
                "http://lorempixel.com/500/500/sports",
                "http://lorempixel.com/800/720/technics"
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
                .into(image);

		label.setText( (feeds.get(position)).FeedName );
		//image.setImageResource(R.drawable.img_super_meat);
		
		
		return v;
	}

}
