package com.ianl.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DateAndTimeUtils {
	final static SimpleDateFormat dateSdf = 
			new SimpleDateFormat("EEE d MMM ''yy");
	final static SimpleDateFormat prayerSdf = 
			new SimpleDateFormat("HH:mm");
	
	public static String formatDateString(String date) {
		if (date == null) {
			return date; // return null
		}
		try {
			return dateSdf.format(dateSdf.parse(date));
		} 
		catch (ParseException e) {
			return date; // return unformatted string
		}
	}
	
	public static String formatTimeString(String time) {
		if (time == null) {
			return time; // return null
		}
		try {
			return prayerSdf.format(prayerSdf.parse(time));
		} 
		catch (ParseException e) {
			return time; // return unformatted string
		}
	}

}
