package com.a1biz.soccerhub.member;

import com.a1biz.soccerhub.MatchActivity;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.SpinningActivity;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;
import com.a1biz.soccerhub.favourite.FavouriteActivity;
import com.a1biz.soccerhub.leagueToday.TodayActivity;
import com.a1biz.soccerhub.readDays.ReadDayActitity;
import com.a1biz.soccerhub.utility.utilityData;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
@SuppressWarnings("deprecation")
public class MemberActivity extends Activity implements OnTouchListener 
{
	protected static LocalActivityManager mLocalActivityManager;
	String memberId;
	String memberName;
	TextView topMember;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member);
		
		setupMemberInfo();
	}

	public void setupMemberInfo()
	{
		try
		{
			SharedPreferences sharedPreferences = PreferenceManager
					  .getDefaultSharedPreferences(this);
			boolean isLogin = sharedPreferences.getBoolean(PREFERENCE_CONF.IS_LOGIN, false);
			if(isLogin == false)
				return;
			
			memberId   = sharedPreferences.getString(PREFERENCE_CONF.MEM_ID, "");
			memberName = sharedPreferences.getString(PREFERENCE_CONF.MEM_NAME, "");
			
			((TextView)findViewById(R.id.textHead)).setText(memberName);

			TextView infoMember = (TextView) findViewById(R.id.memberInfo);
			infoMember.setText(memberName);
			
			TextView logout = (TextView) findViewById(R.id.memberLogout);
			logout.setText("( Log out )");
			logout.setClickable(true);
			logout.setOnTouchListener(this);
		}
		catch(Exception e)
		{
			String msg = e.getMessage();
			Log.d("member", msg);
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{
		if(event.getAction() != MotionEvent.ACTION_DOWN)
			return false;
		
		// TODO Auto-generated method stub
		if(v.getId() == R.id.memberLogout)
		{
			Toast.makeText(getBaseContext(), "Log out", Toast.LENGTH_SHORT).show();
			
			removePreference(PREFERENCE_CONF.MEM_ID);
			removePreference(PREFERENCE_CONF.MEM_NAME);
			removePreference(PREFERENCE_CONF.IS_LOGIN);
			removePreference(PREFERENCE_CONF.IS_ADMIN);
			
            Intent loginActivity = new Intent(getBaseContext(), LoginActivity.class);
            try
    		{
    			replaceContentView("loginActivity", loginActivity);
    		}
    		catch(Exception e)
    		{
    			String msg = e.getMessage();
    			Log.d("Change Activity", msg);
    		}
          
		}
		return false;
	}    
	
	public void replaceContentView(String id, Intent newIntent) 
	{
		try
		{
			/*
			newIntent.addFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
			View view =  getLocalActivityManager()
					    .startActivity(id,newIntent) 
					    .getDecorView(); 
			this.setContentView(view);
			*/
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
	
	public void removePreference(String key)
	{
		SharedPreferences sharedPreferences = PreferenceManager
											  .getDefaultSharedPreferences(this);
		sharedPreferences.edit().remove(key).commit();
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
	/*	
	@Override
	public void onBackPressed() 
    {
		Log.e("back", "Favourite pressed accepted");
    	 	 	//super.onBackPressed(); 
		finish();
   // 	moveTaskToBack(true);
  //  	finish();
	    Log.e("back", "pressed accepted");
	    Intent intent = new Intent(this, MatchActivity.class);
	    replaceContentView("matchActivity", intent);
	//    startActivity(intent);
	 }
	*/ 
}
