package com.ianl.podcasts;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.ianl.R;
import com.ianl.podcasts.PodcastCategoryList.PodcastsCategories;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PodcastKhutbahList extends Activity {
	private PodcastsCategories podcastType; //podcast type e.g. halaqah, khutba
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.podcasts_main);
		//TODO:change below to be of the enum type
		podcastType = getIntent().getParcelableExtra(PodcastCategoryList.PODCAST_TYPE);
		initView();
	}

	private void initView() {
        ListView list = (ListView)findViewById(R.id.podcasts_category_list);
        
        ArrayList<Podcast> podcastList = null;
        try {//TODO: make PodcastLoadingTask take in the podcast type
        	podcastList = new PodcastLoadingTask(this, podcastType).execute(
        			getResources().getString((R.string.podcast_url_feed))).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
        list.setAdapter(new PodcastEfficientAdapter(this, podcastList));
     	// Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri
						.parse(PostList.get(position).getUrl()));
				startActivity(intent);*/

			}
		});
		
	}
	
}