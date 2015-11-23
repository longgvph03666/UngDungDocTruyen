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

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.group1.app.ungdungdoctruyen.adapter.ListMangaAdapter;
import com.group1.app.ungdungdoctruyen.items.ListMangaItems;


public class Tab3 extends Fragment {
	public static EditText edtSearch;
	ImageView ivDelete;
	ListMangaAdapter adapter; 
	 ListView listView;
	 ArrayList<ListMangaItems> arrayMangas ;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_tab3, container, false);
		// final ArrayList<ListMangaItems> listData = getListData();
    
	     listView   = (ListView) v.findViewById(R.id.custom_list);
	        edtSearch = (EditText) v.findViewById(R.id.edtsearch);
	        ivDelete = (ImageView) v.findViewById(R.id.ivDelete);
	      arrayMangas  = new ArrayList<ListMangaItems>();
	        new DoGetRss().execute();
	        
	        listView.setOnItemClickListener(new OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
	            	
	                ListMangaItems newsData = (ListMangaItems) listView.getItemAtPosition(position);
	               
	                Intent intent = new Intent(getActivity(),MangaInforActivity.class);
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
		return v;
	}
	
	class DoGetRss extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				arrayMangas.clear();
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
			
			adapter = new ListMangaAdapter(getActivity(), arrayMangas);
	        listView.setAdapter(adapter);
		}
		}	
	
	
	
	  
}
