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


public class leagueDbHandler  
{
	 // All Static variables
    public DbHelper ourhelper;
    public final Context ourcontext;
    public SQLiteDatabase ourdatabase;
 
    public static abstract class leagueEntry implements BaseColumns 
    {
        static final String KEY_ID       = "id";
        static final String KEY_NAME     = "name";
        static final String KEY_COUNTRY  = "country";
        static final String KEY_OTHER    = "other";
    }
  
    public leagueDbHandler(Context context){
    	ourcontext = context;  
    	ourhelper =  DbHelper.getInstance(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
    }

    public void close(){
        ourhelper.close();
        ourdatabase.close();
    }
    
    public int addLeague(leagueDb league) {
        ContentValues values = new ContentValues();
        values.put(leagueEntry.KEY_ID, league.getID()); 
        values.put(leagueEntry.KEY_NAME, league.getName()); 
        values.put(leagueEntry.KEY_COUNTRY, league.getCountry()); 
        values.put(leagueEntry.KEY_OTHER, league.getOther());
     
        long id = 0;
        try{
        	id = ourdatabase.insert(CONFIG.TABLE_LEAGUE, null, values);
        }
        catch (SQLException e) {
        	 e.printStackTrace();
        	 Log.i("DB",e.getMessage());
        }
       return (int)id;
    }
    
    public leagueDb getLeague(int id) 
    {
        Cursor cursor = ourdatabase.query(CONFIG.TABLE_LEAGUE, new String[] { leagueEntry.KEY_ID,
        		leagueEntry.KEY_NAME, leagueEntry.KEY_COUNTRY, leagueEntry.KEY_OTHER }, leagueEntry.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        leagueDb contact = new leagueDb(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3));
        return contact;
    }
    
    public ArrayList<leagueDb> getAllLeague() {
    	ArrayList<leagueDb> leagueList = new ArrayList<leagueDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_LEAGUE;
        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                leagueDb league = new leagueDb();
                league.setID(Integer.parseInt(cursor.getString(0)));
                league.setName(cursor.getString(1));
                league.setcountry(Integer.parseInt(cursor.getString(2)));
                league.setOther(cursor.getString(3));
                
                leagueList.add(league);
            } while (cursor.moveToNext());
        }
        return leagueList;
    }
    
    public int updateLeague(leagueDb league) {
        ContentValues values = new ContentValues();
        values.put(leagueEntry.KEY_NAME, league.getName());
        values.put(leagueEntry.KEY_COUNTRY, league.getCountry());
        values.put(leagueEntry.KEY_OTHER, league.getOther());
 
        return ourdatabase.update(CONFIG.TABLE_LEAGUE, values, leagueEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(league.getID()) });
    }
    
    public void deleteLeague(String leagueId) {
        ourdatabase.delete(CONFIG.TABLE_LEAGUE, leagueEntry.KEY_ID + " = ?",
                new String[] { leagueId });
    }
    
    public boolean checkLeagueExist(int id)
    {
    	String countQuery = "SELECT  * FROM " + CONFIG.TABLE_LEAGUE 
    						+ " WHERE `" + leagueEntry.KEY_ID +"` = " + String.valueOf(id) ;
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
    
    public int getLeagueCount() {
        String countQuery = "SELECT  * FROM " + CONFIG.TABLE_LEAGUE;
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