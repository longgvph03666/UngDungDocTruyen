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
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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
    ArrayList<MangaInforItems> arrManga;
    ArrayList<ChapterItems> arrChapter;
    ImageLoader imageLoader;
    Button btnLike;
    int position;
	String pos;
   SQLiteDatabase database;
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
		btnLike = (Button) findViewById(R.id.infoButton);
		lv = (ListView) findViewById(R.id.listChap);
	    database = openOrCreateDatabase("mydata.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		imageLoader =new ImageLoader(getBaseContext());
		
		Intent intent = getIntent();
		position = intent.getIntExtra("position",0);
		//String[] myResArray = getResources().getStringArray(R.array.chapter_array);
		
		arrManga = new ArrayList<MangaInforItems>();
		
		  new DoGetRss().execute();
		 
		arrChapter =new ArrayList<ChapterItems>();
		new DoGetChapter().execute();
		//ArrayAdapter<String > adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,myResArray);
		
		
	    lv.setOnItemClickListener(new OnItemClickListener() {
            
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					final int position, long id) {
				// TODO Auto-generated method stub
			final String	chap = arrChapter.get(position).getUrlOnline().toString();
				if(checkResume(chap.substring(chap.lastIndexOf('/') + 1))){
					
					AlertDialog.Builder b = new AlertDialog.Builder(
							MangaInforActivity.this);
					b.setTitle("Xem tiếp");
					b.setMessage("Bạn có muốn xem tiếp truyện?");
					b.setPositiveButton("Có",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
//									int n = database.delete("tbl",
//											"StudentId=?", new String[] { student.getID() });
									Cursor c = database.rawQuery("Select * from tblResume where Chap = ?",new String[] {chap.substring(chap.lastIndexOf('/') + 1)});
									c.moveToFirst();
									while(!c.isAfterLast()){
										pos = c.getString(1);
										c.moveToNext();
									}
									c.close();
									Bundle b = new Bundle();
                                    b.putString("url",arrChapter.get(position).getUrlOnline().toString());
                                    b.putString("Chap",arrManga.get(MangaInforActivity.this.position).getMangaName().toString() + " " + arrChapter.get(position).getChapter().toString());
                                    
                                    b.putString("pager",pos);
                                    database.delete("tblResume","Chap = ?",new String []{chap.substring(chap.lastIndexOf('/') + 1)});
                                    Intent intent = new Intent(MangaInforActivity.this,ReadMangaOnlineActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
								}
							});
					b.setNegativeButton("Xem từ đầu",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Bundle b = new Bundle();
                                    b.putString("url",arrChapter.get(position).getUrlOnline().toString());
                                     b.putString("Chap",arrManga.get(MangaInforActivity.this.position).getMangaName().toString() + " " + arrChapter.get(position).getChapter().toString());
                                   
                                    pos="0";
                                    b.putString("pager",pos);
                                    database.delete("tblResume","Chap = ?",new String []{chap.substring(chap.lastIndexOf('/') + 1)});
                                    Intent intent = new Intent(MangaInforActivity.this,ReadMangaOnlineActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
								}
							});
					b.show();
				
					//Toast.makeText(getBaseContext(),"bạn có muốn đọc tiếp?",Toast.LENGTH_LONG).show();
				}
				else{Bundle b = new Bundle();
                    b.putString("url",arrChapter.get(position).getUrlOnline().toString());
					   pos="0";
					   
                       b.putString("pager",pos);
                        b.putString("Chap",arrManga.get(MangaInforActivity.this.position).getMangaName().toString() + " " + arrChapter.get(position).getChapter().toString());
                       database.delete("tblResume","Chap = ?",new String []{chap.substring(chap.lastIndexOf('/') + 1)});
                       Intent intent = new Intent(MangaInforActivity.this,ReadMangaOnlineActivity.class);
                       intent.putExtras(b);
                       startActivity(intent);
				 }
			}
		});
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manga_infor, menu);
		
		return true;
	}	
	
	
	public  boolean checkName(String _username) throws SQLException {
	    int count = -1;
	    Cursor c = null; 
	    
	       String query = "SELECT * FROM tblLike WHERE Name = ?";
	       c = database.rawQuery(query, new String[] {_username});
	       if (c.moveToFirst()) {
	          count = c.getInt(0);
	          return true;
	       }
	      
	    
	   
	       else  {
	          c.close();
	         return false;
	       }
	    
	}
	public boolean checkResume(String _chap) throws SQLException {
	    int count = -1;
	    Cursor c = null; 
	    
	       String query = "SELECT * FROM tblResume WHERE Chap = ?";
	       c = database.rawQuery(query, new String[] {_chap});
	       if (c.moveToFirst()) {
	          count = c.getInt(0);
	          return true;
	       }
	      
	    
	   
	       else  {
	          c.close();
	         return false;
	       }
	    
	}

	
	class DoGetRss extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				URL url = new URL("http://levietan.5gbfree.com/list_mangainfor.xml");
				//URL url = new URL("http://gianglong7695.tk/list_mangainfor.xml");
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
			//new ImageDownloaderTask(imgCover).execute(arrManga.get(position).getUrl().toString());
			 if(checkName(arrManga.get(position).getMangaName().toString())){
				  btnLike.setText("Dislike");
			  }
			  else{
				  btnLike.setText("Like");
			  }
			   btnLike.setOnClickListener(new OnClickListener() {
					String name = arrManga.get(position).getMangaName().toString();
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 if(checkName(arrManga.get(position).getMangaName().toString())){
							  btnLike.setText("Like");
							  database.delete("tblLike","Name = ?",new String []{name} );
						  }
						  else{
							  
							  ContentValues values = new ContentValues();
							  values.put("Name",arrManga.get(position).getMangaName().toString());
							  values.put("urlImg",arrManga.get(position).getUrl().toString());
							  values.put("author",arrManga.get(position).getAuthor().toString());
							  values.put("type", arrManga.get(position).getGenre().toString());
							  values.put("position",String.valueOf(position));
							  database.insert("tblLike", null, values);
							  btnLike.setText("Dislike");
						  }
					}
				});
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
