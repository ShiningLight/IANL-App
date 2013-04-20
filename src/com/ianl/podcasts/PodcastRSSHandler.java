package com.ianl.podcasts;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class PodcastRSSHandler extends DefaultHandler {
	private ArrayList<Podcast> allPodcasts;
	private Podcast currentPodcast = new Podcast();
	private StringBuffer chars = new StringBuffer();
	
	public PodcastRSSHandler(ArrayList<Podcast> podcast) {
		allPodcasts = podcast;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) {
		chars = new StringBuffer();
		if (localName.equalsIgnoreCase("item")) {

		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		//TODO: Change strings with that from stringx.xml
		// CHECK TYPE OF PODCAST e.g khutba etc. before storing
		// into Podcast object
		// -> can create local Podcast object and only
		// set to currentPodcast is valid podcast type
		if (localName.equalsIgnoreCase("title")
				&& currentPodcast.getTitle() == null) {
			currentPodcast.setTitle(chars.toString());

		}
		if (localName.equalsIgnoreCase("author")
				&& currentPodcast.getArtist() == null) {
			currentPodcast.setArtist(chars.toString());

		}
		if (localName.equalsIgnoreCase("duration")
				&& currentPodcast.getDuration() == null) {
			currentPodcast.setDuration(chars.toString());
		}

		if (localName.equalsIgnoreCase("item")) {
			allPodcasts.add(currentPodcast);
			currentPodcast = new Podcast();
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
