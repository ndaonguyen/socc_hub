package com.a1biz.soccerhub.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.model.leagueDb;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;


public class LeagueGen 
{
	ArrayList<leagueDb> leagues;
	Context childContext ;
	
	public LeagueGen(Context context)
	{
		childContext  = context;
		leagues		  = new ArrayList<leagueDb>();
	}
	
	public ArrayList<leagueDb> genData() 
	{
		try
		{
			Resources res = childContext.getResources();
			XmlResourceParser xpp = res.getXml(R.xml.league);
			xpp.next();
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT)
			{
				leagueDb league = null;
				switch (eventType)
				{
				 	case XmlPullParser.START_DOCUMENT:
				 		break;
				 	case XmlPullParser.START_TAG:
				 		String name = xpp.getName();
				 		if(!name.equalsIgnoreCase("league"))
				 			break;
				 		
                    	league = new leagueDb(Integer.valueOf(xpp.getAttributeValue(0)),
                    			xpp.getAttributeValue(1), Integer.parseInt(xpp.getAttributeValue(2)), 
                    			xpp.getAttributeValue(3));
                    //	leagueHandle.addMatch(league);
                    	leagues.add(league);
		                break;
				 	case XmlPullParser.END_TAG:
		                break;
	            }
				 eventType = xpp.next();
			}
		}
		catch (FileNotFoundException e) {
			Log.d("leagueFile",e.getMessage());
		} catch (IOException e) {
			Log.d("leagueFile",e.getMessage());
		} catch (Exception e){
			Log.d("leagueFile",e.getMessage());
		}
		return leagues;
	}
}
