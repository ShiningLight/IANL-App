package com.ianl.podcasts;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/**
 * Class to handle SAX tags
 */
class PodcastRSSHandler extends DefaultHandler {
	/* List of all items parsed */
	private ArrayList<Podcast> allPodcasts;
	/* We have a local reference to an object which is constructed while 
	 * parser is working on an item tag. Used to reference item while parsing
	 */	
	private Podcast currentPodcast = new Podcast();
	/* Flag to check if the first item has been encountered, to avoid
	 * taking tags from channel info tags with the same tags e.g. title 
	 */
	private boolean firstItemEntered = false;
	private StringBuffer chars = new StringBuffer();
	
	public PodcastRSSHandler() {
		allPodcasts = new ArrayList<Podcast>();
	}

	/* 
	 * The StartElement method creates an empty Podcast object when an
	 * 'item' start tag is being processed.
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) {
		chars = new StringBuffer();
		if (localName.equalsIgnoreCase("channel")) {
			Log.d("PRH", "start channel");
		}
		if (localName.equalsIgnoreCase("item")) {
			Log.d("PRH", "start item");
			firstItemEntered = true;
			currentPodcast = new Podcast();
		}
	}

	/*
	 * The EndElement method adds the current Podcast to the list when a
	 * closing 'item' tag is processed.
	 */	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		//TODO: Change strings with that from stringx.xml
		// CHECK TYPE OF PODCAST e.g khutba etc. before storing
		// into Podcast object
		// -> can create local Podcast object and only
		// set to currentPodcast is valid podcast type
		if (localName.equalsIgnoreCase("channel")) {
			Log.d("PRH", "end channel");
		}
		if (firstItemEntered) {
			if (localName.equalsIgnoreCase("title")
					&& currentPodcast.getTitle() == null) {
				currentPodcast.setTitle(chars.toString());
				Log.d("PRH", "end title");
			}
			else if (localName.equalsIgnoreCase("author")
					&& currentPodcast.getArtist() == null) {
				currentPodcast.setArtist(chars.toString());
			}
			else if (localName.equalsIgnoreCase("duration")
					&& currentPodcast.getDuration() == null) {
				currentPodcast.setDuration(chars.toString());
			}	
			else if (localName.equalsIgnoreCase("item")) {
				allPodcasts.add(currentPodcast);
				currentPodcast = null;
				Log.d("PRH", "end item");
			}
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		chars.append(new String(ch, start, length));
	}

	public ArrayList<Podcast> getAllPodcasts() {
		return allPodcasts;
	}
	
}