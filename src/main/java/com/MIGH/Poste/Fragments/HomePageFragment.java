package com.MIGH.Poste.Fragments;

/* 
 * CURRENTLY BEING USED AS SQUATTER GROUNDS FOR EVENT PAGE.
 * REMOVE THE TESTING CODE AND FIX IN THE STAGGGERED GRIDVIEW. 
 * The working code here is testing code. 
 */


import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.michaelevans.colorart.library.ColorArt;

import com.MIGH.Poste.Adapters.DatabaseAdapter;
import com.MIGH.Poste.DataModels.EventNoticeModel;
import com.MIGH.Poste.R.id;
import com.MIGH.Poste.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePageFragment extends Fragment {
		//Banner Widgets
		RelativeLayout banner;
		ImageView eventType;
		TextView eventName;
		ImageButton mapButton;
		
		//Body widgets
		LinearLayout linLayout;
		ImageView eventImage;
		TextView eventOrgs;
		TextView eventTime;
		TextView eventVenue;
		TextView eventDetails;
		TextView eventContact;
		private DatabaseAdapter db;
		EventNoticeModel eventToDisp;
		String url;
        String EventID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.EventID = getArguments().getString("EventID");
            Toast.makeText(getActivity().getApplicationContext(),"Event ID from Frag is "+this.EventID, Toast.LENGTH_SHORT).show();

        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.event_full_page,container,false);
	}

    public HomePageFragment newInstance(String EventID){
       /*
       Long story on why this is, and why i should not forget why it's used.
       lol. Okay maybe not too long
       When the main activity receives the event to create an eventPage, it
       needs an eventPageFragment to push to the top of the stack.
       Normally, we'd just push it straight, but given that this fragment we want
       to display needs to be 'customised', ie have data relevant to the event
       selected, we need to create a method to bundle up the ID of the event we
       selected, and then create the Fragment to pass BACK to the main, which then
       creates the fragment, and pushes it to the front of the stack.
        */
        HomePageFragment hpFrag = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString("EventID",EventID);
        hpFrag.setArguments(args);
        return hpFrag;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		


        //this.EventID = getArguments().getString("EventID");
         //this.EventID =""+ 75032;

        init();
        placeimg();

        Glide.with(this.getActivity().getApplicationContext())
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(1000,1000) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        // Do something with bitmap here.
                        Bitmap bmp = bitmap;
                        ColorAnalyser cAsync =  new ColorAnalyser();
                        cAsync.execute(bmp);
                    }
                });



		//getAndSetEventObj();
	}


	
	private ColorArt setColors(){
		//Getting the file and putting it into and input stream for ColorArt
		  Bitmap bmp=null;
		ColorArt cArt = new ColorArt(bmp);
		return cArt;
	}
	
	
	
	private void placeimg(){
		
	//	SubsamplingScaleImageView postImg = (SubsamplingScaleImageView) getActivity().findViewById(id.EventPage_eventImage);
	//	postImg.setImageAsset(filepath);


        String urls[]={
                "http://lorempixel.com/640/500/cats",
                "http://lorempixel.com/840/500/",
                "http://lorempixel.com/340/500/",
                "http://lorempixel.com/720/1280/"
        };
        Random rand = new Random();
        url =  urls[ rand.nextInt(urls.length-1)];

        Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.image_loading_spinner_gif)
                .thumbnail(0.3f)
                .fitCenter()
                .error(R.drawable.img_super_meat)
                .crossFade()
                .into(eventImage);


    }
	
	

	private void init() {
        db = new DatabaseAdapter(getActivity().getApplicationContext());

		banner = (RelativeLayout) getActivity().findViewById(R.id.EventPage_Banner_RelativeLayout);
		eventType = (ImageView) getActivity().findViewById(R.id.EventPage_Banner_eventTypeImage);
		eventName = (TextView) getActivity().findViewById(R.id.EventPage_Banner_EventName);
		mapButton = (ImageButton) getActivity().findViewById(R.id.EventPage_Banner_MapButton);
		
		eventImage = (ImageView) getActivity().findViewById(id.EventPage_eventImage);
		eventOrgs = (TextView) getActivity().findViewById(R.id.EventPage_eventOrgs);
        eventTime = (TextView) getActivity().findViewById(id.EventPage_eventTime);
		eventVenue = (TextView) getActivity().findViewById(R.id.EventPage_eventVenue);
		eventDetails = (TextView) getActivity().findViewById(R.id.EventPage_eventDetails);
		eventContact = (TextView) getActivity().findViewById(R.id.EventPage_eventContact);
	}

    private void getAndSetEventObj(){

        db.open();

        eventToDisp = db.getEventNotice(getArguments().getString("EventID"));
        db.close();
        //Get the event type, and set the event type image accordingly
            String eType = eventToDisp.getEventType();
        // the main image loading is going to be handled
        // as is, until the URL loading is settled.
        eventName.setText(eventToDisp.getEventName());
        eventOrgs.setText(eventToDisp.getEventOrganisers());
        eventTime.setText(eventToDisp.getEventTime());
        eventVenue.setText(eventToDisp.getEventVenue());
        eventDetails.setText(eventToDisp.getEventDetails());
        eventContact.setText(eventToDisp.getEventContact());



    }

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
	}


	public class ColorAnalyser extends AsyncTask<Bitmap, Void, ColorArt> {

		@Override
		protected ColorArt doInBackground(Bitmap... params) {
			ColorArt cArt  = new ColorArt(params[0]);
			return cArt;
		}
		protected void onPostExecute(ColorArt cArt){
			//setting colors for banner items 
			eventName.setTextColor(cArt.getPrimaryColor());
			mapButton.setBackgroundColor(cArt.getSecondaryColor());
			banner.setBackgroundColor(cArt.getBackgroundColor());

		}

	}


}
