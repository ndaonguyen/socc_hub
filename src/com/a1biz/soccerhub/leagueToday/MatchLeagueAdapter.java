package com.a1biz.soccerhub.leagueToday;

import java.util.ArrayList;

import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;
import com.a1biz.soccerhub.model.matchDb;
import com.a1biz.soccerhub.utility.utilityData;
import com.a1biz.soccerhub.vote.VoteActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;


public class MatchLeagueAdapter extends ArrayAdapter<matchDb>
{
	private final Activity context;
	private final ArrayList<matchDb> matches;
	private OnTouchListener paraSwiped;
	
	public MatchLeagueAdapter(Activity context, ArrayList<matchDb> matches, OnTouchListener paraSwiped2)
	{
		super(context, R.layout.match_single, matches);
		this.context    = context;
		this.matches    = matches;
		this.paraSwiped = paraSwiped2;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView      = inflater.inflate(R.layout.match_single, null, true);
		matchDb match     = matches.get(position);
		int Id 			  = match.getID();
		rowView.setId(Id);
		
		SharedPreferences sharedPreferences = PreferenceManager
				  .getDefaultSharedPreferences(this.context);
		String memID = sharedPreferences.getString(PREFERENCE_CONF.MEM_ID, "");
		if(!memID.equalsIgnoreCase(""))
			utilityData.setFavouriteStatus(rowView, match, Integer.valueOf(memID));
		
		rowView.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View view) {
				Intent voteActivity = new Intent(context, VoteActivity.class);
		 		voteActivity.putExtra("matchId", view.getId());
		 		voteActivity.putExtra("backActivity", "leagueActivity");
		 		replaceContentView("voteActivity", voteActivity);
			}
		});
		
		if(this.paraSwiped != null)
			rowView.setOnTouchListener(this.paraSwiped);
		
		rowView = utilityData.getViewForAdapter(rowView, match, MainActivity.teams, MainActivity.goals);
		
		return rowView;
	}
	
	public void replaceContentView(String id, Intent newIntent) 
	{
		try
		{
			newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(newIntent);
			context.overridePendingTransition (CONFIG.ACTIVITY_NO_ANIM, CONFIG.ACTIVITY_NO_ANIM);
		}
		catch(Exception e)
		{
			String msg = e.getMessage();
			Log.d("Change Activity", msg);
		}
	}
}