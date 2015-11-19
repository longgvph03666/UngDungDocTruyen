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

import android.os.AsyncTask;
import android.util.Log;
public class RssLoadData extends AsyncTask<Void, Void, Void>{
	public static ArrayList<RssObject> arrlData = new ArrayList<RssObject>();
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
	protected Void doInBackground(Void... arg0) {
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

				}
			} catch (Exception e) {
				Log.d("MyError", e.toString());
			}
		}
		return null;
	}
	public String getSrcImages(String link){
		int index1 = link.indexOf("src=\"") + 5;
		int index2 = link.indexOf("\"", index1);
		String getLink = link.substring(index1, index2);
		
		return getLink;
	}
}
