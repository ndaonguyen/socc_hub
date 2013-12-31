package com.a1biz.soccerhub.favourite;

import java.util.ArrayList;

import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.MatchActivity;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.SpinningActivity;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;
import com.a1biz.soccerhub.leagueToday.TodayActivity;
import com.a1biz.soccerhub.member.LoginActivity;
import com.a1biz.soccerhub.member.MemberActivity;
import com.a1biz.soccerhub.model.favouriteDb;
import com.a1biz.soccerhub.model.matchDb;
import com.a1biz.soccerhub.readDays.ReadDayActitity;
import com.a1biz.soccerhub.utility.OnSwipeTouchListener;
import com.a1biz.soccerhub.utility.utilityData;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.Toast;

public class FavouriteActivity extends Activity 
{
	SharedPreferences sharedPreferences ;
	ArrayList<matchDb> matches;
	MatchFavoriteAdapter adapter;
	OnTouchListener paraSwiped;
	ListView list;
	String memID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_favourite);
			
			sharedPreferences = PreferenceManager
					  .getDefaultSharedPreferences(this);

			memID = sharedPreferences.getString(PREFERENCE_CONF.MEM_ID, "");
			if(memID=="")
				return;
			
			ArrayList<favouriteDb> favourite = MainActivity.favouriteHandle.getFavouriteByMemId(Integer.valueOf(memID));
			matches = getMatchByFavourite(favourite);
			showFavouriteList();
			
		}
		catch(Exception e)
		{
			String msg  = e.getMessage();
			Log.i("Favourite", msg);
		}
	}

	private void showFavouriteList() 
	{
		paraSwiped = new OnSwipeTouchListener()
	    {
			public boolean onSwipeLeft(View v) // remove Favourite
		    {
		    	int matchId    = v.getId();
		    	if(MainActivity.favouriteHandle.checkFavouriteExist(Integer.valueOf(memID), matchId) == false)
		    		return false;
		    	
    			MainActivity.favouriteHandle.deleteFavourite(memID, String.valueOf(matchId));
    			int postitionDelete = getPositionInFavourite(matchId);
    			
    			matches.remove(postitionDelete);
    			adapter.notifyDataSetChanged();
    			Toast.makeText(FavouriteActivity.this, "Remove from Favourite", Toast.LENGTH_SHORT).show();
		    	return false;
		    }
	    };
	    
		adapter  = new MatchFavoriteAdapter(this, matches, paraSwiped);

		list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
  //      list.setOnItemClickListener(this);
	}

	private ArrayList<matchDb> getMatchByFavourite(ArrayList<favouriteDb> favourite)
	{
		ArrayList<matchDb> matches = new ArrayList<matchDb>();
		for(favouriteDb favo:favourite)
		{
			matchDb match = MainActivity.matchHandle.getMatch(favo.getMatchId());
			if(match != null)
				matches.add(match);
		}
		return matches;
	}
	
    protected int getPositionInFavourite(int matchId) 
    {
    	int position = 0;
    	for(matchDb match: matches)
    	{
    		if(match.getID() == matchId)
    			return position;
    		position++;
    	}
		return position;
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



/*	
private void closeKeyboard() 
{
	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

	if (imm.isActive())  
    {
    	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    } 
    else 
    {
    	String a = "";
    }
}
*/
