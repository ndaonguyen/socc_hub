package com.a1biz.soccerhub.model;

public class leagueDb {
     
    int    id;
    String name;
    int country;
    String other;
     
    public leagueDb(){
    	this.id 	= 0;
    }
    public leagueDb(int id, String name, int country, String other){
        this.id      = id;
        this.name    = name;
        this.country= country;
        this.other   = other;
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
    
    public int getCountry(){
        return this.country;
    }
     
    public void setcountry(int country){
        this.country = country;
    }
    
    public String getOther(){
        return this.other;
    }
     
    public void setOther(String other){
        this.other = other;
    }
}