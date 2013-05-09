package com.ianl.podcasts;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.ianl.podcasts.PodcastCategoryList.PodcastsCategories;

import android.util.Log;

/**
 * Class to handle SAX tags
 */
class PodcastRSSHandler extends DefaultHandler {
	PodcastsCategories mPodcastCategory; //Podcast type to extract
	/* List of all items parsed */
	private ArrayList<Podcast> mAllPodcasts;
	/* We have a local reference to an object which is constructed while parser
	 * is working on an item tag. Used to reference item while parsing */	
	private Podcast mCurrentPodcast = new Podcast();
	/* Flag to check if the first item has been encountered, to avoid
	 * taking tags from channel info tags with the same tags e.g. title */
	private boolean mFirstItemEntered = false;
	private StringBuffer mChars = new StringBuffer();
	
	public PodcastRSSHandler(PodcastsCategories podcastCategory) {
		mPodcastCategory = podcastCategory;
		mAllPodcasts = new ArrayList<Podcast>();
	}

	/* 
	 * Called on every opening XML node (e.g. <item>). We create an empty
	 * Podcast object when an 'item' start tag is being processed.
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) {
		// Reset the chars StringBuffer to be sure that the text we retrieve
		// is always only from our current simple element
		mChars = new StringBuffer();
		if (localName.equalsIgnoreCase("channel")) {
			Log.d("PRH", "start channel");
		}
		if (localName.equalsIgnoreCase("item")) {
			Log.d("PRH", "start item");
			mFirstItemEntered = true;
			mCurrentPodcast = new Podcast();
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
		//TODO: Change strings with that from stringx.xml -cannot as no context
		// CHECK TYPE OF PODCAST e.g khutbah etc. before storing
		// into Podcast object
		// -> can create local Podcast object and only
		// set to currentPodcast is valid podcast type
		if (localName.equalsIgnoreCase("channel")) {
			Log.d("PRH", "end channel");
		}
		if (mFirstItemEntered) {
			if (localName.equalsIgnoreCase("title")
					&& mCurrentPodcast.getTitle() == null) {
				mCurrentPodcast.setTitle(mChars.toString());
				Log.d("PRH", "end title");
			}
			else if (localName.equalsIgnoreCase("author")
					&& mCurrentPodcast.getArtist() == null) {
				mCurrentPodcast.setArtist(mChars.toString());
			}
			else if (localName.equalsIgnoreCase("duration")
					&& mCurrentPodcast.getDuration() == null) {
				mCurrentPodcast.setDuration(mChars.toString());
			}	
			else if (localName.equalsIgnoreCase("item")) {
				//if (mCurrentPodcast.getType(mPodcastCategory)) {
				//Log.d("PRH", "end item - podcast added");
				mAllPodcasts.add(mCurrentPodcast);
				//}
				mCurrentPodcast = null;
				Log.d("PRH", "end item");
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

	public ArrayList<Podcast> getAllPodcasts() {
		return mAllPodcasts;
	}
	
}