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


public class teamDbHandler  
{
	 // All Static variables
    public DbHelper ourhelper;
    public final Context ourcontext;
    public SQLiteDatabase ourdatabase;
 
    public static abstract class teamEntry implements BaseColumns 
    {
        static final String KEY_ID           	= "id";
        static final String KEY_NAME    		= "name";
        static final String KEY_FLAG     		= "flag";
        static final String KEY_COUNTRY         = "country";
        static final String KEY_POINT           = "point";
        static final String KEY_GAME_PLAYED     = "game_played";
        static final String KEY_WON     		= "won";
        static final String KEY_DRAWN           = "drawn";
        static final String KEY_LOST    		= "lost";
        static final String KEY_GOALS_FORWARD   = "goals_forward";
        static final String KEY_GOALS_AGAINST   = "goals_against";
        static final String KEY_GOALS_DIFF    	= "goals_diff";
        static final String KEY_OTHER       	= "other";
    }
  
    public teamDbHandler(Context context){
    	ourcontext = context;  
    	ourhelper =  DbHelper.getInstance(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
    }

    public void close(){
        ourhelper.close();
        ourdatabase.close();
    }
    
    public int addTeam(teamDb team) {
        ContentValues values = new ContentValues();
        values.put(teamEntry.KEY_ID, team.getID());
        values.put(teamEntry.KEY_NAME, team.getName());
        values.put(teamEntry.KEY_FLAG, team.getFlag());
        values.put(teamEntry.KEY_COUNTRY, team.getCountry()); 
        values.put(teamEntry.KEY_POINT, team.getPoint()); 
        values.put(teamEntry.KEY_GAME_PLAYED, team.getGamePlayed()); 
        values.put(teamEntry.KEY_WON, team.getWon()); 
        values.put(teamEntry.KEY_DRAWN, team.getDrawn()); 
        values.put(teamEntry.KEY_LOST, team.getLost());
        values.put(teamEntry.KEY_GOALS_FORWARD, team.getGoalForward()); 
        values.put(teamEntry.KEY_GOALS_AGAINST, team.getGoalAgainst()); 
        values.put(teamEntry.KEY_GOALS_DIFF, team.getGoalDiff()); 
        values.put(teamEntry.KEY_OTHER, team.getOther());
     
        long id = 0;
        try{
        	id = ourdatabase.insert(CONFIG.TABLE_TEAM, null, values);
        }
        catch (SQLException e) {
        	 e.printStackTrace();
        	 Log.i("DB",e.getMessage());
        }
       return (int)id;
    }
    
    public teamDb getTeam(int id) {
        Cursor cursor = ourdatabase.query(CONFIG.TABLE_TEAM, new String[] { teamEntry.KEY_ID,
        		teamEntry.KEY_NAME, teamEntry.KEY_FLAG, teamEntry.KEY_COUNTRY, teamEntry.KEY_POINT,
        		teamEntry.KEY_GAME_PLAYED, teamEntry.KEY_WON, teamEntry.KEY_DRAWN, teamEntry.KEY_LOST,
        		teamEntry.KEY_GOALS_FORWARD, teamEntry.KEY_GOALS_AGAINST, teamEntry.KEY_GOALS_DIFF, 
        		teamEntry.KEY_OTHER }, 
        		teamEntry.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        
        teamDb contact = new teamDb(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), 
        				 cursor.getString(3), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5))
        				 , Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8))
        				 , Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(10)), Integer.parseInt(cursor.getString(11))
        				 , cursor.getString(12));
        return contact;
    }
    
    public teamDb getTeam(String id)
    {
    	teamDb team = new teamDb();
    	String selectQuery = "SELECT * FROM " + CONFIG.TABLE_TEAM + " WHERE `"+teamEntry.KEY_ID +"` = '"+ id 
    						 + "'";
    	Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
    	if (cursor.moveToFirst()) 
    	{
            team.setID(Integer.parseInt(cursor.getString(0)));
            team.setName(cursor.getString(1));
            team.setFlag(cursor.getString(2));
            team.setCountry(cursor.getString(3));
            team.setPoint(Integer.parseInt(cursor.getString(4)));
            team.setGamePlayed(Integer.parseInt(cursor.getString(5)));
            team.setWon(Integer.parseInt(cursor.getString(6)));
            team.setDrawn(Integer.parseInt(cursor.getString(7)));
            team.setLost(Integer.parseInt(cursor.getString(8)));
            team.setGoalForward(Integer.parseInt(cursor.getString(9)));
            team.setGoalAgainst(Integer.parseInt(cursor.getString(10)));
            team.setGoalDiff(Integer.parseInt(cursor.getString(11)));
            team.setOther(cursor.getString(12));
        }
    	return team;
    }
    
    public ArrayList<teamDb> getAllTeam() {
    	ArrayList<teamDb> teamList = new ArrayList<teamDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_TEAM;
        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                teamDb team = new teamDb();
                team.setID(Integer.parseInt(cursor.getString(0)));
                team.setName(cursor.getString(1));
                team.setFlag(cursor.getString(2));
                team.setCountry(cursor.getString(3));
                team.setPoint(Integer.parseInt(cursor.getString(4)));
                team.setGamePlayed(Integer.parseInt(cursor.getString(5)));
                team.setWon(Integer.parseInt(cursor.getString(6)));
                team.setDrawn(Integer.parseInt(cursor.getString(7)));
                team.setLost(Integer.parseInt(cursor.getString(8)));
                team.setGoalForward(Integer.parseInt(cursor.getString(9)));
                team.setGoalAgainst(Integer.parseInt(cursor.getString(10)));
                team.setGoalDiff(Integer.parseInt(cursor.getString(11)));
                team.setOther(cursor.getString(12));
                
                teamList.add(team);
            } while (cursor.moveToNext());
        }
        return teamList;
    }
    
    public int updateTeam(teamDb team) {
        ContentValues values = new ContentValues();
        values.put(teamEntry.KEY_NAME, team.getName());
        values.put(teamEntry.KEY_FLAG, team.getFlag());
        values.put(teamEntry.KEY_COUNTRY, team.getCountry()); 
        values.put(teamEntry.KEY_POINT, team.getPoint()); 
        values.put(teamEntry.KEY_GAME_PLAYED, team.getGamePlayed()); 
        values.put(teamEntry.KEY_WON, team.getWon()); 
        values.put(teamEntry.KEY_DRAWN, team.getDrawn()); 
        values.put(teamEntry.KEY_LOST, team.getLost());
        values.put(teamEntry.KEY_GOALS_FORWARD, team.getGoalForward()); 
        values.put(teamEntry.KEY_GOALS_AGAINST, team.getGoalAgainst()); 
        values.put(teamEntry.KEY_GOALS_DIFF, team.getGoalDiff()); 
        values.put(teamEntry.KEY_OTHER, team.getOther());
 
        return ourdatabase.update(CONFIG.TABLE_TEAM, values, teamEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(team.getID()) });
    }
    
    public void deleteTeam(String teamId) {
        ourdatabase.delete(CONFIG.TABLE_TEAM, teamEntry.KEY_ID + " = ?",
                new String[] { teamId });
    }
    
    public boolean checkTeamExist(int id)
    {
    	String countQuery = "SELECT  * FROM " + CONFIG.TABLE_TEAM 
    						+ " WHERE `" + teamEntry.KEY_ID +"` = " + String.valueOf(id) ;
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
    
    public int getTeamCount() {
        String countQuery = "SELECT  * FROM " + CONFIG.TABLE_TEAM;
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