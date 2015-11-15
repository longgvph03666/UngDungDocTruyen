package com.group1.app.ungdungdoctruyen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EcomicType extends Activity {
	ListView lvEcomicType;
	
	String[] arrTitle = new String[10];
	String[] arrDate = new String[10];
	String[] arrLink = new String[10];
	String[] arrURL = new String[10];
	
	Rss_customlv adapter;
	int index1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ecomic_type);
		lvEcomicType = (ListView) findViewById(R.id.lvEcomicType);
		
		Intent it = getIntent();
		int index = it.getIntExtra("index", MODE_PRIVATE);
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
		
		adapter = new Rss_customlv(EcomicType.this, arrTitle, arrDate, arrURL);
		lvEcomicType.setAdapter(adapter);
		
		lvEcomicType.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent it = new Intent(EcomicType.this, Rss_Webview.class);
				it.putExtra("link", arrLink[arg2]);
				startActivity(it);
			}
		});
		
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.animator.push_down_in, R.animator.push_down_out);
	}

}
