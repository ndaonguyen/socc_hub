package com.a1biz.soccerhub.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.model.favouriteDbHandler.favouriteEntry;
import com.a1biz.soccerhub.model.goalDbHandler.goalEntry;
import com.a1biz.soccerhub.model.leagueDbHandler.leagueEntry;
import com.a1biz.soccerhub.model.matchDbHandler.matchEntry;
import com.a1biz.soccerhub.model.memberDbHandler.memberEntry;
import com.a1biz.soccerhub.model.memberVoteDbHandler.memberVoteEntry;
import com.a1biz.soccerhub.model.teamDbHandler.teamEntry;


public class DbHelper extends SQLiteOpenHelper
{
	private static DbHelper sInstance = null;
	 
	private static final String CREATE_MEMBERVOTE_TABLE = "CREATE TABLE " + CONFIG.TABLE_MEMBERVOTE + "("
            + memberVoteEntry.KEY_MEMBERID   + " INTEGER," 
    		+ memberVoteEntry.KEY_MATCHID    + " INTEGER,"
            + memberVoteEntry.KEY_VOTERESULT + " TEXT"
            + ")";
	
	private static final String CREATE_SPORTS_TABLE = "CREATE TABLE " + CONFIG.TABLE_MEMBER + "("
             + memberEntry.KEY_ID       + " INTEGER PRIMARY KEY," 
     		 + memberEntry.KEY_NAME     + " TEXT,"
             + memberEntry.KEY_EMAIL    + " TEXT,"
             + memberEntry.KEY_PASSWORD + " TEXT," 
             + memberEntry.KEY_OTHER    + " TEXT" 
             + ")";
	 
	private static final String CREATE_MATCH_TABLE = "CREATE TABLE " + CONFIG.TABLE_MATCH + "("
            + matchEntry.KEY_ID        + " INTEGER PRIMARY KEY," 
            + matchEntry.KEY_TEAMA_ID  + " INTEGER,"
    		+ matchEntry.KEY_TEAMB_ID  + " INTEGER,"
            + matchEntry.KEY_TIME      + " TEXT,"
            + matchEntry.KEY_LEAGUE_ID + " INTEGER," 
            + matchEntry.KEY_HT        + " TEXT,"
            + matchEntry.KEY_FT        + " TEXT,"
            + matchEntry.KEY_STATUS    + " TEXT,"
            + matchEntry.KEY_MINUTE    + " TEXT,"
            + matchEntry.KEY_CURRENT_PERIOD  + " TEXT,"
            + matchEntry.KEY_OTHER     + " TEXT" 
            + ")";
	
	private static final String CREATE_TEAM_TABLE = "CREATE TABLE " + CONFIG.TABLE_TEAM + "("
            + teamEntry.KEY_ID        	 + " INTEGER PRIMARY KEY," 
            + teamEntry.KEY_NAME 		 + " TEXT,"
    		+ teamEntry.KEY_FLAG  		 + " TEXT,"
            + teamEntry.KEY_COUNTRY      + " TEXT,"
            + teamEntry.KEY_POINT 		 + " INTEGER," 
            + teamEntry.KEY_GAME_PLAYED  + " INTEGER,"
    		+ teamEntry.KEY_WON  		 + " INTEGER,"
            + teamEntry.KEY_DRAWN      	 + " INTEGER,"
            + teamEntry.KEY_LOST 		 + " INTEGER," 
            + teamEntry.KEY_GOALS_FORWARD  + " INTEGER,"
    		+ teamEntry.KEY_GOALS_AGAINST  + " INTEGER,"
            + teamEntry.KEY_GOALS_DIFF     + " INTEGER,"
            + teamEntry.KEY_OTHER     	 + " TEXT" 
            + ")";
	    
	private static final String CREATE_LEAGUE_TABLE = "CREATE TABLE " + CONFIG.TABLE_LEAGUE + "("
            + leagueEntry.KEY_ID       + " INTEGER PRIMARY KEY," 
    		+ leagueEntry.KEY_NAME     + " TEXT,"
            + leagueEntry.KEY_COUNTRY  + " TEXT,"
            + leagueEntry.KEY_OTHER    + " TEXT" 
            + ")";
	
	private static final String CREATE_GOAL_TABLE = "CREATE TABLE " + CONFIG.TABLE_GOAL + "("
            + goalEntry.KEY_ID          + " INTEGER PRIMARY KEY," 
    		+ goalEntry.KEY_MATCH_ID    + " INTEGER,"
    		+ goalEntry.KEY_TYPE        + " TEXT,"
    		+ goalEntry.KEY_TEAM        + " TEXT,"
    		+ goalEntry.KEY_MINUTE      + " TEXT,"
    		+ goalEntry.KEY_NAME        + " TEXT,"
    		+ goalEntry.KEY_OTHER       + " TEXT"
            + ")";
	
	private static final String CREATE_FAVOURITE_TABLE = "CREATE TABLE " + CONFIG.TABLE_FAVOURITE + "("
            + favouriteEntry.KEY_MEMBER_ID   + " INTEGER," 
    		+ favouriteEntry.KEY_MATCH_ID    + " INTEGER"
            + ")";
	
	private static final String CREATE_COUNTRY_TABLE = "CREATE TABLE " + CONFIG.TABLE_COUNTRY + "("
            + leagueEntry.KEY_ID       + " INTEGER PRIMARY KEY," 
    		+ leagueEntry.KEY_NAME     + " TEXT,"
            + leagueEntry.KEY_OTHER    + " TEXT" 
            + ")";
	
	 public static DbHelper getInstance(Context context) 
	 {
	    if (sInstance == null) 
	    	sInstance = new DbHelper(context.getApplicationContext());
	    return sInstance;
	}
	
    public DbHelper(Context context) 
    {
        super(context, CONFIG.DATABASE_NAME, null, CONFIG.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) 
    {
        db.execSQL(CREATE_MEMBERVOTE_TABLE);
        db.execSQL(CREATE_SPORTS_TABLE);
        db.execSQL(CREATE_MATCH_TABLE);
        db.execSQL(CREATE_TEAM_TABLE);
        db.execSQL(CREATE_FAVOURITE_TABLE);
        db.execSQL(CREATE_LEAGUE_TABLE);
        db.execSQL(CREATE_GOAL_TABLE);
        db.execSQL(CREATE_COUNTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG.TABLE_MEMBERVOTE);
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG.TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG.TABLE_MATCH);
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG.TABLE_TEAM);
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG.TABLE_FAVOURITE);
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG.TABLE_LEAGUE);
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG.TABLE_GOAL);
        db.execSQL("DROP TABLE IF EXISTS " + CONFIG.TABLE_COUNTRY);
        onCreate(db);
    }
  
}


/* Goal, team, matches, league : use the same table 
 * when use cron tab to delete data --> make sure don't delete favourite record( team,...) which are in used
*/