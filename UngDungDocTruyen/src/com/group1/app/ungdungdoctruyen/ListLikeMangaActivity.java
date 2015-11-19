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

import com.group1.app.ungdungdoctruyen.Tab3.DoGetRss;
import com.group1.app.ungdungdoctruyen.adapter.ListMangaAdapter;
import com.group1.app.ungdungdoctruyen.items.ListMangaItems;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListLikeMangaActivity extends Activity {

	EditText edtSearch;
	ImageView ivDelete;
	ListMangaAdapter adapter; 
	 ListView listView;
	 ArrayList<ListMangaItems> arrayMangas = new ArrayList<ListMangaItems>();
    SQLiteDatabase database;
    int i=0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//View v = inflater.inflate(R.layout.activity_tab3, container, false);
		// final ArrayList<ListMangaItems> listData = getListData();
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_list_like_manga);
	     listView   = (ListView) findViewById(R.id.custom_list);
	        edtSearch = (EditText) findViewById(R.id.edtsearch);
	        ivDelete = (ImageView) findViewById(R.id.ivDelete);
	        database = openOrCreateDatabase("mydata.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);
	        Cursor c = database.query("tblLike",null,null,null,null,null,null);
	        c.moveToFirst();
	        while(!c.isAfterLast()){
	        	
	        	ListMangaItems item = new ListMangaItems();
	        	item.setHeadline(c.getString(0));
	        	item.setUrl(c.getString(1));
	        	item.setAuthor("Tác giả: " + c.getString(2));
	        	item.setReporterName("Thể loại: " + c.getString(3));
	        	item.setPosition(Integer.parseInt(c.getString(4)));
	        	arrayMangas.add(item);
	        c.moveToNext();
	        }
	        c.close();
	    	adapter = new ListMangaAdapter(ListLikeMangaActivity.this, arrayMangas);
	        listView.setAdapter(adapter);
	      //  new DoGetRss().execute();
	        listView.setOnItemClickListener(new OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	            	
	                ListMangaItems newsData = (ListMangaItems) listView.getItemAtPosition(position);
	               
	                Intent intent = new Intent(ListLikeMangaActivity.this,MangaInforActivity.class);
	                intent.putExtra("position",arrayMangas.get(position).getPosition());
	                startActivity(intent);
	            }
	        });
		
	        edtSearch.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					
					adapter.filter(s.toString());
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				}
			});
	        
	        ivDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					edtSearch.setText("");
				}
			});
		
	}
	
	class DoGetRss extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				URL url = new URL("http://levietan.5gbfree.com/list_manga.xml");
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
                ListMangaItems item = new ListMangaItems(tittle,types,img, authors,i);               
                arrayMangas.add(item);			    
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
			
					
		}
		}	
	
	
	  
}
