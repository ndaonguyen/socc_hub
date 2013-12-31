package com.a1biz.soccerhub.vote;

import java.util.ArrayList;
import java.util.List;

import com.a1biz.soccerhub.MainActivity;
import com.a1biz.soccerhub.MatchActivity;
import com.a1biz.soccerhub.R;
import com.a1biz.soccerhub.SpinningActivity;
import com.a1biz.soccerhub.conf.CONFIG;
import com.a1biz.soccerhub.conf.PREFERENCE_CONF;
import com.a1biz.soccerhub.favourite.FavouriteActivity;
import com.a1biz.soccerhub.lastMatches.LastMatchActivity;
import com.a1biz.soccerhub.leagueToday.TodayActivity;
import com.a1biz.soccerhub.member.LoginActivity;
import com.a1biz.soccerhub.member.MemberActivity;
import com.a1biz.soccerhub.model.goalDb;
import com.a1biz.soccerhub.model.matchDb;
import com.a1biz.soccerhub.model.memberVoteDb;
import com.a1biz.soccerhub.readDays.ReadDayActitity;
import com.a1biz.soccerhub.utility.utilityData;
import com.a1biz.soccerhub.utility.xmlData;
import com.a1biz.soccerhub.vote.DrawBar;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class VoteActivity extends Activity implements OnTouchListener, OnClickListener {
	DrawBar drawView;
	int[]    numVotes = {0, 0, 0};
	String[] teamName = {"Young Boys", "Draw", "MU"};
	String[] voteText = {"1", "X", "2"};
	String[] voteValues = {CONFIG.VOTE_FIRST_WIN, CONFIG.VOTE_DRAW, CONFIG.VOTE_SECOND_WIN};
	int numVoteChoice = 3;
	int matchId;
	matchDb mainMatch;
	TableLayout voteTable;
	SharedPreferences sharedPreferences;
	String activityBackStr ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote);

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		readDataOfMatch();

		boolean isLogin = sharedPreferences.getBoolean(PREFERENCE_CONF.IS_LOGIN, false);
		
		if(isLogin && mainMatch.getStatus().equalsIgnoreCase(CONFIG.MATCH_STATUS_COMING))
			drawVote();
		
		drawResult();
		drawBar();
	
	}

	@SuppressLint("NewApi")
	private void drawResult() 
	{
		int childHeight   = 60;
		int withMinTxt    = 80;
		int paddingBtnTxt = 3; 
		
		LinearLayout resultLayout = (LinearLayout) findViewById(R.id.resultDetail);
		LayoutParams mainParams = resultLayout.getLayoutParams();
		mainParams.height = MainActivity.height/4;
		
		ArrayList<goalDb> goals;
		
		if(activityBackStr.equalsIgnoreCase("matchActivity") || activityBackStr.equalsIgnoreCase("leagueActivity"))
			goals = xmlData.getGoalByMatchId(MainActivity.goals, matchId);
		else
			goals = MainActivity.goalHandle.getGoalsByMatchId(matchId);
		
		if(goals.size() <= 0)
			return;
			
		for (goalDb goal : goals) 
		{
			LinearLayout childLayout = new LinearLayout(this);
			childLayout.setOrientation(LinearLayout.HORIZONTAL);
			if(goal.getTeam().equalsIgnoreCase("1"))
				childLayout.setGravity(Gravity.LEFT);
			else
				childLayout.setGravity(Gravity.RIGHT);
			
			TextView minTxt = new TextView(this);
			minTxt.setText(goal.getMinute()+"'");
			minTxt.setWidth(withMinTxt);
			minTxt.setHeight(childHeight);
			minTxt.setBackground(getResources().getDrawable(R.color.match_goal));
			minTxt.setGravity(Gravity.CENTER_VERTICAL);
			
			
			TextView goalTxt = new TextView(this);
			goalTxt.setText(goal.getName());
			goalTxt.setGravity(Gravity.LEFT);
			if(goal.getTeam().equalsIgnoreCase("1"))
				goalTxt.setPadding(paddingBtnTxt, 0, 0, 0);
			else
				goalTxt.setPadding(0, 0, paddingBtnTxt, 0);
		
			if(goal.getTeam().equalsIgnoreCase("1"))
			{
				childLayout.addView(minTxt);
				childLayout.addView(goalTxt);
			}
			else
			{
				childLayout.addView(goalTxt);
				childLayout.addView(minTxt);
			}

			resultLayout.addView(childLayout);
		}
	}

	private void readDataOfMatch() 
	{
		Bundle valueExtra = getIntent().getExtras();
		if( valueExtra == null)
			return;
		matchId           = valueExtra.getInt("matchId");
		activityBackStr   = valueExtra.getString("backActivity");
		
		mainMatch   = dataSetup_getMatch();
		if(mainMatch == null)
			return;
		
		String[] teams  = dataSetup_getTeamName(mainMatch);
		
		TextView nameTeamA = (TextView) findViewById(R.id.teamA);
		nameTeamA.setGravity(Gravity.RIGHT);
		nameTeamA.setWidth(MainActivity.width/3);
		nameTeamA.setText(teams[0]);
		
		TextView nameTeamB = (TextView) findViewById(R.id.teamB);
		nameTeamB.setGravity(Gravity.LEFT);
		nameTeamB.setWidth(MainActivity.width/3);
		nameTeamB.setText(teams[1]);
		
		TextView noGoalTxt  = (TextView) findViewById(R.id.noGoalTxt);
		TextView resultGoal = (TextView) findViewById(R.id.result);
		ImageView clock     = (ImageView) findViewById(R.id.clock);
		TextView time       = (TextView) findViewById(R.id.time);
		time.setWidth(MainActivity.width/4);
		time.setGravity(Gravity.CENTER_HORIZONTAL);
		time.setText(mainMatch.getTime());
		
		if(mainMatch.getStatus().equalsIgnoreCase(CONFIG.MATCH_STATUS_FINISHED) || 
				mainMatch.getStatus().equalsIgnoreCase(CONFIG.MATCH_STATUS_INPROGRESS))
		{
			resultGoal.setVisibility(View.VISIBLE);
			int[] teamNumGoal = dataSetup_getNumGoalOfTeam(mainMatch);
	    	resultGoal.setGravity(Gravity.CENTER_HORIZONTAL);
	    	resultGoal.setText(String.valueOf(teamNumGoal[0])+" - "+String.valueOf(teamNumGoal[1]));

	    	clock.setVisibility(View.INVISIBLE);
	    	
	    	if(isGoal(teamNumGoal) == false)
	    		noGoalTxt.setVisibility(View.VISIBLE);
		}
		else if(mainMatch.getStatus().equalsIgnoreCase(CONFIG.MATCH_STATUS_COMING)) // postpone
		{
			resultGoal.setVisibility(View.INVISIBLE);
			clock.setVisibility(View.VISIBLE);
			noGoalTxt.setVisibility(View.VISIBLE);
			noGoalTxt.setText(R.string.matchCommingTxt);
		}
    	
		Button backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(this);
		
		RelativeLayout detailLayout = (RelativeLayout) findViewById(R.id.matchDetail);
		detailLayout.setOnClickListener(this);
		
		ImageButton lastMatchImg = (ImageButton) findViewById(R.id.lastMatches);
		lastMatchImg.setOnClickListener(this);
	}
	
	public boolean isGoal(int[] teamNumGoal)
	{
    	for(int numGoal: teamNumGoal)
    		if(numGoal > 0)
    			return true;
    	return false;
	}
	
	public int[] dataSetup_getNumGoalOfTeam(matchDb match)
	{
		int teamANumGoal = 0;
    	int teamBNumGoal = 0;
		if(activityBackStr.equalsIgnoreCase("matchActivity")|| activityBackStr.equalsIgnoreCase("leagueActivity"))
		{
			teamANumGoal = xmlData.getTotalGoal(MainActivity.goals, match.getID(), CONFIG.TEAM_A_ORDER);
	    	teamBNumGoal = xmlData.getTotalGoal(MainActivity.goals, match.getID(), CONFIG.TEAM_B_ORDER);
		}
		else
		{
			teamANumGoal = MainActivity.goalHandle.getGoalCountByMatchNTeam( match.getID(), Integer.valueOf(CONFIG.TEAM_A_ORDER));
	    	teamBNumGoal = MainActivity.goalHandle.getGoalCountByMatchNTeam( match.getID(), Integer.valueOf(CONFIG.TEAM_B_ORDER));
		}
    	int[] teamNumGoal= {teamANumGoal, teamBNumGoal};
    	return teamNumGoal;
	}

	public matchDb dataSetup_getMatch()
	{
		matchDb match;
		if(activityBackStr.equalsIgnoreCase("matchActivity")|| activityBackStr.equalsIgnoreCase("leagueActivity"))
			match = xmlData.getMatchById(MainActivity.matches, matchId);
		else
			match = MainActivity.matchHandle.getMatch(matchId);
		return match;
	}
	
	public String[] dataSetup_getTeamName(matchDb match)
	{
		int teamA_id  = match.getTeamA();
		int teamB_id  = match.getTeamB();
		String teamA  = "";
		String teamB  = "";
				
		if(activityBackStr.equalsIgnoreCase("matchActivity")|| activityBackStr.equalsIgnoreCase("leagueActivity"))
		{
			teamA  = xmlData.getTeamNameById(MainActivity.teams, teamA_id);
			teamB  = xmlData.getTeamNameById(MainActivity.teams,teamB_id);
		}
		else
		{
			teamA  = MainActivity.teamHandle.getTeam(teamA_id).getName();
			teamB  = MainActivity.teamHandle.getTeam(teamB_id).getName();
		}
		
		teamName[0] = teamA;
		teamName[2] = teamB;
		
		String[] teams = {teamA, teamB};
		return teams;
	}
	
	/*
	 * enableClickMore : user thuong --> click 1 time
	 * 					 admin       --> free to click
	 */
	public void drawVote()
	{
		int lineWidth = 1;
		int idVoteTable = 10001;
	
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
	    int width              = metrics.widthPixels - 113 ; // 60: layout padding on 2 sides: emulator 60
	    int cellSpace          = width/numVoteChoice;
	    int paddingSide        = cellSpace/2;
	    	
	    voteTable = new TableLayout(this);
	    voteTable.setId(idVoteTable);
		TableRow tr = new TableRow(this);
		tr.setBackgroundColor(Color.GRAY);

		TableRow.LayoutParams llp = new TableRow.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
															  android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		llp.setMargins(lineWidth,lineWidth,lineWidth,lineWidth);
		
		LinearLayout cell = new LinearLayout(this);
		cell.setBackgroundColor(Color.WHITE);
		cell.setLayoutParams(llp);

		for(int i = 0; i < numVoteChoice; i++ )
		{
			TextView tv = new TextView(this);
			tv.setText(voteText[i]);
			tv.setTag("Vote"+String.valueOf(i));
			tv.setTypeface(null, Typeface.BOLD);
			tv.setTextColor(CONFIG.COLOR_VOTE);
			tv.setPadding(paddingSide, 0, paddingSide, 0);
			tv.setClickable(true);
			tv.setOnTouchListener(this);
	
			cell.addView(tv);
		}
		tr.addView(cell);
		voteTable.addView(tr);
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.drawBarLayout);
	    layout.addView(voteTable);
	}
	
	public void drawBar()
	{
		int topChart    =  30;
		getNumVotesEachType();
		int idAboveBar  = 0;
		if(voteTable == null)  // incase user can vote -->bar in below vote layout.Otherwise, it belows Forecast txt 
			idAboveBar = R.id.forecastTxt;
		else
			idAboveBar = voteTable.getId();
			
		drawView = new DrawBar(this, numVotes, topChart, teamName);
        drawView.setBackgroundColor(Color.WHITE);
        
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        p.addRule(RelativeLayout.BELOW, idAboveBar);
        drawView.setLayoutParams(p);
        
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.drawBarLayout); 
        layout.addView(drawView);
	}
	
	public void getNumVotesEachType()
	{
		int sizeVoteChoice  = CONFIG.VOTE_CHOICE.length;
		for(int i = 0; i < sizeVoteChoice; i++)
		{
			List<memberVoteDb> memberVotes = MainActivity.memberVoteHandle.getVoteByType(matchId,i); // **
			numVotes[i] = memberVotes.size();
		}
	}
	
	@Override
	public void onClick(View v) 
	{
		if(v.getId() == R.id.backBtn)
		{
		    Intent intent = null;
		   
			if(activityBackStr.equalsIgnoreCase("favouriteActivity"))
				intent = new Intent(this, FavouriteActivity.class);
			else 
				intent = new Intent(this, MatchActivity.class);
				
		    replaceContentView("matchActivity", intent);
		}
		if(v.getId() == R.id.matchDetail || v.getId() == R.id.lastMatches)
		{
			Intent intent = new Intent(this, LastMatchActivity.class);
			intent.putExtra("backActivity", "voteActivity");
			intent.putExtra("matchId", matchId);
			intent.putExtra("backAcOfVoreAc", activityBackStr);
			replaceContentView("lastMatchActivity", intent);
		}
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
	public boolean onTouch(View v, MotionEvent event) 
	{
		if(event.getAction() != MotionEvent.ACTION_DOWN)
			return false;
		
		String tag = v.getTag().toString();
		for(int i = 0; i < numVoteChoice; i++)
		{
			String tagCom = "Vote"+String.valueOf(i);
			if(!tag.equals(tagCom) )
				continue;
			
			boolean isAdmin = sharedPreferences.getBoolean(PREFERENCE_CONF.IS_ADMIN, false);
			if(isAdmin)
				redrawAfterIncreasing(i,CONFIG.ADMIN_ID);
			else// click one time
			{
				String memberId = sharedPreferences.getString(PREFERENCE_CONF.MEM_ID, "");
				if (memberId == "")
					return false;
				
				memberVoteDb memberVoteDb = MainActivity.memberVoteHandle.getMemberVote(matchId,Integer.parseInt(memberId));
				if(memberVoteDb == null) // not vote for this match yet
					redrawAfterIncreasing(i,Integer.parseInt(memberId));
				else// check if this time vote is different from previous time -> difference : ok, otherwise: not allow
				{
					String voteResult = memberVoteDb.getVoteResult();
					if(voteResult.equals(voteValues[i]))
						return false;
					// edit vote in table
					redrawAfterChangingVote(i,Integer.parseInt(memberId));
				}
				
			}
		}
		return false;
	}
	
	public void redrawAfterChangingVote(int index, int memberId)
	{
		//update old column,
		memberVoteDb oldMemberVote = MainActivity.memberVoteHandle.getMemberVote(matchId, memberId);
		memberVoteDb memberVote    = new memberVoteDb(memberId, oldMemberVote.getMatchId(), CONFIG.VOTE_CHOICE[index]);
		
		MainActivity.memberVoteHandle.updateMember(memberVote);
		Toast.makeText(getBaseContext(), "Guess done", Toast.LENGTH_LONG).show();
		((ViewManager)drawView.getParent()).removeView(drawView); // remove old view
		drawBar();
	}
	
	public void redrawAfterIncreasing(int index, int memberId)
	{
		memberVoteDb memberVote = new memberVoteDb(memberId, matchId, voteValues[index]);
		MainActivity.memberVoteHandle.addMemberVote(memberVote);
		
		Toast.makeText(getBaseContext(), "Guess done", Toast.LENGTH_LONG).show();
		((ViewManager)drawView.getParent()).removeView(drawView); // remove old view
		drawBar();
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
