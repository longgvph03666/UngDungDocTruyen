package com.group1.app.ungdungdoctruyen;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.group1.app.ungdungdoctruyen.loadimg.ImageLoader;
import com.group1.app.ungdungdoctruyen.objects.RssObject;

@SuppressLint("ResourceAsColor")
public class Tab2 extends Fragment {
	View v;
	ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8;
	TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tvTitle1, tvTitle2;
	LinearLayout l1, l2, l3, l4, l5, l6, l7, l8, layout_tab2, ll1,ll2,ll3,ll4,ll5,ll6,ll7,ll8;
	int num1, num2, num3, num4, num5, num6, num7, num8;
	ImageLoader imageLoader;
	Random rd;
	boolean isCheckedTheme; // return true, false if choose theme
	private ProgressDialog dialog;

	public static ArrayList<RssObject> arrlData = new ArrayList<RssObject>();

	@SuppressLint("ResourceAsColor")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (MainActivity.network) {
			v = inflater.inflate(R.layout.layout_tab2, container, false);
			dialog = ProgressDialog.show(getActivity(), "", "Loading.....");
			new xuLyRss().execute();
			setView();
			setEventForTextView();
			imageLoader = new ImageLoader(getActivity().getApplicationContext());
		} else {
			v = inflater.inflate(R.layout.activity_activity_error_network,
					container, false);
		}
		
		changeTheme();
//		tvTitle1.setTextColor();
		
