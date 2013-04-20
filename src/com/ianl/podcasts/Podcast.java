package com.ianl.podcasts;

public class Podcast {
	//private String podcastType;
	
	private String title;
	private String artist;
	private String duration;
	//private Date when; 
	
	public Podcast() {}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}


}
