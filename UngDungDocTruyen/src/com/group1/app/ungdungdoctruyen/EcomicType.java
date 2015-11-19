package com.group1.app.ungdungdoctruyen;

import com.group1.app.ungdungdoctruyen.adapter.RssCustomLv;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class EcomicType extends Activity {
	BroadcastReceiver broadcastReceiver;
	boolean network = false;
	ListView lvEcomicType;
	String[] arrTypeEcomic = { "18+", "Comedy", "Horror", "Action", "Anime",
			"Trinh thám", "Truyện full", "Romance", "VN comic", "School life",
			"Super Natural", "Người lớn" };
	
	String[] arrTitle = new String[10];
	String[] arrDate = new String[10];
	String[] arrLink = new String[10];
	String[] arrURL = new String[10];
	
	RssCustomLv adapter;
	int index1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ecomic_type);
		lvEcomicType = (ListView) findViewById(R.id.lvEcomicType);
		listenNetwork();
		
		Intent it = getIntent();
		int index = it.getIntExtra("index", MODE_PRIVATE);
		this.setTitle(arrTypeEcomic[index]);
		switch (index) {
		case 0:
			index1 = 0;
			break;
		case 1:
			index1 = 20;
			break;
		case 2:
			index1 = 40;
			break;
		case 3:
			index1 = 60;
			break;
		case 4:
			index1 = 80;
			break;
		case 5:
			index1 = 100;
			break;
		case 6:
			index1 = 120;
			break;
		case 7:
			index1 = 140;
			break;
		case 8:
			index1 = 160;
			break;
		case 9:
			index1 = 180;
			break;
		case 10:
			index1 = 200;
			break;
//		case 11:
//			index1 = 220;
//			index2 = index1 + 18;
		}
		for (int i = 0; i < 10; i++) {
			arrTitle[i] = Tab1.arrlData.get(i+index1).getTitle();
			arrDate[i] = Tab1.arrlData.get(i+index1).getDate();
			arrLink[i] = Tab1.arrlData.get(i+index1).getLink();
			arrURL[i] = Tab1.arrlData.get(i+index1).getImages();
		}
		
		adapter = new RssCustomLv(EcomicType.this, arrTitle, arrDate, arrURL);
		lvEcomicType.setAdapter(adapter);
		
		lvEcomicType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(EcomicType.this, RssWebView.class);
				it.putExtra("link", arrLink[arg2]);
				startActivity(it);
				overridePendingTransition(R.animator.push_up_in, R.animator.push_up_out);
			}
		});
		
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.animator.push_down_in, R.animator.push_down_out);
	}
	
	public void listenNetwork(){
		broadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connectivityManager
						.getActiveNetworkInfo();

				if (networkInfo != null && networkInfo.isConnected()) {
					network = true;
					String netWork = "Loading ...";
					Toast.makeText(getApplicationContext(), netWork,
							Toast.LENGTH_LONG).show();
				} else {
//					AlertDialog alertDialog = new AlertDialog.Builder(
//							EcomicType.this).create();
//					alertDialog.setTitle("Thông báo");
//					alertDialog
//							.setMessage("Không có kết nối mạng. Mời bạn quay lại sau !!!");
//					alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int which) {
//									onBackPressed();
//								}
//							});
//					alertDialog.show();
					Intent it = new Intent(EcomicType.this, ActivityErrorNetwork.class);
					startActivity(it);
					overridePendingTransition(R.animator.fadein, R.animator.fadeout);
				}
			}
		};

		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(broadcastReceiver, filter);
	}

}
