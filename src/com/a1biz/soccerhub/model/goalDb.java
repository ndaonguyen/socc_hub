package com.a1biz.soccerhub.model;

public class goalDb {
     
    int    id;
    int match_id;
    String type;
    String team;
    String minute;
    String name;
    String other;
     
    public goalDb(){
    	this.id 	= 0;
    }
    public goalDb(int id,  int match_id, String type, String team, String minute, String name, String other){
        this.id      = id;
        this.match_id = match_id;
        this.type 	 = type;
        this.team 	 = team;
        this.minute  = minute;
        this.name    = name;
        this.other   = other;
    }
  
    public int getID(){
        return this.id;
    }
     
    public void setID(int id){
        this.id = id;
    }
    
    public int getMatchId(){
        return this.match_id;
    }
     
    public void setMatchId(int match_id){
        this.match_id = match_id;
    }
     
    public String getType(){
        return this.type;
    }
     
    public void setType(String type){
        this.type = type;
    }
    
    public String getTeam(){
        return this.team;
    }
     
    public void setTeam(String team){
        this.team = team;
    }
    
    public String getMinute(){
        return this.minute;
    }
     
    public void setMinute(String minute){
        this.minute = minute;
    }
    
    public String getName(){
        return this.name;
    }
     
    public void setName(String name){
        this.name = name;
    }
    
    public String getOther(){
        return this.other;
    }
     
    public void setOther(String other){
        this.other = other;
    }
}