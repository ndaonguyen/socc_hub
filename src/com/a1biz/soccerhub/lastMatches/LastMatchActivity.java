package com.a1biz.soccerhub.lastMatches;

import java.util.ArrayList;

import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.MatchActivity;
import com.a1biz.soccerhub.MatchEntryItem;
import com.a1biz.soccerhub.MatchHeadingItem;
import com.a1biz.soccerhub.MatchItem;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.SpinningActivity;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.favourite.FavouriteActivity;
import com.a1biz.soccerhub.leagueToday.TodayActivity;
import com.a1biz.soccerhub.member.LoginActivity;
import com.a1biz.soccerhub.member.MemberActivity;
import com.a1biz.soccerhub.model.goalDb;
import com.a1biz.soccerhub.model.matchDb;
import com.a1biz.soccerhub.model.teamDb;
import com.a1biz.soccerhub.readDays.ReadDayActitity;
import com.a1biz.soccerhub.utility.utilityData;
import com.a1biz.soccerhub.utility.xmlData;
import com.a1biz.soccerhub.vote.VoteActivity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class LastMatchActivity extends Activity implements OnClickListener   {
	
	int matchId;
	SharedPreferences sharedPreferences;
	Bundle bundle;
	String backAcFromVoteAc;
	Context context;
	matchDb choosenMatch;
	
	ArrayList<MatchItem> items = new ArrayList<MatchItem>();
	ListView list;
	LastMatchAdapter adapter;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_lastmatch);
			setupData();
			
			drawData();
		}
		catch(Exception e)
		{
			String msg  = e.getMessage();
			Log.i("Favourite", msg);
		}
	}

	private void drawData() 
	{
		choosenMatch =  xmlData.getMatchById(MainActivity.matches, matchId);
		if(choosenMatch == null) // choose from match in last matches
			choosenMatch     = MainActivity.matchHandle.getMatch(matchId);
		
		fillChoosenMatchData();
		
		int[] teamIds = {choosenMatch.getTeamA(), choosenMatch.getTeamB()};
		
		for(int i =0;i < teamIds.length; i++)
		{
			int teamID  = teamIds[i];
			ArrayList<matchDb> matches = MainActivity.matchHandle.getMatchesOfTeamId(teamID);
			
			if( matches.size() <= 1)
				continue;
			
			teamDb team = MainActivity.teamHandle.getTeam(teamID);
			int currentNumItem = 0;
			for( matchDb match:matches)
			{
				if(match.getID() != choosenMatch.getID() && xmlData.checkMatchExist(MainActivity.matches, choosenMatch.getID()))
				{
					currentNumItem++;
					if(currentNumItem ==1 )
						items.add(new MatchHeadingItem(team.getName()));
					items.add(new MatchEntryItem(match));
				}
			}
		}
		
		adapter = new LastMatchAdapter(this, items);
		list = (ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
		
	}

	private void fillChoosenMatchData() 
	{
		
		View rowView      = findViewById(R.id.matchSingleLayout);
		
		ArrayList<teamDb> teams = new ArrayList<teamDb>();
		try
		{
			teams.add(MainActivity.teamHandle.getTeam(choosenMatch.getTeamA()));
			teams.add(MainActivity.teamHandle.getTeam(choosenMatch.getTeamB()));
		}
		catch(Exception e)
		{
			teams.add(xmlData.getTeamById(MainActivity.teams, choosenMatch.getTeamA()));
			teams.add(xmlData.getTeamById(MainActivity.teams, choosenMatch.getTeamB()));
		}
		
		ArrayList<goalDb>  goals = MainActivity.goalHandle.getGoalsByMatchId(choosenMatch.getID());
		
		utilityData.getViewForAdapter(rowView, choosenMatch, teams, goals);
		
	}

	private void setupData() 
	{
		context 	      = this;
		bundle = getIntent().getExtras();
		if( bundle == null)
			return;
		
		matchId           = bundle.getInt("matchId");
		backAcFromVoteAc  = bundle.getString("backAcOfVoreAc");
		Button backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(this);
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
	public void onClick(View v) 
	{
		if(v.getId() == R.id.backBtn)
		{
			String activityStr   = bundle.getString("backActivity");
			if(activityStr.equalsIgnoreCase("voteActivity") == false)
				return;
			
			Intent intent = new Intent(this, VoteActivity.class);
			intent.putExtra("matchId",matchId);
			intent.putExtra("backActivity", backAcFromVoteAc);
			replaceContentView("VoteActivity", intent);
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
