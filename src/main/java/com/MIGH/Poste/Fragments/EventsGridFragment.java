package com.MIGH.Poste.Fragments;

import java.util.ArrayList;

import com.MIGH.Poste.Events.EventNoticeClick;
import com.MIGH.Poste.Activities.MainActivity;
import com.MIGH.Poste.R;
import com.MIGH.Poste.Adapters.DatabaseAdapter;
import com.MIGH.Poste.Adapters.EventGridAdapter;
import com.MIGH.Poste.DataModels.EventNoticeModel;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
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

import de.greenrobot.event.EventBus;

public class EventsGridFragment extends Fragment {
	GridView eventsGridView;
	EventGridAdapter gridAdapter;
	DatabaseAdapter db;
	ArrayList<EventNoticeModel> refreshedEvents;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.event_grid_page, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {		
		super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity)getActivity()).setActionBarText("Events");
        init();
        GridAsync gridAsync=  new GridAsync();
        gridAsync.execute();


        // OnClick Listener for the gridview
        eventsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                EventNoticeModel eveToDisp = (EventNoticeModel)parent.getItemAtPosition(position);
                EventBus.getDefault().post(new EventNoticeClick(eveToDisp));
            }
        });



		
	}

    public void init(){
        //Method for initialising everything.
        db = new DatabaseAdapter(getActivity().getApplicationContext());
        eventsGridView = (GridView) getActivity().findViewById(R.id.EventGridPage_GridView);

    }


    public class GridAsync extends AsyncTask<Void, Void, ArrayList<EventNoticeModel>>{
        /*This Async class is concerned with 2 things, in the event getting procedure.
        * First, to get the event objects from the database.
        * Second, set up the Adapter for the gird and make sure it executes smoothly.*/

        @Override
        protected ArrayList<EventNoticeModel> doInBackground(Void...params){
            //Concerned with getting the event objects
            db.open();
            refreshedEvents = db.getAllEventNotices();
            db.close();
            return refreshedEvents;

        }
        protected void onPostExecute( ArrayList<EventNoticeModel> events){
            //Check if returned an empty container, and display the appropriate screen
            if(events.isEmpty()){
                eventsGridView.setBackgroundResource(R.drawable.img_nothing_screen);
            }
            // Else, set up the the grid adapter, and everything in it.
            gridAdapter = new EventGridAdapter(getActivity().getApplicationContext(), events);
            AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(gridAdapter);
            animationAdapter.setAbsListView(eventsGridView);
            eventsGridView.setAdapter(animationAdapter);



        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_grid_menu, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.EventsGridMenu_searchButton).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        db.open();
        if (item.getItemId() == R.id.EventsGridMenu_refreshButton) {
            //Fetch from the internet. and then,
            //Reload items from the DB
            refreshedEvents = db.getAllEventNotices();
            if (refreshedEvents.isEmpty()) {
                eventsGridView.setBackgroundResource(R.drawable.img_nothing_screen);
            }
            // Else, set up the the grid adapter, and everything in it.
            gridAdapter = new EventGridAdapter(getActivity().getApplicationContext(), refreshedEvents);
            AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(gridAdapter);
            animationAdapter.setAbsListView(eventsGridView);
            eventsGridView.setAdapter(animationAdapter);


        }
        db.close();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity)getActivity()).setActionBarText("Events");
    }

    @Override
	public void onAttach(Activity activity) {
		
		super.onAttach(activity);
	}
	
}