		return v;
	}

	public void setView() {
		iv1 = (ImageView) v.findViewById(R.id.iv_iconNews_1);
		iv2 = (ImageView) v.findViewById(R.id.iv_iconNews_2);
		iv3 = (ImageView) v.findViewById(R.id.iv_iconNews_3);
		iv4 = (ImageView) v.findViewById(R.id.iv_iconNews_4);
		iv5 = (ImageView) v.findViewById(R.id.iv_iconNews_5);
		iv6 = (ImageView) v.findViewById(R.id.iv_iconNews_6);
		iv7 = (ImageView) v.findViewById(R.id.iv_iconNews_7);
		iv8 = (ImageView) v.findViewById(R.id.iv_iconNews_8);

		tv1 = (TextView) v.findViewById(R.id.tv_titleNews_1);
		tv2 = (TextView) v.findViewById(R.id.tv_titleNews_2);
		tv3 = (TextView) v.findViewById(R.id.tv_titleNews_3);
		tv4 = (TextView) v.findViewById(R.id.tv_titleNews_4);
		tv5 = (TextView) v.findViewById(R.id.tv_titleNews_5);
		tv6 = (TextView) v.findViewById(R.id.tv_titleNews_6);
		tv7 = (TextView) v.findViewById(R.id.tv_titleNews_7);
		tv8 = (TextView) v.findViewById(R.id.tv_titleNews_8);

		l1 = (LinearLayout) v.findViewById(R.id.linear1);
		l2 = (LinearLayout) v.findViewById(R.id.linear2);
		l3 = (LinearLayout) v.findViewById(R.id.linear3);
		l4 = (LinearLayout) v.findViewById(R.id.linear4);
		l5 = (LinearLayout) v.findViewById(R.id.linear5);
		l6 = (LinearLayout) v.findViewById(R.id.linear6);
		l7 = (LinearLayout) v.findViewById(R.id.linear7);
		l8 = (LinearLayout) v.findViewById(R.id.linear8);
		
		layout_tab2 =  (LinearLayout) v.findViewById(R.id.layout_tab2);
		tvTitle1 = (TextView) v.findViewById(R.id.layout2_Title1);
		tvTitle2 = (TextView) v.findViewById(R.id.layout2_Title2);
		ll1 = (LinearLayout) v.findViewById(R.id.layout2_ll1);
		ll2 = (LinearLayout) v.findViewById(R.id.layout2_ll2);
		ll3 = (LinearLayout) v.findViewById(R.id.layout2_ll3);
		ll4 = (LinearLayout) v.findViewById(R.id.layout2_ll4);
		ll5 = (LinearLayout) v.findViewById(R.id.layout2_ll5);
		ll6 = (LinearLayout) v.findViewById(R.id.layout2_ll6);
		ll7 = (LinearLayout) v.findViewById(R.id.layout2_ll7);
		ll8 = (LinearLayout) v.findViewById(R.id.layout2_ll8);
	}

	public void setTitle() {
		getNumberRandom();
		tv1.setText(arrlData.get((num1 + 1) * 10 - 10).getTitle());
		tv2.setText(arrlData.get((num2 + 1) * 10 - 10).getTitle());
		tv3.setText(arrlData.get((num3 + 1) * 10 - 10).getTitle());
		tv4.setText(arrlData.get((num4 + 1) * 10 - 10).getTitle());

		tv5.setText(arrlData.get(num5).getTitle());
		tv6.setText(arrlData.get(num6).getTitle());
		tv7.setText(arrlData.get(num7).getTitle());
		tv8.setText(arrlData.get(num8).getTitle());
	}

	public void getNumberRandom() {
		Tab1 tab1 = new Tab1();
		rd = new Random();
		num1 = rd.nextInt(20);
		while ((num2 = rd.nextInt(20)) == num1) {
			num2 = rd.nextInt(20);
		}
		
		
		while ((num3 = rd.nextInt(20)) == num1) {
			num3 = rd.nextInt(20);
			
			while ((num3 = rd.nextInt(20)) == num2) {
				num3 = rd.nextInt(20);
			}
		}
		

		while ((num4 = rd.nextInt(20)) == num1) {
			num4 = rd.nextInt(20);
			
			while ((num4 = rd.nextInt(20)) == num2) {
				num4 = rd.nextInt(20);
			}
			while ((num4 = rd.nextInt(20)) == num3) {
				num4 = rd.nextInt(20);
			}
		}
		

		num5 = rd.nextInt(230);
		while ((num6 = rd.nextInt(230)) == num5) {
			num6 = rd.nextInt(230);
		}

		while ((num7 = rd.nextInt(230)) == num5) {
			num7 = rd.nextInt(10);
			
			while ((num7 = rd.nextInt(230)) == num6) {
				num7 = rd.nextInt(230);
			}
		}
		

		while ((num8 = rd.nextInt(230)) == num5) {
			num8 = rd.nextInt(10);
			
			while ((num8 = rd.nextInt(230)) == num6) {
				num8 = rd.nextInt(10);
			}
			while ((num8 = rd.nextInt(230)) == num7) {
				num8 = rd.nextInt(230);
			}
		}
		
	}

	public void setImages() {
		imageLoader.DisplayImage(
				arrlData.get((num1 + 1) * 10 - 10).getImages(), iv1);
		imageLoader.DisplayImage(
				arrlData.get((num2 + 1) * 10 - 10).getImages(), iv2);
		imageLoader.DisplayImage(
				arrlData.get((num3 + 1) * 10 - 10).getImages(), iv3);
		imageLoader.DisplayImage(
				arrlData.get((num4 + 1) * 10 - 10).getImages(), iv4);

		imageLoader.DisplayImage(arrlData.get(num5).getImages(), iv5);
		imageLoader.DisplayImage(arrlData.get(num6).getImages(), iv6);
		imageLoader.DisplayImage(arrlData.get(num7).getImages(), iv7);
		imageLoader.DisplayImage(arrlData.get(num8).getImages(), iv8);
	}

	public String getSrcImages(String link) {
		int index1 = link.indexOf("src=\"") + 5;
		int index2 = link.indexOf("\"", index1);
		String getLink = link.substring(index1, index2);

		return getLink;
	}

	public void setEventForTextView() {
		ll1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), RssWebView.class);
				it.putExtra("link", arrlData.get((num1 + 1) * 10 - 10)
						.getLink());
				startActivity(it);
				getActivity().overridePendingTransition(R.animator.push_up_in,
						R.animator.push_up_out);
			}
		});
		ll2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), RssWebView.class);
				it.putExtra("link", arrlData.get((num2 + 1) * 10 - 10)
						.getLink());
				startActivity(it);
				getActivity().overridePendingTransition(R.animator.push_up_in,
						R.animator.push_up_out);
			}
		});
		ll3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), RssWebView.class);
				it.putExtra("link", arrlData.get((num1 + 1) * 10 - 10)
						.getLink());
				startActivity(it);
				getActivity().overridePendingTransition(R.animator.push_up_in,
						R.animator.push_up_out);
			}
		});
		ll4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), RssWebView.class);
				it.putExtra("link", arrlData.get((num4 + 1) * 10 - 10)
						.getLink());
				startActivity(it);
				getActivity().overridePendingTransition(R.animator.push_up_in,
						R.animator.push_up_out);
			}
		});
		ll5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), RssWebView.class);
				it.putExtra("link", arrlData.get(num5).getLink());
				startActivity(it);
				getActivity().overridePendingTransition(R.animator.push_up_in,
						R.animator.push_up_out);
			}
		});
		ll6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), RssWebView.class);
				it.putExtra("link", arrlData.get(num6).getLink());
				startActivity(it);
				getActivity().overridePendingTransition(R.animator.push_up_in,
						R.animator.push_up_out);
			}
		});
		ll7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), RssWebView.class);
				it.putExtra("link", arrlData.get(num7).getLink());
				startActivity(it);
				getActivity().overridePendingTransition(R.animator.push_up_in,
						R.animator.push_up_out);
			}
		});
		ll8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), RssWebView.class);
				it.putExtra("link", arrlData.get(num8).getLink());
				startActivity(it);
				getActivity().overridePendingTransition(R.animator.push_up_in,
						R.animator.push_up_out);
			}
		});

	}

	public class xuLyRss extends AsyncTask<Void, Void, Void> {
		String[] UriNguon = { "http://blogtruyen.com/rss/16.html",
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
				"http://blogtruyen.com/rss/18.html" };

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
						RssObject object_RSS = new RssObject(title, link, date,
								getSrcImages(images));
						arrlData.add(object_RSS);

					}

				} catch (Exception e) {
					Log.d("MyError", e.toString());
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setTitle();
			setImages();
			dialog.dismiss();
		}

	}

