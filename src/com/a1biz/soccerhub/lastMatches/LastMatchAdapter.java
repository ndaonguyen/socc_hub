package com.a1biz.soccerhub.lastMatches;

import java.util.ArrayList;

import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.MatchEntryItem;
import com.a1biz.soccerhub.MatchHeadingItem;
import com.a1biz.soccerhub.MatchItem;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;
import com.a1biz.soccerhub.model.goalDb;
import com.a1biz.soccerhub.model.teamDb;
import com.a1biz.soccerhub.utility.utilityData;
import com.a1biz.soccerhub.vote.VoteActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LastMatchAdapter extends ArrayAdapter<MatchItem> {

	private Activity context;
	private ArrayList<MatchItem> items;
	private LayoutInflater inflater;
	

	public LastMatchAdapter(Activity context,ArrayList<MatchItem> items) {
		super(context,0, items);
		this.context = context;
		this.items = items;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View rowView = convertView;
		final MatchItem i = items.get(position);
		if (i == null)
			return rowView;

		if(i.isSection())
		{
			MatchHeadingItem si = (MatchHeadingItem)i;
			rowView = inflater.inflate(R.layout.cate_match_section, null);

			rowView.setOnClickListener(null);
			rowView.setOnLongClickListener(null);
			rowView.setLongClickable(false);
			
			final TextView sectionView = (TextView) rowView.findViewById(R.id.list_item_section_text);
			sectionView.setText(si.getTitle());
		}
		else
		{
			MatchEntryItem ei = (MatchEntryItem)i;
		
			rowView           = inflater.inflate(R.layout.match_single, null, true);
			int Id 			  = ei.match.getID();
			rowView.setId(Id);
			
			SharedPreferences sharedPreferences = PreferenceManager
					  .getDefaultSharedPreferences(this.context);
			String memID = sharedPreferences.getString(PREFERENCE_CONF.MEM_ID, "");
			if(!memID.equalsIgnoreCase(""))
				utilityData.setFavouriteStatus(rowView, ei.match, Integer.valueOf(memID));

			rowView.setOnClickListener(new OnClickListener() 
			{
				@Override
				public void onClick(View view) 
				{
					int id = view.getId();
					Intent voteActivity = new Intent(context, VoteActivity.class);
			 		voteActivity.putExtra("matchId", id);
			 		voteActivity.putExtra("backActivity", "lastMatchActivity");
			 		replaceContentView("voteActivity", voteActivity);
				}
			});

			ArrayList<teamDb> teams = new ArrayList<teamDb>();
			teams.add(MainActivity.teamHandle.getTeam(ei.match.getTeamA()));
			teams.add(MainActivity.teamHandle.getTeam(ei.match.getTeamB()));
			
			ArrayList<goalDb>  goals = MainActivity.goalHandle.getGoalsByMatchId(Id);
			
			rowView = utilityData.getViewForAdapter(rowView, ei.match,teams, goals);
		}
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
