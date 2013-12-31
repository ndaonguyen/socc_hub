package com.a1biz.soccerhub;

import java.util.ArrayList;

import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;
import com.a1biz.soccerhub.utility.utilityData;
import com.a1biz.soccerhub.vote.VoteActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MatchAdapter extends ArrayAdapter<MatchItem> {

	private Activity context;
	private ArrayList<MatchItem> items;
	private LayoutInflater inflater;
	private OnTouchListener paraSwiped;
	

	public MatchAdapter(Activity context,ArrayList<MatchItem> items, OnTouchListener paraSwiped2) {
		super(context,0, items);
		this.context = context;
		this.items = items;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.paraSwiped = paraSwiped2;
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
			sectionView.setTextColor(Color.BLUE);
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
				public void onClick(View view) {
					int id = view.getId();
					Intent voteActivity = new Intent(context, VoteActivity.class);
			 		voteActivity.putExtra("matchId", id);
			 		voteActivity.putExtra("backActivity", "matchActivity");
			 		replaceContentView("voteActivity", voteActivity);
				}
			});
			
			if(this.paraSwiped != null)
				rowView.setOnTouchListener(this.paraSwiped);

			rowView = utilityData.getViewForAdapter(rowView, ei.match, MainActivity.teams, MainActivity.goals);
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
