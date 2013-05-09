package com.ianl;

import java.util.ArrayList;

import com.ianl.R;
import com.ianl.masjidnews.NewsItem;
import com.ianl.masjidnews.NewsWebViewActivity;
import com.ianl.podcasts.PodcastCategoryList;
import com.ianl.utils.UIUpdater;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	public static final String NEWS_URL = "news_url";
	
	private TextView newsBannerTV;
	private ArrayList<NewsItem> masjidNewsHeadlineItems = new ArrayList<NewsItem>();
	private int newsHeadlinesPos = 0;
	private UIUpdater mUIUpdater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		newsBannerTV = (TextView) findViewById(R.id.news_headline);
		
		NewsItem ni1 = new NewsItem();
		ni1.setHeadline("May 2013 Timetable");
		ni1.setUrl("http://www.ianl.org.uk/9-top-level/news/60-may-2013-timetable");
		NewsItem ni2 = new NewsItem();
		ni2.setHeadline("April timetable is now available");
		ni2.setUrl("http://www.ianl.org.uk/9-top-level/news/59-april-timetable-is-now-available");
		NewsItem ni3 = new NewsItem();
		ni3.setHeadline("Mike Freer Surgery 26th April 3 - 4.30pm");
		ni3.setUrl("http://www.ianl.org.uk/9-top-level/news/54-raising-issues-with-our-local-mp");
		
		masjidNewsHeadlineItems.add(ni1);
		masjidNewsHeadlineItems.add(ni2);
		masjidNewsHeadlineItems.add(ni3);
		
		mUIUpdater = new UIUpdater(new Runnable() {
	         @Override 
	         public void run() {
	            updateNewsBannerUI();
	         }
	    });
		
	}
	
	private void updateNewsBannerUI() {
		newsBannerTV.setText(masjidNewsHeadlineItems.get(newsHeadlinesPos)
				.getHeadline());
		newsHeadlinesPos++;
		newsHeadlinesPos %= masjidNewsHeadlineItems.size();
	}
	
	public void onNewsHeadlineClick(View view) {
		Intent newsWebIntent = new Intent(this, NewsWebViewActivity.class);
		newsWebIntent.putExtra(NEWS_URL, 
				masjidNewsHeadlineItems.get(newsHeadlinesPos).getUrl());
		startActivity(newsWebIntent);
	}

	/**
	 * Bind action to clicks on buttons in the view. Method call defined
	 * in the layout xml as android:onClick="onButtonClick"
	 * @param view
	 */
	public void onButtonClick(View view) {
		switch (view.getId()) {
		case R.id.btn_places:
        	startActivity(new Intent(this, PodcastCategoryList.class));
			break;

		default:
			break;
		}
	}
	
	/**
	 * 
	 * @param view for arrow ImageViews
	 */
	public void onNewsBannerArrowClick(View view) {  
		switch (view.getId()) {
		case R.id.left_arrow:
			
			break;
			
		case R.id.right_arrow:
			
			break;
			
		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_about:
	            // app icon in action bar clicked
	        	Intent intent = new Intent(this, AboutActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Start updates
		mUIUpdater.startUpdates();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		// Stop updates
		mUIUpdater.stopUpdates();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Stop updates
		mUIUpdater.stopUpdates();
		mUIUpdater = null;
	}

} // End Activity