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


public class goalDbHandler  
{
	 // All Static variables
    public DbHelper ourhelper;
    public final Context ourcontext;
    public SQLiteDatabase ourdatabase;
 
    public static abstract class goalEntry implements BaseColumns 
    {
        static final String KEY_ID        = "id";
        static final String KEY_MATCH_ID  = "match_id";
        static final String KEY_TYPE      = "type";  // Penalty ,...
        static final String KEY_TEAM      = "team";
        static final String KEY_MINUTE    = "minute";
        static final String KEY_NAME      = "name";
        static final String KEY_OTHER     = "other";
    }
  
    public goalDbHandler(Context context){
    	ourcontext = context;  
    	ourhelper =  DbHelper.getInstance(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
    }

    public void close(){
        ourhelper.close();
        ourdatabase.close();
    }
    
    public int addGoal(goalDb goal) {
        ContentValues values = new ContentValues();
        values.put(goalEntry.KEY_ID, goal.getID()); 
        values.put(goalEntry.KEY_MATCH_ID, goal.getMatchId()); 
        values.put(goalEntry.KEY_TYPE, goal.getType()); 
        values.put(goalEntry.KEY_TEAM, goal.getTeam()); 
        values.put(goalEntry.KEY_MINUTE, goal.getMinute()); 
        values.put(goalEntry.KEY_NAME, goal.getName()); 
        values.put(goalEntry.KEY_OTHER, goal.getOther());
     
        long id = 0;
        try{
        	id = ourdatabase.insert(CONFIG.TABLE_GOAL, null, values);
        }
        catch (SQLException e) {
        	 e.printStackTrace();
        	 Log.i("DB",e.getMessage());
        }
       return (int)id;
    }
    
    public goalDb getGoal(int id) {
        Cursor cursor = ourdatabase.query(CONFIG.TABLE_GOAL, new String[] { goalEntry.KEY_ID,
        		goalEntry.KEY_MATCH_ID, goalEntry.KEY_TYPE, goalEntry.KEY_TEAM, goalEntry.KEY_MINUTE,
        		goalEntry.KEY_NAME,  goalEntry.KEY_OTHER }, goalEntry.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        goalDb contact = new goalDb(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1))
					                ,cursor.getString(2), cursor.getString(3), 
					                cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return contact;
    }

    public ArrayList<goalDb> getGoalsByMatchId(int matchId) 
    {
        ArrayList<goalDb> goalList = new ArrayList<goalDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_GOAL +" WHERE "+ goalEntry.KEY_MATCH_ID +" = "+ String.valueOf(matchId) ;
        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                goalDb goal = new goalDb();
                goal.setID(Integer.parseInt(cursor.getString(0)));
                goal.setMatchId(Integer.parseInt(cursor.getString(1)));
                goal.setType(cursor.getString(2));
                goal.setTeam(cursor.getString(3));
                goal.setMinute(cursor.getString(4));
                goal.setName(cursor.getString(5));
                goal.setOther(cursor.getString(6));
                
                goalList.add(goal);
            } while (cursor.moveToNext());
        }
        return goalList;
    }

    public int updateGoal(goalDb goal) {
        ContentValues values = new ContentValues();
        values.put(goalEntry.KEY_MATCH_ID, goal.getMatchId()); 
        values.put(goalEntry.KEY_TYPE, goal.getType()); 
        values.put(goalEntry.KEY_TEAM, goal.getTeam()); 
        values.put(goalEntry.KEY_MINUTE, goal.getMinute()); 
        values.put(goalEntry.KEY_NAME, goal.getName()); 
        values.put(goalEntry.KEY_OTHER, goal.getOther());
 
        return ourdatabase.update(CONFIG.TABLE_GOAL, values, goalEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(goal.getID()) });
    }
    
    public void deleteGoal(String goalId) {
        ourdatabase.delete(CONFIG.TABLE_GOAL, goalEntry.KEY_ID + " = ?",
                new String[] { goalId });
    }
    
    public boolean checkGoalExist(int id)
    {
    	String countQuery = "SELECT  * FROM " + CONFIG.TABLE_GOAL 
    						+ " WHERE `" + goalEntry.KEY_ID +"` = " + String.valueOf(id) ;
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
    
  /*
   * teamOrder: team 1 or team 2
   */
    public int getGoalCountByMatchNTeam(int matchId, int teamOrder) 
    {
        String countQuery = "SELECT  * FROM " + CONFIG.TABLE_GOAL
        				   +" WHERE "+ goalEntry.KEY_MATCH_ID +" = " + String.valueOf(matchId)
        				   +" AND "  + goalEntry.KEY_TEAM + " = " + String.valueOf(teamOrder);
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