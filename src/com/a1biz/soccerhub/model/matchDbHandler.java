package com.a1biz.soccerhub.model;

import java.util.ArrayList;

import com.a1biz.soccerhub.conf.CONFIG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.provider.BaseColumns;
import android.util.Log;


public class matchDbHandler  
{
	 // All Static variables
    public DbHelper ourhelper;
    public final Context ourcontext;
    public SQLiteDatabase ourdatabase;
 
    public static abstract class matchEntry implements BaseColumns 
    {
        static final String KEY_ID           = "id";
        static final String KEY_TEAMA_ID     = "teamA_id";
        static final String KEY_TEAMB_ID     = "teamB_id";
        static final String KEY_TIME         = "time";
        static final String KEY_LEAGUE_ID    = "league_id";
        static final String KEY_HT		     = "HT";
        static final String KEY_FT		     = "FT";
        static final String KEY_STATUS		     = "status";
        static final String KEY_MINUTE		     = "minute";
        static final String KEY_CURRENT_PERIOD   = "current_period";
        static final String KEY_OTHER        = "other";
    }
  
    public matchDbHandler(Context context){
    	ourcontext = context;  
    	ourhelper =  DbHelper.getInstance(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
    }

    public void close(){
        ourhelper.close();
        ourdatabase.close();
    }
    
    public int addMatch(matchDb match) {
        ContentValues values = new ContentValues();
        values.put(matchEntry.KEY_ID, match.getID()); 
        values.put(matchEntry.KEY_TEAMA_ID, match.getTeamA()); 
        values.put(matchEntry.KEY_TEAMB_ID, match.getTeamB()); 
        values.put(matchEntry.KEY_TIME, match.getTime()); 
        values.put(matchEntry.KEY_LEAGUE_ID, match.getLeagueId()); 
        values.put(matchEntry.KEY_HT, match.getHT()); 
        values.put(matchEntry.KEY_FT, match.getFT()); 
        values.put(matchEntry.KEY_STATUS, match.getStatus()); 
        values.put(matchEntry.KEY_MINUTE, match.getMinute()); 
        values.put(matchEntry.KEY_CURRENT_PERIOD, match.getCurrentPeriod()); 
        values.put(matchEntry.KEY_OTHER, match.getOther());
     
        long id = 0;
        try{
        	id = ourdatabase.insert(CONFIG.TABLE_MATCH, null, values);
        }
        catch (SQLException e) {
        	 String msg  = e.getMessage();
        	 e.printStackTrace();
        	 Log.i("DB",msg);
        }
       return (int)id;
    }
    
    public matchDb getMatch(int id) {
        Cursor cursor = ourdatabase.query(CONFIG.TABLE_MATCH, new String[] { matchEntry.KEY_ID,
        		matchEntry.KEY_TEAMA_ID, matchEntry.KEY_TEAMB_ID, matchEntry.KEY_TIME,
        		matchEntry.KEY_LEAGUE_ID, matchEntry.KEY_HT, matchEntry.KEY_FT,
        		matchEntry.KEY_STATUS, matchEntry.KEY_MINUTE, matchEntry.KEY_CURRENT_PERIOD,
        		matchEntry.KEY_OTHER },
        		matchEntry.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        matchDb contact = new matchDb(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),
        		 Integer.parseInt(cursor.getString(2)), cursor.getString(3), Integer.parseInt(cursor.getString(4)),
        		 cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)
        		 , cursor.getString(9), cursor.getString(10));
        return contact;
    }
    
    public matchDb getMatch(String id)
    {
    	matchDb match = new matchDb();
    	String selectQuery = "SELECT * FROM " + CONFIG.TABLE_MATCH + " WHERE `"+matchEntry.KEY_ID +"` = '"+ id 
    						 + "'";
    	Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
    	if (cursor.moveToFirst()) 
    	{
            match.setID(Integer.parseInt(cursor.getString(0)));
            match.setTeamA(Integer.parseInt(cursor.getString(1)));
            match.setTeamB(Integer.parseInt(cursor.getString(2)));
            match.setTime(cursor.getString(3));
            match.setLeagueId(Integer.parseInt(cursor.getString(4)));
            match.setHT(cursor.getString(5));
            match.setFT(cursor.getString(6));
            match.setStatus(cursor.getString(7));
            match.setMinute(cursor.getString(8));
            match.setCurrentPeriod(cursor.getString(9));
            match.setOther(cursor.getString(10));
        }
    	return match;
    }
    
