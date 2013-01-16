package com.example.ianlprayertimeswidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class PrayerTimesWidget extends AppWidgetProvider {
	final private SimpleDateFormat dt = new SimpleDateFormat("hh:mm");
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// Update the widget UI

		// Get all ids
		ComponentName thisWidget = new ComponentName(context,
				PrayerTimesWidget.class);

		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {

			RemoteViews remoteViews = updatePrayerTimeWidgetUI(context);
			/*RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
					R.layout.prayer_times_grid);*/

			/*// Set the text
			remoteViews.setTextViewText(R.id.prayer_times_date_textView,
					"Mon 23rd Jan 2012");*/
			/*Log.d("PTW", "widgetId is: " + widgetId);
			Intent intent = new Intent(context, 
					PrayerTimesRemoteViewsService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);*/
			
			/*remoteViews.setRemoteAdapter(widgetId, 
					R.id.prayer_tiems_gridViewOnly, intent);*/
			
			/*// Register an onClickListener
			Intent widgetIntent = new Intent(context, PrayerTimesWidget.class);

			widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, widgetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			remoteViews.setOnClickPendingIntent(R.id.prayer_times_date_textView,
					pendingIntent);*/
			
			// Notify App manager to update widget using modified remote view
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}

	}

	private RemoteViews updatePrayerTimeWidgetUI(Context context) {
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.prayer_times_grid);
		
		try {
			PrayerTimings pt = fetchTodaysPrayerTimes();
			
			remoteViews.setTextViewText(R.id.todays_prayer_times_date_textView,
					pt.getToday().toString());
			
			remoteViews.setTextViewText(R.id.fajr_start_textView, 
					dt.format(pt.getStartTimes()[0]));
			remoteViews.setTextViewText(R.id.fajr_jamaa_textView, 
					dt.format(pt.getJamaaTimes()[0]));
			
			remoteViews.setTextViewText(R.id.dhuhr_start_textView, 
					dt.format(pt.getStartTimes()[1]));
			remoteViews.setTextViewText(R.id.dhuhr_jamaa_textView, 
					dt.format(pt.getJamaaTimes()[1]));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		
		return remoteViews;
		
	}
	
	private PrayerTimings fetchTodaysPrayerTimes() throws ParseException {
		//dummy data
		Date today = new Date();			
		//fajr,sunrise,..
		Date[] start = {dt.parse("05:30"), dt.parse("07:30"), dt.parse("12:30"),
				dt.parse("14:30"), dt.parse("16:30")};
		Date[] jamaa = {dt.parse("06:30"), dt.parse("07:30"), dt.parse("13:00"),
				dt.parse("15:30"), dt.parse("16:30")};
		PrayerTimings pt = new PrayerTimings(today, start, jamaa);
		
		return pt;
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
