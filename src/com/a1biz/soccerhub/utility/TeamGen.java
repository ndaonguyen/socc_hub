package com.a1biz.soccerhub.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.a1biz.soccerhub.model.teamDb;
import com.a1biz.soccerhub.R;

public class TeamGen 
{
	ArrayList<teamDb> teams;
	Context childContext ;
	
	public TeamGen(Context context)
	{
		childContext = context;
		teams		 = new ArrayList<teamDb>();
	}
	
	public ArrayList<teamDb> genData() 
	{
		try
		{
			Resources res = childContext.getResources();
			XmlResourceParser xpp = res.getXml(R.xml.team);
			xpp.next();
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT)
			{
				teamDb team = null;
				switch (eventType)
				{
				 	case XmlPullParser.START_DOCUMENT:
				 		break;
				 	case XmlPullParser.START_TAG:
				 		String name = xpp.getName();
				 		if(!name.equalsIgnoreCase("team"))
				 			break;
				 		
                    	team = new teamDb(Integer.valueOf(xpp.getAttributeValue(0)),xpp.getAttributeValue(1), 
                    			xpp.getAttributeValue(2), xpp.getAttributeValue(3), 
                    			Integer.valueOf(xpp.getAttributeValue(4)), Integer.valueOf(xpp.getAttributeValue(5)), 
                    			Integer.valueOf(xpp.getAttributeValue(6)), Integer.valueOf(xpp.getAttributeValue(7)),
                    			Integer.valueOf(xpp.getAttributeValue(8)), Integer.valueOf(xpp.getAttributeValue(9)),
                    			Integer.valueOf(xpp.getAttributeValue(10)), Integer.valueOf(xpp.getAttributeValue(11)),
                    			xpp.getAttributeValue(12));
                    	teams.add(team);
		                break;
				 	case XmlPullParser.END_TAG:
		                break;
	            }
				 eventType = xpp.next();
			}
		}
		catch (FileNotFoundException e) {
			Log.d("teamFile",e.getMessage());
		} catch (IOException e) {
			Log.d("teamFile",e.getMessage());
		} catch (Exception e){
			Log.d("teamFile",e.getMessage());
		}
		return teams;
	}
}
