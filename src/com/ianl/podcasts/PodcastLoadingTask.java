package com.ianl.podcasts;

import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

class PodcastLoadingTask extends AsyncTask<String, Void, ArrayList<Podcast>> {	
	private Context mContext;
	private ProgressDialog dialog;
	
	public PodcastLoadingTask(Context context) {
		mContext = context;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(mContext);
	    dialog.setMessage("Loading. Please wait...");
	    dialog.setIndeterminate(true);
	    dialog.show();
		super.onPreExecute();
	}

	@Override
	protected ArrayList<Podcast> doInBackground(String... urls) {
		SAXHelper sh = null;
		try {
			sh = new SAXHelper(urls[0]);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return sh.parseContent("");
	}

	@Override
	protected void onPostExecute(ArrayList<Podcast> s) {
		if (dialog.isShowing()) {
	        dialog.dismiss();
	    }
		super.onPostExecute(s);
	}
	
}