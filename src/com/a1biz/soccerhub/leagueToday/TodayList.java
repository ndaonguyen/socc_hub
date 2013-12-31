package com.a1biz.soccerhub.leagueToday;

import java.util.ArrayList;

import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.model.countryDb;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class TodayList extends ArrayAdapter<countryDb>
{
	private final Activity context;
	private final ArrayList<countryDb> countries;
	private final Integer imageId;
	public TodayList(Activity context, ArrayList<countryDb> countries, Integer imageId)
	{
		super(context, R.layout.country_single, countries);
		this.context = context;
		this.countries = countries;
		this.imageId = imageId;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView        = inflater.inflate(R.layout.country_single, null, true);
		TextView txtTitle   = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(countries.get(position).getName());
		imageView.setImageResource(imageId);
		rowView.setId(countries.get(position).getID());
		return rowView;
	}
}