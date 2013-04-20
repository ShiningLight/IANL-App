package com.ianl.podcasts;

import java.util.ArrayList;

import com.ianl.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EfficientAdapter extends BaseAdapter {
	
	public static class ViewHolder {
		public TextView title;
		public TextView artist;
		public TextView duration;
	}
	
	private static LayoutInflater inflater = null;
	
	private Activity activity;
	private ArrayList<Podcast> data;
	private ViewHolder holder;

	public EfficientAdapter(Activity a, ArrayList<Podcast> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		//return data.toArray().length;
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null) {
			vi = inflater.inflate(R.layout.podcast_playable_list_row, null);
			holder = new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.title);
			holder.artist = (TextView) vi.findViewById(R.id.artist);
			holder.duration = (TextView) vi.findViewById(R.id.duration);
			vi.setTag(holder);
		} else {
			holder = (ViewHolder) vi.getTag();
		}
		holder.title.setText(data.get(position).getTitle());
		holder.artist.setText(data.get(position).getArtist());
		holder.duration.setText(data.get(position).getDuration());
		
		return vi;
	}

}