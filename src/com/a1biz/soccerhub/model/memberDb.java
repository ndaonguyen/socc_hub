package com.a1biz.soccerhub.model;

public class memberDb {
     
    int    id;
    String name;
    String email;
    String password;
    String other;
     
    public memberDb(){
    	this.id 	= 0;
    }
    public memberDb(int id, String name, String email, String password, String other){
        this.id      = id;
        this.name    = name;
        this.email   = email;
        this.password= password;
        this.other   = other;
    }
     
    public memberDb( String name, String email, String password, String other){
    	this.name    = name;
    	this.email   = email;
    	this.password= password;
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
    
    public String getEmail(){
        return this.email;
    }
     
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPassword(){
        return this.password;
    }
     
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getOther(){
        return this.other;
    }
     
    public void setOther(String other){
        this.other = other;
    }
}