package com.a1biz.soccerhub.model;

public class memberVoteDb {
     
    int    memberId;
    int	   matchId;
    String voteResult;
     
    public memberVoteDb(){
    }
    
    public memberVoteDb(int memberId, int matchId, String voteResult){
        this.memberId      = memberId;
        this.matchId       = matchId;
        this.voteResult    = voteResult;
    }
     
    public int getMemberId(){
        return this.memberId;
    }
     
    public void setMemberId(int memberId){
        this.memberId = memberId;
    }
     
    public int getMatchId(){
        return this.matchId;
    }
     
    public void setMatchId(int matchId){
        this.matchId = matchId;
    }
    
    public String getVoteResult(){
        return this.voteResult;
    }
     
    public void setVoteResult(String voteResult){
        this.voteResult = voteResult;
    }
 
}