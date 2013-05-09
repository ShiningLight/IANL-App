package com.ianl.masjidnews;

import com.ianl.MainActivity;
import com.ianl.R;
import com.ianl.utils.MyWebView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.app.Activity;

public class NewsWebViewActivity extends Activity {
	private WebView webView;
	private String newsUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		newsUrl = getIntent().getStringExtra(MainActivity.NEWS_URL);
		
		// Display the progress in the activity title bar, like the
		// browser app does. Must be called before setContentView()
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		
		webView = new MyWebView(this);
		setContentView(webView);
		
		// Makes Progress bar Visible
		getWindow().setFeatureInt( Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);		
		
		webView.loadUrl(newsUrl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.masjid_news_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.menu_refresh:
		    	webView.reload();
	            return true;
			    
	        case R.id.menu_home:
	            finish();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
}