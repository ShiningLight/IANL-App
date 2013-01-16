package com.example.ianlprayertimeswidget;

import java.util.Date;

public class PrayerTimings {
	
	private Date today;

	private Date[] startTimes;
	private Date[] jamaaTimes;
	
/*	private Time fajr;
	private Time sunrise;
	private Time dhuhr;
	private Time asr;
	private Time maghrib;
	private Time isha;*/
	
	public PrayerTimings(Date today, Date[] start, Date[] jamaa) {
		this.today = today;
		this.startTimes = start;
		this.jamaaTimes = jamaa;
	}
	
	public Date getToday() {
		return today;
	}
	
	public Date[] getStartTimes() {
		return startTimes;
	}
	
	public Date[] getJamaaTimes() {
		return jamaaTimes;
	}
	
}
