package com.ianl.podcasts;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

class SAXHelper {
	private URL url;

	public SAXHelper(String url) throws MalformedURLException {
		this.url = new URL(url);
	}

	public ArrayList<Podcast> parseContent(String parseContent) {
		PodcastRSSHandler df = new PodcastRSSHandler(new ArrayList<Podcast>());
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(df);
			xr.parse(new InputSource(url.openStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df.getAllPodcasts();
	}
	
}