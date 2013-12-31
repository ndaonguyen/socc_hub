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


public class memberDbHandler  
{
	 // All Static variables
    public DbHelper ourhelper;
    public final Context ourcontext;
    public SQLiteDatabase ourdatabase;
 
    public static abstract class memberEntry implements BaseColumns 
    {
        static final String KEY_ID       = "id";
        static final String KEY_NAME     = "name";
        static final String KEY_EMAIL    = "email";
        static final String KEY_PASSWORD = "password";
        static final String KEY_OTHER    = "other";
    }
  
    public memberDbHandler(Context context){
    	ourcontext = context;  
    	ourhelper =  DbHelper.getInstance(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
    }

    public void close(){
        ourhelper.close();
        ourdatabase.close();
    }
    
    public int addMember(memberDb member) {
        ContentValues values = new ContentValues();
        values.put(memberEntry.KEY_NAME, member.getName()); 
        values.put(memberEntry.KEY_EMAIL, member.getEmail()); 
        values.put(memberEntry.KEY_PASSWORD, member.getPassword()); 
        values.put(memberEntry.KEY_OTHER, member.getOther());
     
        long id = 0;
        try{
        	id = ourdatabase.insert(CONFIG.TABLE_MEMBER, null, values);
        }
        catch (SQLException e) {
        	 e.printStackTrace();
        	 Log.i("DB",e.getMessage());
        }
       return (int)id;
    }
    
    public memberDb getMember(int id) {
        Cursor cursor = ourdatabase.query(CONFIG.TABLE_MEMBER, new String[] { memberEntry.KEY_ID,
        		memberEntry.KEY_NAME, memberEntry.KEY_EMAIL, memberEntry.KEY_PASSWORD, memberEntry.KEY_OTHER }, memberEntry.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        memberDb contact = new memberDb(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return contact;
    }
    
    public memberDb getMember(String email, String password)
    {
    	memberDb member = new memberDb();
    	String selectQuery = "SELECT * FROM " + CONFIG.TABLE_MEMBER + " WHERE `"+memberEntry.KEY_EMAIL +"` = '"+ email 
    						 + "' and `" + memberEntry.KEY_PASSWORD +"` ='" + password + "'";
    	Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
    	if (cursor.moveToFirst()) 
    	{
            member.setID(Integer.parseInt(cursor.getString(0)));
            member.setName(cursor.getString(1));
            member.setEmail(cursor.getString(2));
            member.setPassword(cursor.getString(3));
            member.setOther(cursor.getString(4));
        }
    	return member;
    }
    
    public boolean isEmailExist(String email)
    {
    	String selectQuery = "SELECT * FROM " + CONFIG.TABLE_MEMBER + " WHERE `"+memberEntry.KEY_EMAIL +"` = '"+ email + "'";
    	Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
    	if (cursor.moveToFirst()) 
           return true;
    	return false;
    }
    
    
    public ArrayList<memberDb> getAllMember() {
    	ArrayList<memberDb> memberList = new ArrayList<memberDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_MEMBER;
        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
                memberDb member = new memberDb();
                member.setID(Integer.parseInt(cursor.getString(0)));
                member.setName(cursor.getString(1));
                member.setEmail(cursor.getString(2));
                member.setPassword(cursor.getString(3));
                member.setOther(cursor.getString(4));
                
                memberList.add(member);
            } while (cursor.moveToNext());
        }
        return memberList;
    }
    
    public int updateMember(memberDb member) {
        ContentValues values = new ContentValues();
        values.put(memberEntry.KEY_NAME, member.getName());
        values.put(memberEntry.KEY_EMAIL, member.getEmail());
        values.put(memberEntry.KEY_PASSWORD, member.getPassword());
        values.put(memberEntry.KEY_OTHER, member.getOther());
 
        return ourdatabase.update(CONFIG.TABLE_MEMBER, values, memberEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(member.getID()) });
    }
    
    public void deleteMember(String memberId) {
        ourdatabase.delete(CONFIG.TABLE_MEMBER, memberEntry.KEY_ID + " = ?",
                new String[] { memberId });
    }
    
    public int getMemberCount() {
        String countQuery = "SELECT  * FROM " + CONFIG.TABLE_MEMBER;
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