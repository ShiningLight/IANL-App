package com.example.ianlprayertimeswidget;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;

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
			Log.d("PTF", "calling processStream");
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
	//TODO: CHECK TIME AND COMPARE WITH TODAY TO GET CORRECT TIMES!!!
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

				// Check for a start tag of the oml:PrayerTimetable tag
				if (eventType == XmlPullParser.START_TAG &&
						xpp.getName().equals("PrayerTimetable")) {
					Log.d("PTF", "in PrayerTimetable tag");
					eventType = xpp.next();
			        
					// Process each result within the oml:PrayerTimetable tag.
					while (!(eventType == XmlPullParser.END_TAG && 
							xpp.getName().equals("PrayerTimetable"))) {
						
						// Check for the name tag within the oml:Day tag.
						if (eventType == XmlPullParser.START_TAG
								&& xpp.getName().equals("Day")) {
							Log.d("PTF", "in day tag");
							
							// Process each result within the Day tag.
							while (!(eventType == XmlPullParser.END_TAG && 
									xpp.getName().equals("Day"))) {
								
								switch (eventType) {
								case XmlPullParser.START_TAG:
									// Check for the name tag within the Date tag.
									if (xpp.getName().equals("Date")) {
										pTimings = new PrayerTimings(xpp.nextText());
										break;
									}
									// Check for the name tag within the FajrStart tag.
									if (xpp.getName().equals("FajrStart")) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}		
									// Check for the name tag within the FajrJamaat tag.
									if (xpp.getName().equals("FajrJamaat")) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the Shrooq tag.
									if (xpp.getName().equals("Shrooq")) {
										pTimings.getStartTimes().add(xpp.nextText());
										pTimings.getJamaaTimes().add("");
										break;
									}
									// Check for the name tag within the DhuhrStart tag.
									if (xpp.getName().equals("DhuhrStart")) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the DhuhrJamaat tag.
									if (xpp.getName().equals("DhuhrJamaat")) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the AsrStart tag.
									if (xpp.getName().equals("AsrStart")) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the AsrJamaat tag.
									if (xpp.getName().equals("AsrJamaat")) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the MaghribStart tag.
									if (xpp.getName().equals("MaghribStart")) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the MaghribJamaat tag.
									if (xpp.getName().equals("MaghribJamaat")) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the IshaaStart tag.
									if (xpp.getName().equals("IshaaStart")) {
										pTimings.getStartTimes().add(xpp.nextText());
										break;
									}
									// Check for the name tag within the IshaaJamaat tag.
									if (xpp.getName().equals("IshaaJamaat")) {
										pTimings.getJamaaTimes().add(xpp.nextText());
										break;
									}

								default:
									break;
								}
								// Move on to the next tag.
								eventType = xpp.next();
							}
							
							
						}
						// Move on to the next tag.
						eventType = xpp.next();
					}
					// Do something with each POI name.
				}
				// Move on to the next result tag.
				eventType = xpp.next();
			}
			
		} 
		catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return pTimings;
	}

}
