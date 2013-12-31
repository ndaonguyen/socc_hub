package com.a1biz.soccerhub.model;

public class countryDb {
     
    int    id;
    String name;
    String other;
     
    public countryDb(){
    	this.id 	= 0;
    }
    public countryDb(int id, String name,  String other){
        this.id      = id;
        this.name    = name;
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
    
    public String getOther(){
        return this.other;
    }
     
    public void setOther(String other){
        this.other = other;
    }
}