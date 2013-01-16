package com.example.ianlprayertimeswidget;

import java.text.ParseException;
import java.util.Date;

public class PrayerTimesFetcher {

	public PrayerTimesFetcher() {
		
	}	
	
	/**
	 * Gets the prayer times from the database
	 * 
	 * @return PrayerTimings object which contains todays prayer times
	 * @throws ParseException
	 */
	public PrayerTimings fetchTodaysPrayerTimes() throws ParseException {
		//dummy data
		Date today = new Date();	
		
		//fajr,sunrise,..
		Date[] start = { PrayerTimesWidget.prayerSdf.parse("05:30"), 
				PrayerTimesWidget.prayerSdf.parse("07:30"), 
				PrayerTimesWidget.prayerSdf.parse("12:30"),
				PrayerTimesWidget.prayerSdf.parse("14:30"), 
				PrayerTimesWidget.prayerSdf.parse("16:30") };
		
		Date[] jamaa = { PrayerTimesWidget.prayerSdf.parse("06:30"), 
				PrayerTimesWidget.prayerSdf.parse("07:30"), 
				PrayerTimesWidget.prayerSdf.parse("13:00"),
				PrayerTimesWidget.prayerSdf.parse("15:30"), 
				PrayerTimesWidget.prayerSdf.parse("16:30") };
		
		PrayerTimings pt = new PrayerTimings(today, start, jamaa);
		
		return pt;
	}

}
