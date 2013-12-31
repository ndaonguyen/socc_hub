package com.a1biz.soccerhub.model;

public class teamDb {
     
    int    id;
    String name;
    String flag;
    String country;
    int point;
    int game_played;
    int won;
    int drawn;
    int lost;
    int goals_forward;
    int goals_against;
    int goals_diff;
    String other;
     
    public teamDb(){
    	this.id 	= 0;
    }
    public teamDb(int id,String name, String flag, String country,int point, int game_play,
    			  int won, int drawn, int lost, int goals_forward,int goals_against, int goals_diff,   String other){
        this.id     	 = id;
        this.name   	 = name;
        this.flag   	 = flag;
        this.country	 = country;
        this.point  	 = point;
        this.game_played   = game_play;
        this.won   		 = won;
        this.drawn  	 = drawn;
        this.lost        = lost;
        this.goals_forward   = goals_forward;
        this.goals_against   = goals_against;
        this.goals_diff  = goals_diff;
        this.other       = other;
    }
    
    public int getID(){
        return this.id;
    }
     
    public void setID(int id){
        this.id = id;
    }
    
    public String getName(){
        return this.name;
    }
  
    public void setName(String name){
        this.name = name;
    }
    
    public String getFlag(){
        return this.flag;
    }
  
    public void setFlag(String flag){
        this.flag = flag;
    }
    public String getCountry(){
        return this.country;
    }
  
    public void setCountry(String country){
        this.country = country;
    }
    
    public int getPoint(){
        return this.point;
    }
     
    public void setPoint(int point){
        this.point = point;
    }
    
    public int getGamePlayed(){
        return this.game_played;
    }
     
    public void setGamePlayed(int game_play){
        this.game_played = game_play;
    }
    
    public int getWon(){
        return this.won;
    }
     
    public void setWon(int won){
        this.won = won;
    }
    
    public int getDrawn(){
        return this.drawn;
    }
     
    public void setDrawn(int drawn){
        this.drawn = drawn;
    }
    
    public int getLost(){
        return this.lost;
    }
     
    public void setLost(int lost){
        this.lost = lost;
    }
    
    public int getGoalForward(){
        return this.goals_forward;
    }
     
    public void setGoalForward(int goals_forward){
        this.goals_forward = goals_forward;
    }
    
    public int getGoalAgainst(){
        return this.goals_against;
    }
     
    public void setGoalAgainst(int goals_against){
        this.goals_against = goals_against;
    }
    
    public int getGoalDiff(){
        return this.goals_diff;
    }
     
    public void setGoalDiff(int goals_diff){
        this.goals_diff = goals_diff;
    }
    
    public String getOther(){
        return this.other;
    }
  
    public void setOther(String other){
        this.other = other;
    }
}