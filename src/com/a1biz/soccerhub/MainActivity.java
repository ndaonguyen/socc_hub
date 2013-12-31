package com.a1biz.soccerhub;

import java.util.ArrayList;

import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;
import com.a1biz.soccerhub.favourite.FavouriteActivity;
import com.a1biz.soccerhub.leagueToday.TodayActivity;
import com.a1biz.soccerhub.member.LoginActivity;
import com.a1biz.soccerhub.member.MemberActivity;
import com.a1biz.soccerhub.model.countryDb;
import com.a1biz.soccerhub.model.countryDbHandler;
import com.a1biz.soccerhub.model.favouriteDbHandler;
import com.a1biz.soccerhub.model.goalDb;
import com.a1biz.soccerhub.model.goalDbHandler;
import com.a1biz.soccerhub.model.leagueDb;
import com.a1biz.soccerhub.model.leagueDbHandler;
import com.a1biz.soccerhub.model.matchDb;
import com.a1biz.soccerhub.model.matchDbHandler;
import com.a1biz.soccerhub.model.memberDbHandler;
import com.a1biz.soccerhub.model.memberVoteDbHandler;
import com.a1biz.soccerhub.model.teamDb;
import com.a1biz.soccerhub.model.teamDbHandler;
import com.a1biz.soccerhub.readDays.ReadDayActitity;
import com.a1biz.soccerhub.utility.utilityData;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
		Menu menu;
	    // Today's data
		public static ArrayList<matchDb> matches;
		public static ArrayList<leagueDb> leagues;
		public static ArrayList<teamDb> teams;
		public static ArrayList<goalDb> goals;
		public static ArrayList<countryDb> countries;
	
		public static int numReadTodayData;  // to allow user just read 1 time
		public static int numReadDaysData;	 // to allot user just read 1 time
		public static int width;
		public static int height;
		
		public static favouriteDbHandler favouriteHandle;
		public static teamDbHandler  teamHandle;
		public static matchDbHandler matchHandle;
		public static leagueDbHandler leagueHandle;
		public static goalDbHandler goalHandle;
		public static memberDbHandler memberHandle;
		public static memberVoteDbHandler memberVoteHandle;
		public static countryDbHandler countryHandle;
		
		SharedPreferences sharedPreferences ;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ininialData();
		setupTopTabMatch();
		getRealSize();

		Intent activity = new Intent(getApplicationContext(), SpinningActivity.class);
		activity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(activity);
		
	}
	
	private void setupTopTabMatch() 
	{
		sharedPreferences = PreferenceManager
				  .getDefaultSharedPreferences(this);
		savePreferences(PREFERENCE_CONF.IS_LEAGUE, true); // default true
		savePreferences(PREFERENCE_CONF.IS_LIVE, false);
	}
	
	public void savePreferences(String key, boolean value) 
	{
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	@SuppressLint("NewApi")
	private void getRealSize() 
	{
		if (Build.VERSION.SDK_INT >= 11) {
	        Point size = new Point();
	        try {
	            this.getWindowManager().getDefaultDisplay().getRealSize(size);
	            width  = size.x;
	            height = size.y;
	        } catch (NoSuchMethodError e) {
	            Log.i("error", "it can't work");
	        }

	    } else {
	        DisplayMetrics metrics = new DisplayMetrics();
	        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
	        width  = metrics.widthPixels;
	        height = metrics.heightPixels;
	    }
		
	}
	
	private void ininialData() 
	{
		numReadTodayData = 0;
		numReadDaysData  = 0;
		matches   = new ArrayList<matchDb>();
		leagues   = new ArrayList<leagueDb>();
		teams     = new ArrayList<teamDb>();
		goals     = new ArrayList<goalDb>();
		countries = new ArrayList<countryDb>();
	
		favouriteHandle   = new favouriteDbHandler(this);
		matchHandle       = new matchDbHandler(this);
		teamHandle        = new teamDbHandler(this);
		leagueHandle      = new leagueDbHandler(this);
		goalHandle   	  = new goalDbHandler(this);
		memberHandle      = new memberDbHandler(this);
		memberVoteHandle  = new memberVoteDbHandler(this);
		countryHandle     = new countryDbHandler(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		this.menu = menu;
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
