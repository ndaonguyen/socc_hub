package com.a1biz.soccerhub.model;

import java.util.ArrayList;

import com.a1biz.soccerhub.model.DbHelper;
import com.a1biz.soccerhub.conf.CONFIG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.provider.BaseColumns;
import android.util.Log;


public class memberVoteDbHandler  
{
	 // All Static variables
    public DbHelper ourhelper;
    public final Context ourcontext;
    public SQLiteDatabase ourdatabase;
    
    public static abstract class memberVoteEntry implements BaseColumns 
    {
        static final String KEY_MEMBERID    = "member_id";
        static final String KEY_MATCHID     = "match_id";
        static final String KEY_VOTERESULT  = "vote_result";
    }
    
    public memberVoteDbHandler(Context context)
    {
    	ourcontext  = context;  
    	ourhelper =  DbHelper.getInstance(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
    }

    public void close()
    {
        ourhelper.close();
        ourdatabase.close();
    }
    
    public int addMemberVote(memberVoteDb memberVote) 
    {
        ContentValues values = new ContentValues();
        values.put(memberVoteEntry.KEY_MEMBERID, memberVote.getMemberId()); 
        values.put(memberVoteEntry.KEY_MATCHID, memberVote.getMatchId()); 
        values.put(memberVoteEntry.KEY_VOTERESULT, memberVote.getVoteResult()); 
     
        long id = 0;
        try{
        	id = ourdatabase.insert(CONFIG.TABLE_MEMBERVOTE, null, values);
        }
        catch (SQLException e) {
        	 e.printStackTrace();
        	 Log.i("DB",e.getMessage());
        }
       return (int)id;
    }
    
    public memberVoteDb getMemberVote(int matchId, int memberId) 
    {
    	Cursor cursor = null;
    	memberVoteDb contact = null;
    	try
    	{
	        cursor = ourdatabase.query(CONFIG.TABLE_MEMBERVOTE, new String[] { memberVoteEntry.KEY_MEMBERID,
	        		memberVoteEntry.KEY_MATCHID, memberVoteEntry.KEY_VOTERESULT }, 
	        		memberVoteEntry.KEY_MEMBERID + "=? AND " + memberVoteEntry.KEY_MATCHID + "=?" ,
	                new String[] { String.valueOf(memberId), String.valueOf(matchId) }, null, null, null);
    	}
    	catch(Exception e)
    	{
    		String msg = e.getMessage();
    		Log.d("MemberVote", msg);
    		return null;
    	}
    	
        if (cursor != null && cursor.getCount()>0)
            cursor.moveToFirst();
        else
        	return null;
        
        try
        {
        	contact = new memberVoteDb(Integer.parseInt(cursor.getString(0)),
        		Integer.parseInt(cursor.getString(1)), cursor.getString(2));
        }
        catch(Exception e)
        {
        	String msg = e.getMessage();
    		Log.d("MemberVote", msg);
        }
        return contact;
    }
  /*  
    public List<memberVoteDb> getAllMemberVote() 
    {
        List<memberVoteDb> memberList = new ArrayList<memberVoteDb>();
        String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_MEMBERVOTE;
        Cursor cursor = ourdatabase.rawQuery(selectQuery, null);
 
        if (cursor.moveToFirst()) {
            do {
            	memberVoteDb memberVote = new memberVoteDb();
            	memberVote.setMemberId(Integer.parseInt(cursor.getString(0)));
            	memberVote.setMatchId(Integer.parseInt(cursor.getString(1)));
            	memberVote.setVoteResult(cursor.getString(2));
                
                memberList.add(memberVote);
            } while (cursor.moveToNext());
        }
        return memberList;
    }
    */
    public int countVoteByType(int matchId, int index)
    {
    	int result = 0;
    	return result;
    }
    
    public ArrayList<memberVoteDb> getVoteByType(int matchId, int index)
    {
    	ArrayList<memberVoteDb> memberList = new ArrayList<memberVoteDb>();
    	Cursor cursor = null;
    	try
    	{
	    	String selectQuery = "SELECT  * FROM " + CONFIG.TABLE_MEMBERVOTE + " WHERE `" 
	    						 + memberVoteEntry.KEY_VOTERESULT + "` = '" +CONFIG.VOTE_CHOICE[index] +"'"
	    						 + " AND `" + memberVoteEntry.KEY_MATCHID +"` = '" + String.valueOf(matchId) +"'" ;
	        cursor = ourdatabase.rawQuery(selectQuery, null);
    	}
    	catch(Exception e)
    	{
    		String msg = e.getMessage();
    		Log.d("MemberVote", msg);
    	}
    	
        if (cursor.moveToFirst()) 
        {
             do 
             {
             	memberVoteDb memberVote = new memberVoteDb();
             	memberVote.setMemberId(Integer.parseInt(cursor.getString(0)));
             	memberVote.setMatchId(Integer.parseInt(cursor.getString(1)));
             	memberVote.setVoteResult(cursor.getString(2));
                 
                 memberList.add(memberVote);
             } while (cursor.moveToNext());
        }
    	return memberList;
    }
    
    public int updateMember(memberVoteDb memberVote) 
    {
        ContentValues values = new ContentValues();
        values.put(memberVoteEntry.KEY_MEMBERID, memberVote.getMemberId());
        values.put(memberVoteEntry.KEY_MATCHID, memberVote.getMatchId());
        values.put(memberVoteEntry.KEY_VOTERESULT, memberVote.getVoteResult());
 
        try
        {
        	return ourdatabase.update(CONFIG.TABLE_MEMBERVOTE, values, 
        								memberVoteEntry.KEY_MEMBERID + " = ? AND "
        								+ memberVoteEntry.KEY_MATCHID +" = ?",
                new String[] { String.valueOf(memberVote.getMemberId()), String.valueOf(memberVote.getMatchId()) });
        }
        catch(Exception e)
        {
        	String msg = e.getMessage();
    		Log.d("MemberVote", msg);
    		return -1;
        }
    }
    
}