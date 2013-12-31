package com.a1biz.soccerhub.conf;

import android.graphics.Color;

public abstract class CONFIG {
	public final static String ADMIN_EMAIL		  = "admin@gmail.com";
	public final static String ADMIN_PWD		  = "12345";
	public final static int ADMIN_ID	   		  = 0;
	
	public final static int TIME_REFRESH          = 6000000;
	
	public final static int COL_WIDTH   		  = 100;  // emulator : 50 // Note 1 : 100
	public final static int SPACE_LEFT_RIGHT      = 100; // emulator : 50
	public final static int NUM_COL      		  = 3;
	public final static int CHART_HEIGHT 		  = 200;  // total height: chart, info
	public final static int CHART_INFO_HEIGHT     = 30;
	public final static int TEXT_SIZE 		      = 18;  
	public final static int SPACE_TOP_N_CHART     = 30;
	public final static int ACTIVITY_NO_ANIM	  = 0;
	
	//vote result
	public final static String VOTE_FIRST_WIN	  = "0";
	public final static String VOTE_DRAW   		  = "1";
	public final static String VOTE_SECOND_WIN	  = "2";
	public final static String[] VOTE_CHOICE      = {VOTE_FIRST_WIN, VOTE_DRAW, VOTE_SECOND_WIN };
	
	
	// list matches
	public final static int NUM_ROW_MATCHES      = 9;
	public final static int NUM_ROW_LEAGUE       = 13;
	
	// Db
	public final static String TABLE_MEMBER	      = "member";
	public final static String TABLE_MEMBERVOTE	  = "voteResult";
	public final static String TABLE_MATCH	      = "match";
	public final static String TABLE_TEAM	      = "team";
	public final static String TABLE_LEAGUE	      = "league";
	public final static String TABLE_FAVOURITE	  = "favourite";
	public final static String TABLE_GOAL	      = "goal";
	public final static String TABLE_COUNTRY	  = "country";
	
	public final static int DATABASE_VERSION	  = 1;
	public final static String DATABASE_NAME	  = "soccer_hub";
	
	// xml live data
	public final static String MATCH_STATUS_FINISHED	= "Finished";
	public final static String MATCH_STATUS_INPROGRESS  = "inprogress";
	public final static String MATCH_STATUS_COMING	    = "NSY";  // NSY
//	public final static String MATCH_STATUS_POSTPONE    = "Postponed";
	public final static String[] MATCH_STATUS           = {MATCH_STATUS_INPROGRESS, MATCH_STATUS_COMING, MATCH_STATUS_FINISHED };
	
	public final static String TEAM_A_ORDER		 = "1";
	public final static String TEAM_B_ORDER		 = "2";
	
	
	public final static String DATA_USER_ID	 	  = "266";
	public final static String DATA_PWD			  = "123digital321"; 
	public final static String LIVE_URL	          = "http://xml.tbwsport.com/SportccFixtures.aspx?sport_id=1&userID="+DATA_USER_ID
													+"&pass="+DATA_PWD;
//	public final static String LIVE_URL	          = "http://xml.tbwsport.com/SportccLive.aspx?sport_id=1&userID="+DATA_USER_ID
//													+"&pass="+DATA_PWD;  
	public final static String PARENT_URL	      = "http://xml.tbwsport.com/SportccFixtures.aspx";  
	public final static int    NUM_DAY_BEFORE_TODAY   = 5;  // 2
	public final static int    NUM_DAY_AFTER_TODAY    = 5;  // 2 
	
	public final static int    NUM_PAST_DAYS_RECORD = 3;
	
	public static int COLOR_VOTE 				 = Color.rgb(100, 100, 200);
	
}
