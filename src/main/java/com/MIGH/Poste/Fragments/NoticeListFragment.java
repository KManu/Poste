package com.MIGH.Poste.Fragments;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.MIGH.Poste.Activities.InformationActivity;
import com.MIGH.Poste.Activities.SearchableActivity;
import com.MIGH.Poste.Events.NoticeClick;
import com.MIGH.Poste.Activities.MainActivity;
import com.MIGH.Poste.R;
import com.MIGH.Poste.Adapters.*;
import com.MIGH.Poste.DataModels.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import de.greenrobot.event.EventBus;

import static android.support.v4.app.ActivityCompat.invalidateOptionsMenu;

public class NoticeListFragment extends Fragment {
	DatabaseAdapter db;
	ArrayList<NoticeModel> notices;
	FeedNoticeListAdapter noticeListAdapter;
	ListView noticeList;
	String TAG = "NoticeListFragment";
    String caller ;
    String feedID;
	SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEditor;
    Menu menu;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.feed_notice_list_page,container , false );
	}
	
	public NoticeListFragment newInstance (String feedID, String caller){
		NoticeListFragment nFrag = new NoticeListFragment();
		Bundle args = new Bundle();
		args.putString("FeedID", feedID);
        args.putString("Caller",caller);
		Log.v(TAG, "NoticeListFragment started for feed " +feedID);
		nFrag.setArguments(args);
		return nFrag;
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {	
		super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        sharedPreferences = getActivity().getSharedPreferences(MainActivity.userPreferences, Context.MODE_PRIVATE);

        db = new DatabaseAdapter(getActivity().getApplicationContext());
		db.open();


		
		//Would have to get which feed was selected from the main activity
		feedID = getArguments().getString("FeedID");

        //Decide on what activity to cast the activity object to.
        //Depends on who calls the fragment
        caller = getArguments().getString("Caller");
        if(caller.contains("Main")){
            ((MainActivity)getActivity()).setActionBarText(db.getFeed(feedID).FeedName);
        }
        else if(caller.contains("Search")){
            ((SearchableActivity)getActivity()).setActionBarText(db.getFeed(feedID).FeedName);
        }

		if(feedID.isEmpty()){
			noticeList.setBackgroundResource(R.drawable.img_nothing_screen);
			return;
		}
		notices = db.getAllFeedNotices(feedID);


		noticeListAdapter = new FeedNoticeListAdapter(notices, getActivity());
		noticeList = (ListView) getActivity().findViewById(R.id.NoticeListPageList);
		
		if(notices.isEmpty()){
			noticeList.setBackgroundResource(R.drawable.img_nothing_screen);
		}
		else{
			AnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(noticeListAdapter);
			animationAdapter.setAbsListView(noticeList);
			//Log.v(TAG,""+notices.size());
			noticeList.setAdapter(animationAdapter);
			

			 noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					NoticeModel notice = (NoticeModel)parent.getItemAtPosition(position);
					EventBus.getDefault().post(new NoticeClick(notice));
				}
				
			});
		}
        db.close();
		
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notice_list_menu, menu);

        db.open();
        //first, long ass check to se if the feed is already displayed
        //if it is, then i set the icon to the added button.
        Set<String> subbedFeeds = new HashSet<String>(sharedPreferences.getStringSet(MainActivity.sharedP_FEEDS_KEY, new HashSet<String>()));

            String[] subbedFeedsArr ;
            subbedFeedsArr = subbedFeeds.toArray(new String[subbedFeeds.size()]);
            String sF= "";
            for(int i=0; i<subbedFeedsArr.length;i++){
                sF = sF+ subbedFeedsArr[i]+" ";
            }

            if (sF.contains(db.getFeed(feedID).FeedID)){ // This means the feed is already added
                menu.findItem(R.id.NoticeListMenu_addButton).setIcon(R.drawable.ic_added_button);
            }

            db.close();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        db.open();
        //first, long ass check to se if the feed is already displayed
        //if it is, then i set the icon to the added button.
        Set<String> subbedFeeds = new HashSet<String>(sharedPreferences.getStringSet(MainActivity.sharedP_FEEDS_KEY, new HashSet<String>()));

        String[] subbedFeedsArr ;
        subbedFeedsArr = subbedFeeds.toArray(new String[subbedFeeds.size()]);
        String sF= "";
        for(int i=0; i<subbedFeedsArr.length;i++){
            sF = sF+ subbedFeedsArr[i]+" ";
        }

        if (sF.contains(db.getFeed(feedID).FeedID)){ // This means the feed is already added
            menu.findItem(R.id.NoticeListMenu_addButton).setIcon(R.drawable.ic_cancel_button);
            menu.findItem(R.id.NoticeListMenu_addButton).setTitle("Unsubscribe");
        }
        else{
            menu.findItem(R.id.NoticeListMenu_addButton).setIcon(R.drawable.ic_add_button);
            menu.findItem(R.id.NoticeListMenu_addButton).setTitle("Subscribe");
        }

        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        db.open();
        if(item.getItemId() == R.id.NoticeListMenu_Refresh){
            //Fetch from the internet. and then,
            //Reload items from the DB
            notices = db.getAllFeedNotices(feedID);
            AnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(noticeListAdapter);
            animationAdapter.setAbsListView(noticeList);
            //Log.v(TAG,""+notices.size());
            noticeList.setAdapter(animationAdapter);

        }
        else if (item.getItemId() == R.id.NoticeListMenu_addButton) {
            // if the add button is clicked, add the feed to the user list in the shared preferences
            //then change the icon
            Set<String> subbedFeeds = new HashSet<String>(sharedPreferences.getStringSet(MainActivity.sharedP_FEEDS_KEY, new HashSet<String>()));

            String[] subbedFeedsArr ;
            subbedFeedsArr = subbedFeeds.toArray(new String[subbedFeeds.size()]);
            String sF= "";
            for(int i=0; i<subbedFeedsArr.length;i++){
                sF = sF+ subbedFeedsArr[i]+" ";
            }

            if (sF.contains(db.getFeed(feedID).FeedID)){ // This means the feed is already added
                sharedEditor = sharedPreferences.edit();
                Set<String> selectedFeedSet = new HashSet<String>(sharedPreferences.getStringSet(MainActivity.sharedP_FEEDS_KEY, new HashSet<String>()));
                selectedFeedSet.remove(db.getFeed(feedID).FeedID);
                sharedEditor.putStringSet(MainActivity.sharedP_FEEDS_KEY, selectedFeedSet);
                sharedEditor.commit();
                invalidateOptionsMenu(getActivity());
                Toast.makeText(getActivity(),"Unsubscribed from "+db.getFeed(feedID).FeedName,Toast.LENGTH_SHORT).show();

            }
            else{
                sharedEditor = sharedPreferences.edit();
                Set<String> selectedFeedSet = new HashSet<String>(sharedPreferences.getStringSet(MainActivity.sharedP_FEEDS_KEY, new HashSet<String>()));
                selectedFeedSet.add(db.getFeed(feedID).FeedID);
                sharedEditor.putStringSet(MainActivity.sharedP_FEEDS_KEY, selectedFeedSet);
                sharedEditor.commit();
                invalidateOptionsMenu(getActivity());
                Toast.makeText(getActivity(),"Subscribed to "+db.getFeed(feedID).FeedName,Toast.LENGTH_SHORT).show();


            }






            //The following section is basically a check.
            /*
            String[] subbedFeeds ;
            subbedFeeds = sharedPreferences.getStringSet(MainActivity.sharedP_FEEDS_KEY,null).toArray(new String[sharedPreferences.getStringSet(MainActivity.sharedP_FEEDS_KEY, null).size()]);
            String sF= "";
            for(int i=0; i<subbedFeeds.length;i++){
                sF = sF+ subbedFeeds[i]+" ";
            }

            Toast.makeText(getActivity(),sF,Toast.LENGTH_SHORT).show();
            */

        }
        else if(item.getItemId()== R.id.NoticeListMenu_information){
            //Start the information activity
            Intent infoStarter = new Intent(getActivity().getApplicationContext(), InformationActivity.class);
            infoStarter.putExtra("CONTENT","Feed");
            infoStarter.putExtra("FEEDID",feedID);
            startActivity(infoStarter);
        }
        db.close();
        return super.onOptionsItemSelected(item);
    }



	@Override
    public void onResume(){
        super.onResume();
        db.open();
        if(caller.contains("Main")){
            ((MainActivity)getActivity()).setActionBarText(db.getFeed(feedID).FeedName);
        }
        else if(caller.contains("Search")){
            ((SearchableActivity)getActivity()).setActionBarText(db.getFeed(feedID).FeedName);
        }
        db.close();
    }
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	

}
