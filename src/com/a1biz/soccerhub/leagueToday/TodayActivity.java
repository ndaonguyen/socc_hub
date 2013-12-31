package com.a1biz.soccerhub.leagueToday;

import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.MatchActivity;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.SpinningActivity;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.favourite.FavouriteActivity;
import com.a1biz.soccerhub.member.LoginActivity;
import com.a1biz.soccerhub.member.MemberActivity;
import com.a1biz.soccerhub.readDays.ReadDayActitity;
import com.a1biz.soccerhub.utility.utilityData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TodayActivity extends Activity {
	
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_league_today);
		
		Integer imageId = R.drawable.flag;
		TodayList adapter = new TodayList(this, MainActivity.countries, imageId);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) 
            {
            	int countryId         = view.getId();
            	Intent leagueActivity = new Intent(getBaseContext(), LeagueActivity.class);
            	leagueActivity.putExtra("countryId", countryId);
				
				replaceContentView("leagueActivity", leagueActivity);
            }
        });
                
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
