package com.ianl.podcasts;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.ianl.R;
import com.ianl.podcasts.PodcastCategoryList.PodcastsCategories;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PodcastKhutbahList extends Activity {
	private PodcastsCategories mPodcastType; //podcast type e.g. halaqah, khutba
	private ArrayList<Podcast> mPodcastList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.podcasts_main);
		//TODO:change below to be of the enum type
		mPodcastType = getIntent().getParcelableExtra(PodcastCategoryList.PODCAST_TYPE);
		initView();
	}

	private void initView() {
        ListView list = (ListView)findViewById(R.id.podcasts_category_list);
        
        try {//TODO: make PodcastLoadingTask take in the podcast type
        	mPodcastList = new PodcastLoadingTask(this, mPodcastType).execute(
        			getResources().getString((R.string.podcast_url_feed))).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
        list.setAdapter(new PodcastEfficientAdapter(this, mPodcastList));
     	// Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri
						.parse(PostList.get(position).getUrl()));
				startActivity(intent);*/
				final Intent intent = new Intent();  
				intent.setAction(android.content.Intent.ACTION_VIEW);
				//File file = new File(YOUR_SONG_URI);
				//intent.setDataAndType(Uri.fromFile(file), "audio/*");
				intent.setDataAndType(Uri.parse(mPodcastList.get(position).getUrl()), "audio/*");  
				startActivity(intent);
			}
		});
		
	}
	
}