package com.ianl.ianlprayertimeswidget;

import android.content.Context;
import android.os.AsyncTask;

/**
 * AsyncTask to perform the URL connection and XML parsing
 * in a background thread, off the main UI thread.
 * 
 * @author Tehmur
 *
 */
class RetreiveFeedTask extends AsyncTask<String, Void, PrayerTimings> {
	private Context mContext;
	
	public RetreiveFeedTask(Context context) {
        mContext = context;
    } 

    protected PrayerTimings doInBackground(String... urls) {
    	PrayerTimings pt = null;
        try {
            pt = new PrayerTimesFetcher(mContext).fetchTodaysPrayerTimes();
        } 
        catch (Exception e) {
        	e.printStackTrace();
        }
        return pt;
    }
    
 }