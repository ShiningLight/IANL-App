package com.example.ianlprayertimeswidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.Time;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class PrayerTimesRemoteViewsService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		return new PrayerTimesRemoteViewsFactory(getApplicationContext());
	}

	class PrayerTimesRemoteViewsFactory implements RemoteViewsFactory {
		final private SimpleDateFormat dt = new SimpleDateFormat("hh:mm");
		
		private Context mContext;
		private PrayerTimings prayerTimings;		
		
		public PrayerTimesRemoteViewsFactory(Context context) {
			mContext = context;
		}

		@Override
		public void onCreate() {
			try {
				prayerTimings = fetchTodaysPrayerTimes();
			} catch (ParseException e) {
				e.printStackTrace();
			}
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
		public void onDataSetChanged() {
		}
		
		@Override
		public int getCount() {
			if (prayerTimings != null) {
				return prayerTimings.getStartTimes().length;
			}
			else {
				return 0;
			}
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public RemoteViews getViewAt(int index) {
			RemoteViews rv = new RemoteViews(mContext.getPackageName(),
					R.layout.prayer_times_widget_item);
			Log.d("PTRV", "index is: " + index);
			switch (index) {			
			case 0:
				rv.setTextViewText(R.id.prayer_textView, "Prayer");
				break;
			case 1:
				rv.setTextViewText(R.id.prayer_textView, "Start");
				break;
			case 2:
				rv.setTextViewText(R.id.prayer_textView, "Jamaa\'");
				break;
			case 3:
				rv.setTextViewText(R.id.prayer_textView, 
						getResources().getString(R.string.fajr_label));
				break;
			case 4:
				rv.setTextViewText(R.id.prayer_textView, 
						dt.format(prayerTimings.getStartTimes()[0]));
				break;
			case 5:
				rv.setTextViewText(R.id.prayer_textView, 
						dt.format(prayerTimings.getJamaaTimes()[0].toString()));
				break;
			case 6:
				rv.setTextViewText(R.id.prayer_textView, 
						getResources().getString(R.string.sunrise_label));
				break;
			case 7:
				rv.setTextViewText(R.id.prayer_textView, 
						prayerTimings.getStartTimes()[1].toString());
				break;
			case 8:
				rv.setTextViewText(R.id.prayer_textView, 
						prayerTimings.getJamaaTimes()[1].toString());
				break;
			default:
				break;
			}			
			
			/*RemoteViews rv = new RemoteViews(mContext.getPackageName(),
					R.layout.prayer_times_widget_layout);
			rv.setTextViewText(R.id.mosque_timetable_textView, "Prayer times");
			rv.setTextViewText(R.id.prayer_times_date_textView, "Wed 12th Feb 2012");
			*/
			/*Intent fillInIntent = new Intent();
			Uri uri = Uri.parse("Jan 23");
			fillInIntent.setData(uri);
			rv.setOnClickFillInIntent(R.id.mosque_timetable_textView, fillInIntent);
			rv.setOnClickFillInIntent(R.id.prayer_times_date_textView, fillInIntent);*/
			return rv;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}
		
		@Override
		public RemoteViews getLoadingView() {
			return null;
		}

		@Override
		public void onDestroy() {			
		}
		
	} // End Factory class
	
} // End RemoteViewService class
