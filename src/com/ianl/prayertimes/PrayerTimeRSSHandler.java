package com.ianl.prayertimes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ianl.utils.DateAndTimeUtils;

import android.util.Log;

/**
 * Class to handle SAX tags
 */
class PrayerTimeRSSHandler extends DefaultHandler {
	/* List of all items parsed */
	private ArrayList<PrayerTimings> mAllPrayerTimes;
	/* We have a local reference to an object which is constructed while parser
	 * is working on an item tag. Used to reference item while parsing */	
	private PrayerTimings mCurrentPrayerTimings = new PrayerTimings();
	/* Flag to check if the first item has been encountered, to avoid
	 * taking tags from channel info tags with the same tags e.g. title */
	private StringBuffer mChars = new StringBuffer();
	private boolean mTodaysPrayerTimesComplete = false;
	
	public PrayerTimeRSSHandler() {
		mAllPrayerTimes = new ArrayList<PrayerTimings>();
	}

	/* 
	 * Called on every opening XML node (e.g. <item>). We create an empty
	 * Podcast object when an 'item' start tag is being processed.
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) {
		if (!mTodaysPrayerTimesComplete) {
			// Reset the chars StringBuffer to be sure that the text we retrieve
			// is always only from our current simple element
			mChars = new StringBuffer();
			if (localName.equalsIgnoreCase("PrayerTimetable")) {
				Log.d("PRH", "start timetable");
			}
			else if (localName.equalsIgnoreCase("Day")) {
				Log.d("PRH", "start Day");
				mCurrentPrayerTimings = new PrayerTimings();
			}
		}
	}

	/*
	 * Called when any closing XML marker is found (e.g. </item>). At this
	 * point we check which element we are in and decide if we should process
	 * the contents. We know our StringBuffer was reset in startElement
	 * and we know our characters() method has been called and collected all
	 * the text inside this element, so we can now safely use this information
	 * to set an attribute on our Podcast object.
	 * 
	 * The EndElement method adds the current Podcast to the list when a
	 * closing 'item' tag is processed.
	 */	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase("PrayerTimetable")) {
			Log.d("PRH", "end timetable");
		}
		else if (!mTodaysPrayerTimesComplete) {
			if (localName.equalsIgnoreCase("Date")
					&& mCurrentPrayerTimings.getTodaysDate() == null) {
				mCurrentPrayerTimings.setTodaysDate(mChars.toString());
				Log.d("PRH", "end Date");
			}
			else if (localName.equalsIgnoreCase("FajrStart")
					&& mCurrentPrayerTimings.getFajrStart() == null) {
				mCurrentPrayerTimings.setFajrStart(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("FajrJamaat")
					&& mCurrentPrayerTimings.getFajrJamaa() == null) {
				mCurrentPrayerTimings.setFajrJamaa(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("Shrooq")
					&& mCurrentPrayerTimings.getSunrise() == null) {
				mCurrentPrayerTimings.setSunrise(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("DhuhrStart")
					&& mCurrentPrayerTimings.getDhuhrStart() == null) {
				mCurrentPrayerTimings.setDhuhrStart(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("DhuhrJamaat")
					&& mCurrentPrayerTimings.getDhuhrJamaa() == null) {
				mCurrentPrayerTimings.setDhuhrJamaa(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("AsrStart")
					&& mCurrentPrayerTimings.getAsrStart() == null) {
				mCurrentPrayerTimings.setAsrStart(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("AsrJamaat")
					&& mCurrentPrayerTimings.getAsrJamaa() == null) {
				mCurrentPrayerTimings.setAsrJamaa(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("MaghribStart")
					&& mCurrentPrayerTimings.getMaghribStart() == null) {
				mCurrentPrayerTimings.setMaghribStart(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("MaghribJamaat")
					&& mCurrentPrayerTimings.getMaghribJamaa() == null) {
				mCurrentPrayerTimings.setMaghribJamaa(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("IshaaStart")
					&& mCurrentPrayerTimings.getIshaaStart() == null) {
				mCurrentPrayerTimings.setIshaaStart(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("IshaaJamaat")
					&& mCurrentPrayerTimings.getIshaaJamaa() == null) {
				mCurrentPrayerTimings.setIshaaJamaa(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("Day")) {
				if (!isParsedDateToday(mCurrentPrayerTimings.getTodaysDate())) {
					mCurrentPrayerTimings = null;
					Log.d("PRH", "end Day - not today");
				} else {
					mTodaysPrayerTimesComplete = true;
					Log.d("PRH", "end Day - today");
				}
			}
		}
	}

	/*
	 * Called whilst reading the text stored in a simple element - however,
	 * this is not just called once at the end of the element, but can be
	 * called several times, so we must be careful to be sure we don't process
	 * the text here as it maybe incomplete - so for now we just accumulate
	 * the text in our StringBuffer, and we will process it later, when we
	 * are sure we have all the text.
	 */
	@Override
	public void characters(char ch[], int start, int length) {
		mChars.append(new String(ch, start, length));
	}

	public ArrayList<PrayerTimings> getAllPrayerTimings() {
		return mAllPrayerTimes;
	}
	
	/**
	 * 
	 * @return Object with today's prayer times, or create an empty object
	 */
	public PrayerTimings getTodaysPrayerTimings() {
		return (mCurrentPrayerTimings != null) 
				? mCurrentPrayerTimings : new PrayerTimings();
	}
	
	/**
	 * Checks to see if the parsed date is today's date
	 * @param nextText - parsed date
	 * @return boolean stating whether parsed date is today
	 */
	private boolean isParsedDateToday(String nextText) {
		String todaysDate = "2013-01-20";//DateAndTimeUtils.formatDateString(new Date());
		Log.d("PRH", "param date is " + nextText + " today is " + todaysDate);
		return todaysDate.equals(nextText);
	}	
}