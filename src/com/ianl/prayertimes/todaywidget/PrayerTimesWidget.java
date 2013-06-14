package com.ianl.prayertimes.todaywidget;

import java.util.concurrent.ExecutionException;

import com.ianl.R;
import com.ianl.prayertimes.PrayerTimeLoadingTask;
import com.ianl.prayertimes.PrayerTimings;
import com.ianl.utils.DateAndTimeUtils;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.RemoteViews;

public class PrayerTimesWidget extends AppWidgetProvider {
	
	@Override // Update the widget UI
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
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
		if (isOnline(context)) {
			try {
				PrayerTimings pt = new PrayerTimeLoadingTask(context)
						.execute(context.getString(
								R.string.todays_prayer_times_url_feed)).get();
				remoteViews = setUIWithTimes(remoteViews, pt);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		return remoteViews;		
	}

	private boolean isOnline(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			boolean connected = networkInfo != null
					&& networkInfo.isAvailable() && networkInfo.isConnected();
			Log.d("PTW", "Internet status is " + connected);
			return connected;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private RemoteViews setUIWithTimes(RemoteViews remoteViews, PrayerTimings pt) {
		// Set todays date
		remoteViews.setTextViewText(R.id.todays_prayer_times_date_textView,
				DateAndTimeUtils.formatDateString(pt.getTodaysDate()));

		// Set the prayer times
		remoteViews.setTextViewText(R.id.fajr_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getFajrStart()));
		remoteViews.setTextViewText(R.id.fajr_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getFajrJamaa()));
		
		remoteViews.setTextViewText(R.id.sunrise_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getSunrise()));
		remoteViews.setTextViewText(R.id.sunrise_jamaa_textView,
				"");

		remoteViews.setTextViewText(R.id.dhuhr_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getDhuhrStart()));
		remoteViews.setTextViewText(R.id.dhuhr_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getDhuhrJamaa()));

		remoteViews.setTextViewText(R.id.asr_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getAsrStart()));
		remoteViews.setTextViewText(R.id.asr_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getAsrJamaa()));

		remoteViews.setTextViewText(R.id.maghrib_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getMaghribStart()));
		remoteViews.setTextViewText(R.id.maghrib_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getMaghribJamaa()));

		remoteViews.setTextViewText(R.id.isha_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getIshaaStart()));
		remoteViews.setTextViewText(R.id.isha_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getIshaaJamaa()));
		
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
