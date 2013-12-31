package com.a1biz.soccerhub.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.a1biz.soccerhub.model.countryDb;
import com.a1biz.soccerhub.model.goalDb;
import com.a1biz.soccerhub.model.leagueDb;
import com.a1biz.soccerhub.model.matchDb;
import com.a1biz.soccerhub.model.teamDb;
import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class utilityData 
{
	static public boolean isLogin(Context context)
	{
		SharedPreferences sharedPreferences = PreferenceManager
											  .getDefaultSharedPreferences(context);
		boolean isLogin = sharedPreferences.getBoolean(PREFERENCE_CONF.IS_LOGIN, false);
		if(isLogin == false)
			return false;
		return true;
	}
	
	public static InputStream getUrlData(String url) throws URISyntaxException, ClientProtocolException, IOException {

	    DefaultHttpClient client = new DefaultHttpClient();
	    HttpGet method = new HttpGet(new URI(url));
	    HttpResponse res = client.execute(method);
	    return res.getEntity().getContent();
	}
	
	public static void setFavouriteStatus(View rowView, matchDb match, int memID)
	{
		if(MainActivity.favouriteHandle.checkFavouriteExist(memID, match.getID()) == false)
			turnFavouriteOff(rowView);
		else
			turnFavouriteOn(rowView);
	}	
	
	public static void turnFavouriteOff(View rowView)
	{
		ImageView grayStat   = (ImageView) rowView.findViewById(R.id.grayStar);
		ImageView orangeStat = (ImageView) rowView.findViewById(R.id.orangeStar);
		
		grayStat.setVisibility(View.VISIBLE);
		orangeStat.setVisibility(View.INVISIBLE);
	}
	
	public static void turnFavouriteOn(View rowView) // rowview of each match
	{
		ImageView grayStat   = (ImageView) rowView.findViewById(R.id.grayStar);
		ImageView orangeStat = (ImageView) rowView.findViewById(R.id.orangeStar);
		
		grayStat.setVisibility(View.INVISIBLE);
		orangeStat.setVisibility(View.VISIBLE);
	}
	
	
	public static void saveDaysRecordDb(String daysUrl) // save to database
	{
		int countryLeague    = 0;
    	int leagueId         = 0;
		matchDb matchTemp    = new matchDb();
    	try 
    	{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();       
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser(); 
			xpp.setInput(new InputStreamReader(getUrlData(daysUrl)));
			
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) 
			{
				switch (xpp.getEventType())
				{
				 	case XmlPullParser.START_DOCUMENT:
				 		break;
				 	case XmlPullParser.START_TAG:
				 		String tagName = xpp.getName();
				 		
				 		if(tagName.equalsIgnoreCase("Category"))
				 		{
				 			countryLeague     = Integer.valueOf(xpp.getAttributeValue(null, "id"));
				 			countryDb country = new countryDb();
				 			country.setID(Integer.valueOf(countryLeague));
				 			country.setName(xpp.getAttributeValue(null, "name"));
				 			
				 			if(MainActivity.countryHandle.checkCountryExist(countryLeague) == false)
				 				MainActivity.countryHandle.addCountry(country);
				 		}
				 		else if(tagName.equalsIgnoreCase("Tournament"))
				 		{
				 			leagueDb league = new leagueDb();
				 			leagueId = Integer.valueOf(xpp.getAttributeValue(null, "id"));
				 			league.setID(leagueId);
				 			league.setName(xpp.getAttributeValue(null, "name"));
				 			league.setcountry(countryLeague);

				 			if(MainActivity.leagueHandle.checkLeagueExist(leagueId) == false)
				 				MainActivity.leagueHandle.addLeague(league);
				 		}
				 		else if(tagName.equalsIgnoreCase("Match"))
				 		{
				 			int matchId = Integer.valueOf(xpp.getAttributeValue(null, "id"));
				 			matchTemp.setID(matchId);
				 			matchTemp.setStatus(xpp.getAttributeValue(null, "status"));
				 			matchTemp.setTime(xpp.getAttributeValue(null, "date"));
				 			matchTemp.setStatus(xpp.getAttributeValue(null, "status"));
				 			matchTemp.setCurrentPeriod(xpp.getAttributeValue(null, "CurentPeriod"));
				 			matchTemp.setMinute(xpp.getAttributeValue(null, "minutes"));
				 			matchTemp.setLeagueId(leagueId);
				 		}
				 		else if(tagName.equalsIgnoreCase("Competitor"))
				 		{
				 			String teamOrder = xpp.getAttributeValue(null, "type");
				 			if(teamOrder.equalsIgnoreCase(CONFIG.TEAM_A_ORDER)) // team A
				 			{
				 				teamDb teamA    = new teamDb();
				 				int teamA_id = Integer.valueOf(xpp.getAttributeValue(null, "ID"));
				 				matchTemp.setTeamA(teamA_id);
				 				teamA.setID(teamA_id);
				 				teamA.setName(xpp.getAttributeValue(null, "name"));
				 				
				 				if(MainActivity.teamHandle.checkTeamExist(teamA_id) == false)
				 					MainActivity.teamHandle.addTeam(teamA);
				 			}
				 			else if(teamOrder.equalsIgnoreCase(CONFIG.TEAM_B_ORDER)) // team B
				 			{
				 				teamDb teamB    = new teamDb();
				 				int teamB_id = Integer.valueOf(xpp.getAttributeValue(null, "ID"));
				 				matchTemp.setTeamB(teamB_id);
				 				teamB.setID(teamB_id);
				 				teamB.setName(xpp.getAttributeValue(null, "name"));
				 				
				 				if(MainActivity.teamHandle.checkTeamExist(teamB_id) == false)
				 					MainActivity.teamHandle.addTeam(teamB);
				 			}
				 		}
				 		else if(tagName.equalsIgnoreCase("Score"))
				 		{
				 			String currentMatchType = xpp.getAttributeValue(null, "Type");
				 			String resultMatch      = xpp.getAttributeValue(null, "name");
				 			if(currentMatchType.equalsIgnoreCase("HT"))
				 				matchTemp.setHT(resultMatch);
				 			else
				 				matchTemp.setFT(resultMatch);
				 		}
				 		
				 		else if(tagName.equalsIgnoreCase("Goal"))
				 		{
				 			goalDb goal     = new goalDb();
				 			goal.setID(Integer.valueOf(xpp.getAttributeValue(null, "goalid")));
				 			goal.setType(xpp.getAttributeValue(null, "type"));
				 			goal.setMatchId(matchTemp.getID());
				 			goal.setTeam(xpp.getAttributeValue(null, "team"));
				 			goal.setMinute(xpp.getAttributeValue(null, "minute"));
				 			goal.setName(xpp.getAttributeValue(null, "name"));
				 			
				 			if(MainActivity.goalHandle.checkGoalExist(goal.getID()) == false)
				 				MainActivity.goalHandle.addGoal(goal);
				 		}
				 		
		                break;
				 	case XmlPullParser.END_TAG:
				 		String endTagName = xpp.getName();
				 		if(endTagName.equalsIgnoreCase("Match"))
				 		{
				 			if(MainActivity.matchHandle.checkMatchExist(matchTemp.getID()) == false)
			 				{
			 					matchDb match = new matchDb(matchTemp.getID(), matchTemp.getTeamA(),
											matchTemp.getTeamB(), matchTemp.getTime(), matchTemp.getLeagueId(),
											matchTemp.getHT(), matchTemp.getFT(), matchTemp.getStatus(),
											matchTemp.getMinute(), matchTemp.getCurrentPeriod(), matchTemp.getOther());
			 					MainActivity.matchHandle.addMatch(match);
			 				}
				 		}
		                break;
				}
	            xpp.next();
	        }
    	} 
    	catch (Throwable t) 
    	{
            String msg = t.getMessage();
            Log.d("read", msg);
        }
	}
	
	
	public static void readLiveDataToArray(String url, ArrayList<matchDb> matches, ArrayList<leagueDb> leagues,
			ArrayList<teamDb> teams, ArrayList<goalDb> goals, ArrayList<countryDb> countries) // save to static xml data
	{
		int countryLeague    = 0;
    	int leagueId         = 0;
		matchDb matchTemp    = new matchDb();
    	try 
    	{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();       
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser(); 
			xpp.setInput(new InputStreamReader(getUrlData(url)));
			
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) 
			{
				switch (xpp.getEventType())
				{
				 	case XmlPullParser.START_DOCUMENT:
				 		break;
				 	case XmlPullParser.START_TAG:
				 		String tagName = xpp.getName();
				 		
				 		if(tagName.equalsIgnoreCase("Category"))
				 		{
				 			countryLeague = Integer.valueOf(xpp.getAttributeValue(null, "id"));
				 			countryDb country = new countryDb();
				 			country.setID(Integer.valueOf(countryLeague));
				 			country.setName(xpp.getAttributeValue(null, "name"));
				 			
				 			if(xmlData.checkCountryExist(countries, countryLeague) == false)
				 				countries.add(country);
				 		}
				 		else if(tagName.equalsIgnoreCase("Tournament"))
				 		{
				 			leagueDb league = new leagueDb();
				 			leagueId = Integer.valueOf(xpp.getAttributeValue(null, "id"));
				 			league.setID(leagueId);
				 			league.setName(xpp.getAttributeValue(null, "name"));
				 			league.setcountry(countryLeague);

				 			if(xmlData.checkLeagueExist(leagues, leagueId) == false)
				 				leagues.add(league);
				 		}
				 		else if(tagName.equalsIgnoreCase("Match"))
				 		{
				 			int matchId = Integer.valueOf(xpp.getAttributeValue(null, "id"));
				 			matchTemp.setID(matchId);
				 			matchTemp.setStatus(xpp.getAttributeValue(null, "status"));
				 			matchTemp.setTime(xpp.getAttributeValue(null, "date"));
				 			matchTemp.setStatus(xpp.getAttributeValue(null, "status"));
				 			matchTemp.setCurrentPeriod(xpp.getAttributeValue(null, "CurentPeriod"));
				 			matchTemp.setMinute(xpp.getAttributeValue(null, "minutes"));
				 			matchTemp.setLeagueId(leagueId);
				 		}
				 		else if(tagName.equalsIgnoreCase("Competitor"))
				 		{
				 			String teamOrder = xpp.getAttributeValue(null, "type");
				 			if(teamOrder.equalsIgnoreCase(CONFIG.TEAM_A_ORDER)) // team A
				 			{
				 				teamDb teamA    = new teamDb();
				 				int teamA_id = Integer.valueOf(xpp.getAttributeValue(null, "ID"));
				 				matchTemp.setTeamA(teamA_id);
				 				teamA.setID(teamA_id);
				 				teamA.setName(xpp.getAttributeValue(null, "name"));
				 				if(xmlData.checkTeamExist(teams, teamA_id) == false)
				 					teams.add(teamA);
				 			}
				 			else if(teamOrder.equalsIgnoreCase(CONFIG.TEAM_B_ORDER)) // team B
				 			{
				 				teamDb teamB    = new teamDb();
				 				int teamB_id = Integer.valueOf(xpp.getAttributeValue(null, "ID"));
				 				matchTemp.setTeamB(teamB_id);
				 				teamB.setID(teamB_id);
				 				teamB.setName(xpp.getAttributeValue(null, "name"));
				 				if(xmlData.checkTeamExist(teams, teamB_id) == false)
				 					teams.add(teamB);
				 			}
				 		}
				 		else if(tagName.equalsIgnoreCase("Score"))
				 		{
				 			String currentMatchType = xpp.getAttributeValue(null, "Type");
				 			String resultMatch      = xpp.getAttributeValue(null, "name");
				 			if(currentMatchType.equalsIgnoreCase("HT"))
				 				matchTemp.setHT(resultMatch);
				 			else
				 				matchTemp.setFT(resultMatch);
				 		}
				 		else if(tagName.equalsIgnoreCase("Goal"))
				 		{
				 			goalDb goal     = new goalDb();
				 			goal.setID(Integer.valueOf(xpp.getAttributeValue(null, "goalid")));
				 			goal.setType(xpp.getAttributeValue(null, "type"));
				 			goal.setMatchId(matchTemp.getID());
				 			goal.setTeam(xpp.getAttributeValue(null, "team"));
				 			goal.setMinute(xpp.getAttributeValue(null, "minute"));
				 			goal.setName(xpp.getAttributeValue(null, "name"));
				 			
				 			if(xmlData.checkGoalExist(goals, goal.getID())==false)
				 				goals.add(goal);
				 		}
				 		
		                break;
				 	case XmlPullParser.END_TAG:
				 		String endTagName = xpp.getName();
				 		if(endTagName.equalsIgnoreCase("Match"))
				 		{
				 			if(xmlData.checkMatchExist(matches, matchTemp.getID()) == false)
			 				{
			 					matchDb match = new matchDb(matchTemp.getID(), matchTemp.getTeamA(),
											matchTemp.getTeamB(), matchTemp.getTime(), matchTemp.getLeagueId(),
											matchTemp.getHT(), matchTemp.getFT(), matchTemp.getStatus(),
											matchTemp.getMinute(), matchTemp.getCurrentPeriod(), matchTemp.getOther());
			 					matches.add(match);
			 				}
				 		}
		                break;
				}
	            xpp.next();
	        }
    	} 
    	catch (Throwable t) 
    	{
            String msg = t.getMessage();
            Log.d("read", msg);
        }
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getDaysLiveURL(int numDayBeForeToday, int numDayAfterToday)
	{
		Calendar now = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); 
		now.add(Calendar.DATE, - numDayBeForeToday);
	    String daysBeforeNow = formatter.format(now.getTime());
	    
	    now = Calendar.getInstance();
	    now.add(Calendar.DATE, numDayAfterToday);
	    String daysAfterNow = formatter.format(now.getTime());
	    
	    String daysUrl = CONFIG.PARENT_URL + "?sport_id=1&userID="+ CONFIG.DATA_USER_ID +"&fromDate=" + daysBeforeNow 
	    		         + "&toDate=" + daysAfterNow+ "&pass="+ CONFIG.DATA_PWD;
	    
	    return daysUrl;
	}
	

	public static View getViewForAdapter(View rowView, matchDb match,
			ArrayList<teamDb> teams, ArrayList<goalDb> goals)
	{
		String teamA       = xmlData.getTeamNameById( teams,match.getTeamA());
		TextView teamAView = (TextView) rowView.findViewById(R.id.teamA);
		teamAView.setText(teamA);
		
		String teamB       = xmlData.getTeamNameById( teams,match.getTeamB());
		TextView teamBView = (TextView) rowView.findViewById(R.id.teamB);
		teamBView.setText(teamB);
	
		try
		{
			TextView HT     = (TextView) rowView.findViewById(R.id.HTorDis);
			TextView FT     = (TextView) rowView.findViewById(R.id.FTorMinute);
			TextView result = (TextView) rowView.findViewById(R.id.finalResult);
			ImageView clock = (ImageView) rowView.findViewById(R.id.clock);
			
			String timeAll     = match.getTime();
			String[] timeParts = timeAll.split(" ");
			
			if(match.getStatus().equalsIgnoreCase(CONFIG.MATCH_STATUS_INPROGRESS))
	    	{
				FT.setVisibility(View.VISIBLE);
				FT.setText(match.getMinute());
				int teamANumGoal = xmlData.getTotalGoal(goals, match.getID(), CONFIG.TEAM_A_ORDER);
		    	int teamBNumGoal = xmlData.getTotalGoal(goals, match.getID(), CONFIG.TEAM_B_ORDER);
		    	result.setText(String.valueOf(teamANumGoal)+":"+String.valueOf(teamBNumGoal));
		    	HT.setVisibility(View.VISIBLE);
		    	clock.setVisibility(View.INVISIBLE);
		    	HT.setText(match.getHT());
	    	}
			else if(match.getStatus().equalsIgnoreCase(CONFIG.MATCH_STATUS_COMING))
	    	{
	    		FT.setVisibility(View.VISIBLE);
	    		if(timeParts.length > 1)
	    			FT.setText(timeParts[1]);
	    		
	    		HT.setVisibility(View.INVISIBLE);
	    		result.setVisibility(View.INVISIBLE);
	    		clock.setVisibility(View.VISIBLE);
	    	}
			else if(match.getStatus().equalsIgnoreCase(CONFIG.MATCH_STATUS_FINISHED))
			{
				FT.setVisibility(View.VISIBLE);
				HT.setVisibility(View.VISIBLE);
				HT.setText("HT " + match.getHT());
				result.setVisibility(View.VISIBLE);
				result.setText(match.getFT());
				clock.setVisibility(View.INVISIBLE);
			}
		}
		catch(Exception e)
		{
			String msg = e.getMessage();
			Log.i("adapter", msg);
		}
		
    	return rowView;
	}
	
	
	
}
