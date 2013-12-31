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


public class favouriteDbHandler  
{
	 // All Static variables
    public DbHelper ourhelper;
    public final Context ourcontext;
    public SQLiteDatabase ourdatabase;
 
    public static abstract class favouriteEntry implements BaseColumns 
    {
        static final String KEY_MEMBER_ID       = "member_id";
        static final String KEY_MATCH_ID        = "match_id";
    }
  
    public favouriteDbHandler(Context context){
    	ourcontext = context;  
    	ourhelper =  DbHelper.getInstance(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
    }

    public void close(){
        ourhelper.close();
        ourdatabase.close();
    }
    
    public int addFavourite(favouriteDb favourite) {
    	try
    	{
	        ContentValues values = new ContentValues();
	        values.put(favouriteEntry.KEY_MEMBER_ID, favourite.getMemberID()); 
	        values.put(favouriteEntry.KEY_MATCH_ID, favourite.getMatchId());
	     
	        long id = 0;
	        try{
	        	id = ourdatabase.insert(CONFIG.TABLE_FAVOURITE, null, values);
	        }
	        catch (SQLException e) {
	        	 e.printStackTrace();
	        	 Log.i("DB",e.getMessage());
	        }
	       return (int)id;
    	}
    	catch(Exception e)
        {
        	String msg = e.getMessage();
        	Log.d("Error msg", msg);
        }
    	return -1;
    }
    
    public ArrayList<favouriteDb> getFavouriteByMemId( int  member_id) {
    	ArrayList<favouriteDb> favouriteList = new ArrayList<favouriteDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_FAVOURITE + "  WHERE "+ 
        					  favouriteEntry.KEY_MEMBER_ID + "= '"+ String.valueOf(member_id) +"'";
        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                favouriteDb favourite = new favouriteDb();
                favourite.setMemberId(Integer.parseInt(cursor.getString(0)));
                favourite.setMatchId(Integer.parseInt(cursor.getString(1)));
                
                favouriteList.add(favourite);
            } while (cursor.moveToNext());
        }
        return favouriteList;
    }
    
    public boolean checkFavouriteExist(int  member_id, int  match_id)
    {
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_FAVOURITE + "  WHERE "+ 
        					  favouriteEntry.KEY_MEMBER_ID + "= '"+ String.valueOf(member_id) +"'"
        					  + " AND " + favouriteEntry.KEY_MATCH_ID + " ='" +  String.valueOf(match_id) +"'";
        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) 
           return true;
        return false;
    }
    
    public ArrayList<favouriteDb> getAllFavourite() {
    	ArrayList<favouriteDb> favouriteList = new ArrayList<favouriteDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_FAVOURITE;
        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	 favouriteDb favourite = new favouriteDb();
                 favourite.setMemberId(Integer.parseInt(cursor.getString(0)));
                 favourite.setMatchId(Integer.parseInt(cursor.getString(1)));
                 
                 favouriteList.add(favourite);
            } while (cursor.moveToNext());
        }
        return favouriteList;
    }
    
    public int updateFavourite(favouriteDb favourite) {
        ContentValues values = new ContentValues();
        values.put(favouriteEntry.KEY_MEMBER_ID, favourite.getMemberID());
        values.put(favouriteEntry.KEY_MATCH_ID, favourite.getMatchId());
 
        return ourdatabase.update(CONFIG.TABLE_FAVOURITE, values, favouriteEntry.KEY_MEMBER_ID + " = ?",
                new String[] { String.valueOf(favourite.getMemberID()) });
    }
    
    public void deleteFavourite(String memberId, String matchId ) {
    	try
    	{
    		ourdatabase.delete(CONFIG.TABLE_FAVOURITE, favouriteEntry.KEY_MEMBER_ID + " = ? AND " + favouriteEntry.KEY_MATCH_ID + "= ?",
                new String[] { memberId, matchId });
    	}
    	catch(Exception e)
        {
        	String msg = e.getMessage();
        	Log.d("Error msg", msg);
        }
    }
    
    public int getFavouriteCount() {
        String countQuery = "SELECT  * FROM " + CONFIG.TABLE_FAVOURITE;
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