package com.a1biz.soccerhub.model;

public class matchDb {
     
    int    id;
    int teamA_id;
    int teamB_id;
    String time;
    int    league_id;
    String HT;
    String FT;
    String status;
    String minute;
    String current_period;
    String other;
     
    public matchDb(){
    	this.id 	= 0;
    }
    public matchDb(int id, int teamA_id, int teamB_id, String time, int league_id, String HT, String FT,
    				String status, String minute, String current_period, String other){
        this.id          = id;
        this.teamA_id    = teamA_id;
        this.teamB_id    = teamB_id;
        this.time        = time;
        this.league_id   = league_id;
        this.HT			 = HT;
        this.FT			 = FT;
        this.status		 = status;
        this.minute		 = minute;
        this.current_period = current_period;
        this.other       = other;
    }
    
    public int getID(){
        return this.id;
    }
     
    public void setID(int id){
        this.id = id;
    }
     
    public int getTeamA(){
        return this.teamA_id;
    }
     
    public void setTeamA(int teamA_id){
        this.teamA_id = teamA_id;
    }
    
    public int getTeamB(){
        return this.teamB_id;
    }
     
    public void setTeamB(int teamB_id){
        this.teamB_id = teamB_id;
    }
    
    public String getTime(){
        return this.time;
    }
     
    public void setTime(String time){
        this.time = time;
    }
    
    public int getLeagueId(){
        return this.league_id;
    }
     
    public void setLeagueId(int league_id){
        this.league_id = league_id;
    }
    
    public String getHT(){
        return this.HT;
    }
     
    public void setHT(String HT){
        this.HT = HT;
    }
    
    public String getFT(){
        return this.FT;
    }
     
    public void setFT(String FT){
        this.FT = FT;
    }

    public String getStatus(){
        return this.status;
    }
     
    public void setStatus(String status){
        this.status = status;
    }
    
    public String getMinute(){
        return this.minute;
    }
     
    public void setMinute(String minute){
        this.minute = minute;
    }
    
    public String getCurrentPeriod(){
        return this.current_period;
    }
     
    public void setCurrentPeriod(String current_period){
        this.current_period = current_period;
    }
    
    public String getOther(){
        return this.other;
    }
    
    public void setOther(String other){
        this.other = other;
    }
}