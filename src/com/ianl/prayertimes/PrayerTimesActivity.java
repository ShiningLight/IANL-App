package com.ianl.prayertimes;

import java.util.concurrent.ExecutionException;

import junit.framework.Assert;

import com.ianl.R;
import com.ianl.utils.DateAndTimeUtils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PrayerTimesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prayer_times_main);
		
		try {
			PrayerTimings pt = new PrayerTimeLoadingTask(this).execute(
					getResources().getString(
							R.string.todays_prayer_times_url_feed)).get();
			setUIWithTimes(pt);		
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setUIWithTimes(PrayerTimings pt) {
		Assert.assertNotNull("PrayerTimings is null", pt);
		
		// Set todays date
		setTextViewText(R.id.todays_prayer_times_date_textView,
				DateAndTimeUtils.formatDateString(pt.getTodaysDate()));

		// Set the prayer times
		setTextViewText(R.id.fajr_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getFajrStart()));
		setTextViewText(R.id.fajr_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getFajrJamaa()));
		
		setTextViewText(R.id.sunrise_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getSunrise()));
		setTextViewText(R.id.sunrise_jamaa_textView,
				"");

		setTextViewText(R.id.dhuhr_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getDhuhrStart()));
		setTextViewText(R.id.dhuhr_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getDhuhrJamaa()));

		setTextViewText(R.id.asr_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getAsrStart()));
		setTextViewText(R.id.asr_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getAsrJamaa()));

		setTextViewText(R.id.maghrib_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getMaghribStart()));
		setTextViewText(R.id.maghrib_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getMaghribJamaa()));

		setTextViewText(R.id.isha_start_textView,
				DateAndTimeUtils.formatTimeString(pt.getIshaaStart()));
		setTextViewText(R.id.isha_jamaa_textView,
				DateAndTimeUtils.formatTimeString(pt.getIshaaJamaa()));
	}

	/**
	 * Finds the text view and adds text to it
	 * @param textViewId - the text view to set
	 * @param text - text to set in text view
	 */
	private void setTextViewText(int textViewId, String text) {
		TextView tv = ((TextView) findViewById(textViewId));
		if (tv != null) {
			tv.setText(text);
		}
	}

}
