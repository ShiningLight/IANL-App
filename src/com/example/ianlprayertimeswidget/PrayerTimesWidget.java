package com.example.ianlprayertimeswidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class PrayerTimesWidget extends AppWidgetProvider {
	final static public SimpleDateFormat prayerSdf = 
			new SimpleDateFormat("hh:mm");
	final private SimpleDateFormat dateSdf = 
			new SimpleDateFormat("EEE d MMM ''yy");
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// Update the widget UI

		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				PrayerTimesWidget.class);

		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {
			
			// Set the data in the views of the widget
			RemoteViews remoteViews = updatePrayerTimeWidgetUI(context);
			
			// Notify App manager to update widget using modified remote view
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}

	}

	private RemoteViews updatePrayerTimeWidgetUI(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.prayer_times_widget_layout);
		
		try {
			PrayerTimings pt = new PrayerTimesFetcher().fetchTodaysPrayerTimes();
			
			// Set todays date
			remoteViews.setTextViewText(R.id.todays_prayer_times_date_textView,
					dateSdf.format(pt.getToday()));
			
			// Set the prayer times
			remoteViews.setTextViewText(R.id.fajr_start_textView, 
					prayerSdf.format(pt.getStartTimes()[0]));
			remoteViews.setTextViewText(R.id.fajr_jamaa_textView, 
					prayerSdf.format(pt.getJamaaTimes()[0]));
			
			remoteViews.setTextViewText(R.id.sunrise_start_textView, 
					prayerSdf.format(pt.getStartTimes()[1]));
			remoteViews.setTextViewText(R.id.sunrise_jamaa_textView, 
					prayerSdf.format(pt.getJamaaTimes()[1]));
			
			remoteViews.setTextViewText(R.id.dhuhr_start_textView, 
					prayerSdf.format(pt.getStartTimes()[1]));
			remoteViews.setTextViewText(R.id.dhuhr_jamaa_textView, 
					prayerSdf.format(pt.getJamaaTimes()[1]));
			
			remoteViews.setTextViewText(R.id.asr_start_textView, 
					prayerSdf.format(pt.getStartTimes()[1]));
			remoteViews.setTextViewText(R.id.asr_jamaa_textView, 
					prayerSdf.format(pt.getJamaaTimes()[1]));
			
			remoteViews.setTextViewText(R.id.maghrib_start_textView, 
					prayerSdf.format(pt.getStartTimes()[1]));
			remoteViews.setTextViewText(R.id.maghrib_jamaa_textView, 
					prayerSdf.format(pt.getJamaaTimes()[1]));
			
			remoteViews.setTextViewText(R.id.isha_start_textView, 
					prayerSdf.format(pt.getStartTimes()[1]));
			remoteViews.setTextViewText(R.id.isha_jamaa_textView, 
					prayerSdf.format(pt.getJamaaTimes()[1]));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		
		return remoteViews;
		
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		// Handle deletion of the widget
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		// Widget has been disabled
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		// Widget has been enabled
	}

}
