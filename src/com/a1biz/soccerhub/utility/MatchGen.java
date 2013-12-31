package com.a1biz.soccerhub.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.model.matchDb;

public class MatchGen 
{
	ArrayList<matchDb> matches;
	Context childContext ;
	
	public MatchGen(Context context)
	{
		childContext = context;
		matches 	 = new ArrayList<matchDb>();
	}
	
	public ArrayList<matchDb> genData() 
	{
		try
		{
			Resources res = childContext.getResources();
			XmlResourceParser xpp = res.getXml(R.xml.match);
			xpp.next();
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT)
			{
				matchDb match = null;
				switch (eventType)
				{
				 	case XmlPullParser.START_DOCUMENT:
				 		break;
				 	case XmlPullParser.START_TAG:
				 		String name = xpp.getName();
				 		if(!name.equalsIgnoreCase("match"))
				 			break;
				 		
                    	match = new matchDb(Integer.valueOf(xpp.getAttributeValue(0)), Integer.valueOf(xpp.getAttributeValue(1)), 
                    			Integer.valueOf(xpp.getAttributeValue(2)), xpp.getAttributeValue(3), 
                    			Integer.valueOf(xpp.getAttributeValue(4)), xpp.getAttributeValue(5),
                    			xpp.getAttributeValue(6), xpp.getAttributeValue(7),xpp.getAttributeValue(8),
                    			xpp.getAttributeValue(9),xpp.getAttributeValue(10)); 
                    	matches.add(match);
		                break;
				 	case XmlPullParser.END_TAG:
		                break;
	            }
				 eventType = xpp.next();
			}
		}
		catch (FileNotFoundException e) {
			Log.d("matchFile",e.getMessage());
		} catch (IOException e) {
			Log.d("matchFile",e.getMessage());
		} catch (Exception e){
			String msg = e.getMessage();
			Log.d("matchFile",msg);
		}
		return matches;
	}
}
