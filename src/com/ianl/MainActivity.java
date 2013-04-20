package com.ianl;

import com.ianl.R;
import com.ianl.podcasts.PodcastCategoryList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
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

} // End Activity