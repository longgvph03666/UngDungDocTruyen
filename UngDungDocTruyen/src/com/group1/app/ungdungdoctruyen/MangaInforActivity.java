package com.group1.app.ungdungdoctruyen;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.group1.app.ungdungdoctruyen.Tab3.DoGetRss;
import com.group1.app.ungdungdoctruyen.adapter.ChapterAdapter;
import com.group1.app.ungdungdoctruyen.adapter.ListMangaAdapter;
import com.group1.app.ungdungdoctruyen.items.ChapterItems;
import com.group1.app.ungdungdoctruyen.items.ListMangaItems;
import com.group1.app.ungdungdoctruyen.items.MangaInforItems;
import com.group1.app.ungdungdoctruyen.loadimg.ImageLoader;

public class MangaInforActivity extends Activity {
    ImageView imgCover;
    TextView tvAuthor;
    TextView tvMangaName;
    TextView tvGenre;
    TextView tvSummary;
    ListView lv;
    ArrayList<MangaInforItems> arrManga;
    ArrayList<ChapterItems> arrChapter;
    ImageLoader imageLoader;

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
	
		imageLoader =new ImageLoader(getBaseContext());
		
		Intent intent = getIntent();
		position = intent.getIntExtra("position",0);
		//String[] myResArray = getResources().getStringArray(R.array.chapter_array);
		
		arrManga = new ArrayList<MangaInforItems>();
		
		  new DoGetRss().execute();
		// new ImageDownloaderTask(imgCover).execute(arrManga.get(position).getUrl());
		
		arrChapter =new ArrayList<ChapterItems>();
		new DoGetChapter().execute();
		//ArrayAdapter<String > adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,myResArray);
		
		
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(MangaInforActivity.this,ReadMangaOnlineActivity.class);
				 intent.putExtra("url",arrChapter.get(position).getUrlOnline());
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
	
	

	
	class DoGetRss extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				URL url = new URL("http://levietan.5gbfree.com/list_mangainfor.xml");
				URLConnection urlConnection = url.openConnection();
				InputStream iS = urlConnection.getInputStream();			
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(iS);
                doc.getDocumentElement().normalize();
                Node nNews = doc.getElementsByTagName("channel").item(0);
                Element eNews = (Element) nNews;
                NodeList nListNews = eNews.getElementsByTagName("item"); 
                
                for(int i = 0; i<nListNews.getLength();i++){
                	
                Node n1News = nListNews.item(i);        
                String tittle = ((Element) n1News).getElementsByTagName("title").item(0).getTextContent();               
                String img = ((Element) n1News).getElementsByTagName("img").item(0).getTextContent(); 
                String authors = ((Element) n1News).getElementsByTagName("authors").item(0).getTextContent(); 
                String types = ((Element) n1News).getElementsByTagName("types").item(0).getTextContent();          
                String description = ((Element) n1News).getElementsByTagName("description").item(0).getTextContent();
                String chap = ((Element) n1News).getElementsByTagName("chap").item(0).getTextContent();
                MangaInforItems item = new MangaInforItems(authors,tittle,description, types,img,chap);               
                arrManga.add(item);			    
             }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			tvAuthor.setText(arrManga.get(position).getAuthor().toString());
			tvGenre.setText(arrManga.get(position).getGenre().toString());
			tvMangaName.setText(arrManga.get(position).getMangaName().toString());
			tvSummary.setText(arrManga.get(position).getSummary().toString());
			imageLoader.DisplayImage(arrManga.get(position).getUrl().toString(),imgCover);		
		}
		}	
	class DoGetChapter extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				URL url = new URL(arrManga.get(position).getChapter());
				URLConnection urlConnection = url.openConnection();
				InputStream iS = urlConnection.getInputStream();			
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(iS);
                doc.getDocumentElement().normalize();
                Node nNews = doc.getElementsByTagName("channel").item(0);
                Element eNews = (Element) nNews;
                NodeList nListNews = eNews.getElementsByTagName("item"); 
                
                for(int i = 0; i<nListNews.getLength();i++){
                	
                Node n1News = nListNews.item(i);        
                String tittle = ((Element) n1News).getElementsByTagName("title").item(0).getTextContent();               
                String urlOnl = ((Element) n1News).getElementsByTagName("urlOnl").item(0).getTextContent(); 
                String urlDown = ((Element) n1News).getElementsByTagName("urlDown").item(0).getTextContent(); 
                ChapterItems item = new ChapterItems(tittle,urlOnl,urlDown);               
                arrChapter.add(item);			    
             }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
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
		}
		}	
}
