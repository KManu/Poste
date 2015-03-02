package com.MIGH.Poste.Activities;

import com.MIGH.Poste.Adapters.DatabaseAdapter;
import com.MIGH.Poste.DataModels.NoticeModel;
import com.MIGH.Poste.R;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class NoticePageActivity extends ActionBarActivity {
	String TAG = "NoticePageActivity";
	String activityName = "";
	//In the header
	RelativeLayout headerRelativeLayout;
	ImageView headerImageType;
	TextView headerPostTime;
	TextView headerPoster;
	TextView headerPostSubject;
	//Body Widgets
	ScrollView bodyScrollView;
	TextView bodyPostTitle;
	TextView bodyPostMessage;
	TextView bodyDocumentLink;
	ActionBar actionBar;
	
	View actionView;
	Typeface quicksandRegular;
	Typeface quicksandLight;
	Typeface poiretOne;
	
	NoticeModel noticeToDisp=null;
	DatabaseAdapter db;
	String infoID=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_full_page);
		Log.v(TAG,"Started NoticePageActivity");
		db = new DatabaseAdapter(getApplicationContext());
		db.open();
		 //stripIntent();
        infoID =getIntent().getStringExtra("InfoID");
		noticeToDisp = db.getNotice(infoID);
		Log.v(TAG, "Notice displayed " +noticeToDisp.getNoticeTitle());

		init();
		setupActionBar();
		populate();
	}
	
	public void stripIntent(){
		//Recreate the NoticeModel object here.
		Intent intent = getIntent();
		noticeToDisp = new NoticeModel(
				intent.getStringExtra("InfoID"),
				intent.getStringExtra("PosterID"),
				intent.getStringExtra("FeedID"),
				intent.getStringExtra("PostTime"),
				intent.getStringExtra("PostValidity"),
				intent.getStringExtra("NoticeType"),
				intent.getStringExtra("NoticeTitle"),
				intent.getStringExtra("NoticeSubject"),
				intent.getStringExtra("NoticeMessage"),
				intent.getStringExtra("NoticeImage"),
				intent.getStringExtra("NoticeDoc")
				);
		infoID =intent.getStringExtra("InfoID");
		Log.v(TAG, "Stripped ID "+infoID);
	}
	
	public void init(){
		//the Type image would be determined based on the type of message it is.
		String type = noticeToDisp.getNoticeType();
		
		//font initialisations
		poiretOne = Typeface.createFromAsset(getAssets(), "fonts/PoiretOne-Regular.ttf");
		quicksandRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
		quicksandLight = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.otf");
		
		
		/*
		 * OLD Header Resources, before the include
		headerRelativeLayout = (RelativeLayout) findViewById(R.id.NoticePage_HeaderRelativeView);
		headerImageType = (ImageView) findViewById(R.id.NoticePage_Header_TypeImage);
		headerPostTime = (TextView) findViewById(R.id.NoticePage_Header_PostTime);
		headerPoster = (TextView)findViewById(R.id.NoticePage_Header_Poster);
		headerPostSubject = (TextView)findViewById(R.id.NoticePage_Header_Subject);
		*/
		
		headerRelativeLayout = (RelativeLayout) findViewById(R.id.PostCard_RelativeLayout);
		headerImageType = (ImageView) findViewById(R.id.PostCard_TypeImage);
		headerPostTime = (TextView) findViewById(R.id.PostCard_PostTime);
		headerPoster = (TextView) findViewById(R.id.PostCard_Poster);
		headerPostSubject = (TextView) findViewById(R.id.PostCard_Subject);
		
		bodyScrollView = (ScrollView) findViewById(R.id.NoticePage_ScrollView);
		bodyPostTitle = (TextView) findViewById(R.id.NoticePage_Title);
		bodyPostMessage = (TextView) findViewById(R.id.NoticePage_Message);
		bodyDocumentLink = (TextView) findViewById(R.id.NoticePage_DocLink);
	
		//Extra individual settings to each view
		//Releasing max length on Subject text
		InputFilter iFilter1 = new InputFilter.LengthFilter(100);   
		InputFilter iFilter2 = new InputFilter.AllCaps();
		InputFilter [] filters = {iFilter1};
		headerPostSubject.setFilters(filters);
		
	}
	

	
	public void populate(){
		
		headerPostTime.setText(noticeToDisp.getPostTime());
		String posterTitle = (db.getFeedPoster(noticeToDisp.getPosterID()).getPosterTitle());
		headerPoster.setText(posterTitle);
		headerPostSubject.setText(noticeToDisp.getNoticeSubject());
		bodyPostTitle.setText(noticeToDisp.getNoticeTitle());
		bodyPostMessage.setText(noticeToDisp.getNoticeMessage());
		bodyDocumentLink.setText(noticeToDisp.getNoticeDoc());
	}
	
	public void setupActionBar(){
		
		
		actionBar = getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		

		LayoutInflater inflater = LayoutInflater.from(this);
		actionView = inflater.inflate(R.layout.custom_actionbar_title, null);
		((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setText(noticeToDisp.getNoticeTitle());
		((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setTypeface(quicksandRegular);
		actionBar.setCustomView(actionView);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);   
	}

}









