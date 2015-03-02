package com.MIGH.Poste.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.MIGH.Poste.Adapters.DatabaseAdapter;
import com.MIGH.Poste.DataModels.FeedsModel;
import com.MIGH.Poste.R;

public class InformationActivity extends ActionBarActivity {

    //Views
    TextView textHeader;
    TextView textBody;
    View actionView;
    ActionBar actionBar;
    //Others
    Typeface quicksandRegular;
    Typeface quicksandLight;
    Typeface poiretOne;
    DatabaseAdapter db;
    String header;
    String body;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        //An intent specifying the type of information to be shown is received and
        //processed. The required information is then displayed.
        //Display feed description information
        //display About information
        //display Legal information

        Intent startIntent = getIntent();
        db = new DatabaseAdapter(getApplicationContext());
        db.open();

        if(startIntent.getStringExtra("CONTENT").contains("Feed")){
            String feedID = startIntent.getStringExtra("FEEDID");
            FeedsModel feedToDisp = db.getFeed(feedID);
            header = feedToDisp.FeedName;
            body = feedToDisp.FeedDescription;
            init("Information");

        }
        else if(startIntent.getStringExtra("CONTENT").contains("About")){
            init("About the Application");
        }
        else if(startIntent.getStringExtra("CONTENT").contains("Legal")){
            init("Legal");
        }


        populate();
        db.close();

    }



    private void init(String actionBartext) {
        textBody = (TextView)findViewById(R.id.InformationPage_textBody);
        textHeader = (TextView)findViewById(R.id.InformationPage_textHeader);

        //Font initialisations
        poiretOne = Typeface.createFromAsset(getAssets(), "fonts/PoiretOne-Regular.ttf");
        quicksandRegular = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Regular.otf");
        quicksandLight = Typeface.createFromAsset(getAssets(), "fonts/Quicksand-Light.otf");

        //Actionbar setup
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));



        LayoutInflater inflater = LayoutInflater.from(this);
        actionView = inflater.inflate(R.layout.custom_actionbar_title, null);
        ((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setText(actionBartext);
        ((TextView)actionView.findViewById(R.id.custom_actionBar_text)).setTypeface(quicksandRegular);
        actionBar.setCustomView(actionView);

    }

    private void populate() {
        textHeader.setText(header);
        textBody.setText(body);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_information, menu);
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