//	public void doSetting() {
//		SharedPreferences sharedPreferences = PreferenceManager
//				.getDefaultSharedPreferences(getActivity());
//
//		if (sharedPreferences.getBoolean("theme", false)) {
//			Toast.makeText(getActivity(), "Changed...", Toast.LENGTH_SHORT).show();
//		}
//
//	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		changeTheme();
	}
	
	@SuppressLint("ResourceAsColor")
	public void changeTheme(){
		if (MainActivity.isCheckedTheme) {
			layout_tab2.setBackgroundResource(R.color.blackBold);
			tvTitle1.setTextColor(R.color.blueLight);
			tvTitle1.setTextColor(R.color.blueLight);
			ll1.setBackgroundResource(R.drawable.custom_button2);
			ll2.setBackgroundResource(R.drawable.custom_button2);
			ll3.setBackgroundResource(R.drawable.custom_button2);
			ll4.setBackgroundResource(R.drawable.custom_button2);
			ll5.setBackgroundResource(R.drawable.custom_button2);
			ll6.setBackgroundResource(R.drawable.custom_button2);
			ll7.setBackgroundResource(R.drawable.custom_button2);
			ll8.setBackgroundResource(R.drawable.custom_button2);
			tvTitle1.setTextColor(this.getResources().getColor(R.color.white));
			tvTitle2.setTextColor(this.getResources().getColor(R.color.white));
			tv1.setTextColor(this.getResources().getColor(R.color.blueLight));
			tv2.setTextColor(this.getResources().getColor(R.color.blueLight));
			tv3.setTextColor(this.getResources().getColor(R.color.blueLight));
			tv4.setTextColor(this.getResources().getColor(R.color.blueLight));
			tv5.setTextColor(this.getResources().getColor(R.color.blueLight));
			tv6.setTextColor(this.getResources().getColor(R.color.blueLight));
			tv7.setTextColor(this.getResources().getColor(R.color.blueLight));
			tv8.setTextColor(this.getResources().getColor(R.color.blueLight));
		}else{
			layout_tab2.setBackgroundResource(R.color.bgDefault);
			tvTitle1.setTextColor(this.getResources().getColor(R.color.blackBold));
			tvTitle2.setTextColor(this.getResources().getColor(R.color.blackBold));
			ll1.setBackgroundResource(R.drawable.bg_linearlayout_for_rss);
			ll2.setBackgroundResource(R.drawable.bg_linearlayout_for_rss);
			ll3.setBackgroundResource(R.drawable.bg_linearlayout_for_rss);
			ll4.setBackgroundResource(R.drawable.bg_linearlayout_for_rss);
			ll5.setBackgroundResource(R.drawable.bg_linearlayout_for_rss);
			ll6.setBackgroundResource(R.drawable.bg_linearlayout_for_rss);
			ll7.setBackgroundResource(R.drawable.bg_linearlayout_for_rss);
			ll8.setBackgroundResource(R.drawable.bg_linearlayout_for_rss);
			tv1.setTextColor(this.getResources().getColor(R.color.blackBold));
			tv2.setTextColor(this.getResources().getColor(R.color.blackBold));
			tv3.setTextColor(this.getResources().getColor(R.color.blackBold));
			tv4.setTextColor(this.getResources().getColor(R.color.blackBold));
			tv5.setTextColor(this.getResources().getColor(R.color.blackBold));
			tv6.setTextColor(this.getResources().getColor(R.color.blackBold));
			tv7.setTextColor(this.getResources().getColor(R.color.blackBold));
			tv8.setTextColor(this.getResources().getColor(R.color.blackBold));
		}
	}

}
