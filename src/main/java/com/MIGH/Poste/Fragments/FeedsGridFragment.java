package com.MIGH.Poste.Fragments;

import java.util.ArrayList;

import com.MIGH.Poste.Activities.MainActivity;
import com.MIGH.Poste.R;
import com.MIGH.Poste.Adapters.DatabaseAdapter;
import com.MIGH.Poste.Adapters.FeedsGridAdaptor;
import com.MIGH.Poste.DataModels.FeedsModel;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class FeedsGridFragment extends Fragment{
	FeedsGridAdaptor gridAdapter;
	GridView feedsGridView;
	DatabaseAdapter db;
	ArrayList <FeedsModel> Feeds;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.feeds_grid_page, container, false);
	}
	

			//Its an interface
	//Pass in it the FeedID of the selected grid item.
	public interface onItemSelected{
		public  void startFeed(String feedID);
	}
	private onItemSelected listener;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
		feedsGridView = (GridView) getActivity().findViewById(R.id.FeedsGridPage_grid);
		db = new DatabaseAdapter(getActivity().getApplicationContext());
		db.open();
		((MainActivity)getActivity()).setActionBarText("Feeds");
		Feeds = db.getAllFeeds();
		if(Feeds.isEmpty()){
			feedsGridView.setBackgroundResource(R.drawable.img_nothing_screen);
		}
		String len = Feeds.size() +"";
//		Toast sizz = Toast.makeText(getActivity(),len , Toast.LENGTH_SHORT) ;
//		sizz.show();
		gridAdapter = new FeedsGridAdaptor(getActivity().getApplicationContext(),Feeds);
		
		AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(gridAdapter);
		animationAdapter.setAbsListView(feedsGridView);
		feedsGridView.setAdapter(animationAdapter);

		
		feedsGridView.setOnItemClickListener(new  AdapterView.OnItemClickListener() {
			//Create an intent and pass it, with the 
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					FeedsModel feedSelected = (FeedsModel) parent.getItemAtPosition(position);
					
					listener.startFeed(feedSelected.FeedID);
			}
		});
		
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater ){
        menuInflater.inflate(R.menu.feed_grid_menu,menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.FeedsGridMenu_searchButton).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        db.open();
        if (item.getItemId() == R.id.FeedsGridMenu_refreshButton) {
            //Fetch from the internet. and then,
            //Reload items from the DB
            ArrayList<FeedsModel> refreshedFeeds = db.getAllFeeds();
            gridAdapter = new FeedsGridAdaptor(getActivity().getApplicationContext(),refreshedFeeds);

            AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(gridAdapter);
            animationAdapter.setAbsListView(feedsGridView);
            feedsGridView.setAdapter(animationAdapter);
        }
        db.close();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity)getActivity()).setActionBarText("Feeds");
    }

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof onItemSelected) {
	        listener = (onItemSelected) activity;
	      } else {
	        throw new ClassCastException(activity.toString()
	            + " must implement FeedsGridFragment.OnItemSelected");
	      }
	}
	
	
	

}
