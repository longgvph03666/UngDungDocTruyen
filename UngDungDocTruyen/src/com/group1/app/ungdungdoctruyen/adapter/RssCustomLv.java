package com.group1.app.ungdungdoctruyen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.group1.app.ungdungdoctruyen.R;
import com.group1.app.ungdungdoctruyen.loadimg.ImageLoader;

public class RssCustomLv extends ArrayAdapter<String>{
	Activity context;
	String [] title;
	String [] date;
	String [] link;
	ImageLoader imageLoader;
	public RssCustomLv(Activity context, String [] title, String [] date,
			String [] link) {
		super(context, R.layout.activity_custom_lv, title);
		this.context = context;
		this.title = title;
		this.date = date;
		this.link = link;
		
		imageLoader = new ImageLoader(context.getApplicationContext());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View v = inflater.inflate(R.layout.activity_custom_lv, null, true);
		TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
		TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
		ImageView iv = (ImageView) v.findViewById(R.id.iv);
		
		tvTitle.setText(title[position]);
		tvDate.setText(date[position]);
//		new Rss_LoadImages(link[position], iv).execute();
		imageLoader.DisplayImage(link[position], iv);
		return v;
	}
	
}