    public ArrayList<matchDb> getAllMatch() {
    	ArrayList<matchDb> matchList = new ArrayList<matchDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_MATCH;
        try
        {
	        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
	 
	        if (cursor.moveToFirst()) {
	            do {
	                matchDb match = new matchDb();
	                match.setID(Integer.parseInt(cursor.getString(0)));
	                match.setTeamA(Integer.parseInt(cursor.getString(1)));
	                match.setTeamB(Integer.parseInt(cursor.getString(2)));
	                match.setTime(cursor.getString(3));
	                match.setLeagueId(Integer.parseInt(cursor.getString(4)));
	                match.setHT(cursor.getString(5));
	                match.setFT(cursor.getString(6));
	                match.setStatus(cursor.getString(7));
	                match.setMinute(cursor.getString(8));
	                match.setCurrentPeriod(cursor.getString(9));
	                match.setOther(cursor.getString(10));
	                
	                matchList.add(match);
	            } while (cursor.moveToNext());
	        }
	        return matchList;
        }
        catch(Exception e)
        {
			Log.d("DB",e.getMessage());
        }
		return matchList;
    }
    
    public ArrayList<matchDb> getMatchesOfTeamId(int teamId) {
    	ArrayList<matchDb> matchList = new ArrayList<matchDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_MATCH 
        		             + " WHERE " + matchEntry.KEY_TEAMA_ID + " = "+ String.valueOf(teamId)
        		             + " OR " + matchEntry.KEY_TEAMB_ID + " = "+ String.valueOf(teamId);
        try
        {
	        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
	 
	        if (cursor.moveToFirst()) {
	            do {
	                matchDb match = new matchDb();
	                match.setID(Integer.parseInt(cursor.getString(0)));
	                match.setTeamA(Integer.parseInt(cursor.getString(1)));
	                match.setTeamB(Integer.parseInt(cursor.getString(2)));
	                match.setTime(cursor.getString(3));
	                match.setLeagueId(Integer.parseInt(cursor.getString(4)));
	                match.setHT(cursor.getString(5));
	                match.setFT(cursor.getString(6));
	                match.setStatus(cursor.getString(7));
	                match.setMinute(cursor.getString(8));
	                match.setCurrentPeriod(cursor.getString(9));
	                match.setOther(cursor.getString(10));
	                
	                matchList.add(match);
	            } while (cursor.moveToNext());
	        }
	        return matchList;
        }
        catch(Exception e)
        {
			Log.d("DB",e.getMessage());
        }
		return matchList;
    }
    
    
    public int updateMatch(matchDb match) {
        ContentValues values = new ContentValues();
        values.put(matchEntry.KEY_TEAMA_ID, match.getTeamA()); 
        values.put(matchEntry.KEY_TEAMB_ID, match.getTeamB()); 
        values.put(matchEntry.KEY_TIME, match.getTime()); 
        values.put(matchEntry.KEY_LEAGUE_ID, match.getLeagueId()); 
        values.put(matchEntry.KEY_HT, match.getHT()); 
        values.put(matchEntry.KEY_FT, match.getFT()); 
        values.put(matchEntry.KEY_STATUS, match.getStatus()); 
        values.put(matchEntry.KEY_MINUTE, match.getMinute()); 
        values.put(matchEntry.KEY_CURRENT_PERIOD, match.getCurrentPeriod()); 
        values.put(matchEntry.KEY_OTHER, match.getOther());
 
        return ourdatabase.update(CONFIG.TABLE_MATCH, values, matchEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(match.getID()) });
    }
    
    public void deleteMatch(String matchId) {
        ourdatabase.delete(CONFIG.TABLE_MATCH, matchEntry.KEY_ID + " = ?",
                new String[] { matchId });
    }
    
    public boolean checkMatchExist(int id)
    {
    	String countQuery = "SELECT  * FROM " + CONFIG.TABLE_MATCH 
    						+ " WHERE `" + matchEntry.KEY_ID +"` = " + String.valueOf(id) ;
        Cursor cursor = ourdatabase.rawQuery(countQuery, null);
        
        int value = 0;
        try{
        	value = cursor.getCount();
        	cursor.close();
        }
        catch(Exception e)
        {
        	String msg = e.getMessage();
        	Log.d("Error msg", msg);
        }
        if(value==0)
        	return false;
        return true;
    }
    
    public int getMatchCount() {
        String countQuery = "SELECT  * FROM " + CONFIG.TABLE_MATCH;
        Cursor cursor = ourdatabase.rawQuery(countQuery, null);
        
        int value = 0;
        try{
        	value = cursor.getCount();
        	cursor.close();
        }
        catch(Exception e)
        {
        	String msg = e.getMessage();
        	Log.d("Error msg", msg);
        }
        
        return value;
    }
}