package com.example.ianlprayertimeswidget;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

public class PrayerTimesFetcher {
	private Context mContext;
	private final String mURLFeed; 

	public PrayerTimesFetcher(Context context) {
		mContext = context;
		mURLFeed = context.getResources().getString(R.string.my_url_feed);
	}	
	
	/**
	 * Gets the prayer times from the database
	 * 
	 * @return PrayerTimings object which contains todays prayer times
	 * @throws ParseException
	 */
	public PrayerTimings fetchTodaysPrayerTimes() throws ParseException {
		PrayerTimings pt = null;
		try {
			//TODO: GET LINK AND THEN UNCOMMENT CODE
			pt = processStream(mContext.getAssets()
				.open("OpenMosqueMLSample.xml"));
			/*URL url = new URL(mURLFeed);
			
			// Create a new HTTP URL connection
			URLConnection conn = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			
			int responseCode = httpConn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConn.getInputStream();
				//pt = processStream(in);
				pt = processStream(mContext.getAssets()
						.open("OpenMosqueMLSample.xml"));
			}*/
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
		
		return pt;
	}
	
	/**
	 * Parses XML file and extracts today's prayer times
	 * 
	 * @param input stream for XML file
	 * @return PrayerTimings object for today
	 */
	private PrayerTimings processStream(InputStream in) {
		PrayerTimings pTimings = null;
		XmlPullParserFactory factory;
		try {
			Log.d("PTF", "in processStream");
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			// Assign a new input stream
			xpp.setInput(in, null);
			int eventType = xpp.getEventType();
			
			// Continue until the end of the document is reached
			while (eventType != XmlPullParser.END_DOCUMENT) {

				// Check for a start tag of the PrayerTimetable tag
				if (eventType == XmlPullParser.START_TAG &&
						xpp.getName().equals("PrayerTimetable")) {
					eventType = xpp.next();
			        
					boolean isToday = true;
					// Process each result within the PrayerTimetable tag.
					while (!(eventType == XmlPullParser.END_TAG && 
							xpp.getName().equals("PrayerTimetable"))  && isToday) {
						
						// Check for the name tag within the Day tag.
						if (eventType == XmlPullParser.START_TAG
								&& xpp.getName().equals("Day")) {
														
							// Process each result within the Day tag.
							while (!(eventType == XmlPullParser.END_TAG && 
									xpp.getName().equals("Day"))) {

								switch (eventType) {
								case XmlPullParser.START_TAG:
									// Check for the name tag within the Date tag.
									if (xpp.getName().equals(getString(R.string.xml_date_tag))) {
										String nextText = xpp.nextText();
										// Check to see if correct day, if so then
										// negate flag to prevent incorrect date's 
										// data being written to PT object
										if (isParsedDateToday(nextText)) {
											isToday = false;
										}
										pTimings = new PrayerTimings(nextText);
										break;
									}
									// Check for the name tag within the FajrStart tag.
									if (xpp.getName().equals(getString(R.string.xml_fajr_start_tag))) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}		
									// Check for the name tag within the FajrJamaat tag.
									if (xpp.getName().equals(getString(R.string.xml_fajr_jamaa_tag))) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the Shrooq tag.
									if (xpp.getName().equals(getString(R.string.xml_sunrise_tag))) {
										pTimings.getStartTimes().add(xpp.nextText());
										pTimings.getJamaaTimes().add("");
										break;
									}
									// Check for the name tag within the DhuhrStart tag.
									if (xpp.getName().equals(getString(R.string.xml_dhuhr_start_tag))) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the DhuhrJamaat tag.
									if (xpp.getName().equals(getString(R.string.xml_dhuhr_jamaa_tag))) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the AsrStart tag.
									if (xpp.getName().equals(getString(R.string.xml_asr_start_tag))) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the AsrJamaat tag.
									if (xpp.getName().equals(getString(R.string.xml_asr_jamaa_tag))) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the MaghribStart tag.
									if (xpp.getName().equals(getString(R.string.xml_maghrib_start_tag))) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the MaghribJamaat tag.
									if (xpp.getName().equals(getString(R.string.xml_maghrib_jamaa_tag))) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the IshaaStart tag.
									if (xpp.getName().equals(getString(R.string.xml_isha_start_tag))) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the IshaaJamaat tag.
									if (xpp.getName().equals(getString(R.string.xml_isha_jamaa_tag))) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}

								default:
									Log.d("PTF", "None");
									break;
								}
								// Move on to the next tag.
								eventType = xpp.next();
							}
							
							
						}
						// Move on to the next tag.
						eventType = xpp.next();
					}

				} // End PrayerTimetable if check
				
				// Move on to the next result tag.
				eventType = xpp.next();
			} // End of Document While
			
		} 
		catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return pTimings;
	}

	/**
	 * Checks to see if the parsed date is today's date
	 * @param nextText - parsed date
	 * @return boolean stating whether parsed date is today
	 */
	private boolean isParsedDateToday(String nextText) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String todaysDate = dateFormat.format(new Date());
		return todaysDate.equals(nextText);
	}
	
	private String getString(int stringId) {
		return mContext.getResources().getString(stringId);
	}

}
