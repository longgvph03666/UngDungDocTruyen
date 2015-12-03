package com.group1.app.ungdungdoctruyen.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.group1.app.ungdungdoctruyen.MainActivity;
import com.group1.app.ungdungdoctruyen.R;
import com.group1.app.ungdungdoctruyen.items.ListMangaItems;
import com.group1.app.ungdungdoctruyen.loadimg.ImageLoader;

@SuppressLint("ResourceAsColor")
public class ListMangaAdapter extends BaseAdapter {
    private ArrayList<ListMangaItems> listData;
    private List<ListMangaItems> mangaList = null;
    private LayoutInflater layoutInflater;
    private Activity activity;
    public ImageLoader imageLoader; 
    
    public ListMangaAdapter(Context context, List<ListMangaItems> mangaList) {
        this.mangaList = mangaList;
        layoutInflater = LayoutInflater.from(context);
        this.listData = new ArrayList<ListMangaItems>();
        this.listData.addAll(mangaList);
        imageLoader=new ImageLoader(context);
        
    }

    @Override
    public int getCount() {
        return mangaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mangaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
	public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_manga, null);
            holder = new ViewHolder();
            holder.headlineView = (TextView) convertView.findViewById(R.id.title);
            holder.reporterNameView = (TextView) convertView.findViewById(R.id.reporter);
            holder.reportedDateView = (TextView) convertView.findViewById(R.id.date);
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbImage);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListMangaItems newsItem = mangaList.get(position);
        holder.headlineView.setText(newsItem.getHeadline());
        holder.reporterNameView.setText( newsItem.getReporterName());
        holder.reportedDateView.setText(newsItem.getDate());
        holder.author.setText(newsItem.getAuthor());
        imageLoader.DisplayImage(newsItem.getUrl(),holder.imageView);
        
//        if (MainActivity.isCheckedTheme) {
//        	holder.headlineView.setTextColor(R.color.blueLight);
//        	holder.reporterNameView.setTextColor(R.color.white);
//        	holder.reportedDateView.setTextColor(R.color.white);
//        	holder.author.setTextColor(R.color.white);
//		}else{
//			holder.headlineView.setTextColor(R.color.blackBold);
//        	holder.reporterNameView.setTextColor(R.color.blackLight);
//        	holder.reportedDateView.setTextColor(R.color.blackLight);
//        	holder.author.setTextColor(R.color.blackLight);
//		}
        return convertView;
    }

    static class ViewHolder {
        TextView headlineView;
        TextView reporterNameView;
        TextView reportedDateView;
        TextView author;
        ImageView imageView;
    }
    
    public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		mangaList.clear();
		if (charText.length() == 0) {
			mangaList.addAll(listData);
		} 
		else 
		{
			for (ListMangaItems mg : listData) 
			{
				if (mg.getHeadline().toLowerCase(Locale.getDefault()).contains(charText)) 
				{
					mangaList.add(mg);
				}
			}
		}
		notifyDataSetChanged();
	}
    
    
}
