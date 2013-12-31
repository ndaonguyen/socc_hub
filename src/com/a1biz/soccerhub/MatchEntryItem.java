package com.a1biz.soccerhub;

import com.a1biz.soccerhub.model.matchDb;


public class MatchEntryItem implements MatchItem{

	public matchDb match;

	public MatchEntryItem(matchDb match) {
		this.match = match;
	}
	
	@Override
	public boolean isSection() {
		return false;
	}

}
