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

import com.group1.app.ungdungdoctruyen.objects.RssObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Tab1 extends Fragment{
	String[] arrTypeEcomic = { "18+", "Comedy", "Horror", "Action", "Anime",
			"Trinh thám", "Truyện full", "Romance", "VN comic", "School life",
			"Super Natural", "Người lớn" };
	
	ListView lvTypeEcomic;
	ArrayAdapter<String> adapter;
	public static ArrayList<RssObject> arrlData;
	
	String[] arrTitle = new String[10];
	String[] arrDate = new String[10];
	String[] arrLink = new String[10];
	String[] arrURL = new String[10];
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_tab1, container, false);
		arrlData = new ArrayList<RssObject>();
		lvTypeEcomic = (ListView) v.findViewById(R.id.lvTypeEcomic);
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrTypeEcomic);
		lvTypeEcomic.setAdapter(adapter);
		
		lvTypeEcomic.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(getActivity(), EcomicType.class);
				it.putExtra("index", arg2);
				startActivity(it);
				getActivity().overridePendingTransition(R.animator.push_up_in, R.animator.push_up_out);
			}
		});
		
		new xuLyRss().execute();
		return v;
	}
	
	public String getSrcImages(String link){
		int index1 = link.indexOf("src=\"") + 5;
		int index2 = link.indexOf("\"", index1);
		String getLink = link.substring(index1, index2);
		
		return getLink;
	}
	
	//----------------
	public class xuLyRss extends AsyncTask<Void, Void, Void> {
		String[] UriNguon = { 
		"http://blogtruyen.com/rss/16.html",
		"http://blogtruyen.com/rss/comedy.html",
		"http://blogtruyen.com/rss/horror.html",
		"http://blogtruyen.com/rss/action.html",
		"http://blogtruyen.com/rss/anime.html",
		"http://blogtruyen.com/rss/trinh-tham.html",
		"http://blogtruyen.com/rss/truyen-full.html",
		"http://blogtruyen.com/rss/romance.html",
		"http://blogtruyen.com/rss/vncomic.html",
		"http://blogtruyen.com/rss/school-life.html",
		"http://blogtruyen.com/rss/supernatural.html",
		"http://blogtruyen.com/rss/18.html" 
		};

		@Override
		protected Void doInBackground(Void... params) {
			for (int i = 0; i < UriNguon.length; i++) {
				try {
					URL url = new URL(UriNguon[i]);

					URLConnection urlConnection = url.openConnection();
					InputStream is = urlConnection.getInputStream();

					DocumentBuilderFactory dbFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(is);

					Node nChannel = doc.getElementsByTagName("channel").item(0);
					Element eItem = (Element) nChannel;
					NodeList nListItem = (eItem).getElementsByTagName("item");
					for (int j = 0; j < nListItem.getLength(); j++) {
						Node item = nListItem.item(j);
						String title = ((Element) item)
								.getElementsByTagName("title").item(0)
								.getTextContent();
						String link = ((Element) item)
								.getElementsByTagName("link").item(0)
								.getTextContent();
						String date = ((Element) item)
								.getElementsByTagName("pubDate").item(0)
								.getTextContent();
						String images = ((Element) item)
								.getElementsByTagName("description").item(0)
								.getTextContent();
						RssObject object_RSS = new RssObject(title, link,
								date, getSrcImages(images));
						arrlData.add(object_RSS);

						Log.d("RSS", "XML title : " + title);
						Log.d("RSS", "XML link : " + link);
						Log.d("RSS", "XML date : " + date);
						Log.d("RSS", "XML description : " + getSrcImages(images));
						Log.d("RSS", "XML ---------------------------------- : ");

					}
					Log.d("RSS SIZE", "Size : " + arrlData.size() + "");
				} catch (Exception e) {
					Log.d("MyError", e.toString());
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

		}

	}
}
