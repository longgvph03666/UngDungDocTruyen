package com.group1.app.ungdungdoctruyen;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

public class ReadMangaOnlineActivity extends Activity {

	 ArrayList<String> arrImg;
     
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
                 arrImg   = getListData();
                 new ImageDownloaderTask(img).execute(arrImg.get(i).toString());
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
             	

}
