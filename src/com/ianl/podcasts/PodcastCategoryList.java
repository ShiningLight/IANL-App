package com.ianl.podcasts;

import java.util.Arrays;

import com.ianl.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PodcastCategoryList extends Activity {
	protected final static String PODCAST_TYPE = "podcast_type";
	private static enum PodcastsCategories {
	    Downloaded, Khutbah, Halaqah, Other
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.podcasts_main);
		initView();
	}

	private void initView() {
		ListView listView = (ListView) findViewById(R.id.podcasts_category_list);
		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third - the Array of data
		ArrayAdapter<PodcastsCategories> adapter = 
				new ArrayAdapter<PodcastsCategories>(this, 
						android.R.layout.simple_selectable_list_item,
						Arrays.asList(PodcastsCategories.values()));

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					int position, long id) {
				final Intent podcastListPage = new Intent(getApplicationContext(), 
						PodcastKhutbahList.class);
				podcastListPage.putExtra(PODCAST_TYPE, 
						PodcastsCategories.values()[position]);
				startActivity(podcastListPage);
				/*switch (PodcastsCategories.values()[position]) {
					case Downloaded:
						
						break;
						
					case Khutbah:
						startActivity(new Intent(getApplicationContext(), 
								PodcastKhutbahList.class));
						break;
						
					case Halaqah:
						
						break;
						
					case Other:
						
						break;
						
					default:
						break;
				}*/
			}
		});
		
	} // End initView
	
} // End Activity