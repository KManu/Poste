package com.MIGH.Poste.Activities;


import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.MIGH.Poste.Adapters.DatabaseAdapter;
import com.MIGH.Poste.Adapters.EventGridAdapter;
import com.MIGH.Poste.Adapters.FeedsGridAdaptor;
import com.MIGH.Poste.DataModels.EventNoticeModel;
import com.MIGH.Poste.DataModels.FeedsModel;
import com.MIGH.Poste.Fragments.NoticeListFragment;
import com.MIGH.Poste.R;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;


public class SearchableActivity extends ActionBarActivity {
    String query;
    String searchType="";
    DatabaseAdapter db;
    GridView gridView;
    ActionBar actionBar;
    View actionView;
    Typeface quicksandRegular;
    Typeface quicksandLight;
    Typeface poiretOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable_activity);
        db = new DatabaseAdapter(this);
        db.open();

        searchType = MainActivity.currentFrag;
        activitySetup();

        gridView = (GridView) findViewById(R.id.SearchablePage_gridView);

        query =  getIntent().getStringExtra(SearchManager.QUERY);
        //Toast.makeText(this,"Query was "+query,Toast.LENGTH_SHORT).show();

        //Options for searchType are Home, feeds and Events
        if(searchType.isEmpty()){
            Toast.makeText(this,"SearchType is Null",Toast.LENGTH_SHORT);

        }
        else if(searchType.contains("Feeds")){
            feedSearchShow(query);
        }
        else if(searchType.contains("Events")){
            eventSearchShow(query);
        }
        else{
            gridView.setBackgroundResource(R.drawable.img_nothing_screen);
        }

        db.close();
    }

    private void activitySetup() {
        //Font declarations
        poiretOne = Typeface.createFromAsset(getAssets(), "fonts/PoiretOne-Regular.ttf");
        quicksandRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
        quicksandLight = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.otf");

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));



        LayoutInflater inflater = LayoutInflater.from(this);
        actionView = inflater.inflate(R.layout.custom_actionbar_title, null);
        ((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setText("Search Results");
        ((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setTypeface(quicksandRegular);
        actionBar.setCustomView(actionView);
    }

    // basically used by the NoticeList Fragment to be able to change the actionbar text
    public void setActionBarText(String title){
        ((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setText(title);
    }

    private void eventSearchShow(String query) {
       ArrayList<EventNoticeModel> toDisp = db.EventSearch(query);

        if(toDisp.isEmpty()){
            gridView.setBackgroundResource(R.drawable.img_nothing_screen);
            return;
        }

        //Adapter set up
        EventGridAdapter eventAdapter = new EventGridAdapter(this,toDisp);
        AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(eventAdapter);
        animationAdapter.setAbsListView(gridView);
        gridView.setAdapter(animationAdapter);

        // OnClick Listener for the event gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                EventNoticeModel eveToDisp = (EventNoticeModel)parent.getItemAtPosition(position);
                //EventBus.getDefault().post(new EventNoticeClick(eveToDisp));
                Intent intent = new Intent(getApplicationContext(), EventPageActivity.class);

                intent.putExtra("EVENT_ID",eveToDisp.getInfoID());
                startActivity(intent);
            }
        });

    }

    private void feedSearchShow(String query) {
        ArrayList<FeedsModel> toDisp = db.FeedSearch(query);

        if(toDisp.isEmpty()){
            gridView.setBackgroundResource(R.drawable.img_nothing_screen);
            return;
        }

        FeedsGridAdaptor feedAdapter = new FeedsGridAdaptor(this.getApplicationContext(),toDisp);

        AnimationAdapter animationAdapter = new ScaleInAnimationAdapter(feedAdapter);
        animationAdapter.setAbsListView(gridView);
        gridView.setAdapter(animationAdapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Start up the Notice List fragment for the feed selected.
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //swap in the Notice List fragment for the feed that is clicked, but first, get rid of the gridview.
                gridView.setVisibility(View.GONE);
                FeedsModel feedSelected = (FeedsModel)parent.getItemAtPosition(position);
                Fragment frag = new NoticeListFragment().newInstance(feedSelected.FeedID, "Search");
                FragmentManager fManager = getSupportFragmentManager();
                fManager
                        .beginTransaction()
                        .replace(R.id.SearchablePage_frameLayout, frag)
                        .commit();
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_feeds_searchable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
