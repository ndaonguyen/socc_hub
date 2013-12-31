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


public class countryDbHandler  
{
	 // All Static variables
    public DbHelper ourhelper;
    public final Context ourcontext;
    public SQLiteDatabase ourdatabase;
 
    public static abstract class countryEntry implements BaseColumns 
    {
        static final String KEY_ID       = "id";
        static final String KEY_NAME     = "name";
        static final String KEY_OTHER    = "other";
    }
  
    public countryDbHandler(Context context){
    	ourcontext = context;  
    	ourhelper =  DbHelper.getInstance(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
    }

    public void close(){
        ourhelper.close();
        ourdatabase.close();
    }
    
    public int addCountry(countryDb country) {
        ContentValues values = new ContentValues();
        values.put(countryEntry.KEY_ID, country.getID()); 
        values.put(countryEntry.KEY_NAME, country.getName()); 
        values.put(countryEntry.KEY_OTHER, country.getOther());
     
        long id = 0;
        try{
        	id = ourdatabase.insert(CONFIG.TABLE_COUNTRY, null, values);
        }
        catch (SQLException e) {
        	 e.printStackTrace();
        	 Log.i("DB",e.getMessage());
        }
       return (int)id;
    }
    
    public countryDb getCountry(int id) 
    {
        Cursor cursor = ourdatabase.query(CONFIG.TABLE_COUNTRY, new String[] { countryEntry.KEY_ID,
        		countryEntry.KEY_NAME, countryEntry.KEY_OTHER }, countryEntry.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        countryDb contact = new countryDb(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return contact;
    }
    
    public ArrayList<countryDb> getAllCountry() {
    	ArrayList<countryDb> countryList = new ArrayList<countryDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_COUNTRY;
        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                countryDb country = new countryDb();
                country.setID(Integer.parseInt(cursor.getString(0)));
                country.setName(cursor.getString(1));
                country.setOther(cursor.getString(2));
                
                countryList.add(country);
            } while (cursor.moveToNext());
        }
        return countryList;
    }
    
    public int updateCountry(countryDb country) {
        ContentValues values = new ContentValues();
        values.put(countryEntry.KEY_NAME, country.getName());
        values.put(countryEntry.KEY_OTHER, country.getOther());
 
        return ourdatabase.update(CONFIG.TABLE_COUNTRY, values, countryEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(country.getID()) });
    }
    
    public void deleteCountry(String countryId) {
        ourdatabase.delete(CONFIG.TABLE_COUNTRY, countryEntry.KEY_ID + " = ?",
                new String[] { countryId });
    }
    
    public boolean checkCountryExist(int id)
    {
    	String countQuery = "SELECT  * FROM " + CONFIG.TABLE_COUNTRY 
    						+ " WHERE `" + countryEntry.KEY_ID +"` = " + String.valueOf(id) ;
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
    
    public int getCountryCount() {
        String countQuery = "SELECT  * FROM " + CONFIG.TABLE_COUNTRY;
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