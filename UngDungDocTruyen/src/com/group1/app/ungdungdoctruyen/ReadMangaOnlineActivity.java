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
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.group1.app.ungdungdoctruyen.items.MangaInforItems;

public class ReadMangaOnlineActivity extends Activity {

	 ArrayList<String> arrImg;
     String link;
     int i = 0;
     float x1,x2;
     float y1, y2;
     ImageView img;
     @Override
     protected void onCreate(Bundle savedInstanceState) 
     {           
                 super.onCreate(savedInstanceState);
                 setContentView(R.layout.activity_read_manga_online);
                 img = (ImageView) findViewById(R.id.imageView1);
                 arrImg   = new ArrayList<String>();
                 Intent intent = getIntent();
                 link = intent.getStringExtra("url");
                 new DoGetRss().execute();
                
     }
    
     // onTouchEvent () method geints called when User performs any touch event on screen 
    // Method to handle touch event like left to right swap and right to left swap
                 public boolean onTouchEvent(MotionEvent touchevent) 
                 {
                              switch (touchevent.getAction())
                              {
                                     // when user first touches the screen we get x and y coordinate
                                      case MotionEvent.ACTION_DOWN: 
                                      {   
                                          x1 = touchevent.getX();
                                          y1 = touchevent.getY();
                                          break;
                                     }
                                      case MotionEvent.ACTION_UP: 
                                      {   
                                          x2 = touchevent.getX();
                                          y2 = touchevent.getY(); 

                                          //if left to right sweep event on screen
                                          if (x1 < x2) 
                                          {  if(i>0 && i<arrImg.size() ){ 
                                         	 i--;
                                              Log.d("leght",String.valueOf(arrImg.size()));
                                              Log.d("leght",String.valueOf(i));
                                              new ImageDownloaderTask(img).execute(arrImg.get(i).toString());
                                              }
                                              //Toast.makeText(this, "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
                                           }
                                         
                                          // if right to left sweep event on screen
                                          if (x1 > x2)
                                          {  if(i>=0 && i<=(arrImg.size()-2) ){ 
                                         	 i++;
                                              
                                         	 new ImageDownloaderTask(img).execute(arrImg.get(i).toString());
                                              Log.d("leght",String.valueOf(i));
                                              
                                              if(i==(arrImg.size()-1)){ 
                                             	 Toast.makeText(this, "Truyện đã đến trang cuối", Toast.LENGTH_LONG).show();
                                              }
                                             }
                                         
                                             // Toast.makeText(this, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
                                          }
                                         
                                          // if UP to Down sweep event on screen
                                          if (y1 < y2) 
                                          {
                                             // Toast.makeText(this, "UP to Down Swap Performed", Toast.LENGTH_LONG).show();
                                          }
                                         
                                          //if Down to UP sweep event on screen
                                          if (y1 > y2)
                                          {
                                              //Toast.makeText(this, "Down to UP Swap Performed", Toast.LENGTH_LONG).show();
                                           }
                                          break;
                                      }
                              }
                              return false;
                 }

                 private ArrayList<String> getListData() {
                     ArrayList<String> listMockData = new ArrayList<String>();
                     String[] images = getResources().getStringArray(R.array.images_arrays);
                    
                     for (int i = 0; i < images.length; i++) {
                        
                         listMockData.add(images[i]);
                     }
                     return listMockData;
                 }
                 class DoGetRss extends AsyncTask<Void, Void, Void>{

             		@Override
             		protected Void doInBackground(Void... params) {
             			// TODO Auto-generated method stub
             			try {
             				URL url = new URL(link);
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
                             String imgLink = ((Element) n1News).getElementsByTagName("link").item(0).getTextContent();               
                            
                             arrImg.add(imgLink);			    
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
             			 new ImageDownloaderTask(img).execute(arrImg.get(i).toString());
             		}
             		}	 	

}
