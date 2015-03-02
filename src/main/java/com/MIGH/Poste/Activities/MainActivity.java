package com.MIGH.Poste.Activities;





import com.MIGH.Poste.Adapters.DrawerListAdapter;
import com.MIGH.Poste.Events.EventNoticeClick;
import com.MIGH.Poste.Events.NoticeClick;
import com.MIGH.Poste.Fragments.EventsGridFragment;
import com.MIGH.Poste.Fragments.FeedsGridFragment;
import com.MIGH.Poste.Fragments.HomePageFragment;
import com.MIGH.Poste.Fragments.NoticeListFragment;
import com.MIGH.Poste.R;

import de.greenrobot.event.EventBus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import java.util.Set;


public class MainActivity extends ActionBarActivity
        implements FeedsGridFragment.onItemSelected	{
	DrawerLayout mainDrawer;
	ListView mainDrawerList;
	ActionBarDrawerToggle actionBarDrawerToggle;
	Context context;
	View actionView;
	ActionBar actionBar;
	Typeface quicksandRegular;
	Typeface quicksandLight;
	Typeface poiretOne;
	DrawerListAdapter drawerListAdapter;
	FragmentManager fManager = getSupportFragmentManager();
	Fragment fragment;
    static public String currentFrag="Poste";
    static public String userPreferences = "com.MIGH.Poste";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    Set <String>subscribedFeeds ;
    static public String sharedP_FEEDS_KEY="SUBFEEDS";
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		EventBus.getDefault().register(this);
		
		//Definitions and initializations
		context = this;
		mainDrawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
		mainDrawerList = (ListView) findViewById(R.id.main_drawerList);
		mainDrawerList.setDivider(null);

        //Setting up shared preferences
        sharedPreferences = getSharedPreferences(userPreferences, Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
        sharedEditor.putStringSet(sharedP_FEEDS_KEY,subscribedFeeds);

		
		
		
		//Font declarations
		poiretOne = Typeface.createFromAsset(getAssets(), "fonts/PoiretOne-Regular.ttf");
		quicksandRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
		quicksandLight = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.otf");
		
			
		setupActionBar();
		setupNavDrawer();
		setupDrawerList();

		//Attach initial home fragment
		fragment = new HomePageFragment();
		fManager
		.beginTransaction()
		.replace(R.id.main_FragmentSpace, fragment)
		.commit();
		


	//End of onCreate
	}


	
	public void setupDrawerList(){

		drawerListAdapter = new DrawerListAdapter(getApplicationContext(), R.id.main_drawerList);
//		AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(drawerListAdapter);
//		animationAdapter.setAbsListView(mainDrawerList);
		mainDrawerList.setAdapter(drawerListAdapter);
		mainDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mainDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
	
	public void setupActionBar(){
		actionBar = getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		
		

		LayoutInflater inflater = LayoutInflater.from(this);
		actionView = inflater.inflate(R.layout.custom_actionbar_title, null);
		((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setText(R.string.app_name);
		((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setTypeface(quicksandRegular);
		actionBar.setCustomView(actionView);
	}
	
	
	public void setupNavDrawer(){
		// Drawer/ActionBar toggle settings
		 actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainDrawer,
	                R.drawable.ic_drawerr, R.string.drawer_open, R.string.drawer_closed) {
			 
			 
			 				
	            /** Called when a drawer has settled in a completely closed state. */
	            public void onDrawerClosed(View view) {
	                super.onDrawerClosed(view);
	                setActionBarText(currentFrag);
	                //((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setText(currentFrag);
	            }

	            /** Called when a drawer has settled in a completely open state. */
	            public void onDrawerOpened(View drawerView) {
	                super.onDrawerOpened(drawerView);
	                setActionBarText("Poste");
	                //((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setText("Feeds");
	               
	                
	            }
	            
	            public void onDrawerSlide(View drawerView , float offset){
	            	super.onDrawerSlide(drawerView, offset);
//	            	if(offset >0){
//	            		setupDrawerList();
//	            	}
	            }
	            
	            public void onDrawerStateChanged(int newState){
	            	super.onDrawerStateChanged(newState);
	            }
	            
	            
	        };
	        
	      
	        
// Drawer Icon Settings 
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);    
		    actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
	       getSupportActionBar().setHomeButtonEnabled(true);    
			mainDrawer.setDrawerListener(actionBarDrawerToggle);
	}
	
	  public void setActionBarText(String title){
		  ((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setText(title);
      }
	
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Pass the event to ActionBarDrawerToggle, if it returns
	        // true, then it has handled the app icon touch event
	        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	        }
	        // Handle your other action bar items...

	        return super.onOptionsItemSelected(item);
	    }


    @Override
    public void onResume(){
        super.onResume();
        setupActionBar();
    }
	
	//Action bar drawer toggle methods
	 @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        actionBarDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        actionBarDrawerToggle.onConfigurationChanged(newConfig);
	       
	    }
	    	    
	 
	//Drawer list view onClick handler
		public class DrawerItemClickListener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	 //String option=(String) parent.getItemAtPosition(position);
	             String selected =(String) parent.getItemAtPosition(position);
	             selectItem(selected, position);
	             	             
	         }
	        
	        //Sets the drawer list item as selected, then swaps fragment, or jumps activities. Whichever happens.
	 		private void selectItem(String selected, int position) {
	 			 mainDrawerList.setItemChecked(position, true);
	 			 //mainDrawer.closeDrawer(mainDrawerList);
	 			 
	 			 Resources r = context.getResources();
	 			
	 			 String [] drawerOptions= r.getStringArray(R.array.DrawerOptions);
	 			 selected = drawerOptions[position];
	 			 if(selected.equals(drawerOptions[0])){
	 				 //If the selected is Home,
	 				 //swap in the home fragment, or do nothing if its already there
	 				fragment = new HomePageFragment();
	 				fManager
	 				.beginTransaction()
	 				.replace(R.id.main_FragmentSpace, fragment)
	 				.commit();
	 				 currentFrag = selected;
	 				 mainDrawer.closeDrawer(mainDrawerList);
	 			 }
	 			 else if(selected.equals(drawerOptions[1])){
	 				 //If the selected item is Feeds,
	 				 //swap in the Feeds Grid page
	 				 fragment = new FeedsGridFragment();
	 				 fManager
	 				 .beginTransaction()
	 				 .replace(R.id.main_FragmentSpace, fragment)
	 				 .commit();
	 				 currentFrag= selected;
	 				 mainDrawer.closeDrawer(mainDrawerList);

	 			 }
	 			 else if(selected.equals(drawerOptions[2])){
	 				 //If the selected item is Events
	 				 // Swap in the Events Grid Page
	 				 fragment = new EventsGridFragment();
	 				 fManager
	 				 .beginTransaction()
	 				 .replace(R.id.main_FragmentSpace, fragment)
	 				 .commit();
	 				currentFrag = selected;
	 				mainDrawer.closeDrawer(mainDrawerList);
	 			 }
	 		}
	    }
	
	@Override 
	public void onStart(){
		super.onStart();
		
	}
	
	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	
		//various interfaces from different fragments.
	@Override
	public  void startFeed(String feedID) {
		//Swap in NoticeListFragment with notices from the feed selected.
		Fragment frag  = new NoticeListFragment().newInstance(feedID, "Main");
		fManager
		.beginTransaction()
		.replace(R.id.main_FragmentSpace, frag)
		.addToBackStack("")
		.commit();
	}   
	
	public void onEvent(NoticeClick e){
		Intent intent = new Intent(this, NoticePageActivity.class);
		
		//Passing the selected Notice piece by piece to the Notice Page
		// Twas a bother to code, but until a way is found to pass the object directly,
		// YEAH.
		intent.putExtra("InfoID", e.getNoticemodel().getInfoID());
		intent.putExtra("PosterID", e.getNoticemodel().getPosterID());
		intent.putExtra("FeedID", e.getNoticemodel().FeedID);
		intent.putExtra("PostTime", e.getNoticemodel().PostTime);
		intent.putExtra("PostValidity",e.getNoticemodel().PostValidity);
		intent.putExtra("NoticeType", e.getNoticemodel().NoticeType);
		intent.putExtra("NoticeTitle", e.getNoticemodel().NoticeTitle);
		intent.putExtra("NoticeSubject", e.getNoticemodel().NoticeSubject);
		intent.putExtra("NoticeMessage", e.getNoticemodel().NoticeMessage);
		intent.putExtra("NoticeImage", e.getNoticemodel().NoticeImage);
		intent.putExtra("NoticeDoc", e.getNoticemodel().NoticeDoc);
		startActivity(intent);
	}
    public void onEvent(EventNoticeClick e){

       Intent intent = new Intent(this, EventPageActivity.class);

        intent.putExtra("EVENT_ID",e.getEventID());
        startActivity(intent);
    }
	
	
	
	
}

