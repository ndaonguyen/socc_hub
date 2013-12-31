package com.a1biz.soccerhub.leagueToday;

import java.util.ArrayList;

import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.model.leagueDb;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
public class LeagueList extends ArrayAdapter<leagueDb>
{
	private final Activity context;
	private final ArrayList<leagueDb> leagues;
	public LeagueList(Activity context, ArrayList<leagueDb> leagues)//, Integer[] imageId) 
	{
		super(context, R.layout.league_single, leagues);
		this.context = context;
		this.leagues = leagues;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView      = inflater.inflate(R.layout.league_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		txtTitle.setText(leagues.get(position).getName());
		rowView.setId(leagues.get(position).getID());
		return rowView;
	}
}