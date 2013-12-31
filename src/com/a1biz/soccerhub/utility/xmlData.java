package com.a1biz.soccerhub.utility;

import java.util.ArrayList;

import com.a1biz.soccerhub.model.countryDb;
import com.a1biz.soccerhub.model.goalDb;
import com.a1biz.soccerhub.model.leagueDb;
import com.a1biz.soccerhub.model.matchDb;
import com.a1biz.soccerhub.model.teamDb;

public class xmlData 
{
	public static String getTeamNameById(ArrayList<teamDb> teams, int teamId)
	{
		int numTeam = teams.size();
		if(numTeam <= 0)
			return "";
		
		for(teamDb team : teams)
		{
			if(team.getID() == teamId)
				return team.getName();
		}
		return "";
	}
	
	public static String getCountryNameById(ArrayList<countryDb> countries, int countryId)
	{
		int numCountries = countries.size();
		if(numCountries <= 0)
			return "";
		
		for(countryDb country : countries)
		{
			if(country.getID() == countryId)
				return country.getName();
		}
		return "";
	}
	
	public static String getLeagueNameById(ArrayList<leagueDb> leagues, int leagueId)
	{
		int numLeague = leagues.size();
		if(numLeague <= 0)
			return "";
		
		for(leagueDb league : leagues)
		{
			if(league.getID() == leagueId)
				return league.getName();
		}
		return "";
	}
	
	public static matchDb getMatchById(ArrayList<matchDb> matches, int matchId)
	{
		if(matches.size() <= 0)
			return null;
		
		for(matchDb matchGet:matches)
		{
			if(matchGet.getID() == matchId)
				return matchGet;
		}
		return null;
	}
	
	public static teamDb getTeamById(ArrayList<teamDb> teams, int teamId)
	{
		if(teams.size() <= 0)
			return null;
		
		for(teamDb team:teams)
		{
			if(team.getID() == teamId)
				return team;
		}
		return null;
	}

	public static boolean checkLeagueExist(ArrayList<leagueDb> leagues, int leagueId)
	{
		if(leagues.size() <= 0)
			return false;
		
		for(leagueDb league:leagues)
			if(league.getID() == leagueId)
				return true;
		return false;
	}
	
	
	public static boolean checkTeamExist(ArrayList<teamDb> teams, int teamId)
	{
		if(teams.size() <= 0)
			return false;
		
		for(teamDb team:teams)
			if(team.getID() == teamId)
				return true;
		return false;
	}
	
	public static boolean checkMatchExist(ArrayList<matchDb> matches, int matchId)
	{
		if(matches.size() <= 0)
			return false;
		
		for(matchDb match:matches)
			if(match.getID() == matchId)
				return true;
		return false;
	}
	
	public static boolean checkGoalExist(ArrayList<goalDb> goals, int goalId)
	{
		if(goals.size() <= 0)
			return false;
		
		for(goalDb goal:goals)
			if(goal.getID() == goalId)
				return true;
		return false;
	}
	
	public static boolean checkCountryExist(ArrayList<countryDb> countries,int countryId) 
	{
		if(countries.size() <= 0)
			return false;
		
		for(countryDb country:countries)
			if(country.getID() == countryId)
				return true;
		return false;
	}
	
	
	// teamOrder = 1: first team, otherwise: second team
	public static int getTotalGoal(ArrayList<goalDb> goals,  int matchId, String teamOrder)
	{
		return getGoalByMatchIdNTeam(goals, matchId, teamOrder).size();
	}
	
	public static leagueDb getLeagueByLeagueId(ArrayList<leagueDb> leagues, int leagueId)
	{
		if(leagues.size() <= 0)
			return null;
		
		for (leagueDb league : leagues) 
			if(league.getID() == leagueId)
				return league;
				
		return null;
	}
	
	public static ArrayList<leagueDb> getLeaguesByCountryId(ArrayList<leagueDb> leagues, int countryId )
	{
		ArrayList<leagueDb> arrReturn = new ArrayList<leagueDb>();
		if(leagues.size() <= 0)
			return arrReturn;
		
		for (leagueDb league : leagues) 
			if(league.getCountry() == countryId)
				arrReturn.add(league);
				
		return arrReturn;
	}
	
	public static ArrayList<goalDb> getGoalByMatchId(ArrayList<goalDb> goals, int matchId)
	{
		ArrayList<goalDb> arrReturn = new ArrayList<goalDb>();
		if(goals.size() <= 0)
			return arrReturn;
		
		for (goalDb goal : goals) 
			if(goal.getMatchId() == matchId)
				arrReturn.add(goal);
				
		return arrReturn;
	}
	
	public static ArrayList<goalDb> goalSortedByMinute(ArrayList<goalDb> goals)
	{
		ArrayList<goalDb> arrReturn = goals;
		int n = arrReturn.size();
		int i,j;
		int iMin;
		 
		for (j = 0; j < n-1; j++) 
		{
		    iMin = j;
		    for ( i = j+1; i < n; i++) 
		    {
		    	/*
		        if (a[i] < a[iMin]) 
		        {
		            iMin = i;
		        }
		        */
		    }
		    if ( iMin != j ) 
		    {
		     //   swap(a[j], a[iMin]);
		    }
		}
				
		return arrReturn;
	}
	
	public static ArrayList<goalDb> getGoalByMatchIdNTeam(ArrayList<goalDb> goals, int matchId, String teamOrder)
	{
		ArrayList<goalDb> arrReturn = new ArrayList<goalDb>();
		if(goals.size() <= 0)
			return arrReturn;
		
		for (goalDb goal : goals) 
		{
			if(goal.getMatchId() == matchId && goal.getTeam().equalsIgnoreCase(teamOrder))
				arrReturn.add(goal);
		}
		
		return arrReturn;
	}
	
	// Status : Finished, inprogess, NSY
	public static ArrayList<matchDb> getMatchByStatus(ArrayList<matchDb> matches, String status)
	{
		ArrayList<matchDb> arrReturn = new ArrayList<matchDb>();
		if(matches.size() <= 0)
			return arrReturn;
		
		for (matchDb match : matches) 
			if(match.getStatus().equalsIgnoreCase(status))
				arrReturn.add(match);
		
		return arrReturn;
	}
	
	public static ArrayList<matchDb> getMatchByLeague(ArrayList<matchDb> matches, int leagueId)
	{
		ArrayList<matchDb> arrReturn = new ArrayList<matchDb>();
		if(matches.size() <= 0)
			return arrReturn;
		
		for (matchDb match : matches) 
			if(match.getLeagueId() == leagueId)
				arrReturn.add(match);
		
		return arrReturn;
	}
	
	public static ArrayList<matchDb> getMatchByLeagueNStatus(ArrayList<matchDb> matches, int leagueId, String liveStatus)
	{
		ArrayList<matchDb> arrReturn = new ArrayList<matchDb>();
		if(matches.size() <= 0)
			return arrReturn;
		
		for (matchDb match : matches) 
			if(match.getLeagueId() == leagueId && match.getStatus().equalsIgnoreCase(liveStatus))
				arrReturn.add(match);
		
		return arrReturn;
	}

	
	public static ArrayList<matchDb> matchSortedByTime(ArrayList<matchDb> matches, ArrayList<matchDb> matchesOri)
	{
		return matchesOri;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
