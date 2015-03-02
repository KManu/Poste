package com.MIGH.Poste.Activities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.michaelevans.colorart.library.ColorArt;

import com.MIGH.Poste.R;
import com.MIGH.Poste.R.id;
import com.MIGH.Poste.DataModels.EventNoticeModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.MIGH.Poste.Adapters.DatabaseAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventPageActivity extends ActionBarActivity {
    //Actionbar Fonts
    Typeface quicksandRegular;
    Typeface quicksandLight;
    Typeface poiretOne;

	//Banner Widgets
	RelativeLayout banner;
	ImageView eventType;
	TextView eventName;
	ImageButton mapButton;
	
	//Body widgets
	ImageView eventImage;
	TextView eventOrgs;
	TextView eventTime;
	TextView eventVenue;
	TextView eventDetails;
	TextView eventContact;
	
	DatabaseAdapter db;
	EventNoticeModel eventToDisp;
	String filepath;
    ActionBar actionBar;
    String url;
    View actionView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_full_page);

        //catch, then strip intent to get the info ID. Query the database using that ID and fill views with appropriate info.
        Intent caught = getIntent();
        String InfoID = caught.getExtras().getString("EVENT_ID");

		//Retrieve from the database
		db = new DatabaseAdapter(this);
		db.open();
		eventToDisp = db.getEventNotice(InfoID);
        db.close();


		init();
        setup();
        setupActionBar();
        placeimg();
        extractColors();
        getSupportActionBar().setTitle(eventToDisp.getEventName());
	}

    private void extractColors() {
        Glide.with(this)
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
    }


    private void init() {
        poiretOne = Typeface.createFromAsset(getAssets(), "fonts/PoiretOne-Regular.ttf");
        quicksandRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
        quicksandLight = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.otf");

        banner = (RelativeLayout) findViewById(R.id.EventPage_Banner_RelativeLayout);
        eventType = (ImageView) findViewById(R.id.EventPage_Banner_eventTypeImage);
        eventName = (TextView) findViewById(R.id.EventPage_Banner_EventName);
        mapButton = (ImageButton) findViewById(R.id.EventPage_Banner_MapButton);

        eventImage = (ImageView) findViewById(id.EventPage_eventImage);
        eventOrgs = (TextView) findViewById(R.id.EventPage_eventOrgs);
        eventTime = (TextView) findViewById(id.EventPage_eventTime);
        eventVenue = (TextView) findViewById(R.id.EventPage_eventVenue);
        eventDetails = (TextView) findViewById(R.id.EventPage_eventDetails);
        eventContact = (TextView) findViewById(R.id.EventPage_eventContact);
    }

    private void setup() {



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


        String urls[]={
                "http://lorempixel.com/800/600/cats",
                "http://lorempixel.com/500/700/",
                "http://lorempixel.com/900/900/",
                "http://lorempixel.com/1000/1200/"
        };
        Random rand = new Random();
        url =  urls[ rand.nextInt(urls.length-1)];

    }


    public void setupActionBar(){
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));



        LayoutInflater inflater = LayoutInflater.from(this);
        actionView = inflater.inflate(R.layout.custom_actionbar_title, null);
        ((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setText(eventToDisp.getEventName());
        ((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setTypeface(quicksandRegular);
        actionBar.setCustomView(actionView);
    }

    private String imgSelect(){
        //returns file path to the event image. Upon final implementation, the image would probably have
        //to be copied from the database into assets or downloaded directly into assets.
        String filep[]= {
                "Images/img_ (1).jpg",
                "Images/img_ (2).jpg",
                "Images/img_ (3).jpg",
                "Images/img_ (4).jpg",
                "Images/img_ (5).jpg",
                "Images/img_ (6).jpg",
                "Images/img_ (7).jpg",
                "Images/img_ (8).jpg"
        };
        Random rand = new Random();
        return filep[ rand.nextInt(filep.length-1)];

    }



    private void placeimg(){

        //SubsamplingScaleImageView postImg = (SubsamplingScaleImageView) findViewById(id.EventPage_eventImage);
        //postImg.setImageAsset(filepath);



        Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.image_loading_spinner_gif)
                .fitCenter()
                .error(R.drawable.img_super_meat)
                .crossFade()
                .into(eventImage);

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
