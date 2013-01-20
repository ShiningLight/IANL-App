package com.ianl.ianlprayertimeswidget;

import android.content.Context;
import android.os.AsyncTask;

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