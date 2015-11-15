package com.group1.app.ungdungdoctruyen;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.group1.app.ungdungdoctruyen.adapter.ChapterAdapter;
import com.group1.app.ungdungdoctruyen.items.ChapterItems;
import com.group1.app.ungdungdoctruyen.items.MangaInforItems;
import com.group1.app.ungdungdoctruyen.loadimg.ImageLoader;

public class MangaInforActivity extends Activity {
    ImageView imgCover;
    TextView tvAuthor;
    TextView tvMangaName;
    TextView tvGenre;
    TextView tvSummary;
    ListView lv;
   
	

    int position;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_manga_infor);
		
		imgCover = (ImageView) findViewById(R.id.mangaMenuCover);
		tvAuthor = (TextView) findViewById(R.id.infoArthur);
		tvGenre = (TextView) findViewById(R.id.infoGenre);
		tvMangaName = (TextView) findViewById(R.id.infoName);
		tvSummary = (TextView) findViewById(R.id.infoSummary);
		

		lv = (ListView) findViewById(R.id.listChap);
	
		ImageLoader imageLoader=new ImageLoader(getBaseContext());
		
		Intent intent = getIntent();
		position = intent.getIntExtra("position",0);
		//String[] myResArray = getResources().getStringArray(R.array.chapter_array);
		
		ArrayList<MangaInforItems> arrManga =getListData();
		
		tvAuthor.setText(arrManga.get(position).getAuthor().toString());
		tvGenre.setText(arrManga.get(position).getGenre().toString());
		tvMangaName.setText(arrManga.get(position).getMangaName().toString());
		tvSummary.setText(arrManga.get(position).getSummary().toString());
		// new ImageDownloaderTask(imgCover).execute(arrManga.get(position).getUrl());
		 imageLoader.DisplayImage(arrManga.get(position).getUrl().toString(),imgCover);
		 ArrayList<ChapterItems> arrChapter =getChapterData();
		//ArrayAdapter<String > adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,myResArray);
		ChapterAdapter adapter = new ChapterAdapter(MangaInforActivity.this,R.layout.chapter_row, arrChapter);
		
		lv.setAdapter(adapter);
		ListAdapter listAdapter = lv.getAdapter();
		int totalHeight = 0;
	    for (int i = 0, len = listAdapter.getCount(); i < len; i++) { 
	        View listItem = listAdapter.getView(i, null, lv);
	        listItem.measure(0, 0);
	        totalHeight += listItem.getMeasuredHeight(); 
	    }

	    ViewGroup.LayoutParams params = lv.getLayoutParams();
	    params.height = totalHeight
	            + (lv.getDividerHeight() * (listAdapter.getCount() - 1));
	    
	    lv.setLayoutParams(params);
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(MangaInforActivity.this,ReadMangaOnlineActivity.class);
				 startActivity(intent);
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manga_infor, menu);
		
		return true;
	}
	private ArrayList<MangaInforItems> getListData() {
        ArrayList<MangaInforItems> listMockData = new ArrayList<MangaInforItems>();
        String[] images = getResources().getStringArray(R.array.covers_array);
        String[] mangaName = getResources().getStringArray(R.array.headline_array);
        String[] authors = getResources().getStringArray(R.array.author2_array);
        String[] types = getResources().getStringArray(R.array.type2_array);
        String [] summary = getResources().getStringArray(R.array.summary_array);
        for (int i = 0; i < images.length; i++) {
            MangaInforItems newsData = new MangaInforItems();
            newsData.setUrl(images[i]);
            newsData.setMangaName(mangaName[i]);
            newsData.setAuthor(authors[i]);
            newsData.setGenre(types[i]);
            newsData.setSummary(summary[i]);
            listMockData.add(newsData);
        }
        return listMockData;
    }
	
	private ArrayList<ChapterItems> getChapterData() {
        ArrayList<ChapterItems> listMockData = new ArrayList<ChapterItems>();
        String[] myResArray = getResources().getStringArray(R.array.chapter_array);
   
        for (int i = 0; i < myResArray.length; i++) {
            ChapterItems newsData = new ChapterItems();
           newsData.setChapter(myResArray[i]);
            listMockData.add(newsData);
        }
        return listMockData;
    }
	
	

	

	
}
