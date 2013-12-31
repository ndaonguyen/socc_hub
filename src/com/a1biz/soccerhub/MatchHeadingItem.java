package com.a1biz.soccerhub;

public class MatchHeadingItem implements MatchItem{

	private final String title;
	
	public MatchHeadingItem(String title) {
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	@Override
	public boolean isSection() {
		return true;
	}

}
