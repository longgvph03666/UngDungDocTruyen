package com.group1.app.ungdungdoctruyen.OtherActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.group1.app.ungdungdoctruyen.MainActivity;
import com.group1.app.ungdungdoctruyen.R;

@SuppressLint("NewApi")
public class ActivityInfomation extends Activity {
	String [] arrName = {"Tính năng & Hướng dẫn", "Kiểm tra phiên bản mới", "Thông tin về nhóm lập trình", "Hỗ trợ"};
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infomation);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		lv = (ListView) findViewById(R.id.lvInfomation);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_row, arrName);
		lv.setAdapter(adapter);
		
		MainActivity.mDrawerLayout.closeDrawer(GravityCompat.START);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_infomation, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			onBackPressed();
			MainActivity.mDrawerLayout.closeDrawer(GravityCompat.START);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.animator.push_down_in, R.animator.push_down_out);
	}

}
