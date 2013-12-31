package com.a1biz.soccerhub.leagueToday;

import java.util.ArrayList;

import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.MatchActivity;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.SpinningActivity;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.favourite.FavouriteActivity;
import com.a1biz.soccerhub.member.LoginActivity;
import com.a1biz.soccerhub.member.MemberActivity;
import com.a1biz.soccerhub.model.leagueDb;
import com.a1biz.soccerhub.readDays.ReadDayActitity;
import com.a1biz.soccerhub.utility.utilityData;
import com.a1biz.soccerhub.utility.xmlData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class LeagueActivity extends Activity {

	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_league);
		
		Bundle valueExtra = getIntent().getExtras();
		if( valueExtra == null)
			return;
		
		int countryId       = valueExtra.getInt("countryId");
		TextView headingTxt = (TextView) findViewById(R.id.headingLeague);
		String countryName  = xmlData.getCountryNameById(MainActivity.countries, countryId);
		headingTxt.setText(countryName);
		
		ArrayList<leagueDb> leaguesByCountry = xmlData.getLeaguesByCountryId(MainActivity.leagues, countryId);
		
		LeagueList adapter = new LeagueList(this, leaguesByCountry);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) 
            {
            	int leagueId = view.getId();
            	Intent leagueMatcheActivity = new Intent(getBaseContext(), LeagueMatchesActivity.class);
            	leagueMatcheActivity.putExtra("leagueId", leagueId);
				replaceContentView("leagueMatcheActivity", leagueMatcheActivity);
            	
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
