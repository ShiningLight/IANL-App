package com.example.ianlprayertimeswidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PrayerTimings {
	final static public SimpleDateFormat prayerSdf = 
			new SimpleDateFormat("HH:mm");
	final private SimpleDateFormat dateSdf = 
			new SimpleDateFormat("EEE d MMM ''yy");
	
	private String mToday;

	private ArrayList<String> mStartTimes;
	private ArrayList<String> mJamaaTimes;
	
/*	private Time fajr;
	private Time sunrise;
	private Time dhuhr;
	private Time asr;
	private Time maghrib;
	private Time isha;*/

	public PrayerTimings(String today) {
		mToday = today;//dateSdf.format(new Date(today));
		mStartTimes = new ArrayList<String>();
		mJamaaTimes = new ArrayList<String>();
	}
	
	public PrayerTimings(String today, ArrayList<String> start, ArrayList<String> jamaa) {
		mToday = today;
		mStartTimes = start;
		mJamaaTimes = jamaa;
	}
	
	public String getToday() {
		return mToday;
	}
	
	public ArrayList<String> getStartTimes() {
		return mStartTimes;
	}
	
	public ArrayList<String> getJamaaTimes() {
		return mJamaaTimes;
	}
	
	public String getTodaysDate() {
		try {
			return dateSdf.format(dateSdf.parse(mToday));
		} catch (ParseException e) {
			return mToday; // return unformatted string
		}
	}
	
	public String getStartTimesAtIndex(int index) {
		try {
			return prayerSdf.format(prayerSdf.parse(mStartTimes.get(index)));
		} catch (ParseException e) {
			return mStartTimes.get(index); // return unformatted string
		}
	}
	
	public String getJamaaTimesAtIndex(int index) {
		try {
			return prayerSdf.format(prayerSdf.parse(mJamaaTimes.get(index)));
		} catch (ParseException e) {
			return mJamaaTimes.get(index); // return unformatted string
		}
	}
	
}
