package com.a1biz.soccerhub.leagueToday;

import java.util.ArrayList;

import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.MatchActivity;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.SpinningActivity;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;
import com.a1biz.soccerhub.favourite.FavouriteActivity;
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

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LeagueMatchesActivity extends Activity {

	ListView list;
	public  SharedPreferences sharedPreferences ;
	public  Context context;
	public  OnTouchListener paraSwiped;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_league_matches);
		
		Bundle valueExtra = getIntent().getExtras();
		if( valueExtra == null)
			return;
		setupData();
		
		int leagueId        = valueExtra.getInt("leagueId");
		TextView headingTxt = (TextView) findViewById(R.id.leagueMatchHeading);
		String leagueName   = xmlData.getLeagueNameById(MainActivity.leagues, leagueId);
		headingTxt.setText(leagueName);
		
		ArrayList<matchDb> matches = xmlData.getMatchByLeague(MainActivity.matches, leagueId);
		MatchLeagueAdapter adapter  = new MatchLeagueAdapter(this, matches, paraSwiped);

		list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
      
	}

	private void setupData() 
	{
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
