package com.a1biz.soccerhub.model;

public class favouriteDb {
     
    int    member_id;
    int    match_id;
     
    public favouriteDb(){
    }
    
    public favouriteDb(int member_id, int match_id){
        this.member_id      = member_id;
        this.match_id		= match_id;
    }
 
    public int getMemberID(){
        return this.member_id;
    }
     
    public void setMemberId(int member_id){
        this.member_id = member_id;
    }
     
    public int getMatchId(){
        return this.match_id;
    }
     
    public void setMatchId(int match_id){
        this.match_id = match_id;
    }
}