package com.a1biz.soccerhub;

import java.util.ArrayList;

import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;
import com.a1biz.soccerhub.favourite.FavouriteActivity;
import com.a1biz.soccerhub.leagueToday.TodayActivity;
import com.a1biz.soccerhub.member.LoginActivity;
import com.a1biz.soccerhub.member.MemberActivity;
import com.a1biz.soccerhub.model.favouriteDb;
import com.a1biz.soccerhub.model.goalDb;
import com.a1biz.soccerhub.model.leagueDb;
import com.a1biz.soccerhub.model.matchDb;
import com.a1biz.soccerhub.readDays.ReadDayActitity;
import com.a1biz.soccerhub.utility.OnSwipeTouchListener;
import com.a1biz.soccerhub.utility.utilityData;
import com.a1biz.soccerhub.utility.xmlData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MatchActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	static ArrayList<MatchItem> items = new ArrayList<MatchItem>();
	ListView list;
	public  OnTouchListener paraSwiped;  
	
	public static SharedPreferences sharedPreferences ;
	public static Context context;
	static MatchAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       try
       {
    	   setContentView(R.layout.activity_match);
    	   setupData();
   		
    	   ImageView refeshImg    = (ImageView)findViewById(R.id.refresh);
    	   ProgressBar loadingBar = (ProgressBar) findViewById(R.id.loadingPanel);
    	   
    	   
    	   adapter = new MatchAdapter(this, items, paraSwiped);
		   list = (ListView)findViewById(R.id.list);
		   list.setAdapter(adapter);
		       
		   if(MainActivity.numReadTodayData ==0)
		   {
			   doThread.getInstance(this, refeshImg, loadingBar);
			   MainActivity.numReadTodayData = 1;
		   }
		   else
		   {
	        	drawDataUpLeagueNLive();
	        	refeshImg.setVisibility(View.VISIBLE);  
	        	loadingBar.setVisibility(View.GONE);  
			}
       }
       catch(Exception e)
       {
    	   String msg = e.getMessage();
    	   Log.i("adaper", msg);
       }
    }
    
    private void setupData() 
	{
		ImageView refreshIcon = (ImageView) findViewById(R.id.refresh);
		refreshIcon.setOnClickListener(this);
		
		ImageView leagueIcon  = (ImageView) findViewById(R.id.league);
		leagueIcon.setOnClickListener(this);
		
		ImageView liveIcon    = (ImageView) findViewById(R.id.live);
		liveIcon.setOnClickListener(this);
		
		sharedPreferences = PreferenceManager
				  .getDefaultSharedPreferences(this);
	
		context 	      = this;
		
		paraSwiped = new OnSwipeTouchListener()
	    {
			public boolean onSwipeRight(View v)  // add to Favourite
			{
				String memID = sharedPreferences.getString(PREFERENCE_CONF.MEM_ID, "");
				if(memID =="")
					return true;
				
				int matchId = v.getId();
				matchDb match = xmlData.getMatchById(MainActivity.matches, matchId);
				if(MainActivity.favouriteHandle.checkFavouriteExist(Integer.valueOf(memID), matchId) == false) 
					// add type for each favourite added --> check include type
				{
					favouriteDb favourite = new favouriteDb(Integer.valueOf(memID), matchId);
					MainActivity.favouriteHandle.addFavourite(favourite);
					
					utilityData.turnFavouriteOn(v);
				
					if(MainActivity.matchHandle.checkMatchExist(matchId) == false)
						MainActivity.matchHandle.addMatch(match);
					
					if(MainActivity.teamHandle.checkTeamExist(match.getTeamA()) == false)
						MainActivity.teamHandle.addTeam(xmlData.getTeamById(MainActivity.teams, match.getTeamA()));
					
					if(MainActivity.teamHandle.checkTeamExist(match.getTeamB()) == false)
						MainActivity.teamHandle.addTeam(xmlData.getTeamById(MainActivity.teams, match.getTeamB()));
					
					if(MainActivity.leagueHandle.checkLeagueExist(match.getLeagueId()) == false)
					{
						// get League from xmlData -->bc it is today data
						leagueDb league = xmlData.getLeagueByLeagueId(MainActivity.leagues, match.getLeagueId());
						MainActivity.leagueHandle.addLeague(new leagueDb(league.getID(), league.getName(), 
															league.getCountry(), league.getOther()));
					}

					ArrayList<goalDb> goalsMatch = xmlData.getGoalByMatchId(MainActivity.goals, matchId);
					for(goalDb goal: goalsMatch)
						if(MainActivity.goalHandle.checkGoalExist(goal.getID() ) == false)
							MainActivity.goalHandle.addGoal(goal);
					Toast.makeText(context, "Add to Favourite", Toast.LENGTH_SHORT).show();
				}
				return true;
		    }
			
		    public boolean onSwipeLeft(View v) // remove Favourite
		    {
		    	String memID = sharedPreferences.getString(PREFERENCE_CONF.MEM_ID, "");
				if(memID =="")
					return true;
				
		    	int matchId    = v.getId();
		    	if(MainActivity.favouriteHandle.checkFavouriteExist(Integer.valueOf(memID), matchId) == true)
				{
		    		MainActivity.favouriteHandle.deleteFavourite(memID, String.valueOf(matchId));
		    		utilityData.turnFavouriteOff(v);
					// cron tab --> delete match, team, goal, league not in use
					Toast.makeText(context, "Remove from Favourite", Toast.LENGTH_SHORT).show();
				}
		    	return true;
		    }

	    };
	}

	public static class doThread
	{
		private static doThread sInstance = null;
		
		public static doThread getInstance(MatchActivity testActivity, ImageView refeshImg, ProgressBar loadingBar) 
		 {
		    if (sInstance == null) 
		    	sInstance = new doThread(testActivity, refeshImg, loadingBar);
		    return sInstance;
		}
		
	    public doThread(final MatchActivity testActivity, final ImageView refeshImg, final ProgressBar loadingBar) 
	    {
	    	Thread t = new Thread() 
	        {
	        	@Override
	        	public void run() 
	        	{
				    try 
				    {
				    	while (!isInterrupted()) 
				    	{
				    		testActivity.runOnUiThread(new Runnable() 
				    		{
				    			@Override
				    			public void run() 
				    			{
				    				readLiveData(refeshImg, loadingBar);
				    			}
				    		});
				    		Thread.sleep(CONFIG.TIME_REFRESH);
				    	}
				    } 
				    catch (InterruptedException e) 
				    {
				    	String msg = e.getMessage();
			            Log.i("HtmlCleaner", msg);
				    }
	        	}
	        };
	        t.start();
	    }
	    
	}
	
	public void replaceContentView(String id, Intent newIntent) 
	{
		try
		{
			newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(newIntent);
			overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
		}
		catch(Exception e)
		{
			String msg = e.getMessage();
			Log.d("Change Activity", msg);
		}
	}

	public void savePreferences(String key, boolean value) 
	{
		SharedPreferences sharedPreferences = PreferenceManager
											  .getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static class taskReadToday extends AsyncTask<View, View, View[]>   // just for MatchActivity
    {
        @Override
        protected View[] doInBackground(View... imgs) 
        {
        	publishProgress(imgs);
        	String daysUrl = utilityData.getDaysLiveURL(0, 0);
    //    	String daysUrl = CONFIG.LIVE_URL;
        	utilityData.readLiveDataToArray(daysUrl, MainActivity.matches, MainActivity.leagues,
        			 						MainActivity.teams, MainActivity.goals, MainActivity.countries);
			return imgs;
        }

        @Override
        protected void onPostExecute(View...imgs) 
        {
        	try
        	{
	        	drawDataUpLeagueNLive();
	        	
	        	imgs[0].setVisibility(View.VISIBLE);  // refresh
        		imgs[1].setVisibility(View.GONE);  // loading
        	}
        	catch(Exception e)
        	{
        		String msg = e.getMessage();
        		Log.i("Thread", msg);
        	}
        }
        
        @Override
     	protected void onProgressUpdate(View... imgs) 
     	{
    		imgs[0].setVisibility(View.INVISIBLE);  // refresh
    		imgs[1].setVisibility(View.VISIBLE);  // loading
     	}
        
    }
    
    public static void drawDataUpLeagueNLive()
	{
		boolean isLive   = MatchActivity.sharedPreferences.getBoolean(PREFERENCE_CONF.IS_LIVE, true);
		boolean isLeague = MatchActivity.sharedPreferences.getBoolean(PREFERENCE_CONF.IS_LEAGUE, true);
		
		items.removeAll(items);
   		adapter.notifyDataSetChanged();
		
		if(isLeague == false)
			if(isLive)
				drawCountryMatchDataByLive(true);
			else
				drawCountryMatchDataByLive(false);	
		else
			if(isLive)
				drawLeagueMatchDataByLive(true);
			else
				drawLeagueMatchDataByLive(false);
	}
    
    public static void drawCountryMatchDataByLive(boolean isLive)
	{
		if(MainActivity.matches.size() <= 0 )
			return;
		
		int sizeStatus = CONFIG.MATCH_STATUS.length;
		for (int i =0; i < sizeStatus; i++) 
		{
			if(isLive == true && i > 0)
				break;
			
			String matchStatus = CONFIG.MATCH_STATUS[i];
			ArrayList<matchDb> matches = xmlData.getMatchByStatus(MainActivity.matches, matchStatus);
			if(matches.size() <= 0)
				continue;
			
			items.add(new MatchHeadingItem(matchStatus));
			for( matchDb match:matches)
				items.add(new MatchEntryItem(match));
		}
		adapter.notifyDataSetChanged();
	}
    
    public static void drawLeagueMatchDataByLive(boolean isLive)
   	{
   		if(MainActivity.matches.size() <= 0 || MainActivity.leagues.size() <=0)
   			return;
   		
   		
   		int sizeLeague = MainActivity.leagues.size();
   		for (int i =0; i < sizeLeague; i++) 
   		{
   			leagueDb league = MainActivity.leagues.get(i);
   			int leagueId = league.getID();
   			ArrayList<matchDb> matches =  new ArrayList<matchDb>();
   			if(isLive)
   				matches = xmlData.getMatchByLeagueNStatus(MainActivity.matches, leagueId, CONFIG.MATCH_STATUS_INPROGRESS);
   			else
   				matches = xmlData.getMatchByLeague(MainActivity.matches, leagueId);
   				
   			if(matches.size() <= 0)
   				continue;
   			
   			String countryName = xmlData.getCountryNameById(MainActivity.countries, league.getCountry());
   			String textHead    =  countryName +": "+league.getName();
   			
   			items.add(new MatchHeadingItem(textHead));
			for( matchDb match:matches)
				items.add(new MatchEntryItem(match));
   		}
   		
   	}
    
    public static void readLiveData(ImageView refeshImg, ProgressBar loadingBar) 
	{
    	taskReadToday task     = new taskReadToday();
	    task.execute(new View[] { refeshImg, loadingBar});
	}

    @SuppressLint("NewApi")
	@Override
	public void onClick(View v) 
	{
		if(v.getId() == R.id.refresh)
		{
			ImageView refeshImg    = (ImageView)findViewById(R.id.refresh);
			ProgressBar loadingBar = (ProgressBar) findViewById(R.id.loadingPanel); 
			readLiveData(refeshImg, loadingBar);
		}
		
		if(v.getId() == R.id.live)
		{
			ImageView liveIcon  = (ImageView) findViewById(R.id.live);
			boolean isLive = sharedPreferences.getBoolean(PREFERENCE_CONF.IS_LIVE, true);
			if(isLive == false) // change to live is true
				liveIcon.setImageResource(R.drawable.all_match);
			else
				liveIcon.setImageResource(R.drawable.live_match);
			savePreferences(PREFERENCE_CONF.IS_LIVE, !isLive);
			
			drawDataUpLeagueNLive();
		}
		
		if(v.getId() == R.id.league)
		{
			ImageView leagueIcon  = (ImageView) findViewById(R.id.league);
			boolean isLeague = sharedPreferences.getBoolean(PREFERENCE_CONF.IS_LEAGUE, true);
			if(isLeague == false) // change to league is true
				leagueIcon.setImageResource(R.drawable.whistle);
			else
				leagueIcon.setImageResource(R.drawable.league);
			savePreferences(PREFERENCE_CONF.IS_LEAGUE, !isLeague);
			
			drawDataUpLeagueNLive();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        
        case R.id.spinning:
            Intent spinningActivity = new Intent(getApplicationContext(), SpinningActivity.class);
            startActivity(spinningActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
        
        case R.id.home:
            Intent matchActivity = new Intent(getApplicationContext(), MatchActivity.class);
            startActivity(matchActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
            
        case R.id.leagueToday:
            Intent leagueActivity = new Intent(getApplicationContext(), TodayActivity.class);
            startActivity(leagueActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
 
        case R.id.member:
            Intent memberActivity;
            if(utilityData.isLogin(this) == true)
            	memberActivity = new Intent(getApplicationContext(), MemberActivity.class);
            else
            	memberActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(memberActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
            
        case R.id.favourite:
            Intent favouriteActivity = new Intent(getBaseContext(), FavouriteActivity.class);
            startActivity(favouriteActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
            
        case R.id.readDays:
            Intent readDaysActivity = new Intent(getBaseContext(), ReadDayActitity.class);
            startActivity(readDaysActivity);
            overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
}
