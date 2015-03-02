package com.MIGH.Poste.Adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.MIGH.Poste.DataModels.EventNoticeModel;
import com.MIGH.Poste.DataModels.EventPosterModel;
import com.MIGH.Poste.DataModels.FeedPosterModel;
import com.MIGH.Poste.DataModels.FeedsModel;
import com.MIGH.Poste.DataModels.NoticeModel;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseAdapter {
	private Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase PosteDB;
	
	/*
	 * Declarations of constants to be used throughout the application. 
	 */
	private final String TAG ="DatabaseAdapter";
	
	/*
	 * TODO
	 * Table names (Pref by DATABASE_TABLE_) 
	 * Column names(Pref by KEY_ )
	 * Column numbers(Pref by COL_) Zero-indexed. 
	 * 
	 */
	//Database name
	public final String DATABASE_NAME="Poste";
	
	//Tables
	public final String DATABASE_TABLE_POSTER="Poster";
	public final String DATABASE_TABLE_POST="Post";
	public final String DATABASE_TABLE_FEEDS = "Feeds";
	/*
	public final String DATABASE_TABLE_EVENT_NOTICE="EventNotice";
	public final String DATABASE_TABLE_NOTICE="Notices";
	*/
	
	/*
	 *			  Post table information
	 */
	//Post keys
	public final String POST_KEY_INFOID="InfoID";
	public final String POST_KEY_POSTERID="PosterID";
	public final String POST_KEY_FEEDID ="FeedID";
	public final String POST_KEY_POSTTIME="PostTime";
	public final String POST_KEY_POSTVALIDITY="PostValidity";
	public final String POST_KEY_POSTTYPE="PostType";
	public final String EVENTNOTICE_KEY_EVENTNAME="EventName";
	public final String EVENTNOTICE_KEY_EVENTTYPE="EventType";
	public final String EVENTNOTICE_KEY_EVENTTIME="EventTime";
	public final String EVENTNOTICE_KEY_EVENTVENUE="EventVenue";
	public final String EVENTNOTICE_KEY_EVENTORGANISERS="EventOrganisers";
	public final String EVENTNOTICE_KEY_EVENTDETAILS="EventDetails";
	public final String EVENTNOTICE_KEY_EVENTCONTACT="EventContact";
	public final String EVENTNOTICE_KEY_EVENTEVENTGPS="EventGPS";
	public final String EVENTNOTICE_KEY_EVENTEVENTIMAGE="EventImage";
	public final String NOTICE_KEY_NOTICETYPE="NoticeType";
	public final String  NOTICE_KEY_NOTICETITLE="NoticeTitle";
	public final String  NOTICE_KEY_NOTICESUBJECT="NoticeSubject";
	public final String  NOTICE_KEY_NOTICEMESSAGE="NoticeMessage";
	public final String  NOTICE_KEY_NOTICEIMAGE="NoticeImage";
	public final String  NOTICE_KEY_NOTICEDOC="NoticeDoc";
	//Post Columns info;
	public final int POST_COL_INFOID =0;
	public final int POST_COL_POSTERID=1;
	public final int POST_COL_FEEDID = 2;
	public final int POST_COL_POSTTIME=3;
	public final int POST_COL_POSTVALIDITY=4;
	public final int POST_COL_POSTTYPE=5;
	public final int EVENTNOTICE_COL_EVENTNAME=6;
	public final int EVENTNOTICE_COL_EVENTTYPE=7;
	public final int EVENTNOTICE_COL_EVENTTIME=8;
	public final int EVENTNOTICE_COL_EVENTVENUE=9;
	public final int EVENTNOTICE_COL_EVENTORGANISERS=10;
	public final int EVENTNOTICE_COL_EVENTDETAILS=11;
	public final int EVENTNOTICE_COL_EVENTCONTACT=12;
	public final int EVENTNOTICE_COL_EVENTGPS=13;
	public final int EVENTNOTICE_COL_EVENTIMAGE=14;
	public final int NOTICE_COL_NOTICETYPE=15;
	public final int NOTICE_COL_NOTICETITLE=16;
	public final int NOTICE_COL_NOTICESUBJECT=17;
	public final int NOTICE_COL_NOTICEMESSAGE=18;
	public final int NOTICE_COL_NOTICEIMAGE=19;
	public final int NOTICE_COL_NOTICEDOC=20;
	
	
	/*
	 * 				Poster table information
	 */
	//Poster keys
	public final String POSTER_KEY_POSTERID="PosterID";
	public final String POSTER_KEY_POSTERNAME ="PosterName";
	public final String POSTER_KEY_POSTERCONTACTNUMBER="PosterContactNumber";
	public final String POSTER_KEY_POSTERCONTACTLOCATION="PosterContactLocation";
	public final String POSTER_KEY_POSTERTITLE = "PosterTitle";
	public final String POSTER_KEY_POSTERENABLED = "PosterEnabled";
	//Poster column indexes
	public final int POSTER_COL_POSTERID=0;
	public final int POSTER_COL_POSTERNAME=1;
	public final int POSTER_COL_POSTERCONTACTNUMBER=2;
	public final int POSTER_COL_POSTERCONTACTLOCATION=3;
	public final int POSTER_COL_POSTERTITLE=4;
	public final int POSTER_COL_POSTERENABLED=5;
	
	/*
	 * 				Notice table information
	 *
	//Notice keys
	public final String NOTICE_KEY_INFOID ="InfoID";
	public final String NOTICE_KEY_NOTICETYPE="NoticeType";
	public final String  NOTICE_KEY_NOTICETITLE="NoticeTitle";
	public final String  NOTICE_KEY_NOTICESUBJECT="NoticeSubject";
	public final String  NOTICE_KEY_NOTICEMESSAGE="NoticeMessage";
	public final String  NOTICE_KEY_NOTICEIMAGE="NoticeImage";
	public final String  NOTICE_KEY_NOTICEDOC="NoticeDoc";
	//Notice column indexes=
	public final int NOTICE_COL_INFOID=0;
	public final int NOTICE_COL_NOTICETYPE=1;
	public final int NOTICE_COL_NOTICETITLE=2;
	public final int NOTICE_COL_NOTICESUBJECT=3;
	public final int NOTICE_COL_NOTICEMESSAGE=4;
	public final int NOTICE_COL_NOTICEIMAGE=5;
	public final int NOTICE_COL_NOTICEDOC=6;
	
	/*
	 * 				Event Notice table information
	 *
	//Event Notice keys
	public final String EVENTNOTICE_KEY_INFOID="InfoID";
	public final String EVENTNOTICE_KEY_EVENTNAME="EventName";
	public final String EVENTNOTICE_KEY_EVENTTYPE="EventType";
	public final String EVENTNOTICE_KEY_EVENTTIME="EventTime";
	public final String EVENTNOTICE_KEY_EVENTVENUE="EventVenue";
	public final String EVENTNOTICE_KEY_EVENTORGANISERS="EventOrganisers";
	public final String EVENTNOTICE_KEY_EVENTDETAILS="EventDetails";
	public final String EVENTNOTICE_KEY_EVENTCONTACT="EventContact";
	public final String EVENTNOTICE_KEY_EVENTEVENTGPS="EventGPS";
	public final String EVENTNOTICE_KEY_EVENTEVENTIMAGE="EventImage";
	//Event Notice column indexes
	public final int EVENTNOTICE_COL_INFOID=0;
	public final int EVENTNOTICE_COL_EVENTNAME=1;
	public final int EVENTNOTICE_COL_EVENTTYPE=2;
	public final int EVENTNOTICE_COL_EVENTTIME=3;
	public final int EVENTNOTICE_COL_EVENTVENUE=4;
	public final int EVENTNOTICE_COL_EVENTORGANISERS=5;
	public final int EVENTNOTICE_COL_EVENTDETAILS=6;
	public final int EVENTNOTICE_COL_EVENTCONTACT=7;
	public final int EVENTNOTICE_COL_EVENTGPS=8;
	public final int EVENTNOTICE_COL_EVENTIMAGE=9;
	
	
	 * 				Event Poster table information
	 *
	//Event Poster keys
	public final String EVENTPOSTER_KEY_POSTERID="PosterID";
	public final String EVENTPOSTER_KEY_POSTERENABLED="PosterEnabled";
	//Event Poster column indexes
	public final int EVENTPOSTER_COL_POSTERID=0;
	public final int EVENTPOSTER_COL_POSTERENABLED=1;
	
	/*
	 * 				Feed Poster table information
	 *
	//Feed Poster keys
	public final String FEEDPOSTER_KEY_POSTERID="PosterID";
	public final String FEEDPOSTER_KEY_POSTERTITILE="PosterTitle";
	//Feed Poster column indexes
	public final int FEEDPOSTER_COL_POSTERID=0;
	public final int FEEDPOSTER_COL_POSTERTITLE=1;
	*/
	
	
	/*
	 * 				Feeds table information
	 */
	//Feeds table keys 
	public final String FEEDS_KEY_FEEDID = "FeedID";
	public final String FEEDS_KEY_POSTERID = "PosterID";
	public final String FEEDS_KEY_FEEDNAME = "FeedName";
	public final String FEEDS_KEY_FEEDDESCRIPTION = "FeedDescription";
	//Feeds table column numbers
	public final int FEEDS_COL_FEEDID = 0;
	public final int FEEDS_COL_POSTERID = 1 ;
	public final int FEEDS_COL_FEEDNAME = 2;
	public final int FEEDS_COL_FEEDDESCRIPTION = 3;
	
	
	/*
	 * 				Key collection definitions
	 * 
	 * In the case that multi table deletions need to happen, these should be concatenated as needed.
	 */
	String [] KEYS_POST ={
			POST_KEY_INFOID,
			POST_KEY_POSTERID,
			POST_KEY_POSTTIME,
			POST_KEY_POSTVALIDITY
	};
	
	String [] KEYS_POSTER={
		POSTER_KEY_POSTERID,
		POSTER_KEY_POSTERCONTACTLOCATION,
		POSTER_KEY_POSTERCONTACTNUMBER,
		POSTER_KEY_POSTERNAME,
		POSTER_KEY_POSTERTITLE,
		POSTER_KEY_POSTERENABLED,
		EVENTNOTICE_KEY_EVENTCONTACT,
		EVENTNOTICE_KEY_EVENTDETAILS,
		EVENTNOTICE_KEY_EVENTEVENTGPS,
		EVENTNOTICE_KEY_EVENTEVENTIMAGE,
		EVENTNOTICE_KEY_EVENTNAME,
		EVENTNOTICE_KEY_EVENTORGANISERS,
		EVENTNOTICE_KEY_EVENTTIME,
		EVENTNOTICE_KEY_EVENTTYPE,
		EVENTNOTICE_KEY_EVENTVENUE,
		NOTICE_KEY_NOTICEDOC,
		NOTICE_KEY_NOTICEIMAGE,
		NOTICE_KEY_NOTICEMESSAGE,
		NOTICE_KEY_NOTICESUBJECT,
		NOTICE_KEY_NOTICETITLE,
		NOTICE_KEY_NOTICETYPE
	};
	
	String [] KEYS_FEEDS= {
		FEEDS_KEY_FEEDDESCRIPTION,
		FEEDS_KEY_FEEDID,
		FEEDS_KEY_FEEDNAME,
		FEEDS_KEY_POSTERID
	};
	
	/*
	String [] KEYS_NOTICES ={
			NOTICE_KEY_INFOID,
			NOTICE_KEY_NOTICEDOC,
			NOTICE_KEY_NOTICEIMAGE,
			NOTICE_KEY_NOTICEMESSAGE,
			NOTICE_KEY_NOTICESUBJECT,
			NOTICE_KEY_NOTICETITLE,
			NOTICE_KEY_NOTICETYPE
	};
	
	String [] KEYS_EVENTNOTICES={
			EVENTNOTICE_KEY_EVENTCONTACT,
			EVENTNOTICE_KEY_EVENTDETAILS,
			EVENTNOTICE_KEY_EVENTEVENTGPS,
			EVENTNOTICE_KEY_EVENTEVENTIMAGE,
			EVENTNOTICE_KEY_EVENTNAME,
			EVENTNOTICE_KEY_EVENTORGANISERS,
			EVENTNOTICE_KEY_EVENTTIME,
			EVENTNOTICE_KEY_EVENTTYPE,
			EVENTNOTICE_KEY_EVENTVENUE,
			EVENTNOTICE_KEY_INFOID
	};
	
	
	String [] KEYS_FEEDPOSTER={
			FEEDPOSTER_KEY_POSTERID,
			FEEDPOSTER_KEY_POSTERTITILE
	};
	
	String [] KEYS_EVENTPOSTER={
			EVENTPOSTER_KEY_POSTERENABLED,
			EVENTPOSTER_KEY_POSTERID
	};
	*/
	
	
	
	
	
	/*
	 * 					METHOD DEFINITIONS
	 */
	
	public DatabaseAdapter(Context context) {
			this.context = context;
			Log.v(TAG, "Databse Adapter initialized");
			 DBHelper = new DatabaseHelper(context);
			 
	}
	
	/*
	 * TODO 
	 * Implement opening and closing functions to complement the constructor
	 * Lay out usual get, delete, getAll, deleteAll, Insert, Search functions. 
	 * Create more specific search, and get functions.
	 * Basically, i'd need retrieval functions for every major data model i'd create. 
	 * Or at least, every complete screen view. 
	 * the methods to be done are 
	 * getEventPoster  +
	 * getFeedPoster  +
	 * getAllFeeds +
	 * getAllFeedNotices(Feed ID) +
	 * getFeedNotice(Info ID) +
	 * getAllEventNotices() +
	 * getEventNotice (Info ID) 
	 */
	
	public DatabaseAdapter open(){
		 this.PosteDB = this.DBHelper.getWritableDatabase();
		 Log.v(TAG, "Database "+DATABASE_NAME+" opened" );
		 return  this;
	}
	
	public void  close(){
		DBHelper.close();
	}


    /**
     * @Doc  look for, and return an event poster object based on the poster id
     * @param PosterID
     * @return
     */
	public EventPosterModel getEventPoster(String PosterID){

		
		
		String rawq = " SELECT * "
				+" FROM "
				+DATABASE_TABLE_POSTER
				+" WHERE "
				+DATABASE_TABLE_POSTER
				+".PosterID = ?"
				;
		Cursor c  = PosteDB.rawQuery(rawq, new String []{PosterID});
		
		if(c.moveToFirst()){

			EventPosterModel takeBack = new EventPosterModel(
				c.getString(c.getColumnIndex(POSTER_KEY_POSTERID)), 
				c.getString(c.getColumnIndex(POSTER_KEY_POSTERNAME)), 
				c.getString(c.getColumnIndex(POSTER_KEY_POSTERCONTACTNUMBER)), 
				c.getString(c.getColumnIndex(POSTER_KEY_POSTERCONTACTLOCATION)), 
				c.getString(c.getColumnIndex(POSTER_KEY_POSTERENABLED))
				);
		
		//Log.v(TAG, "Returning event poster "+takeBack.getPosterName() +" data");
		return takeBack;
		}
		else{
			return null;
		}
		
		
	}

    /**
     * @Doc Returns the Feed Object for the FeedID
     * @param FeedID
     * @return
     */
    public FeedsModel getFeed(String FeedID){
        String rawq = " SELECT * "
                +" FROM "
                +DATABASE_TABLE_FEEDS
                +" WHERE "
                +DATABASE_TABLE_FEEDS
                +".FeedID = ?"
                ;
        Cursor c  = PosteDB.rawQuery(rawq, new String []{FeedID});

        if(c.moveToFirst()){

            FeedsModel takeBack = new FeedsModel(
                    c.getString(c.getColumnIndex(FEEDS_KEY_FEEDID)),
                    c.getString(c.getColumnIndex(FEEDS_KEY_POSTERID)),
                    c.getString(c.getColumnIndex(FEEDS_KEY_FEEDNAME)),
                    c.getString(c.getColumnIndex(FEEDS_KEY_FEEDDESCRIPTION))
            );
            return takeBack;
        }
        else{
            return null;
        }
    }

    /**
     * @Doc Find a poster based on the posterID
     * @param PosterID
     * @return posterModel Object
     */
	public FeedPosterModel getFeedPoster(String PosterID){
		/*
		 * Okay. Due to the structure of the database, it is possible that when a posterID 
		 * is passed here for a search, nothing would be found because the posterID
		 * would belong to an event poster, not a feed poster. 
		 * Thus, we need to check if the posterID belongs to a feed poster first,
		 * before retrieving.
		 */
	
		/*
		SQLiteQueryBuilder QB = new SQLiteQueryBuilder();
		QB.setTables(DATABASE_TABLE_FEED_POSTER + " NATURAL JOIN "+ DATABASE_TABLE_POSTER);
		String where = DATABASE_TABLE_POSTER+"."+POSTER_KEY_POSTERID + " = " + " '" + PosterID + "' ";
		//Cursor c = QB.query(PosteDB, null, where, null, null, null, null);
		*/
		
			String rawq = " SELECT * "
				+" FROM "
				+DATABASE_TABLE_POSTER
				+" WHERE "
				+DATABASE_TABLE_POSTER
				+".PosterID = ?"
				;
		Cursor c  = PosteDB.rawQuery(rawq, new String []{PosterID});
		
		//Log.v(TAG, "getFeedPoster cursor has " + c.getCount());
		
		if(c.moveToFirst()){
			
			FeedPosterModel takeBack = new FeedPosterModel(
					c.getString(c.getColumnIndex(POSTER_KEY_POSTERID)), 
					c.getString(c.getColumnIndex(POSTER_KEY_POSTERNAME)), 
					c.getString(c.getColumnIndex(POSTER_KEY_POSTERCONTACTNUMBER)), 
					c.getString(c.getColumnIndex(POSTER_KEY_POSTERCONTACTLOCATION)), 
					c.getString(c.getColumnIndex(POSTER_KEY_POSTERTITLE))
					);
			//Log.v(TAG, "getFeedPoster poster is " + takeBack.getPosterName());
			return takeBack;
		}
		else 
		{
			return null;
		}
		
	}
	
	//Gets all Feeds in the database. 
	public ArrayList<FeedsModel> getAllFeeds(){
		//Log.v(TAG, "Started getAllFeeds. ");
		ArrayList<FeedsModel> feeds = new ArrayList<FeedsModel> ();
		String rawq ="SELECT * FROM "
					+DATABASE_TABLE_FEEDS
					+" ";
		Cursor c= PosteDB.rawQuery("SELECT * FROM "+DATABASE_TABLE_FEEDS+" ", null);
		
		if(c.moveToFirst()){
			do{
				FeedsModel ret = new FeedsModel(
						c.getString(c.getColumnIndex(FEEDS_KEY_FEEDID)),
						c.getString(c.getColumnIndex(FEEDS_KEY_POSTERID)),
						c.getString(c.getColumnIndex(FEEDS_KEY_FEEDNAME)),
						c.getString(c.getColumnIndex(FEEDS_KEY_FEEDDESCRIPTION))
						);
				feeds.add(ret);
				//Log.v(TAG, "Retrieved Feed "+ ret.FeedName);
			}while(c.moveToNext());
		}
		//Log.v(TAG, "Returned feed arraylist of size "+feeds.size());

        //Some Sorting is in order

        Collections.sort(feeds, new Comparator<FeedsModel>() {
            public int compare(FeedsModel result1, FeedsModel result2) {
                return result1.FeedName.compareTo(result2.FeedName);
            }
        });
		return feeds;
	}
	
	//Gets all the posts in a particular feed, arranged by date.
	public ArrayList<NoticeModel> getAllFeedNotices(String FeedID){
		//Log.v(TAG, "Started getAllFeedNotices");
		String rawq = "SELECT * "
					+" FROM "
					+DATABASE_TABLE_POST
					+" WHERE "
					+DATABASE_TABLE_POST
					+".FeedID = ?";
		Cursor c = PosteDB.rawQuery(rawq, new String[]{FeedID});
		
		ArrayList <NoticeModel>takeBack= new ArrayList<NoticeModel> ();
		//Log.v(TAG,"Number of rows in cursor "+c.getCount());
		if(c.moveToFirst()){
			do{
				NoticeModel ret = new NoticeModel(
						//Retrieved  from POST for the super
						c.getString(c.getColumnIndex(POST_KEY_INFOID)), 
						c.getString(c.getColumnIndex(POST_KEY_POSTERID)), 
						c.getString(c.getColumnIndex(POST_KEY_FEEDID)),
						c.getString(c.getColumnIndex(POST_KEY_POSTTIME)), 
						c.getString(c.getColumnIndex(POST_KEY_POSTVALIDITY)), 
						//Retrieved from NOTICE for the subclass
						c.getString(c.getColumnIndex(NOTICE_KEY_NOTICETYPE)), 
						c.getString(c.getColumnIndex(NOTICE_KEY_NOTICETITLE)), 
						c.getString(c.getColumnIndex(NOTICE_KEY_NOTICESUBJECT)), 
						c.getString(c.getColumnIndex(NOTICE_KEY_NOTICEMESSAGE)), 
						c.getString(c.getColumnIndex(NOTICE_KEY_NOTICEIMAGE)), 
						c.getString(c.getColumnIndex(NOTICE_KEY_NOTICEDOC)));
				//Log.v(TAG, "Returned notice " + ret.getInfoID());
				takeBack.add(ret);
				
			}
			while(c.moveToNext());
		}

        //Sort by date
        Collections.sort(takeBack, new Comparator<NoticeModel>() {
            public int compare(NoticeModel result1, NoticeModel result2) {
                return result1.PostTime.compareTo(result2.PostTime);
            }
        });
		return takeBack;
	}

	//get all event notices
	public ArrayList<EventNoticeModel> getAllEventNotices(){
		ArrayList <EventNoticeModel>takeBack= new ArrayList<EventNoticeModel> ();
		String rawq ="SELECT *"
					+" FROM "
					+DATABASE_TABLE_POST
					+" WHERE "
					+DATABASE_TABLE_POST
					+".PostType LIKE "
					+" '%Event%' "
					;
		Cursor c = PosteDB.rawQuery(rawq, null);
		
		if(c.moveToFirst()){
			do{
				EventNoticeModel ret = new EventNoticeModel(
						//Retrieved  from POST for the super
						c.getString(c.getColumnIndex(POST_KEY_INFOID)), 
						c.getString(c.getColumnIndex(POST_KEY_POSTERID)), 
						c.getString(c.getColumnIndex(POST_KEY_FEEDID)),
						c.getString(c.getColumnIndex(POST_KEY_POSTTIME)), 
						c.getString(c.getColumnIndex(POST_KEY_POSTVALIDITY)), 
						//Retrieved from NOTICE for the subclass
						c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTNAME)),
						c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTTYPE)),
						c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTTIME)),
						c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTVENUE)),
						c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTORGANISERS)),
						c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTDETAILS)),
						c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTCONTACT)),
						c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTEVENTGPS)),
						c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTEVENTIMAGE))
						);
				
				takeBack.add(ret);
				//Log.v(TAG, "Retrieved Event Notice "+ ret.getEventName());
			}
			while(c.moveToNext());
		}
		//Log.v(TAG, "Returning event notice arraylist of size " +takeBack.size());
        //Sort
        Collections.sort(takeBack, new Comparator<EventNoticeModel>() {
            public int compare(EventNoticeModel result1, EventNoticeModel result2) {
                return result1.PostTime.compareTo(result2.PostTime);
            }
        });
		return takeBack;
	}

    /**
     * @Doc Get a specific Notice based on the info id
     * @param infoID
     * @return
     */
	public NoticeModel getNotice(String infoID){
		//Log.v(TAG, "Started getNotice.");
		
		String rawq = "SELECT * "
				+" FROM "
				+DATABASE_TABLE_POST
				+" WHERE "
				+DATABASE_TABLE_POST
				+".InfoID = ?";
		
		Cursor c = PosteDB.rawQuery(rawq, new String[]{infoID});
		//Log.v(TAG, "Cursor returned " + c.getCount());
		if(c.moveToFirst()){	
			NoticeModel ret = new NoticeModel(
				//Retrieved  from POST for the super
				c.getString(c.getColumnIndex(POST_KEY_INFOID)), 
				c.getString(c.getColumnIndex(POST_KEY_POSTERID)), 
				c.getString(c.getColumnIndex(POST_KEY_FEEDID)),
				c.getString(c.getColumnIndex(POST_KEY_POSTTIME)), 
				c.getString(c.getColumnIndex(POST_KEY_POSTVALIDITY)), 
				//Retrieved from NOTICE for the subclass
				c.getString(c.getColumnIndex(NOTICE_KEY_NOTICETYPE)), 
				c.getString(c.getColumnIndex(NOTICE_KEY_NOTICETITLE)), 
				c.getString(c.getColumnIndex(NOTICE_KEY_NOTICESUBJECT)), 
				c.getString(c.getColumnIndex(NOTICE_KEY_NOTICEMESSAGE)), 
				c.getString(c.getColumnIndex(NOTICE_KEY_NOTICEIMAGE)), 
				c.getString(c.getColumnIndex(NOTICE_KEY_NOTICEDOC)));
		
			return ret;
		}
		else{
			return null;
		}
		
	}
	
	/**
	 * @Doc get a specific Event Notice based on the info id
	 * @param InfoID
	 * @return
	 */
	public EventNoticeModel getEventNotice(String InfoID){
        String rawq = "SELECT * "
                +" FROM "
                +DATABASE_TABLE_POST
                +" WHERE "
                +DATABASE_TABLE_POST
                +".InfoID = ?";
		Cursor c =PosteDB.rawQuery(rawq, new String[]{InfoID});
		if(c.moveToFirst()){
            EventNoticeModel ret = new EventNoticeModel(
                    //Retrieved  from POST for the super
                    c.getString(c.getColumnIndex(POST_KEY_INFOID)),
                    c.getString(c.getColumnIndex(POST_KEY_POSTERID)),
                    c.getString(c.getColumnIndex(POST_KEY_FEEDID)),
                    c.getString(c.getColumnIndex(POST_KEY_POSTTIME)),
                    c.getString(c.getColumnIndex(POST_KEY_POSTVALIDITY)),
                    //Retrieved from EVENTNOTICE for the subclass
                    c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTNAME)),
                    c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTTYPE)),
                    c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTTIME)),
                    c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTVENUE)),
                    c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTORGANISERS)),
                    c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTDETAILS)),
                    c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTCONTACT)),
                    c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTEVENTGPS)),
                    c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTEVENTIMAGE))
            );

            return ret;
		}
        else{
            return null;
        }

		
	}

    //Search Methods.

    //Feed Search
    public ArrayList<FeedsModel> FeedSearch(String searchParam){
        Log.v(TAG, "Started FeedSearch on  "+searchParam);
        ArrayList<FeedsModel> feeds = new ArrayList<FeedsModel> ();
        String rawq ="SELECT * FROM "
                +DATABASE_TABLE_FEEDS
                +" WHERE "
                +DATABASE_TABLE_FEEDS
                +"."+FEEDS_KEY_FEEDDESCRIPTION
                +" LIKE "+" '%"+searchParam+"%' "
                +" OR "
                +DATABASE_TABLE_FEEDS
                +"."+FEEDS_KEY_FEEDNAME
                +" LIKE "+" '%"+searchParam+"%' ";

        Cursor c= PosteDB.rawQuery(rawq, null);
        //Cursor c =PosteDB.rawQuery(rawq, new String[]{searchParam, searchParam});
        if(c.moveToFirst()){
            do{
                FeedsModel ret = new FeedsModel(
                        c.getString(c.getColumnIndex(FEEDS_KEY_FEEDID)),
                        c.getString(c.getColumnIndex(FEEDS_KEY_POSTERID)),
                        c.getString(c.getColumnIndex(FEEDS_KEY_FEEDNAME)),
                        c.getString(c.getColumnIndex(FEEDS_KEY_FEEDDESCRIPTION))
                );
                feeds.add(ret);
                //Log.v(TAG, "Retrieved Feed "+ ret.FeedName);
            }while(c.moveToNext());
        }
        //Log.v(TAG, "Returned feed arraylist of size "+feeds.size());
        return feeds;
    }

    //Event Search
    public ArrayList<EventNoticeModel> EventSearch(String searchParam){
        ArrayList <EventNoticeModel>takeBack= new ArrayList<EventNoticeModel> ();
        String rawq ="SELECT * FROM "
                +DATABASE_TABLE_POST
                +" WHERE "
                +DATABASE_TABLE_POST
                +"."+EVENTNOTICE_KEY_EVENTDETAILS
                +" LIKE "+" '%"+searchParam+"%' "
                +" OR "
                +DATABASE_TABLE_POST
                +"."+EVENTNOTICE_KEY_EVENTNAME
                +" LIKE "+" '%"+searchParam+"%' "
                +" OR "
                +DATABASE_TABLE_POST
                +"."+EVENTNOTICE_KEY_EVENTORGANISERS
                +" LIKE "+" '%"+searchParam+"%' "
                +" OR "
                +DATABASE_TABLE_POST
                +"."+EVENTNOTICE_KEY_EVENTVENUE
                +" LIKE "+" '%"+searchParam+"%' "
                ;

        //Cursor c =PosteDB.rawQuery(rawq, new String[]{searchParam,searchParam,searchParam,searchParam});
        Cursor c = PosteDB.rawQuery(rawq,null);
        if(c.moveToFirst()){
            do{
                EventNoticeModel ret = new EventNoticeModel(
                        //Retrieved  from POST for the super
                        c.getString(c.getColumnIndex(POST_KEY_INFOID)),
                        c.getString(c.getColumnIndex(POST_KEY_POSTERID)),
                        c.getString(c.getColumnIndex(POST_KEY_FEEDID)),
                        c.getString(c.getColumnIndex(POST_KEY_POSTTIME)),
                        c.getString(c.getColumnIndex(POST_KEY_POSTVALIDITY)),
                        //Retrieved from NOTICE for the subclass
                        c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTNAME)),
                        c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTTYPE)),
                        c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTTIME)),
                        c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTVENUE)),
                        c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTORGANISERS)),
                        c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTDETAILS)),
                        c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTCONTACT)),
                        c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTEVENTGPS)),
                        c.getString(c.getColumnIndex(EVENTNOTICE_KEY_EVENTEVENTIMAGE))
                );

                takeBack.add(ret);
                //Log.v(TAG, "Retrieved Event Notice "+ ret.getEventName());
            }
            while(c.moveToNext());
        }
       // Log.v(TAG, "Returning event notice arraylist of size " +takeBack.size());
        return takeBack;

    }

	
	
	
	
	
	
	private class DatabaseHelper extends SQLiteAssetHelper{
		private static final String DATABASE_FILE_NAME = "PosteDB.db";
		//private static final String DATABASE_FILE_NAME = "PosteDB_2";
		private static final int DATABASE_VERSION =4;

		
		@Override
		public void setForcedUpgrade() {
			// TODO Auto-generated method stub
			super.setForcedUpgrade();
			
		}
		
		public DatabaseHelper(Context context) {
			
			super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
			setForcedUpgrade();
		}
		
	}


}



